package com.onwing.household.biz.logic.core.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.onwing.household.biz.dto.AdminiStratorDto;
import com.onwing.household.biz.dto.UserRoleDto;
import com.onwing.household.biz.exception.BusinessException;
import com.onwing.household.biz.logic.core.AdminiStratorBiz;
import com.onwing.household.comm.AppConstants;
import com.onwing.household.comm.dal.dao.AdminiStratorMapper;
import com.onwing.household.comm.dal.dao.UserRoleMapper;
import com.onwing.household.comm.dal.model.AdminiStrator;
import com.onwing.household.comm.dal.model.UserRole;
import com.onwing.household.util.ModelUtil;

@Service
public class AdminiStratorimpl implements AdminiStratorBiz{

	@Autowired
	private AdminiStratorMapper adminiStratorMapper;
	
	@Autowired 
	private UserRoleMapper userRoleMapper;


	//登录
	@Override
	public UserRoleDto queryLoginUser(AdminiStratorDto adminiStratorDto) throws BusinessException {
		UserRoleDto userRoleDto = new UserRoleDto();
		AdminiStrator adminStrator;
		try {
			adminStrator = ModelUtil.dtoToModel(adminiStratorDto, AdminiStrator.class);
			adminStrator=adminiStratorMapper.login(adminStrator);
			UserRole userRole=userRoleMapper.selectByUserId(adminStrator.getId());
			userRoleDto.setCommunityId(userRole.getCommunity().getId());
			userRoleDto.setCommunityName(userRole.getCommunity().getName());
			userRoleDto.setRoleName(userRole.getRole().getRoleName());

		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(AppConstants.QUERY_LOGIN_USER_ERROR_CODE,
					AppConstants.QUERY_LOGIN_USER_ERROR_MESSAGE);
		}
		return userRoleDto;
	}
	

}
