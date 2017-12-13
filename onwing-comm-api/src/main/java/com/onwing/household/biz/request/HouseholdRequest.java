package com.onwing.household.biz.request;

import com.onwing.household.biz.dto.HouseHoldDto;

@SuppressWarnings("serial")
public class HouseholdRequest extends BaseRequest{
	
	private HouseHoldDto householdDto;

	public HouseHoldDto getHouseholdDto() {
		return householdDto;
	}

	public void setHouseholdDto(HouseHoldDto householdDto) {
		this.householdDto = householdDto;
	}
	
	


}
