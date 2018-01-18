package com.onwing.socket.bytemessage.utils;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;

public class ByteBufUtil {
	private final static Logger logger = LoggerFactory.getLogger(ByteBufUtil.class);
	private static final byte _NULL_TERMINAL_CHAR = (byte) 0;

	/*
	 * 从bytebuf中获取string 本质是将长为length的byte[]根据编码转换成string
	 */
	public static String getStringFromByteBuf(ByteBuf src, int length, String charset) {
		byte[] bytes = new byte[length];
		src.readBytes(bytes);

		int decodeLength = 0;
		for (int i = 0; i < length; i++) {
			if (bytes[i] == _NULL_TERMINAL_CHAR) {
				decodeLength = i;
				break;
			}
		}
		if (decodeLength == 0)
			decodeLength = length;
		String target = null;
		try {
			target = new String(bytes, 0, decodeLength, charset);
		} catch (UnsupportedEncodingException e) {
			logger.error("", e);
		}
		return target;
	}

	/*
	 * 追加string 到ByteBuf中 本质是将string放入长为length的byte[],然后将byte[]放入bytebuf
	 */
	public static void addStringBytesToByteBuf(ByteBuf dst, String src, int length) {
		int begin = dst.writerIndex();
		int srcLength = src.length();
		if (srcLength >= length) {
			srcLength = length - 1;
		}
		for (int i = 0; i < srcLength; i++) {
			dst.writeByte((byte) src.charAt(i));
		}
		dst.writerIndex(begin + length);
	}

}
