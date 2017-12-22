package com.onwing.socket.client;

import java.io.OutputStream;
import java.net.Socket;

public class ClientTest {

	public static void main(String[] args) {
		try {
			Socket socket = new Socket("192.168.1.163", 8888);
			OutputStream out = socket.getOutputStream();
			byte[] msgBytes = "{\"data\": \"22-203-俞小洋\", \"time\": \"2017/12/13 17:30:52\"}".getBytes("GBK");
			out.write(msgBytes);
			Thread.sleep(5000);
			/*
			out.write("{'data': 'zouxu', 'time': '2017/12/19 01:30:52'".getBytes());
			Thread.sleep(5000);
			out.write("}".getBytes());
			*/
			out.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
