package com.onwing.household.biz.logic.core.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.onwing.household.biz.dto.StrangerDto;
import com.onwing.household.biz.exception.BusinessException;
import com.onwing.household.biz.logic.core.StrangerBiz;
import com.onwing.household.comm.dal.dao.StrangerAccessRecordMapper;
import com.onwing.household.comm.dal.dao.StrangerMapper;
import com.onwing.household.comm.dal.model.Stranger;
import com.onwing.household.comm.dal.model.StrangerAccessRecord;
import com.onwing.household.util.ModelUtil;

@Service
public class Strangerimpl implements StrangerBiz {

	@Autowired
	private StrangerMapper strangerMapper;

	@Autowired
	private StrangerAccessRecordMapper strangerAccessRecordMapper;

	@Override
	public boolean addStranger(StrangerDto strangerDto, String fileUrl) throws BusinessException {
		boolean flag = false;
		Stranger stranger;
		try {
			stranger = ModelUtil.dtoToModel(strangerDto, Stranger.class);
			strangerMapper.insertSelective(stranger);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<StrangerDto> findAllStranger(int startRow,int pageSize) throws BusinessException {
		List<StrangerDto> strangerDtolist = null;
		try {
			strangerDtolist = new ArrayList<StrangerDto>();
			List<Stranger> strangerList = strangerMapper.getAllStranger(startRow,pageSize,null);
			if (strangerList != null) {
				for (Stranger strangerParam : strangerList) {
					strangerDtolist.add(ModelUtil.modelToDto(strangerParam, StrangerDto.class));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return strangerDtolist;
	}

	@Override
	public boolean updateStrangerByIdentify(StrangerDto strangerDto, String fileUrl) throws BusinessException {
		boolean flag=false;
		Stranger stranger;
		try {
			stranger = ModelUtil.dtoToModel(strangerDto, Stranger.class);
			//更新访客out_off_into
			strangerMapper.updateStrangerByIdentify(stranger);
			List<Stranger> strangerList = strangerMapper.selectBySelective(stranger);
			//插入访客出入记录
			StrangerAccessRecord accessRecord = new StrangerAccessRecord();
			accessRecord.setOutOffInto("1");
			accessRecord.setPhotoUrl(fileUrl);
			accessRecord.setStrangerId(strangerList.get(0).getId());
			accessRecord.setTime(new Date());
			strangerAccessRecordMapper.insertSelective(accessRecord);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Stranger findStrangerByIdentifyCard(String identifyCard) throws BusinessException {
        Stranger stranger = new Stranger();
        stranger.setIdentifyCard(identifyCard);
        List<Stranger> slist=strangerMapper.selectBySelective(stranger);
        if (slist!=null) {
			stranger=slist.get(0);
			return stranger;
		}
		return null;
	}

}
