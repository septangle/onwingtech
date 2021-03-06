package com.onwing.household.biz.logic.facade;

import com.onwing.household.biz.request.AdminiStratorRequest;
import com.onwing.household.biz.response.AdminiStratorResponse;

public interface AdminiStratorFacade {
	
	/**
	 * 登录
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public AdminiStratorResponse login(AdminiStratorRequest request) throws Exception;

}
