package com.onwing.household.biz.response;

import java.util.List;

import com.onwing.household.biz.dto.HouseHoldDto;

@SuppressWarnings("serial")
public class HouseholdResponse extends BaseResponse{
	
	private List<HouseHoldDto> householdlist;
	
	private HouseHoldDto houseHoldDto;

	public List<HouseHoldDto> getHouseholdlist() {
		return householdlist;
	}

	public void setHouseholdlist(List<HouseHoldDto> householdlist) {
		this.householdlist = householdlist;
	}

	public HouseHoldDto getHouseHoldDto() {
		return houseHoldDto;
	}

	public void setHouseHoldDto(HouseHoldDto houseHoldDto) {
		this.houseHoldDto = houseHoldDto;
	}
	
	

	
	
	

}
