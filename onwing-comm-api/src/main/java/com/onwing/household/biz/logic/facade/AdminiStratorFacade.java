package com.onwing.household.biz.logic.facade;

import com.onwing.household.biz.request.AdminiStratorRequest;
import com.onwing.household.biz.request.UserRoleRequest;
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
	
	public AdminiStratorResponse addUser(UserRoleRequest request) throws Exception;
	
	public AdminiStratorResponse findAllUser(int startRow,int pageSize,String communityId,int count) throws Exception;

}
