package com.onwing.household.biz.response;

import java.util.ArrayList;
import java.util.List;

import com.onwing.household.biz.dto.HouseHoldDto;

@SuppressWarnings("serial")
public class CommunityResponse extends BaseResponse {
	private ArrayList<String> childNodeNameList;

	public ArrayList<String> getChildNodeNameList() {
		return childNodeNameList;
	}

	public void setChildNodeNameList(ArrayList<String> childNodeNameList) {
		this.childNodeNameList = childNodeNameList;
	}

}
