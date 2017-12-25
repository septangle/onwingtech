package com.onwing.household.biz.logic.core;

public interface DoorLockBiz {
	public void connect(String ip, int port) throws Exception;

	public void openBigDoorLock() throws Exception;

	public void closeBigDoorLock() throws Exception;
	
	public void closeSocket();
}
