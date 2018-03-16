package com.onwing.socket.bytemessage.pdu;


import io.netty.buffer.ByteBuf;

public abstract class MessageV2 {

	// Message Header
	protected final MessageHeaderV2 header;

	public MessageV2() {
		header = new MessageHeaderV2();
	}

	public MessageV2(MessageHeaderV2 header) {
		this.header = header;
	}

	public MessageV2(int moduleId) {
		header = new MessageHeaderV2(moduleId);
	}

	public MessageV2(int moduleId, int messageId) {
		header = new MessageHeaderV2(moduleId, messageId);
	}

	public MessageV2(int moduleId, int messageId, int payloadSize) {
		header = new MessageHeaderV2(moduleId, messageId, payloadSize);
	}

	public void setModuleId(int moduleId) {
		this.header.setModuleId(moduleId);
	}

	public int getModuleId() {
		return this.header.getModuleId();
	}

	public short getMajorVersion() {
		return this.header.getMajorVerion();
	}

	public short getMinorVersion() {
		return this.header.getMinorVerion();
	}

	public int getMessageId() {
		return this.header.getMessageId();
	}

	public void setMessageId(int messageId) {
		this.header.setMessageId(messageId);
	}

	public int getErrorCode() {
		return this.header.getErrorCode();
	}

	public void setErrorCode(int errorCode) {
		this.header.setErrorCode(errorCode);
	}

	public int getPayloadSize() {
		return this.header.getPayloadSize();
	}

	public void setPayloadSize(int payloadSize) {
		this.header.setPayloadSize(payloadSize);
	}

	public short getMajorVerion() {
		return this.header.getMajorVerion();
	}

	public void setMajorVerion(byte majorVerion) {
		this.header.setMajorVerion(majorVerion);
	}

	public short getMinorVerion() {
		return this.header.getMinorVerion();
	}

	public void setMinorVerion(byte minorVerion) {
		this.header.setMinorVerion(minorVerion);
	}

	public void marshall(ByteBuf buf) {
		marshallMessageHeaderV2(buf);
		marshallMessageBody(buf);
	}

	public void marshallMessageHeaderV2(ByteBuf buf) {
		this.header.marshall(buf);
	}

	public void unmarshallMessageHeaderV2(ByteBuf buf) {
		this.header.unmarshall(buf);
	}

	public boolean unmarshall(ByteBuf buf) {
		unmarshallMessageHeaderV2(buf);
		return unmarshallMessageBody(buf);
	}

	public abstract void marshallMessageBody(ByteBuf byteBuffer);

	public abstract boolean unmarshallMessageBody(ByteBuf byteBuf);

	public MessageHeaderV2 getHeader() {
		return header;
	}

}
