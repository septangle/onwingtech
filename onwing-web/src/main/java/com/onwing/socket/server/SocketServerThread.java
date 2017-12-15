package com.onwing.socket.server;

import com.onwing.socket.server.decoder.CameraRecognitionMsgDecoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class SocketServerThread extends Thread {
	private final int port = 8888;
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;
	
	public SocketServerThread() {
		
	}

	@Override
	public void run() {
		// EventLoopGroup是用来处理IO操作的多线程事件循环器
		// bossGroup 用来接收进来的连接
		if (bossGroup == null)
			bossGroup = new NioEventLoopGroup();
		// workerGroup 用来处理已经被接收的连接
		if (workerGroup == null)
			workerGroup = new NioEventLoopGroup();
		try {
			// 启动 NIO 服务的辅助启动类
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					// 配置 Channel
					.channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							// 注册handler
							ch.pipeline().addLast(new CameraRecognitionMsgDecoder(), new SimpleServerHandler());
						}
					}).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);

			// 绑定端口，开始接收进来的连接
			ChannelFuture f = b.bind(port).sync();
			// 等待服务器 socket 关闭 。
			f.channel().closeFuture().sync();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
	
	public void closeSocketServer() {
		workerGroup.shutdownGracefully();
		bossGroup.shutdownGracefully();
	}

}
