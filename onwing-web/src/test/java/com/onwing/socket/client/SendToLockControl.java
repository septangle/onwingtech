package com.onwing.socket.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.onwing.household.util.OutputFormatter;

public class SendToLockControl {
	public static final String IP_ADDR = "192.168.1.253";// 服务器地址
	public static final int PORT = 4196;// 服务器端口号
	private InputStream input;
	private OutputStream out;

	public void connect() {
		Socket socket = null;
		try {
			// 创建一个流套接字并将其连接到指定主机上的指定端口号
			socket = new Socket(IP_ADDR, PORT);

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

	public void openDoor() {
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
			String result2 = OutputFormatter.nullTerminatedCharsToString(result);
			System.out.println("opendoor return result is: " + result2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void closeDoor() {
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
		// 继电器
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
			String result2 = OutputFormatter.nullTerminatedCharsToString(result);
			System.out.println("closedoor return result is: " + result2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取刷卡记录总数
	 */
	public void getRecordCount() {
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
		try {
			out.write(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// receive data from server
		byte[] result = new byte[256];
		try {
			input.read(result);
			// [75, 48, 48, 50, 71, 52, 50, 51, 50, 83, 85, 48, 48, 48, 48, 49,
			// 66, 85, 48, 48, 48, 48, 83, 66, 48, 48, 48, 48, 82, 68, 48, 48,
			// 48, 50, 48, 82, 70, 48, 48, 48, 48, 51, 56, 13, 0, 0, 0, 0, 0, 0,
			// 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			// 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			// 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
			String result2 = OutputFormatter.nullTerminatedCharsToString(result);
			System.out.println(result2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 清除所有刷卡记录
	 */
	public void clearRecordCount() {
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
		try {
			out.write(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// receive data from server
		byte[] result = new byte[256];
		try {
			input.read(result);
			String result2 = OutputFormatter.nullTerminatedCharsToString(result);
			System.out.println(result2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readOneEvent() {
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
		try {
			out.write(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// receive data from server
		byte[] result = new byte[256];
		try {
			input.read(result);
			System.out.println(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SendToLockControl anwei = new SendToLockControl();
		anwei.connect();
		anwei.openDoor();
		anwei.closeDoor();
		//anwei.clearRecordCount();
		anwei.getRecordCount();
	}

}
