package com.onwing.household.biz.dto;

import java.util.Date;

public class AccessRecordDto {

	private Long id;

	private Long householdId;

	private Date outOffTime;

	private String outOff;


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
	
	

}
