package com.onwing.household.biz.logic.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onwing.household.biz.dto.StrangerAccessRecordMapDto;
import com.onwing.household.biz.logic.core.StrangerAccessRecordBiz;
import com.onwing.household.biz.logic.facade.StrangerAccessRecordFacade;
import com.onwing.household.biz.response.StrangerAccessRecordResponse;

@Service
public class StrangerAccessRecordFacadeimpl implements StrangerAccessRecordFacade {

	@Autowired
	private StrangerAccessRecordBiz strangerAccessRecordBiz;

	@Override
	public StrangerAccessRecordResponse findAllStrangerAccessRecord(int startRow, int pageSize) throws Exception {
		StrangerAccessRecordResponse accessRecordResponse= new StrangerAccessRecordResponse();
		List<StrangerAccessRecordMapDto> accessRecordMapDtosList = strangerAccessRecordBiz
				.findAllStrangerAccessRecord(startRow, pageSize);
		accessRecordResponse.setAccessRecordMapDtosList(accessRecordMapDtosList);
		return accessRecordResponse;
	}

}
