package com.onwing.household.comm.dal.model;

public class UserRole {
    private AdminiStrator adminiStrator;

    private Role role;

    private Community community;

	public AdminiStrator getAdminiStrator() {
		return adminiStrator;
	}

	public void setAdminiStrator(AdminiStrator adminiStrator) {
		this.adminiStrator = adminiStrator;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}

   
}