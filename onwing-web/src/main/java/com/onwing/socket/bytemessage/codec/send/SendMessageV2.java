package com.onwing.socket.bytemessage.codec.send;


import com.onwing.socket.bytemessage.pdu.MessageV2;

import io.netty.buffer.ByteBuf;

public abstract class SendMessageV2 extends MessageV2 {

	public static final int MODULE_ID = 0x00000001;
	public static final int MAX_RESPONSE_LENGTH = 2048;

	public static final int ERROR_SUCCESS = 0;
	public static final int ERROR_FAILURE = 1;
	public final static int ERROR_REQUEST = 0x000000F1;
	public final static int ERROR_BROKER = 0x000000F2;

	@Override
	public void marshall(ByteBuf buf) {
		if (getErrorCode() == ERROR_SUCCESS) {
			super.marshall(buf);
		} else {
			// only marshall header
			marshallMessageHeaderV2(buf);
		}
	}

	@Override
	public void setErrorCode(int errorCode) {
		this.header.setErrorCode(errorCode);
		if (errorCode != ERROR_SUCCESS) {
			this.header.setPayloadSize(0);
		}
	}

	public SendMessageV2() {
		this.header.setModuleId(MODULE_ID);
	}

	public SendMessageV2(int moduleId, int messageId, int payloadSize) {
		this.header.setModuleId(moduleId);
		this.header.setMessageId(messageId);
		this.header.setPayloadSize(payloadSize);
	}

	public SendMessageV2(int messageId) {
		this.header.setModuleId(MODULE_ID);
		this.header.setMessageId(messageId);
	}

	public SendMessageV2(int messageId, int payloadSize) {
		this.header.setModuleId(MODULE_ID);
		this.header.setMessageId(messageId);
		this.header.setPayloadSize(payloadSize);
	}

}
