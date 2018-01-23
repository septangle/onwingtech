package com.onwing.socket.client;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onwing.household.biz.logic.core.DoorLockBiz;
import com.onwing.household.biz.logic.core.impl.DoorLockMap;
import com.onwing.household.biz.logic.core.impl.DoorLockimpl;
import com.onwing.household.comm.dal.dao.CamaraMapper;
import com.onwing.household.comm.dal.model.Camara;
import com.onwing.household.comm.dal.model.Control;

public class QueryCardRecordsThread extends Thread {
	private final static Logger logger = LoggerFactory.getLogger(QueryCardRecordsThread.class);
	private DoorLockBiz doorLockBiz = new DoorLockimpl();

	private CamaraMapper camaraMapper;
	private Map<String, String> lockControlProperties;
	private InitCameraLock initCameraLock;
	private Control control;
	private DoorLockMap doorLockMap;

	// 当前控制器中刷卡记录总数
	private String currentRecordCount;

	public QueryCardRecordsThread(CamaraMapper camaraMapper, Map<String, String> lockControlProperties,
			InitCameraLock initCameraLock, Control control, DoorLockMap doorLockMap) {
		this.camaraMapper = camaraMapper;
		this.lockControlProperties = lockControlProperties;
		this.initCameraLock = initCameraLock;
		this.control = control;
		this.doorLockMap = doorLockMap;
		getInitialRecordCount();
		//将control 和 control发送命令类 关联起来
		this.doorLockMap.getLockSocketMap().put(control.getName(), doorLockBiz);
	}

	// 获取最初的刷卡记录 数目
	public void getInitialRecordCount() {
		try {
			doorLockBiz.connect(control.getIp(), Integer.parseInt(control.getPort()));
			currentRecordCount = doorLockBiz.getRecordCount();
		} catch (Exception e) {
			logger.error("getInitialRecordCount from lock control error", e);
		}
	}

	@Override
	public void run() {
		// 每隔1000ms向lock control查询刷卡记录，一旦有变化，则触发白名单拍照功能
		// 如果是500ms，控制器则会出错
		while (true) {
			try {
				doorLockBiz.connect(control.getIp(), Integer.parseInt(control.getPort()));
				String recordCount = doorLockBiz.getRecordCount();
				if (!currentRecordCount.equals(recordCount)) {
					// 有新的刷卡记录，获取最新一条刷卡记录
					String[] recordInfo = doorLockBiz.getOneRecord(recordCount);
					String readHeadNumber = recordInfo[0];
					String cardNumber = recordInfo[1];

					String in_out = lockControlProperties.get(control.getName() + "-" + readHeadNumber);
					if (in_out == null || in_out.isEmpty()) {
						logger.error("in_out from lockControlProperties is null");
						return;
					}

					Camara camara = new Camara();
					camara.setControlId(control.getId());
					camara.setDirection(in_out);
					List<Camara> cameraList = camaraMapper.selectBySelective(camara);
					if (cameraList == null || cameraList.size() != 1) {
						logger.error("get camera from DB with controlId {} and direction {} return not only",
								control.getId(), in_out);
						return;
					}
					// 打开摄像头锁
					initCameraLock.cameraLockMap.put(cameraList.get(0).getName(), cardNumber + ":" + in_out);
					// end
					currentRecordCount = recordCount;
				}
			} catch (Exception e) {
				logger.error("getRecordCount from lock control error", e);
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error("", e);
			}
		}
	}

}
