package com.onwing.household.biz.logic.facade;

import com.onwing.household.biz.request.StrangerRequest;
import com.onwing.household.biz.response.StrangerResponse;

public interface StrangerFacade {
	
	
	/**
	 * 录入陌生人信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public StrangerResponse addStranger(StrangerRequest strangerRequest);
	

}
