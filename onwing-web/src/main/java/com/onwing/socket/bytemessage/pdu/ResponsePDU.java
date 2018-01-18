package com.onwing.socket.bytemessage.pdu;

public class ResponsePDU {

  // Message Header
  private MessageHeader msgHeader;

  // Message Body
  private MessageBody msgBody;

  public ResponsePDU() {

  }

  public MessageHeader getMsgHeader() {
    return msgHeader;
  }

  public MessageBody getMsgBody() {
    return msgBody;
  }

  public void setMsgHeader(MessageHeader msgHeader) {
    this.msgHeader = msgHeader;
  }

  public void setMsgBody(MessageBody msgBody) {
    this.msgBody = msgBody;
  }

  public byte[] construct() {
    byte[] responsePDU = null;

    int payloadSize = msgHeader.getPayloadSize();

    responsePDU = new byte[24 + payloadSize];

    System.arraycopy(
        msgHeader.toBytes(),
        0,
        responsePDU,
        0,
        msgHeader.toBytes().length);

    if (msgHeader.getPayloadSize() > 0) {
      System.arraycopy(
          msgBody.getPayload(),
          0,
          responsePDU,
          msgHeader.toBytes().length,
          msgHeader.getPayloadSize());
    }

    return responsePDU;
  }

}