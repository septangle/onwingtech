package com.onwing.household.biz.logic.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.onwing.household.biz.dto.UserRoleDto;
import com.onwing.household.biz.logic.core.AdminiStratorBiz;
import com.onwing.household.biz.logic.facade.AdminiStratorFacade;
import com.onwing.household.biz.request.AdminiStratorRequest;
import com.onwing.household.biz.request.UserRoleRequest;
import com.onwing.household.biz.response.AdminiStratorResponse;
@Service 
public class AdminiStratorFacadeImpl implements AdminiStratorFacade{

	@Autowired
	private AdminiStratorBiz adminiStratorBiz;
	
	@Override
	public AdminiStratorResponse login(AdminiStratorRequest request) throws Exception {
		AdminiStratorResponse adminiStratorResponse = new AdminiStratorResponse();
		UserRoleDto userRoleDto=adminiStratorBiz.queryLoginUser(request.getAdminiStratorDto());
		adminiStratorResponse.setUserRoleDto(userRoleDto);;
		return adminiStratorResponse;
	}
	
	@Override
	public AdminiStratorResponse addUser(UserRoleRequest request) throws Exception {
		AdminiStratorResponse adminiStratorResponse = new AdminiStratorResponse();
		adminiStratorBiz.addUser(request);
		return adminiStratorResponse;
	}


}
