package com.onwing.socket.client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onwing.household.comm.dal.dao.CamaraMapper;
import com.onwing.household.comm.dal.model.Camara;
import com.onwing.household.comm.dal.model.Control;
import com.onwing.household.util.OutputFormatter;

public class QueryCardRecordsThread extends Thread {
	private final static Logger logger = LoggerFactory.getLogger(QueryCardRecordsThread.class);
	private Socket socket = null;
	private InputStream input;
	private OutputStream out;

	private CamaraMapper camaraMapper;
	private Map<String, String> lockControlProperties;
	private InitCameraLock initCameraLock;
	private Control control;

	// 当前控制器中刷卡记录总数
	private String currentRecordCount;

	public QueryCardRecordsThread(CamaraMapper camaraMapper, Map<String, String> lockControlProperties,
			InitCameraLock initCameraLock, Control control) {
		this.camaraMapper = camaraMapper;
		this.lockControlProperties = lockControlProperties;
		this.initCameraLock = initCameraLock;
		this.control = control;
		getInitialRecordCount();
	}

	public boolean isConnected() {
		try {
			socket.sendUrgentData(0xFF);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	public void connect() throws Exception {
		// 创建一个流套接字并将其连接到指定主机上的指定端口号
		if (socket == null || !isConnected()) {
			logger.info("re-connect to lock control socket server");
			socket = new Socket();
			socket.connect(new InetSocketAddress(control.getIp(), Integer.parseInt(control.getPort())), 2000);// 设置连接请求超时时间2
			// s
		}

		// 读取服务器端数据
		input = socket.getInputStream();
		// 向服务器端发送数据
		out = socket.getOutputStream();
	}

	// 获取最初的刷卡记录 数目
	public void getInitialRecordCount() {
		try {
			connect();
			currentRecordCount = getRecordCount();
		} catch (Exception e) {
			logger.error("getInitialRecordCount from lock control error", e);
		}
	}

	// 获取刷卡记录数目
	public String getRecordCount() throws Exception {
		byte[] cmd = new byte[256];
		cmd[0] = (int) 'K';
		cmd[1] = (int) '0';
		cmd[2] = (int) '0';
		cmd[3] = (int) '2';

		cmd[4] = (int) 'G';
		cmd[5] = (int) '4';
		cmd[6] = (int) '2';

		cmd[7] = (int) '0';
		cmd[8] = (int) '0';

		// cmd[9] = (int) '0';
		// cmd[10] = (int) '1';
		// 异或校验
		int j = cmd[0];
		for (int i = 1; i <= 8; i++) {
			j = j ^ cmd[i];
		}
		char a = Integer.toHexString(j / 16).toUpperCase().charAt(0);
		char b = Integer.toHexString(j % 16).toUpperCase().charAt(0);
		cmd[9] = (byte) a;
		cmd[10] = (byte) b;
		cmd[11] = 13;

		// send command
		out.write(cmd);
		// receive data from lock control
		byte[] result = new byte[256];
		input.read(result);
		// [75, 48, 48, 50, 71, 52, 50, 51, 50, 83, 85, 48, 48, 48, 48, 49,
		// 66, 85, 48, 48, 48, 48, 83, 66, 48, 48, 48, 48, 82, 68, 48, 48,
		// 48, 50, 48, 82, 70, 48, 48, 48, 48, 51, 56, 13]
		String result2 = OutputFormatter.nullTerminatedCharsToString(result);
		int recordIndex = result2.indexOf("RD") + 2;
		String recordCount = result2.substring(recordIndex, recordIndex + 5);
		return recordCount;
	}

	// 获取最新一条刷卡记录
	public void getOneRecord(String recordIndex) throws Exception {
		byte[] cmd = new byte[256];
		cmd[0] = (int) 'K';
		cmd[1] = (int) '0';
		cmd[2] = (int) '0';
		cmd[3] = (int) '2';

		cmd[4] = (int) 'G';
		cmd[5] = (int) '4';
		cmd[6] = (int) '5';

		cmd[7] = (int) '0';
		cmd[8] = (int) '5';
		// 将00026(即recordIndex)放入cmd中
		int count = 0;
		for (byte b : recordIndex.getBytes()) {
			count++;
			cmd[8 + count] = b;
		}

		// 异或校验
		int j = cmd[0];
		for (int i = 1; i <= 8 + count; i++) {
			j = j ^ cmd[i];
		}
		char a = Integer.toHexString(j / 16).toUpperCase().charAt(0);
		char b = Integer.toHexString(j % 16).toUpperCase().charAt(0);
		cmd[8 + count + 1] = (byte) a;
		cmd[8 + count + 2] = (byte) b;
		cmd[8 + count + 3] = 13;

		// send command
		out.write(cmd);
		// receive data from lock control
		byte[] result = new byte[256];
		input.read(result);
		String result2 = OutputFormatter.nullTerminatedCharsToString(result);
		// result2:K002G4529K00202118010317502027863254767E
		// 消息主体：K
		// 002(控制器编号)02(读头编号)1(代表当前读头继电器开启关闭状态)180103175020(时间)2786325476(10位卡号)
		String msgBody = result2.substring(9);
		logger.info("record msgBody is {}", msgBody);
		String readHeadNumber = msgBody.substring(4, 6);
		String cardNumber = msgBody.substring(msgBody.length() - 13, msgBody.length() - 3);
		System.out.println(readHeadNumber);// 根据读头判断进出方向
		System.out.println(cardNumber);// 根据卡号判断是何人，访客和白名单 卡号和身份证都是一一绑定的
		// 打开摄像头锁
		String in_out = lockControlProperties.get(control.getName() + "-" + readHeadNumber);
		if (in_out == null || in_out.isEmpty()) {
			logger.error("in_out from lockControlProperties is null");
			return;
		}

		Camara camara = new Camara();
		camara.setControlId(control.getId());
		camara.setDirection(in_out);
		List<Camara> cameraList = camaraMapper.selectBySelective(camara);
		if (cameraList == null || cameraList.size() != 1) {
			logger.error("get camera from DB with controlId {} and direction {} return not only", control.getId(),
					in_out);
			return;
		}
		initCameraLock.cameraLockMap.put(cameraList.get(0).getName(), cardNumber + ":" + in_out);
	}

	@Override
	public void run() {
		// 每隔1000ms向lock control查询刷卡记录，一旦有变化，则触发白名单拍照功能
		// 如果是500ms，控制器则会出错
		while (true) {
			try {
				connect();
				String recordCount = getRecordCount();
				if (!currentRecordCount.equals(recordCount)) {
					// TODO 有新的刷卡记录，获取最新一条刷卡记录
					getOneRecord(recordCount);
					// 如果是陌生人卡号，跳过，如果是白名单卡号，则保留摄像头拍的照片
					currentRecordCount = recordCount;
				}
			} catch (Exception e) {
				logger.error("getRecordCount from lock control error", e);
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error("", e);
			}
		}
	}

}
