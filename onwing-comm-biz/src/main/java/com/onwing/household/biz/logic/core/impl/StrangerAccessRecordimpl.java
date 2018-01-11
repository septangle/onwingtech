package com.onwing.household.biz.logic.core.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onwing.household.biz.dto.StrangerAccessRecordMapDto;
import com.onwing.household.biz.exception.BusinessException;
import com.onwing.household.biz.logic.core.StrangerAccessRecordBiz;
import com.onwing.household.comm.dal.dao.StrangerAccessRecordMapper;
import com.onwing.household.comm.dal.model.StrangerAccessRecordMap;
import com.onwing.household.util.ModelUtil;

@Service
public class StrangerAccessRecordimpl implements StrangerAccessRecordBiz {

	@Autowired
	private StrangerAccessRecordMapper strangerAccessRecordMapper;

	@Override
	public List<StrangerAccessRecordMapDto> findAllStrangerAccessRecord(int startRow, int pageSize)
			throws BusinessException {
		List<StrangerAccessRecordMapDto> accessRecordMapDtosList=null;
		try {
			accessRecordMapDtosList = new ArrayList<StrangerAccessRecordMapDto>();
			List<StrangerAccessRecordMap> accessRecordMaps = strangerAccessRecordMapper.getAllStrangerAccessRecord(startRow,
					pageSize, null);
			String path = System.getProperty("onwing.root");
			if (accessRecordMaps!=null) {
				for (StrangerAccessRecordMap strangerAccessRecordMap : accessRecordMaps) {
					strangerAccessRecordMap.setPhotoUrl(path+strangerAccessRecordMap.getPhotoUrl());
					accessRecordMapDtosList.add(ModelUtil.modelToDto(strangerAccessRecordMap, StrangerAccessRecordMapDto.class));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return accessRecordMapDtosList;
	}

}
