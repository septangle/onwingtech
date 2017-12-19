package com.onwing.household.biz.logic.facade;

import com.onwing.household.biz.response.AccessRecordResponse;

public interface AccessRecordFacade {
	
	/**
	 * 出入记录
	 */
	public AccessRecordResponse findAllAccessRecord()throws Exception;

}
