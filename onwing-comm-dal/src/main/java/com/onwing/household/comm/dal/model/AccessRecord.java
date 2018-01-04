package com.onwing.household.comm.dal.model;

import java.util.Date;

public class AccessRecord {
    private Long id;

    private Long householdId;

    private Date outOffTime;

    private String outOff;

    private String strangerPhotoId;
    
    private String strangerId;

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

    public String getStrangerPhotoId() {
        return strangerPhotoId;
    }

    public void setStrangerPhotoId(String strangerPhotoId) {
        this.strangerPhotoId = strangerPhotoId == null ? null : strangerPhotoId.trim();
    }

	public String getStrangerId() {
		return strangerId;
	}

	public void setStrangerId(String strangerId) {
		this.strangerId = strangerId;
	}
    
    
}