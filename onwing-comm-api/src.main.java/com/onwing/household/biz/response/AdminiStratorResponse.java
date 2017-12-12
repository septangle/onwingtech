package com.onwing.household.biz.response;

import com.onwing.household.biz.dto.AdminiStratorDto;

@SuppressWarnings("serial")
public class AdminiStratorResponse extends BaseResponse{
	
	private AdminiStratorDto adminiStratorDto;

	public AdminiStratorDto getAdminiStratorDto() {
		return adminiStratorDto;
	}

	public void setAdminiStratorDto(AdminiStratorDto adminiStratorDto) {
		this.adminiStratorDto = adminiStratorDto;
	}
	
	

}
