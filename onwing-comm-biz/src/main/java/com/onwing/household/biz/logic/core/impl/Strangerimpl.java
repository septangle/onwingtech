package com.onwing.household.biz.logic.core.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onwing.household.biz.dto.StrangerDto;
import com.onwing.household.biz.logic.core.StrangerBiz;
import com.onwing.household.comm.dal.dao.StrangerAccessRecordMapper;
import com.onwing.household.comm.dal.dao.StrangerMapper;
import com.onwing.household.comm.dal.model.Stranger;
import com.onwing.household.comm.dal.model.StrangerAccessRecord;
import com.onwing.household.util.ModelUtil;

@Service
public class Strangerimpl implements StrangerBiz{

	@Autowired
	private StrangerMapper strangerMapper;
	
	@Autowired
	private StrangerAccessRecordMapper strangerAccessRecordMapper;
	
	
	@Override
	public boolean addStranger(StrangerDto strangerDto) {
		boolean flag=false;
		Stranger stranger;
		StrangerAccessRecord strangerAccessRecord=new StrangerAccessRecord();
		try {
			stranger = ModelUtil.dtoToModel(strangerDto, Stranger.class);
			strangerMapper.insertSelective(stranger);
			strangerAccessRecord.setOutOffInto("0");
			strangerAccessRecord.setStrangerId(stranger.getId());
			strangerAccessRecord.setTime(new Date());
			strangerAccessRecord.setPhotoUrl(strangerDto.getFileUrl());
			strangerAccessRecordMapper.insertSelective(strangerAccessRecord);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

}
