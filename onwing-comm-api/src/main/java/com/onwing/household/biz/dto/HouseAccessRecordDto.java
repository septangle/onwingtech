package com.onwing.household.biz.dto;

import java.util.Date;

public class HouseAccessRecordDto {

	private Long id;

	private Long householdId;

	private String householdName;

	private String roomPath;

	private Date outOffTime;

	private String outOff;

	private String photoUrl;

	private Long communityId;

	private String communityName;

	private Long cameraId;

	private String cameraName;

	private Long controlId;

	private String controlName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getHouseholdId() {
		return householdId;
	}

	public void setHouseholdId(Long householdId) {
		this.householdId = householdId;
	}

	public String getHouseholdName() {
		return householdName;
	}

	public void setHouseholdName(String householdName) {
		this.householdName = householdName;
	}

	public String getRoomPath() {
		return roomPath;
	}

	public void setRoomPath(String roomPath) {
		this.roomPath = roomPath;
	}

	public Date getOutOffTime() {
		return outOffTime;
	}

	public void setOutOffTime(Date outOffTime) {
		this.outOffTime = outOffTime;
	}

	public String getOutOff() {
		return outOff;
	}

	public void setOutOff(String outOff) {
		this.outOff = outOff;
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

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public Long getCameraId() {
		return cameraId;
	}

	public void setCameraId(Long cameraId) {
		this.cameraId = cameraId;
	}

	public String getCameraName() {
		return cameraName;
	}

	public void setCameraName(String cameraName) {
		this.cameraName = cameraName;
	}

	public Long getControlId() {
		return controlId;
	}

	public void setControlId(Long controlId) {
		this.controlId = controlId;
	}

	public String getControlName() {
		return controlName;
	}

	public void setControlName(String controlName) {
		this.controlName = controlName;
	}

}
