package com.onwing.household.comm.dal.model;

import java.util.Date;

public class HouseAccessRecord {
	
    private Long id;

    private String householdName;
    
    private String buildingBlockNumber;
    
    private String roomNumber;
    
    private Date outOffTime;
    
    private String photoUrl;
    
    private Long communityId;

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
	
	

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public Date getOutOffTime() {
		return outOffTime;
	}

	public void setOutOffTime(Date outOffTime) {
		this.outOffTime = outOffTime;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public Long getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Long communityId) {
		this.communityId = communityId;
	}
    
	
    

}
