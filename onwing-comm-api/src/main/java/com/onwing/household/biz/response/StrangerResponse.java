package com.onwing.household.biz.response;

import java.util.List;

import com.onwing.household.biz.dto.StrangerDto;

@SuppressWarnings("serial")
public class StrangerResponse extends BaseResponse{
	
	private List<StrangerDto> strangerDtoList;

	public List<StrangerDto> getStrangerDtoList() {
		return strangerDtoList;
	}

	public void setStrangerDtoList(List<StrangerDto> strangerDtoList) {
		this.strangerDtoList = strangerDtoList;
	}
	
	
	
	
	

}
