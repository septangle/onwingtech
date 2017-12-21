package com.onwing.household.biz.logic.core;

public interface DoorLockBiz {
	public void connect(String ip, int port);
	
	public void openBigDoorLock();
	
	public void closeBigDoorLock();
}
