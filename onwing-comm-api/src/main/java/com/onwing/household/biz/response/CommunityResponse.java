package com.onwing.household.biz.response;

import java.util.ArrayList;
import java.util.List;

import com.onwing.household.biz.dto.HouseHoldDto;

@SuppressWarnings("serial")
public class CommunityResponse extends BaseResponse {
	private ArrayList<String> childNodeNameList;

	private Long newCommunityId;

	public ArrayList<String> getChildNodeNameList() {
		return childNodeNameList;
	}

	public void setChildNodeNameList(ArrayList<String> childNodeNameList) {
		this.childNodeNameList = childNodeNameList;
	}

	public Long getNewCommunityId() {
		return newCommunityId;
	}

	public void setNewCommunityId(Long newCommunityId) {
		this.newCommunityId = newCommunityId;
	}

}
