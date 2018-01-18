package com.onwing.socket.bytemessage.pdu;

public class RequestPDU {

  // Message Header
  private MessageHeader msgHeader;

  // Message Body
  private MessageBody msgBody;

  public RequestPDU() {

  }

  public RequestPDU(MessageHeader msgHeader, MessageBody msgBody) {
    this.msgHeader = msgHeader;
    this.msgBody = msgBody;
  }

  public MessageHeader getMsgHeader() {
    return msgHeader;
  }

  public void setMsgHeader(MessageHeader msgHeader) {
    this.msgHeader = msgHeader;
  }

  public MessageBody getMsgBody() {
    return msgBody;
  }

  public void setMsgBody(MessageBody msgBody) {
    this.msgBody = msgBody;
  }

  public byte[] construct() {
    byte[] requestPDU = null;

    int payloadSize = msgHeader.getPayloadSize();

    requestPDU = new byte[24 + payloadSize];

    System.arraycopy(
        msgHeader.toBytes(),
        0,
        requestPDU,
        0,
        msgHeader.toBytes().length);

    if (msgHeader.getPayloadSize() > 0) {
      System.arraycopy(
          msgBody.getPayload(),
          0,
          requestPDU,
          msgHeader.toBytes().length,
          msgHeader.getPayloadSize());
    }

    return requestPDU;
  }

}