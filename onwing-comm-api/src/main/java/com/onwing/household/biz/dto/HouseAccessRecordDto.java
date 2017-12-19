package com.onwing.household.biz.dto;

import java.util.Date;

public class HouseAccessRecordDto {
	
	private Long id;

    private String householdName;
    
    private String buildingBlockNumber;
    
    private Date outOffTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHouseholdName() {
		return householdName;
	}

	public void setHouseholdName(String householdName) {
		this.householdName = householdName;
	}

	public String getBuildingBlockNumber() {
		return buildingBlockNumber;
	}

	public void setBuildingBlockNumber(String buildingBlockNumber) {
		this.buildingBlockNumber = buildingBlockNumber;
	}

	public Date getOutOffTime() {
		return outOffTime;
	}

	public void setOutOffTime(Date outOffTime) {
		this.outOffTime = outOffTime;
	}
    
    

}
