package com.onwing.household.comm.dal.model;

import java.util.Date;

public class AccessRecord {
    private Long id;

    private HouseHold houseHold;

    private Date outOffTime;

    private String outOff;
    
    private String photoUrl;

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

    
    
    
}