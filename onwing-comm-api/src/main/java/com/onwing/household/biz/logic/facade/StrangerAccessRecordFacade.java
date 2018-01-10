package com.onwing.household.biz.logic.facade;

import com.onwing.household.biz.response.StrangerAccessRecordResponse;

public interface StrangerAccessRecordFacade {
	
	public StrangerAccessRecordResponse findAllStrangerAccessRecord(int startRow,int pageSize) throws Exception;

}
