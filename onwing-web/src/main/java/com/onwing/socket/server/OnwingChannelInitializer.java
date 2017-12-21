package com.onwing.socket.server;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.onwing.household.biz.logic.core.AccessRecordBiz;
import com.onwing.household.biz.logic.core.impl.DoorLockMap;
import com.onwing.household.comm.dal.dao.HouseHoldMapper;
import com.onwing.socket.server.decoder.CameraRecognitionMsgDecoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class OnwingChannelInitializer extends ChannelInitializer<SocketChannel> {
	@Resource
	private Map<String, String> lockControlProperties;

	@Autowired
	private DoorLockMap doorLockMap;

	@Autowired
	private AccessRecordBiz accessRecordBiz;

	@Autowired
	private HouseHoldMapper householdMapper;

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// 注册handler
		ch.pipeline().addLast(new CameraRecognitionMsgDecoder(),
				new SimpleServerHandler(lockControlProperties, doorLockMap, accessRecordBiz, householdMapper));

	}

}
