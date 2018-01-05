package com.onwing.household.comm.dal.model;

import java.util.Date;

public class StrangerAccessRecord {
    private Long id;

    private Long strangerId;

    private Date time;

    private String outOffInto;

    private String photoUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStrangerId() {
        return strangerId;
    }

    public void setStrangerId(Long strangerId) {
        this.strangerId = strangerId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getOutOffInto() {
        return outOffInto;
    }

    public void setOutOffInto(String outOffInto) {
        this.outOffInto = outOffInto == null ? null : outOffInto.trim();
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl == null ? null : photoUrl.trim();
    }
}