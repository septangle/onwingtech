package com.onwing.socket.bytemessage.pdu;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

public class MessageHeader {

  public final static int MESSAGE_HEADER_LENGTH = 24;
  public final static int PAYLOAD_SIZE_POSITION = 20;
  public final static int VARIABLE_LENGTH = -1;

  // Connection Broker Identifier
  public static final int MAGIC_NUMBER = 0xABCD2010;

  // Connection Broker APP ID
  // public static final int MODULE_ID = 0x00000001;

  // Connection Broker protocol version
  public static final short MAJOR_VERSION = 0x0001;

  public static final short MINOR_VERSION = 0x0001;

  public MessageHeader() {

  }

  public MessageHeader(int moduleId) {
    this.moduleId = moduleId;
  }

  public MessageHeader(int moduleId, int messageId) {
    this.moduleId = moduleId;
    this.messageId = messageId;
  }

  public MessageHeader(int moduleId, int messageId, int payloadSize) {
    this.moduleId = moduleId;
    this.messageId = messageId;
    this.payloadSize = payloadSize;
  }

  public MessageHeader(byte[] headerBytes) {
    unmarshall(ByteBuffer.wrap(headerBytes));
  }

  public void setModuleId(int moduleId) {
    this.moduleId = moduleId;
  }

  public int getModuleId() {
    return moduleId;
  }

  public short getMajorVersion() {
    return MAJOR_VERSION;
  }

  public short getMinorVersion() {
    return MINOR_VERSION;
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

  public short getMajorVerion() {
    return majorVerion;
  }

  public void setMajorVerion(short majorVerion) {
    this.majorVerion = majorVerion;
  }

  public short getMinorVerion() {
    return minorVerion;
  }

  public void setMinorVerion(short minorVerion) {
    this.minorVerion = minorVerion;
  }

  public void marshall(ByteBuffer buf) {

    // this.bytes = new byte[24];
    // ByteBuffer buf = ByteBuffer.wrap(bytes);
    buf.putInt(MAGIC_NUMBER);
    buf.putInt(this.moduleId);
    buf.putShort(this.majorVerion);
    buf.putShort(this.minorVerion);
    buf.putInt(this.messageId);
    buf.putInt(this.errorCode);
    buf.putInt(this.payloadSize);

    // return bytes;
  }

  public byte[] toBytes() {
    byte[] bytes = new byte[MESSAGE_HEADER_LENGTH];
    ByteBuffer buf = ByteBuffer.wrap(bytes);
    marshall(buf);
    return bytes;
  }

  public void unmarshall(ByteBuffer buf) {
    // if (isBigedian) {
    // ByteBuffer buf = ByteBuffer.wrap(bytes);
    buf.position(buf.position() + 4);
    this.moduleId = buf.getInt();
    this.majorVerion = buf.getShort();
    this.minorVerion = buf.getShort();
    this.messageId = buf.getInt();
    this.errorCode = buf.getInt();
    this.payloadSize = buf.getInt();

  }

  public static boolean verifyProtocolNumber(int magicNumber) {
    if (magicNumber == MAGIC_NUMBER) {
      return true;
    } else {
      return false;
    }
  }

  private int moduleId;

  // Connection Broker Message ID
  private int messageId;

  // Connection Broker Error code
  private int errorCode;

  // Connection Broker Payload size
  private int payloadSize;

  private short majorVerion = MAJOR_VERSION;

  private short minorVerion = MINOR_VERSION;

  // private byte[] bytes;

}