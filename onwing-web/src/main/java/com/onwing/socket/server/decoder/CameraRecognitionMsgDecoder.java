package com.onwing.socket.server.decoder;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class CameraRecognitionMsgDecoder extends ByteToMessageDecoder {
	private byte[] initial = null;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		byte[] result = new byte[in.readableBytes()];
		in.readBytes(result);

		byte[] result2 = calculateSum(initial, result);
		initial = cutMsgFromBytes(result2, out);
	}
	
	/**
	 * calculate data1 + data2
	 * @param data1
	 * @param data2
	 * @return data1 + data2
	 */
	private byte[] calculateSum(byte[] data1, byte[] data2) {
		if (data1 == null) {
			return data2;
		}
		byte[] data3 = new byte[data1.length + data2.length];
		System.arraycopy(data1, 0, data3, 0, data1.length);
		System.arraycopy(data2, 0, data3, data1.length, data2.length);
		return data3;
	}

	/**
	 * cut out json msg from byte array
	 * 
	 * @param target
	 * @param out
	 * @return remained bytes
	 */
	private byte[] cutMsgFromBytes(byte[] target, List<Object> out) {
		int length = target.length;
		int flag = 0;
		for (int i = 0; i < length; i++) {
			if (target[i] == 125) {
				String msg = new String(target, flag, i - flag + 1);
				out.add(msg);
				flag = i + 1;
			}
		}

		byte[] remainBytes = null;
		if (flag < length) {
			remainBytes = new byte[length - flag];
			System.arraycopy(target, flag, remainBytes, 0, length - flag);
			return remainBytes;

		} else {
			return null;
		}

	}

}
