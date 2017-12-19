package com.onwing.household.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FaceRecognitionMsg {
	@JsonProperty("data")
	private String photoName;
	
	private String time;
	
	@JsonIgnore
	private String enterOrLeave;
	
	@JsonIgnore
	private String cameraId;
	
	public String getPhotoName() {
		return photoName;
	}
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getEnterOrLeave() {
		return enterOrLeave;
	}
	public void setEnterOrLeave(String enterOrLeave) {
		this.enterOrLeave = enterOrLeave;
	}
	public String getCameraId() {
		return cameraId;
	}
	public void setCameraId(String cameraId) {
		this.cameraId = cameraId;
	}
	@Override
	public String toString() {
		return "FaceRecognitionMsg [photoName=" + photoName + ", time=" + time + ", enterOrLeave=" + enterOrLeave
				+ ", cameraId=" + cameraId + "]";
	}
	

}
