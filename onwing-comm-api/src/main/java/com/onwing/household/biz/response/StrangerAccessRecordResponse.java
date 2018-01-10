package com.onwing.household.biz.response;

import java.util.List;

import com.onwing.household.biz.dto.StrangerAccessRecordMapDto;

@SuppressWarnings("serial")
public class StrangerAccessRecordResponse extends BaseResponse{
	
	private List<StrangerAccessRecordMapDto> accessRecordMapDtosList;

	public List<StrangerAccessRecordMapDto> getAccessRecordMapDtosList() {
		return accessRecordMapDtosList;
	}

	public void setAccessRecordMapDtosList(List<StrangerAccessRecordMapDto> accessRecordMapDtosList) {
		this.accessRecordMapDtosList = accessRecordMapDtosList;
	}
	
	

}
