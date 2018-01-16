package com.onwing.household.biz.logic.core.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.onwing.household.biz.dto.HouseAccessRecordDto;
import com.onwing.household.biz.logic.core.AccessRecordBiz;
import com.onwing.household.comm.dal.dao.AccessRecordMapper;
import com.onwing.household.comm.dal.model.AccessRecord;
import com.onwing.household.comm.dal.model.HouseAccessRecord;
import com.onwing.household.util.ModelUtil;

@Service
public class AccessRecordimpl implements AccessRecordBiz {

	@Autowired
	private AccessRecordMapper accessRecordMapper;

	@Override
	public List<HouseAccessRecordDto> selectAccessRecord(int startRow,int pageSize) {
		List<HouseAccessRecordDto> houseAccessRecordDtoList = new ArrayList<HouseAccessRecordDto>();
		HouseAccessRecord houseAccessRecord = null;
		try {
			List<HouseAccessRecord> houseAccessRecordList = accessRecordMapper.selectAccessRecord(startRow,pageSize,houseAccessRecord);
			String path = System.getProperty("onwing.root");
			if (houseAccessRecordList != null) {
				for (HouseAccessRecord houseAccessRecordParam : houseAccessRecordList) {
					houseAccessRecordParam.setPhotoUrl(path+houseAccessRecordParam.getPhotoUrl());
					houseAccessRecordDtoList
							.add(ModelUtil.modelToDto(houseAccessRecordParam, HouseAccessRecordDto.class));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return houseAccessRecordDtoList;
	}

	@Override
	public void addAccessRecord(AccessRecord accessRecord) {
		try {
			accessRecordMapper.insertSelective(accessRecord);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	
	
	

}
