package com.onwing.household.comm.dal.model;

import java.util.Date;

import javafx.scene.Camera;

public class AccessRecord {
	private Long id;

	private HouseHold houseHold;

	private Date outOffTime;

	private String outOff;

	private String photoUrl;

	private Community community;

	private Camara camera;

	private String householdName;

	private String communityName;

	private String cameraName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public HouseHold getHouseHold() {
		return houseHold;
	}

	public void setHouseHold(HouseHold houseHold) {
		this.houseHold = houseHold;
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
		this.outOff = outOff == null ? null : outOff.trim();
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}

	public Camara getCamara() {
		return camera;
	}

	public void setCamara(Camara camera) {
		this.camera = camera;
	}

	public Camara getCamera() {
		return camera;
	}

	public void setCamera(Camara camera) {
		this.camera = camera;
	}

	public String getHouseholdName() {
		return householdName;
	}

	public void setHouseholdName(String householdName) {
		this.householdName = householdName;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public String getCameraName() {
		return cameraName;
	}

	public void setCameraName(String cameraName) {
		this.cameraName = cameraName;
	}

}