package com.onwing.socket.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 将socket service随tomcat启动
 * 
 * @author yuxiaoyang
 * 
 */
public class SocketServiceLoader implements ServletContextListener {
	// socket server 线程
	private SocketServerThread socketServerThread;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		if (null != socketServerThread && !socketServerThread.isInterrupted()) {
			socketServerThread.closeSocketServer();
			socketServerThread.interrupt();
		}

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		if (null == socketServerThread) {
			// 新建线程类
			socketServerThread = new SocketServerThread();
			// 启动线程
			socketServerThread.start();
		}

	}

}
