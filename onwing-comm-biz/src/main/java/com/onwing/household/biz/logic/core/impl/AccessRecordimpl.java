package com.onwing.household.biz.logic.core.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private final Logger logger = LoggerFactory.getLogger(AccessRecordimpl.class);
	@Autowired
	private AccessRecordMapper accessRecordMapper;

	@Override
	public List<HouseAccessRecordDto> selectAccessRecord(int startRow,int pageSize,String communityId) {
		List<HouseAccessRecordDto> houseAccessRecordDtoList = new ArrayList<HouseAccessRecordDto>();
		HouseAccessRecord houseAccessRecord = null;
		try {
			Long cid =Long.parseLong(communityId);
			List<HouseAccessRecord> houseAccessRecordList = accessRecordMapper.selectAccessRecord(startRow,pageSize,cid,houseAccessRecord);
			if (houseAccessRecordList != null) {
				for (HouseAccessRecord houseAccessRecordParam : houseAccessRecordList) {
					houseAccessRecordDtoList
							.add(ModelUtil.modelToDto(houseAccessRecordParam, HouseAccessRecordDto.class));
				}
			}
		} catch (Exception e) {
         logger.error("selectAccessRecord error",e);
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
