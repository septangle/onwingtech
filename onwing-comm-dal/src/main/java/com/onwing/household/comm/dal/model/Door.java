package com.onwing.household.comm.dal.model;

public class Door {
	
	private Long id;
	
	private String name;
	
	private Community community;

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

	public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}


	
	

}
