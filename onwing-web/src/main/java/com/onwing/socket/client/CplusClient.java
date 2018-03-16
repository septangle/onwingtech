package com.onwing.socket.client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class CplusClient {
	private Map<String, String> cplusClientProperties;

	private final static Logger logger = LoggerFactory.getLogger(CplusClient.class);
	private Socket socket = null;
	private InputStream input;
	private OutputStream out;

	public CplusClient(Map<String, String> cplusClientProperties) {
		this.cplusClientProperties = cplusClientProperties;
	}

	public boolean isConnected() {
		try {
			socket.sendUrgentData(0xFF);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void connect(String ip, String port) throws Exception {
		// 创建一个流套接字并将其连接到指定主机上的指定端口号
		if (socket == null || !isConnected()) {
			socket = new Socket();
			socket.connect(new InetSocketAddress(ip, Integer.parseInt(port)), 2000);// 设置连接请求超时时间2
			// s
		}

		// 读取服务器端数据
		input = socket.getInputStream();
		// 向服务器端发送数据
		out = socket.getOutputStream();
	}

	public void close() throws Exception {
		input.close();
		out.close();
		socket.close();
	}

	private void sendReloadPictureMsg(String pictureName) throws Exception {
		ByteBuf buf = Unpooled.buffer();
		// 填充头字段
		byte[] payloadBytes = pictureName.getBytes();
		int payloadSize = payloadBytes.length;
		buf.writeInt(0xABCD2010);
		buf.writeInt(1);
		buf.writeShort(0);
		buf.writeByte(1);
		buf.writeByte(1);
		buf.writeInt(0x000000F1);
		buf.writeInt(0);
		buf.writeInt(payloadSize);
		// 填充payload
		buf.writeBytes(payloadBytes);

		byte[] msgBytes = new byte[24 + payloadSize];
		buf.readBytes(msgBytes);
		out.write(msgBytes);
	}

	public void sendReloadPictureMsgMain(String pictureName) throws Exception {
		Collection<String> list = cplusClientProperties.values();
		for (String connectInfo : list) {
			String[] connectInfoList = connectInfo.split(":");
			try {
				this.connect(connectInfoList[0], connectInfoList[1]);
				this.sendReloadPictureMsg(pictureName);
				this.close();
			} catch (Exception ex) {
				logger.error("cannot sendReloadPictureMsg to {}", connectInfoList[0], ex);
				continue;
			}
		}

	}

}
