package com.onwing.household.comm.dal.model;

public class Camara {
    private Long id;

    private String name;

    private Control control;

    private String direction;
    
    private String ip;
    
    private String replayId;

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


    public Control getControl() {
		return control;
	}

	public void setControl(Control control) {
		this.control = control;
	}

	public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction == null ? null : direction.trim();
    }

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getReplayId() {
		return replayId;
	}

	public void setReplayId(String replayId) {
		this.replayId = replayId;
	}
    
    
}