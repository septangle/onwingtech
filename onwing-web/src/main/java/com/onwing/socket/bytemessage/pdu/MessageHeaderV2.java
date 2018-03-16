package com.onwing.socket.bytemessage.pdu;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class MessageHeaderV2 {

	public final static int MESSAGE_HEADER_LENGTH = 24;
	public final static int PAYLOAD_SIZE_POSITION = 20;
	public final static int VARIABLE_LENGTH = -1;

	// Connection Broker Identifier
	public static final int MAGIC_NUMBER = 0xABCD2010;

	// Connection Broker APP ID
	// public static final int MODULE_ID = 0x00000001;

	// Connection Broker protocol version
	public static final byte MAJOR_VERSION = 0x0001;

	public static final byte MINOR_VERSION = 0x0001;

	public MessageHeaderV2() {

	}

	public MessageHeaderV2(int moduleId) {
		this.moduleId = moduleId;
	}

	public MessageHeaderV2(int moduleId, int messageId) {
		this.moduleId = moduleId;
		this.messageId = messageId;
	}

	public MessageHeaderV2(int moduleId, int messageId, int payloadSize) {
		this.moduleId = moduleId;
		this.messageId = messageId;
		this.payloadSize = payloadSize;
	}

	public void marshall(ByteBuf buf) {
		buf.writeInt(MAGIC_NUMBER);
		buf.writeInt(this.moduleId);
		buf.writeShort(this.msgType);
		buf.writeByte(this.majorVerion);
		buf.writeByte(this.minorVerion);
		buf.writeInt(this.messageId);
		buf.writeInt(this.errorCode);
		buf.writeInt(this.payloadSize);
	}

	public byte[] toBytes() {
		byte[] bytes = new byte[MESSAGE_HEADER_LENGTH];
		ByteBuf buf = Unpooled.buffer();
		marshall(buf);
		buf.readBytes(bytes);
		return bytes;
	}

	public void unmarshall(ByteBuf buf) {
		buf.readerIndex(buf.readerIndex()+4);
		this.moduleId = buf.readInt();
		this.msgType = buf.readShort();
		this.majorVerion = buf.readByte();
		this.minorVerion = buf.readByte();
		this.messageId = buf.readInt();
		this.errorCode = buf.readInt();
		this.payloadSize = buf.readInt();
	}

	public static boolean verifyProtocolNumber(int magicNumber) {
		if (magicNumber == MAGIC_NUMBER) {
			return true;
		} else {
			return false;
		}
	}

	private int moduleId;

	// indicate request(0), response(1) or notify(2)
	private short msgType;

	// Connection Broker Message ID
	private int messageId;

	// Connection Broker Error code
	private int errorCode;

	// Connection Broker Payload size
	private int payloadSize;

	private byte majorVerion = MAJOR_VERSION;

	private byte minorVerion = MINOR_VERSION;

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public short getMsgType() {
		return msgType;
	}

	public void setMsgType(short msgType) {
		this.msgType = msgType;
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public int getPayloadSize() {
		return payloadSize;
	}

	public void setPayloadSize(int payloadSize) {
		this.payloadSize = payloadSize;
	}

	public byte getMajorVerion() {
		return majorVerion;
	}

	public void setMajorVerion(byte majorVerion) {
		this.majorVerion = majorVerion;
	}

	public byte getMinorVerion() {
		return minorVerion;
	}

	public void setMinorVerion(byte minorVerion) {
		this.minorVerion = minorVerion;
	}

}