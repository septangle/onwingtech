package com.onwing.household.biz.logic.core.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.onwing.household.biz.logic.core.DoorLockBiz;

public class DoorLockMap {
	private Map<String, DoorLockBiz> lockSocketMap = null;

	public Map<String, DoorLockBiz> getLockSocketMap() {
		if (lockSocketMap == null) {
			lockSocketMap = new ConcurrentHashMap<String, DoorLockBiz>();
		}
		return lockSocketMap;
	}
	

}
