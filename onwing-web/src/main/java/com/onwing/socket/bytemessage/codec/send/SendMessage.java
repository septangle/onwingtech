package com.onwing.socket.bytemessage.codec.send;

import java.nio.ByteBuffer;

import com.onwing.socket.bytemessage.pdu.Message;

public abstract class SendMessage extends Message {

  public static final int MODULE_ID = 0x00000002;
  public static final int MAX_RESPONSE_LENGTH = 2048;

  public static final int ERROR_SUCCESS = 0;
  public static final int ERROR_FAILURE = 1;
  public final static int ERROR_REQUEST = 0x000000F1;
  public final static int ERROR_BROKER = 0x000000F2;

  @Override
  public void marshall(ByteBuffer buf) {
    if (getErrorCode() == ERROR_SUCCESS) {
      super.marshall(buf);
    } else {
      // only marshall header
      marshallMessageHeader(buf);
    }
  }

  @Override
  public void setErrorCode(int errorCode) {
    this.header.setErrorCode(errorCode);
    if (errorCode != ERROR_SUCCESS) {
      this.header.setPayloadSize(0);
    }
  }

  public SendMessage() {
    this.header.setModuleId(MODULE_ID);
  }

  public SendMessage(int moduleId, int messageId, int payloadSize) {
    this.header.setModuleId(moduleId);
    this.header.setMessageId(messageId);
    this.header.setPayloadSize(payloadSize);
  }

  public SendMessage(int messageId) {
    this.header.setModuleId(MODULE_ID);
    this.header.setMessageId(messageId);
  }

  public SendMessage(int messageId, int payloadSize) {
    this.header.setModuleId(MODULE_ID);
    this.header.setMessageId(messageId);
    this.header.setPayloadSize(payloadSize);
  }

}
