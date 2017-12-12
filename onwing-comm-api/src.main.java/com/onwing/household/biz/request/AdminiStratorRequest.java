package com.onwing.household.biz.request;

import com.onwing.household.biz.dto.AdminiStratorDto;

@SuppressWarnings("serial")
public class AdminiStratorRequest extends BaseRequest{
	
	private AdminiStratorDto adminiStratorDto;

	public AdminiStratorDto getAdminiStratorDto() {
		return adminiStratorDto;
	}

	public void setAdminiStratorDto(AdminiStratorDto adminiStratorDto) {
		this.adminiStratorDto = adminiStratorDto;
	}
	
	

}
