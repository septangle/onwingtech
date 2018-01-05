package com.onwing.household.biz.request;

import com.onwing.household.biz.dto.StrangerDto;

@SuppressWarnings("serial")
public class StrangerRequest extends BaseRequest{
	
	private StrangerDto strangerDto;

	public StrangerDto getStrangerDto() {
		return strangerDto;
	}

	public void setStrangerDto(StrangerDto strangerDto) {
		this.strangerDto = strangerDto;
	}
	
	

}
