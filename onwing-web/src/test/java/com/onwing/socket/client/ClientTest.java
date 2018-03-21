package com.onwing.socket.client;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.onwing.socket.bytemessage.utils.ByteBufUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ClientTest {

	public static void main(String[] args) {
		ClientTest test = new ClientTest();
		test.sendPhotoTest();
	}

	public void sendJsonTest() {
		try {
			Socket socket = new Socket("127.0.0.1", 8888);
			OutputStream out = socket.getOutputStream();
			byte[] msgBytes = "{\"data\": \"22-203-俞小洋\", \"time\": \"2017/12/13 17:30:52\"}".getBytes("GBK");
			out.write(msgBytes);
			Thread.sleep(100);
			out.write(msgBytes);
			/*
			 * out.write("{'data': 'zouxu', 'time': '2017/12/19 01:30:52'"
			 * .getBytes()); Thread.sleep(5000); out.write("}".getBytes());
			 */
			out.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendPhotoTest() {
		try {
			Socket socket = new Socket("127.0.0.1", 8888);
			OutputStream out = socket.getOutputStream();

			ByteBuf buf = Unpooled.buffer();
			// 获取图片信息
			Resource res = new FileSystemResource("/Users/lvxiaobu/iso/330000000000000.png");
			InputStream input = res.getInputStream();
			int photoSize = (int) res.contentLength();

			byte[] photoBytes = new byte[photoSize];
			ByteBuf photoBuf = Unpooled.buffer();
			byte[] tmpbuf = new byte[1024];
			int len = 0;
			while ((len = input.read(tmpbuf)) != -1) {
				photoBuf.writeBytes(tmpbuf, 0, len);
			}
			photoBuf.readBytes(photoBytes);

			// 填充头字段
			int payloadSize = 94 + photoSize;
			buf.writeInt(1);
			buf.writeInt(1);
			buf.writeShort(1);
			buf.writeByte(1);
			buf.writeByte(1);
			buf.writeInt(2);
			buf.writeInt(0);
			buf.writeInt(payloadSize);
			// 填充payload
			ByteBufUtil.addStringBytesToByteBuf(buf, "camera2", 10);
			ByteBufUtil.addStringBytesToByteBuf(buf, "111111111.jpg", 40); //330000000000000.jpg 0-0-0
			ByteBufUtil.addStringBytesToByteBuf(buf, "2018/01/19 10:50:52", 40);
			buf.writeInt(photoSize);
			buf.writeBytes(photoBytes);

			byte[] msgBytes = new byte[24 + payloadSize];
			buf.readBytes(msgBytes);
			while (true) {
				out.write(msgBytes);
				Thread.sleep(1000);
				break;
			}
			//out.close();
			//socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
