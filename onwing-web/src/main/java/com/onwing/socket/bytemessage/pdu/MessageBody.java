package com.onwing.socket.bytemessage.pdu;

public class MessageBody {

  private byte[] payload;

  public MessageBody() {

  }

  public byte[] getPayload() {
    return payload;
  }

  public void setPayload(byte[] payload) {
    this.payload = payload;
  }
}