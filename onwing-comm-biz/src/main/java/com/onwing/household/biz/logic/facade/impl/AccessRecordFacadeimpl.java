package com.onwing.household.biz.logic.facade.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.onwing.household.biz.dto.HouseAccessRecordDto;
import com.onwing.household.biz.dto.HouseHoldDto;
import com.onwing.household.biz.logic.core.AccessRecordBiz;
import com.onwing.household.biz.logic.facade.AccessRecordFacade;
import com.onwing.household.biz.response.AccessRecordResponse;
import com.onwing.household.comm.dal.model.AccessRecord;
import com.onwing.household.comm.dal.model.Community;
import com.onwing.household.util.ModelUtil;

@Service
public class AccessRecordFacadeimpl implements AccessRecordFacade {

	@Autowired
	private AccessRecordBiz accessRecordBiz;

	@Override
	public AccessRecordResponse findAllAccessRecord(int startRow, int pageSize, int count, String searchContent,
			String communityId) throws Exception {
		List<HouseAccessRecordDto> houseAccessRecordDtoList = new ArrayList<HouseAccessRecordDto>();
		AccessRecordResponse accessRecordResponse = new AccessRecordResponse();
		AccessRecord accessRecord = new AccessRecord();
		if (!communityId.equals("-1")) {
			Community community = new Community();
			community.setId(Long.parseLong(communityId));
			accessRecord.setCommunity(community);
		}
		List<AccessRecord> houseAccessRecordList = accessRecordBiz.selectAccessRecord(startRow, pageSize, searchContent,
				accessRecord);
		if (houseAccessRecordList != null) {
			for (AccessRecord houseAccessRecord : houseAccessRecordList) {
				houseAccessRecordDtoList.add(ModelUtil.modelToDto(houseAccessRecord, HouseAccessRecordDto.class));
			}
		}
		accessRecordResponse.setHouseAccessRecordDtosList(houseAccessRecordDtoList);
		accessRecordResponse.setTotalNumber(count);
		return accessRecordResponse;
	}

}
