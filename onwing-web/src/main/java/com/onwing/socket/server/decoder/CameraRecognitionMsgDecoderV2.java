package com.onwing.socket.server.decoder;

import java.util.List;

import com.onwing.socket.bytemessage.pdu.MessageHeaderV2;
import com.onwing.socket.server.receive.CameraRecognitionRequest;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class CameraRecognitionMsgDecoderV2 extends ByteToMessageDecoder {
	private CameraRecognitionRequest request = new CameraRecognitionRequest();

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		int startIndex = in.readerIndex();// 记录初始index

		int availableBytes = in.readableBytes();
		if (availableBytes >= MessageHeaderV2.MESSAGE_HEADER_LENGTH) {
			int payloadSize = in.getInt(startIndex + 20);
			if (availableBytes >= MessageHeaderV2.MESSAGE_HEADER_LENGTH + payloadSize) {
				// 接收到完整的二进制消息
				if (request.unmarshall(in))
					out.add(request);
			}
		}
	}
}
