package com.onwing.household.biz.logic.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.onwing.household.biz.dto.HouseAccessRecordDto;
import com.onwing.household.biz.logic.core.AccessRecordBiz;
import com.onwing.household.biz.logic.facade.AccessRecordFacade;
import com.onwing.household.biz.response.AccessRecordResponse;

@Service
public class AccessRecordFacadeimpl implements AccessRecordFacade {

	@Autowired
	private AccessRecordBiz accessRecordBiz;
	
	
	@Override
	public AccessRecordResponse findAllAccessRecord(int page,int pageSize) throws Exception {
		AccessRecordResponse accessRecordResponse= new AccessRecordResponse();
		List<HouseAccessRecordDto> houseAccessRecordDtolist=accessRecordBiz.selectAccessRecord(page,pageSize);
		accessRecordResponse.setHouseAccessRecordDtosList(houseAccessRecordDtolist);
		return accessRecordResponse;
	}


}
