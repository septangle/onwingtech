package com.onwing.household.biz.dto;

public class CommunityDto {

	private Long id;

	private String name;

	private String address;

	private Long communityId;

	private Long cameraCount;

	private Long controlCount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Long communityId) {
		this.communityId = communityId;
	}

	public Long getCameraCount() {
		return cameraCount;
	}

	public void setCameraCount(Long cameraCount) {
		this.cameraCount = cameraCount;
	}

	public Long getControlCount() {
		return controlCount;
	}

	public void setControlCount(Long controlCount) {
		this.controlCount = controlCount;
	}

}
