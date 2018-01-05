package com.onwing.household.comm.dal.model;

public class Stranger {
    private Long id;

    private String name;

    private String identifyCard;

    private String tel;

    private String reason;

    private String outOffInto;

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
        this.name = name == null ? null : name.trim();
    }

    public String getIdentifyCard() {
        return identifyCard;
    }

    public void setIdentifyCard(String identifyCard) {
        this.identifyCard = identifyCard == null ? null : identifyCard.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getOutOffInto() {
        return outOffInto;
    }

    public void setOutOffInto(String outOffInto) {
        this.outOffInto = outOffInto == null ? null : outOffInto.trim();
    }
}