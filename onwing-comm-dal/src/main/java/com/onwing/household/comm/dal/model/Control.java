package com.onwing.household.comm.dal.model;

public class Control {
    private Long id;

    private String name;

    private String doorName;

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

    public String getDoorName() {
        return doorName;
    }

    public void setDoorName(String doorName) {
        this.doorName = doorName == null ? null : doorName.trim();
    }
}