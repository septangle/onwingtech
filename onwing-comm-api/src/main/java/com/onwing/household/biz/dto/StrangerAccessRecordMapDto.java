package com.onwing.household.biz.dto;

import java.util.Date;

public class StrangerAccessRecordMapDto {

	private Long id;
	
	private String name;
	
	private String outOffInto;
	
	private Date time;
	
	private String photoUrl;

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

	public String getOutOffInto() {
		return outOffInto;
	}

	public void setOutOffInto(String outOffInto) {
		this.outOffInto = outOffInto;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	
}
