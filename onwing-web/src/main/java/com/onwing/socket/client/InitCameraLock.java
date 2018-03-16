package com.onwing.socket.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.onwing.household.comm.dal.dao.CamaraMapper;
import com.onwing.household.comm.dal.model.Camara;

public class InitCameraLock {
	private final static Logger logger = LoggerFactory.getLogger(InitCameraLock.class);

	@Autowired
	private CamaraMapper camaraMapper;

	public Map<String, String> cameraLockMap = new HashMap<String, String>();

	public void init() {
		List<Camara> camaraList = camaraMapper.getAllCamara();
		if (camaraList == null || camaraList.size() == 0) {
			logger.error("camaraList from DB empty!");
		} else {
			for (Camara camara : camaraList) {
				cameraLockMap.put(camara.getName(), null);
			}
			logger.info("cameraLockMap is {}", cameraLockMap);
		}
	}

}
