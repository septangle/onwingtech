package com.onwing.household.biz.logic.core.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.onwing.household.biz.dto.AdminiStratorDto;
import com.onwing.household.biz.dto.UserRoleDto;
import com.onwing.household.biz.exception.BusinessException;
import com.onwing.household.biz.logic.core.AdminiStratorBiz;
import com.onwing.household.biz.request.UserRoleRequest;
import com.onwing.household.comm.AppConstants;
import com.onwing.household.comm.dal.dao.AdminiStratorMapper;
import com.onwing.household.comm.dal.dao.RoleMapper;
import com.onwing.household.comm.dal.dao.UserRoleMapper;
import com.onwing.household.comm.dal.model.AdminiStrator;
import com.onwing.household.comm.dal.model.Community;
import com.onwing.household.comm.dal.model.Role;
import com.onwing.household.comm.dal.model.UserRole;
import com.onwing.household.util.MD5Util;
import com.onwing.household.util.ModelUtil;

@Service
public class AdminiStratorimpl implements AdminiStratorBiz {
	private final static Logger logger = LoggerFactory.getLogger(AdminiStratorimpl.class);

	@Autowired
	private AdminiStratorMapper adminiStratorMapper;

	@Autowired
	private UserRoleMapper userRoleMapper;

	@Autowired
	private RoleMapper roleMapper;

	// 登录
	@Override
	public UserRoleDto queryLoginUser(AdminiStratorDto adminiStratorDto) throws BusinessException {
		UserRoleDto userRoleDto = new UserRoleDto();
		AdminiStrator adminStrator;
		try {
			adminStrator = ModelUtil.dtoToModel(adminiStratorDto, AdminiStrator.class);
			adminStrator = adminiStratorMapper.login(adminStrator);
			UserRole userRole = userRoleMapper.selectByUserId(adminStrator.getId());
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

	// 创建物业管理员、物业保安等用户
	@Override
	public void addUser(UserRoleRequest request) throws BusinessException {
		String username = request.getUsername();
		String rawPassword = request.getPassword();
		String tel = request.getTel();
		String roleName = request.getRoleName();
		Long communityId = request.getCommunityId();

		String password = MD5Util.encodeMD5String(rawPassword);// MD5加密

		// tbl_administrator插入一个user
		AdminiStrator user = new AdminiStrator();
		user.setAdminName(username);
		List<AdminiStrator> oldUsers = adminiStratorMapper.selectBySelective(user);
		if (oldUsers != null && oldUsers.size() >= 1) {
			// 用户名已经存在
			logger.error("username: {} already existed in db", username);
			throw new BusinessException(AppConstants.EXISTED_CODE, AppConstants.EXISTED_MESSAGE);
		}
		user.setPassword(password);
		user.setTel(tel);
		adminiStratorMapper.insertSelective(user);

		// tbl_user_role 增加一条记录
		UserRole userRole = new UserRole();
		AdminiStrator adminiStrator = new AdminiStrator();
		adminiStrator.setId((long) user.getId());//user.getId()是回调，mybatis自动将insert后的记录id 设置到user对象中
		userRole.setAdminiStrator(adminiStrator);
		Community community = new Community();
		community.setId(communityId);
		userRole.setCommunity(community);

		Role role = new Role();
		role.setRoleName(roleName);
		List<Role> roles = roleMapper.selectBySelective(role);
		if (roles == null || roles.size() != 1) {
			logger.error("roles by roleName: {} in db is null or not only", roleName);
			throw new BusinessException(AppConstants.ROLE_EXISTED_CODE, AppConstants.ROLE_EXISTED_MESSAGE);
		}
		role.setId(roles.get(0).getId());
		userRole.setRole(role);
		userRoleMapper.insertSelective(userRole);
		// end
	}

}
