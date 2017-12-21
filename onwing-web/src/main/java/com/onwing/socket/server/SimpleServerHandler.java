package com.onwing.socket.server;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onwing.household.biz.logic.core.AccessRecordBiz;
import com.onwing.household.biz.logic.core.DoorLockBiz;
import com.onwing.household.biz.logic.core.impl.DoorLockMap;
import com.onwing.household.biz.logic.core.impl.DoorLockimpl;
import com.onwing.household.comm.dal.dao.HouseHoldMapper;
import com.onwing.household.comm.dal.model.AccessRecord;
import com.onwing.household.comm.dal.model.HouseHold;
import com.onwing.household.message.FaceRecognitionMsg;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SimpleServerHandler extends ChannelInboundHandlerAdapter {
	private Map<String, String> lockControlProperties;
	private DoorLockMap doorLockMap;
	private AccessRecordBiz accessRecordBiz;
	private HouseHoldMapper householdMapper;

	private Map<String, Date> personEnterTimeMap = new HashMap<String, Date>();

	public SimpleServerHandler(Map<String, String> lockControlProperties, DoorLockMap doorLockMap,
			AccessRecordBiz accessRecordBiz, HouseHoldMapper householdMapper) {
		this.lockControlProperties = lockControlProperties;
		this.doorLockMap = doorLockMap;
		this.accessRecordBiz = accessRecordBiz;
		this.householdMapper = householdMapper;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// System.out.println("complete json msg received");
		String result = (String) msg;

		// 接收并打印客户端的信息
		// String remote = ctx.channel().remoteAddress().toString();
		// System.out.println(remote + " said:" + result);

		ObjectMapper mapper = new ObjectMapper();
		FaceRecognitionMsg faceRecognitionMsg = mapper.readValue(result, FaceRecognitionMsg.class);
		System.out.println(result);
		String photoName = faceRecognitionMsg.getPhotoName();// 11-1131-俞小洋
		Date enterTime = personEnterTimeMap.get(photoName); // 上一次成功进入时间
		Date currentTime = new Date();
		long timeDeltaInSecond = (currentTime.getTime() - enterTime.getTime()) / 1000L;
		if (enterTime != null
				&& (timeDeltaInSecond < Integer.parseInt(lockControlProperties.get("timeDeltaInSecond")))) { // 住户连续刷脸，时间间隔小于设定值，则忽略，并不开门
			return;
		}

		// open lock
		String lockControlId = "0"; // 0号 锁控制器
		DoorLockBiz doorLockimpl = doorLockMap.getLockSocketMap().get(lockControlId);
		if (doorLockimpl == null) {
			doorLockimpl = new DoorLockimpl();
			doorLockMap.getLockSocketMap().put(lockControlId, doorLockimpl);
		}

		String connectInfo = lockControlProperties.get(lockControlId);
		String[] connectInfoList = connectInfo.split(":");
		doorLockimpl.connect(connectInfoList[0], Integer.parseInt(connectInfoList[1]));
		doorLockimpl.openBigDoorLock();
		//延时若干秒关闭大门
		Thread.sleep(1000 * Integer.parseInt(lockControlProperties.get("closeDelayInSecond")));
		doorLockimpl.closeBigDoorLock();
		// end

		// 记录出入记录
		String[] photoKeyWords = photoName.split("-");
		String buildingBlockNumber = photoKeyWords[0];
		String roomNumber = photoKeyWords[1];
		String householdName = photoKeyWords[2];
		// 根据上述三个字段向数据库查找householdId
		HouseHold houseHold = new HouseHold();
		houseHold.setBuildingBlockNumber(buildingBlockNumber);
		houseHold.setRoomNumber(roomNumber);
		houseHold.setHouseholdName(householdName);
		List<HouseHold> houseHoldList = householdMapper.selectBySelective(houseHold);
		if (houseHoldList.size() != 1) {
			// 日志记录错误，查找无人或不止一个人
		} else {
			HouseHold selHouseHold = houseHoldList.get(0);
			AccessRecord accessRecord = new AccessRecord();
			accessRecord.setHouseholdId(selHouseHold.getId());
			accessRecord.setOutOff("0");
			accessRecord.setOutOffTime(new Date());
			accessRecordBiz.addAccessRecord(accessRecord);
		}
		// end

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
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
}
