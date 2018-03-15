package com.onwing.household.biz.response;

import java.util.List;
import com.onwing.household.biz.dto.HouseAccessRecordDto;

@SuppressWarnings("serial")
public class AccessRecordResponse extends BaseResponse {

	private List<HouseAccessRecordDto> houseAccessRecordDtosList;

	public List<HouseAccessRecordDto> getHouseAccessRecordDtosList() {
		return houseAccessRecordDtosList;
	}

	public void setHouseAccessRecordDtosList(List<HouseAccessRecordDto> houseAccessRecordDtosList) {
		this.houseAccessRecordDtosList = houseAccessRecordDtosList;
	}

}
