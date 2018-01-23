package com.onwing.household.biz.logic.core;

public interface DoorLockBiz {
	public void connect(String ip, int port) throws Exception;
	
	/**
	 * send open lock cmd to lock control
	 * @param relayNumber 继电器号，指示开启哪个方向的闸机
	 * @throws Exception
	 */
	public void openBigDoorLock(String relayNumber) throws Exception;

	public void closeBigDoorLock(String relayNumber) throws Exception;
	
	public String[] getOneRecord(String recordIndex) throws Exception;
	
	public String getRecordCount() throws Exception;
	
	public void closeSocket();
}
