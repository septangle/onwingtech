package com.onwing.socket.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SimpleServerHandler extends ChannelInboundHandlerAdapter {
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("complete json msg received");
		String result = (String) msg;
		
		// 接收并打印客户端的信息
		String remote = ctx.channel().remoteAddress().toString();
		System.out.println(remote + " said:" + result);

		// 向客户端发送消息
		/*
		String response = "hello client!";
		ByteBuf encoded = ctx.alloc().buffer(4 * response.length());// 在当前场景下，发送的数据必须转换成ByteBuf数组
		encoded.writeBytes(response.getBytes());
		ctx.write(encoded);
		ctx.flush();
		*/
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// 当出现异常就关闭连接
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
}
