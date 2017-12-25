package com.onwing.household.biz.logic.core.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.onwing.household.biz.logic.core.DoorLockBiz;

public class DoorLockimpl implements DoorLockBiz {
	private Socket socket = null;
	private InputStream input;
	private OutputStream out;

	@Override
	public void connect(String ip, int port) {

		try {
			// 创建一个流套接字并将其连接到指定主机上的指定端口号
			if (socket == null || !socket.isConnected())
				socket = new Socket(ip, port);

			// 读取服务器端数据
			input = socket.getInputStream();
			// 向服务器端发送数据
			out = socket.getOutputStream();
		} catch (Exception e) {
			System.out.println("客户端异常:" + e.getMessage());
		} finally {
			// if (socket != null) {
			// try {
			// socket.close();
			// } catch (IOException e) {
			// socket = null;
			// System.out.println("客户端 finally 异常:" + e.getMessage());
			// }
			// }
		}
	}

	@Override
	public void openBigDoorLock() {
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

		cmd[9] = (int) '0';
		cmd[10] = (int) '1';
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
		try {
			out.write(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// receive data from server
		byte[] result = new byte[256];
		try {
			input.read(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void closeBigDoorLock() {
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

		cmd[9] = (int) '0';
		cmd[10] = (int) '1';
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
		try {
			out.write(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// receive data from server
		byte[] result = new byte[256];
		try {
			input.read(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
