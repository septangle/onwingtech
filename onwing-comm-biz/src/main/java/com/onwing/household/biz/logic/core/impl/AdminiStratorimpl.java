package com.onwing.household.biz.logic.core.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onwing.household.biz.dto.AdminiStratorDto;
import com.onwing.household.biz.exception.BusinessException;
import com.onwing.household.biz.logic.core.AdminiStratorBiz;
import com.onwing.household.comm.AppConstants;
import com.onwing.household.comm.dal.dao.AdminiStratorMapper;
import com.onwing.household.comm.dal.model.AdminiStrator;
import com.onwing.household.util.ModelUtil;

@Service
public class AdminiStratorimpl implements AdminiStratorBiz{

	@Autowired
	private AdminiStratorMapper adminiStratorMapper;

	//登录
	@Override
	public AdminiStratorDto queryLoginUser(AdminiStratorDto adminiStratorDto) throws BusinessException {
		AdminiStrator adminStrator;
		try {
			adminStrator = ModelUtil.dtoToModel(adminiStratorDto, AdminiStrator.class);
			adminStrator=adminiStratorMapper.login(adminStrator);
			adminiStratorDto = ModelUtil.modelToDto(adminStrator, AdminiStratorDto.class);
			adminiStratorDto.setPassword(null);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(AppConstants.QUERY_LOGIN_USER_ERROR_CODE,
					AppConstants.QUERY_LOGIN_USER_ERROR_MESSAGE);
		}
		return adminiStratorDto;
	}
	

}
