package com.onwing.socket.bytemessage.pdu;

import java.nio.ByteBuffer;

public abstract class Message {

  // Message Header
  protected final MessageHeader header;

  public Message() {
    header = new MessageHeader();
  }

  public Message(MessageHeader header) {
    this.header = header;
  }

  public Message(int moduleId) {
    header = new MessageHeader(moduleId);
  }

  public Message(int moduleId, int messageId) {
    header = new MessageHeader(moduleId, messageId);
  }

  public Message(int moduleId, int messageId, int payloadSize) {
    header = new MessageHeader(moduleId, messageId, payloadSize);
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

  public void setMajorVerion(short majorVerion) {
    this.header.setMajorVerion(majorVerion);
  }

  public short getMinorVerion() {
    return this.header.getMinorVerion();
  }

  public void setMinorVerion(short minorVerion) {
    this.header.setMinorVerion(minorVerion);
  }

  public void marshall(ByteBuffer buf) {
    int begin = buf.position();
    marshallMessageHeader(buf);
    marshallMessageBody(buf);
    if (this.header.getPayloadSize() == MessageHeader.VARIABLE_LENGTH) {
      // variable length payload. set payload length
      buf.putInt(
          begin + MessageHeader.PAYLOAD_SIZE_POSITION,
          buf.position() - begin - MessageHeader.MESSAGE_HEADER_LENGTH);
    }
  }

  public void marshallMessageHeader(ByteBuffer buf) {
    this.header.marshall(buf);
  }

  public void unmarshallMessageHeader(ByteBuffer buf) {
    this.header.unmarshall(buf);
  }

  public void unmarshall(ByteBuffer buf) {
    unmarshallMessageHeader(buf);
    unmarshallMessageBody(buf);
  }

  public abstract void marshallMessageBody(ByteBuffer byteBuffer);

  public abstract void unmarshallMessageBody(ByteBuffer byteBuffer);

  public MessageHeader getHeader() {
    return header;
  }

}
