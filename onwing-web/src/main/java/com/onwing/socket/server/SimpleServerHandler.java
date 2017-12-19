package com.onwing.socket.server;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onwing.household.biz.logic.core.DoorLockBiz;
import com.onwing.household.biz.logic.core.impl.DoorLockMap;
import com.onwing.household.biz.logic.core.impl.DoorLockimpl;
import com.onwing.household.message.FaceRecognitionMsg;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SimpleServerHandler extends ChannelInboundHandlerAdapter {
    private Map<String, String> lockControlProperties;
	private DoorLockMap doorLockMap;
	
	public SimpleServerHandler(Map<String, String> lockControlProperties, DoorLockMap doorLockMap) {
		this.lockControlProperties = lockControlProperties;
		this.doorLockMap = doorLockMap;
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("complete json msg received");
		String result = (String) msg;
		
		// 接收并打印客户端的信息
		// String remote = ctx.channel().remoteAddress().toString();
		// System.out.println(remote + " said:" + result);
		
		ObjectMapper mapper = new ObjectMapper();  
		FaceRecognitionMsg faceRecognitionMsg = mapper.readValue(result, FaceRecognitionMsg.class);
		System.out.println(faceRecognitionMsg.getPhotoName());
		
		//open lock
		String lockControlId = "0"; //0号 锁控制器
		DoorLockBiz doorLockimpl = doorLockMap.getLockSocketMap().get(lockControlId);
		if (doorLockimpl == null) {
			doorLockimpl = new DoorLockimpl();
			doorLockMap.getLockSocketMap().put(lockControlId, doorLockimpl);
		}
		
		String connectInfo = lockControlProperties.get(lockControlId);
		String[] connectInfoList = connectInfo.split(":");
		doorLockimpl.connect(connectInfoList[0], Integer.parseInt(connectInfoList[1]));
		doorLockimpl.openBigDoorLock();
		//end
		
		//记录出入记录
		//TODO
		//end
		
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
