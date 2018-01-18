package com.onwing.socket.server.receive;

import com.onwing.socket.bytemessage.codec.send.SendMessageV2;
import com.onwing.socket.bytemessage.utils.ByteBufUtil;

import io.netty.buffer.ByteBuf;

public class CameraRecognitionRequest extends SendMessageV2 {
	private String cameraId;
	private String photoName;
	private String time;
	private int photoSize;
	private byte[] photo;

	private final static int cameraIdLength = 10;
	private final static int photoNameLength = 40;
	private final static int timeLength = 40;

	public int getPhotoSize() {
		return photoSize;
	}

	public void setPhotoSize(int photoSize) {
		this.photoSize = photoSize;
	}

	public String getCameraId() {
		return cameraId;
	}

	public void setCameraId(String cameraId) {
		this.cameraId = cameraId;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	@Override
	public void marshallMessageBody(ByteBuf byteBuffer) {
		// no use
	}

	@Override
	public void unmarshallMessageBody(ByteBuf byteBuf) {
		cameraId = ByteBufUtil.getStringFromByteBuf(byteBuf, cameraIdLength, "GBK");
		photoName = ByteBufUtil.getStringFromByteBuf(byteBuf, photoNameLength, "GBK");
		time = ByteBufUtil.getStringFromByteBuf(byteBuf, timeLength, "GBK");
		photoSize = byteBuf.readInt();
		photo = new byte[photoSize];
		byteBuf.readBytes(photo);
	}
}
