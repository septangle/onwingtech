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
import com.onwing.household.comm.dal.dao.CamaraMapper;
import com.onwing.household.comm.dal.model.AccessRecord;
import com.onwing.household.comm.dal.model.Camara;
import com.onwing.household.comm.dal.model.Community;
import com.onwing.household.comm.dal.model.HouseHold;
import com.onwing.household.util.ModelUtil;

@Service
public class AccessRecordFacadeimpl implements AccessRecordFacade {

	@Autowired
	private AccessRecordBiz accessRecordBiz;

	@Autowired
	private CamaraMapper camaraMapper;

	@Override
	public AccessRecordResponse findAllAccessRecord(int startRow, int pageSize, int count, String searchContent,
			String communityId) throws Exception {
		List<HouseAccessRecordDto> houseAccessRecordDtoList = new ArrayList<HouseAccessRecordDto>();
		AccessRecordResponse accessRecordResponse = new AccessRecordResponse();
		AccessRecord accessRecord = new AccessRecord();
		HouseHold houseHold = new HouseHold();// 设置一个空对象
		Camara camera = new Camara();
		Community community = new Community();

		if (!communityId.equals("-1")) {
			community.setId(Long.parseLong(communityId));
		}
		accessRecord.setCommunity(community);
		accessRecord.setHouseHold(houseHold);
		accessRecord.setCamara(camera);
		List<AccessRecord> houseAccessRecordList = accessRecordBiz.selectAccessRecord(startRow, pageSize, searchContent,
				accessRecord);
		if (houseAccessRecordList != null) {
			for (AccessRecord houseAccessRecord : houseAccessRecordList) {
				HouseAccessRecordDto houseAccessRecordDto = ModelUtil.modelToDto(houseAccessRecord,
						HouseAccessRecordDto.class);
				houseAccessRecordDto.setHouseholdId(houseAccessRecord.getHouseHold().getId());
				houseAccessRecordDto.setHouseholdName(houseAccessRecord.getHouseHold().getHouseholdName());
				houseAccessRecordDto.setCommunityId(houseAccessRecord.getCommunity().getId());
				houseAccessRecordDto.setCommunityName(houseAccessRecord.getCommunityName());
				houseAccessRecordDto.setCameraId(houseAccessRecord.getCamara().getId());
				houseAccessRecordDto.setCameraName(houseAccessRecord.getCameraName());
				// todo
				Camara camera2 = camaraMapper.selectByPrimaryKey(houseAccessRecord.getCamara().getId());
				houseAccessRecordDto.setControlId(camera2.getControl().getId());
				houseAccessRecordDto.setControlName(camera2.getControl().getName());
				houseAccessRecordDto.setRoomPath(houseAccessRecord.getHouseHold().getRoomPath());
				// end
				houseAccessRecordDtoList.add(houseAccessRecordDto);
			}
		}
		accessRecordResponse.setHouseAccessRecordDtosList(houseAccessRecordDtoList);
		accessRecordResponse.setTotalNumber(count);
		return accessRecordResponse;
	}

}
