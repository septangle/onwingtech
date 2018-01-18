package com.onwing.socket.server;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.onwing.household.biz.logic.core.AccessRecordBiz;
import com.onwing.household.biz.logic.core.StrangerAccessRecordBiz;
import com.onwing.household.biz.logic.core.impl.DoorLockMap;
import com.onwing.household.comm.dal.dao.ControlMapper;
import com.onwing.household.comm.dal.dao.HouseHoldMapper;
import com.onwing.household.comm.dal.dao.StrangerAccessRecordMapper;
import com.onwing.household.comm.dal.dao.StrangerMapper;
import com.onwing.socket.client.InitCameraLock;
import com.onwing.socket.server.decoder.CameraRecognitionMsgDecoder;
import com.onwing.socket.server.decoder.CameraRecognitionMsgDecoderV2;

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
	private StrangerAccessRecordMapper strangerAccessRecordMapper;

	@Autowired
	private HouseHoldMapper householdMapper;

	@Autowired
	private StrangerMapper strangerMapper;

	@Autowired
	private InitCameraLock initCameraLock;
	
	@Autowired
	private ControlMapper controlMapper;

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// 注册handler
		ch.pipeline().addLast(new CameraRecognitionMsgDecoderV2(),
				new SimpleServerHandler(initCameraLock, lockControlProperties, doorLockMap, accessRecordBiz,
						strangerAccessRecordMapper, householdMapper, strangerMapper, controlMapper));

	}

}
