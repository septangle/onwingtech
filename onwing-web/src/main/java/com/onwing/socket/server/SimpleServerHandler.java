package com.onwing.socket.server;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onwing.household.biz.logic.core.AccessRecordBiz;
import com.onwing.household.biz.logic.core.DoorLockBiz;
import com.onwing.household.biz.logic.core.impl.DoorLockMap;
import com.onwing.household.biz.logic.core.impl.DoorLockimpl;
import com.onwing.household.comm.dal.dao.ControlMapper;
import com.onwing.household.comm.dal.dao.HouseHoldMapper;
import com.onwing.household.comm.dal.dao.StrangerAccessRecordMapper;
import com.onwing.household.comm.dal.dao.StrangerMapper;
import com.onwing.household.comm.dal.model.AccessRecord;
import com.onwing.household.comm.dal.model.HouseHold;
import com.onwing.household.comm.dal.model.Stranger;
import com.onwing.household.comm.dal.model.StrangerAccessRecord;
import com.onwing.household.message.FaceRecognitionMsg;
import com.onwing.socket.client.InitCameraLock;
import com.onwing.socket.server.receive.CameraRecognitionRequest;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SimpleServerHandler extends ChannelInboundHandlerAdapter {
	private final static Logger logger = LoggerFactory.getLogger(SimpleServerHandler.class);
	private Map<String, String> lockControlProperties;
	private DoorLockMap doorLockMap;
	private AccessRecordBiz accessRecordBiz;
	private StrangerAccessRecordMapper strangerAccessRecordMapper;
	private HouseHoldMapper householdMapper;
	private StrangerMapper strangerMapper;
	private InitCameraLock initCameraLock;
	private ControlMapper controlMapper;

	private Map<String, Date> personEnterTimeMap = new HashMap<String, Date>();

	public SimpleServerHandler(InitCameraLock initCameraLock, Map<String, String> lockControlProperties,
			DoorLockMap doorLockMap, AccessRecordBiz accessRecordBiz,
			StrangerAccessRecordMapper strangerAccessRecordMapper, HouseHoldMapper householdMapper,
			StrangerMapper strangerMapper, ControlMapper controlMapper) {
		this.initCameraLock = initCameraLock;
		this.lockControlProperties = lockControlProperties;
		this.doorLockMap = doorLockMap;
		this.accessRecordBiz = accessRecordBiz;
		this.strangerAccessRecordMapper = strangerAccessRecordMapper;
		this.householdMapper = householdMapper;
		this.strangerMapper = strangerMapper;
		this.controlMapper = controlMapper;
	}

	private void savePhoto(String photoFullPath, byte[] photoBytes) {
		try {
			FileOutputStream fos = new FileOutputStream(photoFullPath);
			fos.write(photoBytes);
			fos.close();
		} catch (Exception e) {
			logger.error("savePhoto error", e);
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		CameraRecognitionRequest request = (CameraRecognitionRequest) msg;
		String cameraName = request.getCameraId();
		String photoName = request.getPhotoName();
		String time = request.getTime(); // 2018-01-04 13:16:54
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy_MM_dd-HH_mm_ss");
		Date timeDate = sdf.parse(time);
		request.getPhotoSize();
		byte[] photoBytes = request.getPhoto();
		// 识别结果是否陌生人
		if (photoName.equals("0-0-0")) { // 陌生人
			String cardInfo = initCameraLock.cameraLockMap.get(cameraName);
			String[] cardInfoSplitList = cardInfo.split(":");
			String cardNumber = cardInfoSplitList[0];
			String direction = cardInfoSplitList[1];
			if (cardNumber != null) { // 保存图片，记录出入记录
				HouseHold household = new HouseHold();
				household.setCardNumber(cardNumber);
				List<HouseHold> householdList = householdMapper.selectBySelective(household);
				if (householdList != null && householdList.size() == 1) { // 白名单刷卡
					// 保存抓拍图片到白名单目录
					String identifyCard = householdList.get(0).getIdentifyCard();
					if (identifyCard == null || identifyCard.isEmpty()) {
						logger.error("identifyCard is null with cardNumber {}", cardNumber);
						return;
					}
					String pathPrefix = System.getProperty("onwing.root") + "accessRecord/household/" + identifyCard;
					File pathPrefixFile = new File(pathPrefix);

					if (!pathPrefixFile.exists()) {
						pathPrefixFile.mkdirs();
					}
					String photoFullPath = pathPrefix + "/" + sdf2.format(timeDate) + ".jpg";
					savePhoto(photoFullPath, photoBytes);
					// end
					// 记录到白名单出入记录
					AccessRecord accessRecord = new AccessRecord();
					accessRecord.setHouseholdId(householdList.get(0).getId());
					if (direction.equals("IN")) {// 0 入 1出
						accessRecord.setOutOff("0");
					} else {
						accessRecord.setOutOff("1");
					}
					Date outOffTime = sdf.parse(time);
					accessRecord.setOutOffTime(outOffTime);
					String photoUrl = "accessRecord/household/" + identifyCard + "/" + sdf2.format(timeDate) + ".jpg";
					accessRecord.setPhotoUrl(photoUrl);
					accessRecordBiz.addAccessRecord(accessRecord);
					// end
					// 重新上锁
					initCameraLock.cameraLockMap.put(cameraName, null);
					return;
				}
				// 是否访客刷卡
				Stranger stranger = new Stranger();
				stranger.setCardNumber(cardNumber);
				List<Stranger> strangerList = strangerMapper.selectBySelective(stranger);
				if (strangerList != null && strangerList.size() == 1) { // 访客刷卡
					// 保存抓拍图片到访客目录
					String identifyCard = strangerList.get(0).getIdentifyCard();
					if (identifyCard == null || identifyCard.isEmpty()) {
						logger.error("identifyCard is null with cardNumber {}", cardNumber);
						return;
					}
					String pathPrefix = System.getProperty("onwing.root") + "accessRecord/visitor/" + identifyCard;
					File pathPrefixFile = new File(pathPrefix);

					if (!pathPrefixFile.exists()) {
						pathPrefixFile.mkdirs();
					}
					String photoFullPath = pathPrefix + "/" + sdf2.format(timeDate) + ".jpg";
					savePhoto(photoFullPath, photoBytes);
					// end
					// 记录到访客出入记录
					StrangerAccessRecord accessRecord = new StrangerAccessRecord();
					accessRecord.setStrangerId(strangerList.get(0).getId());
					if (direction.equals("IN")) {// 0 入 1出
						accessRecord.setOutOffInto("0");
					} else {
						accessRecord.setOutOffInto("1");
					}
					Date outOffTime = sdf.parse(time);
					accessRecord.setTime(outOffTime);
					String photoUrl = "accessRecord/visitor/" + identifyCard + "/" + sdf2.format(timeDate) + ".jpg";
					accessRecord.setPhotoUrl(photoUrl);
					strangerAccessRecordMapper.insertSelective(accessRecord);
					// end
					// 重新上锁
					initCameraLock.cameraLockMap.put(cameraName, null);
					return;
				}

			}
		} else {
			// 人脸匹配正确,即白名单

		}

		Date enterTime = personEnterTimeMap.get(photoName); // 上一次成功进入时间
		Date currentTime = sdf.parse(time);
		// long timeDeltaInSecond = (currentTime.getTime() -
		// enterTime.getTime()) / 1000L;
		if (enterTime != null && (((currentTime.getTime() - enterTime.getTime()) / 1000L) < Integer
				.parseInt(lockControlProperties.get("timeDeltaInSecond")))) { // 住户连续刷脸，时间间隔小于设定值，则忽略，并不开门
			return;
		}
		String[] photoNameSplitList = photoName.split(".");
		String identifyCard = photoNameSplitList[0];// 身份证号

		// save photo
		String pathPrefix = System.getProperty("onwing.root") + "accessRecord/household/" + identifyCard;
		File pathPrefixFile = new File(pathPrefix);

		if (!pathPrefixFile.exists()) {
			pathPrefixFile.mkdirs();
		}
		String photoFullPath = pathPrefix + "/" + sdf2.format(timeDate) + ".jpg";
		savePhoto(photoFullPath, photoBytes);
		// end

		// open lock
		String control_relay_map = lockControlProperties.get(cameraName);
		String[] control_relay_map_split = control_relay_map.split("-");
		String lockControlId = control_relay_map_split[0];
		String relayNumber = control_relay_map_split[1]; //继电器号 
		DoorLockBiz doorLockimpl = doorLockMap.getLockSocketMap().get(lockControlId);
		if (doorLockimpl == null) {
			doorLockimpl = new DoorLockimpl();
			doorLockMap.getLockSocketMap().put(lockControlId, doorLockimpl);
		}

		String connectInfo = lockControlProperties.get(lockControlId);
		controlMapper//根据lockControlId向数据库查找control，进而获取ip和port
		String[] connectInfoList = connectInfo.split(":");
		logger.info("start to open lock with photoName: {}", photoName);
		try {
			doorLockimpl.connect(connectInfoList[0], Integer.parseInt(connectInfoList[1]));
			doorLockimpl.openBigDoorLock();
			// 延时若干秒关闭大门
			// Thread.sleep(1000 *
			// Integer.parseInt(lockControlProperties.get("closeDelayInSecond")));
			doorLockimpl.closeBigDoorLock();
		} catch (Exception ex) {
			logger.error("cannot connect to lock control {}:{}", connectInfoList[0],
					Integer.parseInt(connectInfoList[1]), ex);
			doorLockimpl.closeSocket();
		}
		// open lock end

		// 记录出入记录
		// 根据上述三个字段向数据库查找householdId
		HouseHold houseHold = new HouseHold();
		houseHold.setBuildingBlockNumber(buildingBlockNumber);
		houseHold.setRoomNumber(roomNumber);
		houseHold.setHouseholdName(householdName);
		List<HouseHold> houseHoldList = householdMapper.selectBySelective(houseHold);
		if (houseHoldList.size() != 1) {
			// 日志记录错误，查找无人或不止一个人
			logger.error("no houseHold or not single household found in DB with photoName: {}", photoName);
		} else {
			HouseHold selHouseHold = houseHoldList.get(0);
			AccessRecord accessRecord = new AccessRecord();
			accessRecord.setHouseholdId(selHouseHold.getId());
			accessRecord.setOutOff("0");
			accessRecord.setOutOffTime(new Date());
			accessRecordBiz.addAccessRecord(accessRecord);
		}
		// 记录出入记录end

		// 更新住户进入时间
		personEnterTimeMap.put(photoName, currentTime);

	}

	public void channelRead2(ChannelHandlerContext ctx, Object msg) throws Exception {
		// logger.debug("complete json msg received");
		String result = (String) msg;

		// 接收并打印客户端的信息
		// String remote = ctx.channel().remoteAddress().toString();
		// logger.debug(remote + " said:" + result);

		ObjectMapper mapper = new ObjectMapper();
		FaceRecognitionMsg faceRecognitionMsg = mapper.readValue(result, FaceRecognitionMsg.class);
		String photoName = faceRecognitionMsg.getPhotoName();// 11-1131-俞小洋
		Date enterTime = personEnterTimeMap.get(photoName); // 上一次成功进入时间
		Date currentTime = new Date();
		// long timeDeltaInSecond = (currentTime.getTime() -
		// enterTime.getTime()) / 1000L;
		if (enterTime != null && (((currentTime.getTime() - enterTime.getTime()) / 1000L) < Integer
				.parseInt(lockControlProperties.get("timeDeltaInSecond")))) { // 住户连续刷脸，时间间隔小于设定值，则忽略，并不开门
			return;
		}
		logger.info(result);

		String[] photoKeyWords = photoName.split("-");
		String buildingBlockNumber = photoKeyWords[0];
		String roomNumber = photoKeyWords[1];
		String householdName = photoKeyWords[2];

		if (buildingBlockNumber.equals("0") && roomNumber.equals("0") && householdName.equals("0")) {
			// 判断为陌生人，do nothing
			logger.info("stranger 0-0-0 catched, do nothing");
		} else {
			// open lock
			String lockControlId = "control0"; // 0号 锁控制器
			DoorLockBiz doorLockimpl = doorLockMap.getLockSocketMap().get(lockControlId);
			if (doorLockimpl == null) {
				doorLockimpl = new DoorLockimpl();
				doorLockMap.getLockSocketMap().put(lockControlId, doorLockimpl);
			}

			String connectInfo = lockControlProperties.get(lockControlId);
			String[] connectInfoList = connectInfo.split(":");
			logger.info("start to open lock with photoName: {}", photoName);
			try {
				doorLockimpl.connect(connectInfoList[0], Integer.parseInt(connectInfoList[1]));
				doorLockimpl.openBigDoorLock();
				// 延时若干秒关闭大门
				// Thread.sleep(1000 *
				// Integer.parseInt(lockControlProperties.get("closeDelayInSecond")));
				doorLockimpl.closeBigDoorLock();
			} catch (Exception ex) {
				logger.error("cannot connect to lock control {}:{}", connectInfoList[0],
						Integer.parseInt(connectInfoList[1]), ex);
				doorLockimpl.closeSocket();
			}
			// open lock end
		}

		// 记录出入记录
		// 根据上述三个字段向数据库查找householdId
		HouseHold houseHold = new HouseHold();
		houseHold.setBuildingBlockNumber(buildingBlockNumber);
		houseHold.setRoomNumber(roomNumber);
		houseHold.setHouseholdName(householdName);
		List<HouseHold> houseHoldList = householdMapper.selectBySelective(houseHold);
		if (houseHoldList.size() != 1) {
			// 日志记录错误，查找无人或不止一个人
			logger.error("no houseHold or not single household found in DB with photoName: {}", photoName);
		} else {
			HouseHold selHouseHold = houseHoldList.get(0);
			AccessRecord accessRecord = new AccessRecord();
			accessRecord.setHouseholdId(selHouseHold.getId());
			accessRecord.setOutOff("0");
			accessRecord.setOutOffTime(new Date());
			accessRecordBiz.addAccessRecord(accessRecord);
		}
		// 记录出入记录end

		// 更新住户进入时间
		personEnterTimeMap.put(photoName, currentTime);

		// 向客户端发送消息
		/*
		 * String response = "hello client!"; ByteBuf encoded =
		 * ctx.alloc().buffer(4 * response.length());//
		 * 在当前场景下，发送的数据必须转换成ByteBuf数组 encoded.writeBytes(response.getBytes());
		 * ctx.write(encoded); ctx.flush();
		 */
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// 当出现异常就关闭连接
		logger.error("c++ socket connect exceptionCaught:", cause);
		ctx.close();
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		logger.info("c++ socket connected: {}", ctx.channel().remoteAddress().toString());
	}

}
