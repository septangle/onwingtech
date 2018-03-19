package com.onwing.household.biz.logic.facade.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onwing.household.biz.dto.CommunityDto;
import com.onwing.household.biz.dto.HouseHoldDto;
import com.onwing.household.biz.dto.UserRoleCommunityDto;
import com.onwing.household.biz.dto.UserRoleDto;
import com.onwing.household.biz.exception.BusinessException;
import com.onwing.household.biz.logic.core.AdminiStratorBiz;
import com.onwing.household.biz.logic.facade.AdminiStratorFacade;
import com.onwing.household.biz.request.AdminiStratorRequest;
import com.onwing.household.biz.request.UserRoleRequest;
import com.onwing.household.biz.response.AdminiStratorResponse;
import com.onwing.household.comm.AppConstants;
import com.onwing.household.comm.dal.dao.AdminiStratorMapper;
import com.onwing.household.comm.dal.dao.CommunityMapper;
import com.onwing.household.comm.dal.dao.UserRoleMapper;
import com.onwing.household.comm.dal.model.AdminiStrator;
import com.onwing.household.comm.dal.model.Community;
import com.onwing.household.comm.dal.model.CommunityCameraControlCount;
import com.onwing.household.comm.dal.model.HouseHold;
import com.onwing.household.comm.dal.model.UserRole;
import com.onwing.household.comm.dal.model.UserRoleCommunity;
import com.onwing.household.util.ModelUtil;

@Service
public class AdminiStratorFacadeImpl implements AdminiStratorFacade {
	private final static Logger logger = LoggerFactory.getLogger(AdminiStratorFacadeImpl.class);

	@Autowired
	private AdminiStratorBiz adminiStratorBiz;

	@Autowired
	private AdminiStratorMapper adminiStratorMapper;

	@Autowired
	private CommunityMapper communityMapper;

	@Autowired
	private UserRoleMapper userRoleMapper;

	@Override
	public AdminiStratorResponse login(AdminiStratorRequest request) throws Exception {
		AdminiStratorResponse adminiStratorResponse = new AdminiStratorResponse();
		UserRoleDto userRoleDto = adminiStratorBiz.queryLoginUser(request.getAdminiStratorDto());
		userRoleDto.setCommunityId(null);
		userRoleDto.setCommunityName(null);
		adminiStratorResponse.setUserRoleDto(userRoleDto);
		// 获取所有小区的门禁数和摄像头数目
		List<CommunityCameraControlCount> communityCameraControlCountList = communityMapper.queryCameraControlCount();
		HashMap<Long, CommunityCameraControlCount> hMap = new HashMap<Long, CommunityCameraControlCount>();
		if (communityCameraControlCountList != null) {
			for (CommunityCameraControlCount communityCameraControlCount : communityCameraControlCountList) {
				Long communityId = communityCameraControlCount.getId();
				hMap.put(communityId, communityCameraControlCount);
			}
		}
		// 判断用户角色
		ArrayList<CommunityDto> communityDtoList = new ArrayList<CommunityDto>();
		String roleName = userRoleDto.getRoleName();
		if (roleName.equals("超级管理员")) {
			// 返回所有小区列表 todo
			Community record = new Community();
			List<Community> communityList = communityMapper.selectBySelective(record);
			if (communityList == null || communityList.size() == 0) {
				// 小区列表为空
				adminiStratorResponse.setCommunityList(communityDtoList);
			} else {
				for (Community community : communityList) {
					CommunityDto communityDto = new CommunityDto();
					communityDto.setAddress(community.getAddress());
					communityDto.setCommunityId(community.getId());
					communityDto.setName(community.getName());

					CommunityCameraControlCount countObj = hMap.get(community.getId());
					communityDto.setCameraCount(countObj == null ? 0 : countObj.getCameraCount());
					communityDto.setControlCount(countObj == null ? 0 : countObj.getControlCount());
					communityDtoList.add(communityDto);
				}
				adminiStratorResponse.setCommunityList(communityDtoList);
			}
		} else {
			// 其他角色，返回与之绑定的小区信息
			String username = request.getAdminiStratorDto().getAdminName();
			AdminiStrator record = new AdminiStrator();
			record.setAdminName(username);
			List<AdminiStrator> userList = adminiStratorMapper.selectBySelective(record);
			if (userList == null || userList.size() != 1) {
				// todo
				logger.error("user with username: {} from db is null or not only", username);
				throw new BusinessException(AppConstants.NAME_MULTI_OR_NULL_CODE,
						AppConstants.NAME_MULTI_OR_NULL_MESSAGE);
			}
			Long userId = userList.get(0).getId();
			UserRole userRole = userRoleMapper.selectByUserId(userId);
			if (userRole == null) {
				logger.error("userId: {} is not existed in table: tbl_user_role", userId);
			}
			Community community = userRole.getCommunity();
			CommunityDto communityDto = new CommunityDto();
			communityDto.setAddress(community.getAddress());
			communityDto.setCommunityId(community.getId());
			communityDto.setName(community.getName());

			CommunityCameraControlCount countObj = hMap.get(community.getId());
			communityDto.setCameraCount(countObj == null ? 0 : countObj.getCameraCount());
			communityDto.setControlCount(countObj == null ? 0 : countObj.getControlCount());
			communityDtoList.add(communityDto);
			adminiStratorResponse.setCommunityList(communityDtoList);
		}
		return adminiStratorResponse;
	}

	@Override
	public AdminiStratorResponse addUser(UserRoleRequest request) throws Exception {
		AdminiStratorResponse adminiStratorResponse = new AdminiStratorResponse();
		adminiStratorBiz.addUser(request);
		return adminiStratorResponse;
	}

	@Override
	public AdminiStratorResponse findAllUser(String communityId) throws Exception {
		AdminiStratorResponse adminiStratorResponse = new AdminiStratorResponse();
		List<UserRoleCommunityDto> communityDtosList= new ArrayList<UserRoleCommunityDto>();
		
		List<UserRoleCommunity> userList=adminiStratorMapper.selectbyAllUser(communityId);
		if (userList != null) {
			for (UserRoleCommunity userRoleCommunity : userList) {
				communityDtosList.add(ModelUtil.modelToDto(userRoleCommunity, UserRoleCommunityDto.class));
			}
		}
		adminiStratorResponse.setUserRoleCommunityDto(communityDtosList);
		return adminiStratorResponse;
	}

}
