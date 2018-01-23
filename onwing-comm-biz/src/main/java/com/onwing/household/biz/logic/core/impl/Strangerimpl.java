package com.onwing.household.biz.logic.core.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.onwing.household.biz.dto.StrangerDto;
import com.onwing.household.biz.exception.BusinessException;
import com.onwing.household.biz.logic.core.StrangerBiz;
import com.onwing.household.comm.dal.dao.StrangerMapper;
import com.onwing.household.comm.dal.model.Stranger;
import com.onwing.household.util.ModelUtil;

@Service
public class Strangerimpl implements StrangerBiz {
	
	private final static Logger logger = LoggerFactory.getLogger(Strangerimpl.class);

	@Autowired
	private StrangerMapper strangerMapper;

	@Override
	public boolean addStranger(StrangerDto strangerDto) throws BusinessException {
		boolean flag = false;
		Stranger stranger;
		try {
			stranger = ModelUtil.dtoToModel(strangerDto, Stranger.class);
			strangerMapper.insertSelective(stranger);
			flag = true;
		} catch (Exception e) {
			logger.error("add stranger fail", e);
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
         logger.error("find All Stranger Error", e);
		}

		return strangerDtolist;
	}

	@Override
	public boolean updateStrangerByIdentify(StrangerDto strangerDto) throws BusinessException {
		boolean flag=false;
		Stranger stranger;
		try {
			stranger = ModelUtil.dtoToModel(strangerDto, Stranger.class);
			//更新访客out_off_into
			strangerMapper.updateStrangerByIdentify(stranger);
			flag=true;
		} catch (Exception e) {
          logger.error("update stranger By Idengtify", e);
		}
		return flag;
	}

	@Override
	public Stranger findStrangerBySelective(Stranger stranger) throws BusinessException {
        List<Stranger> slist=strangerMapper.selectBySelective(stranger);
        if (slist!=null) {
			stranger=slist.get(0);
			return stranger;
		}
		return null;
	}

  



}
