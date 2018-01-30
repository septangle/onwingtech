package com.onwing.household.biz.logic.core.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onwing.household.biz.logic.core.DoorLockBiz;
import com.onwing.household.comm.dal.model.Camara;
import com.onwing.household.util.OutputFormatter;

public class DoorLockimpl implements DoorLockBiz {
	private final static Logger logger = LoggerFactory.getLogger(DoorLockimpl.class);
	private Socket socket = null;
	private InputStream input;
	private OutputStream out;

	public boolean isConnected() {
		try {
			socket.sendUrgentData(0xFF);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public synchronized void connect(String ip, int port) throws Exception {
		// 创建一个流套接字并将其连接到指定主机上的指定端口号
		if (socket == null || !isConnected()) {
			logger.info("connect to lock control socket server");
			socket = new Socket();
			socket.connect(new InetSocketAddress(ip, port), 2000);// 设置连接请求超时时间2
																	// s
		}

		// 读取服务器端数据
		input = socket.getInputStream();
		// 向服务器端发送数据
		out = socket.getOutputStream();
	}

	public synchronized void closeSocket() {
		if (socket != null) {
			try {
				socket.close();
			} catch (Exception e) {
				socket = null;
				logger.error("close socket error", e);
			}
		}
	}

	@Override
	public synchronized void openBigDoorLock(String relayNumber) throws Exception {
		byte[] cmd = new byte[256];
		cmd[0] = (int) 'K';
		cmd[1] = (int) '0';
		cmd[2] = (int) '0';
		cmd[3] = (int) '2';

		cmd[4] = (int) 'G';
		cmd[5] = (int) '0';
		cmd[6] = (int) '6';

		cmd[7] = (int) '0';
		cmd[8] = (int) '2';

		cmd[9] = (byte) relayNumber.charAt(0);
		cmd[10] = (byte) relayNumber.charAt(1);
		// 异或校验
		int j = cmd[0];
		for (int i = 1; i <= 10; i++) {
			j = j ^ cmd[i];
		}
		char a = Integer.toHexString(j / 16).toUpperCase().charAt(0);
		char b = Integer.toHexString(j % 16).toUpperCase().charAt(0);
		cmd[11] = (byte) a;
		cmd[12] = (byte) b;
		cmd[13] = 13;

		// send open command
		out.write(cmd);

		// receive data from server
		byte[] result = new byte[256];
		input.read(result);

	}

	@Override
	public synchronized void closeBigDoorLock(String relayNumber) throws Exception {
		byte[] cmd = new byte[256];
		cmd[0] = (int) 'K';
		cmd[1] = (int) '0';
		cmd[2] = (int) '0';
		cmd[3] = (int) '2';

		cmd[4] = (int) 'G';
		cmd[5] = (int) '0';
		cmd[6] = (int) '8';

		cmd[7] = (int) '0';
		cmd[8] = (int) '2';

		cmd[9] = (byte) relayNumber.charAt(0);
		cmd[10] = (byte) relayNumber.charAt(1);
		// 异或校验
		int j = cmd[0];
		for (int i = 1; i <= 10; i++) {
			j = j ^ cmd[i];
		}
		char a = Integer.toHexString(j / 16).toUpperCase().charAt(0);
		char b = Integer.toHexString(j % 16).toUpperCase().charAt(0);
		cmd[11] = (byte) a;
		cmd[12] = (byte) b;
		cmd[13] = 13;

		// send open command

		out.write(cmd);

		// receive data from server
		byte[] result = new byte[256];
		input.read(result);
	}

	/**
	 * 获取刷卡记录数目
	 * 
	 * @return 刷卡记录总数
	 * @throws Exception
	 */
	@Override
	public synchronized String getRecordCount() throws Exception {
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
		//System.out.println(result2);
		int recordIndex = result2.indexOf("RD") + 2;
		String recordCount = result2.substring(recordIndex, recordIndex + 5);
		// 一旦记录总数超出阈值，则清空记录总数
		if (Integer.parseInt(recordCount) > 50000) {
			clearRecordCount();
			return "0";
		}
		return recordCount;
	}

	/**
	 * 获取最新一条刷卡记录
	 * 
	 * @param recordIndex
	 *            记录序号
	 * @throws Exception
	 */
	@Override
	public synchronized String[] getOneRecord(String recordIndex) throws Exception {
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
		logger.info("one record msgBody is {}", msgBody);
		String readHeadNumber = msgBody.substring(4, 6);
		String cardNumber = msgBody.substring(msgBody.length() - 13, msgBody.length() - 3);
		System.out.println(readHeadNumber);// 根据读头判断进出方向
		System.out.println(cardNumber);// 根据卡号判断是何人，访客和白名单 卡号和身份证都是一一绑定的
		return new String[] { readHeadNumber, cardNumber };
	}

	/**
	 * 清除所有刷卡记录
	 * 
	 * @throws Exception
	 */
	private synchronized void clearRecordCount() throws Exception {
		byte[] cmd = new byte[256];
		cmd[0] = (int) 'K';
		cmd[1] = (int) '0';
		cmd[2] = (int) '0';
		cmd[3] = (int) '2';

		cmd[4] = (int) 'G';
		cmd[5] = (int) '4';
		cmd[6] = (int) '8';

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
		String result2 = OutputFormatter.nullTerminatedCharsToString(result);
		logger.info("clearRecordCount cmd return result is: {}", result2);
	}

}
