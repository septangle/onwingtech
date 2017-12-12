package com.onwing.household.biz.logic.core;


import com.onwing.household.biz.dto.AdminiStratorDto;
import com.onwing.household.biz.exception.BusinessException;

/**
 * 登录用户查询、校验
 * @author zx
 */
public interface AdminiStratorBiz {
	
	/**
	 * 登录
	 * 
	 * @param AdminiStratorDto 
	 *  
	 * @return AdminiStratorDto
	 * @throws Exception
	 */
	public AdminiStratorDto queryLoginUser(AdminiStratorDto adminiStratorDto) throws BusinessException;
}
