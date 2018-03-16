package com.onwing.household.comm.dal.model;

public class CommunityCameraControlCount {
	private Long id;
	private String name;
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
