package com.onwing.socket.client;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.onwing.household.biz.logic.core.DoorLockBiz;
import com.onwing.household.biz.logic.core.impl.DoorLockMap;
import com.onwing.household.biz.logic.core.impl.DoorLockimpl;
import com.onwing.household.comm.dal.dao.CamaraMapper;
import com.onwing.household.comm.dal.dao.ControlMapper;
import com.onwing.household.comm.dal.model.Control;

public class QueryCardRecordsThreadMain extends Thread {
	private final static Logger logger = LoggerFactory.getLogger(QueryCardRecordsThreadMain.class);

	@Resource
	private Map<String, String> lockControlProperties;

	@Autowired
	private InitCameraLock initCameraLock;

	@Autowired
	private ControlMapper controlMapper;

	@Autowired
	private CamaraMapper camaraMapper;
	
	@Autowired
	private DoorLockMap doorLockMap;

	@Override
	public void run() {
		// get lock control list from DB
		List<Control> controlList = controlMapper.getAllControl();
		if (controlList == null || controlList.size() == 0) {
			logger.error("lock control list from DB is null!");
			return;
		}
		for (Control control : controlList) {
			//QueryCardRecordsThread thread = new QueryCardRecordsThread(camaraMapper, lockControlProperties,
			//		initCameraLock, control, doorLockMap);
			//thread.start();
			
			DoorLockBiz doorLockBiz = new DoorLockimpl();
			//将control 和 control发送命令类 关联起来
			doorLockMap.getLockSocketMap().put(control.getName(), doorLockBiz);
		}
	}
}
