package com.onwing.household.biz.response;

import java.util.ArrayList;

import com.onwing.household.biz.dto.AdminiStratorDto;
import com.onwing.household.biz.dto.CommunityDto;
import com.onwing.household.biz.dto.UserRoleDto;

@SuppressWarnings("serial")
public class AdminiStratorResponse extends BaseResponse {

	private AdminiStratorDto adminiStratorDto;

	private UserRoleDto userRoleDto;

	private ArrayList<CommunityDto> communityList;

	public AdminiStratorDto getAdminiStratorDto() {
		return adminiStratorDto;
	}

	public void setAdminiStratorDto(AdminiStratorDto adminiStratorDto) {
		this.adminiStratorDto = adminiStratorDto;
	}

	public UserRoleDto getUserRoleDto() {
		return userRoleDto;
	}

	public void setUserRoleDto(UserRoleDto userRoleDto) {
		this.userRoleDto = userRoleDto;
	}

	public ArrayList<CommunityDto> getCommunityList() {
		return communityList;
	}

	public void setCommunityList(ArrayList<CommunityDto> communityList) {
		this.communityList = communityList;
	}

}
