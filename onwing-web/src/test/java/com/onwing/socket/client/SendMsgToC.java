package com.onwing.socket.client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.onwing.socket.bytemessage.utils.ByteBufUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class SendMsgToC {

	public static void main(String[] args) {
		SendMsgToC client = new SendMsgToC();
		client.sendPhotoTest();

	}
	
	public void sendPhotoTest() {
		try {
			Socket socket = new Socket("192.168.0.112", 27015);
			OutputStream out = socket.getOutputStream();

			ByteBuf buf = Unpooled.buffer();

			// 填充头字段
			byte[] payloadBytes = "hello world".getBytes();
			
			int payloadSize = payloadBytes.length;
			buf.writeInt(1);
			buf.writeInt(1);
			buf.writeShort(1);
			buf.writeByte(1);
			buf.writeByte(1);
			buf.writeInt(2);
			buf.writeInt(0);
			buf.writeInt(payloadSize);
			// 填充payload
			buf.writeBytes(payloadBytes);

			byte[] msgBytes = new byte[24 + payloadSize];
			buf.readBytes(msgBytes);
			out.write(msgBytes);
			out.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
