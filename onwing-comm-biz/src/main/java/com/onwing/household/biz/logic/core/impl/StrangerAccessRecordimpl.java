package com.onwing.household.biz.logic.core.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.onwing.household.biz.dto.StrangerAccessRecordMapDto;
import com.onwing.household.biz.exception.BusinessException;
import com.onwing.household.biz.logic.core.StrangerAccessRecordBiz;
import com.onwing.household.comm.dal.dao.AccessRecordMapper;
import com.onwing.household.comm.dal.dao.StrangerAccessRecordMapper;
import com.onwing.household.comm.dal.model.AccessRecord;
import com.onwing.household.comm.dal.model.StrangerAccessRecord;
import com.onwing.household.comm.dal.model.StrangerAccessRecordMap;
import com.onwing.household.util.ModelUtil;

@Service
public class StrangerAccessRecordimpl implements StrangerAccessRecordBiz {

	private final static Logger logger = LoggerFactory.getLogger(StrangerAccessRecordimpl.class);

	@Autowired
	private StrangerAccessRecordMapper strangerAccessRecordMapper;

	@Autowired
	private AccessRecordMapper accessRecordMapper;

	@Override
	public List<StrangerAccessRecordMapDto> findAllStrangerAccessRecord(int startRow, int pageSize)
			throws BusinessException {
		List<StrangerAccessRecordMapDto> accessRecordMapDtosList = null;
		try {
			accessRecordMapDtosList = new ArrayList<StrangerAccessRecordMapDto>();
			List<StrangerAccessRecordMap> accessRecordMaps = strangerAccessRecordMapper
					.getAllStrangerAccessRecord(startRow, pageSize, null);
			if (accessRecordMaps != null) {
				for (StrangerAccessRecordMap strangerAccessRecordMap : accessRecordMaps) {
					accessRecordMapDtosList
							.add(ModelUtil.modelToDto(strangerAccessRecordMap, StrangerAccessRecordMapDto.class));
				}
			}
		} catch (Exception e) {
           logger.error("find All StrangerAccessRecord", e);			
		}
		return accessRecordMapDtosList;
	}

	@Override
	public void startCleanUpAccessRecord(int numberDay) {
		try {
			String rootPath = System.getProperty("onwing.root");

			List<StrangerAccessRecord> slist = strangerAccessRecordMapper.getAccessRecordByDate(numberDay);
			if (slist != null && slist.size() > 0) {
				// 删除访客出入图片
				for (StrangerAccessRecord strangerAccessRecord : slist) {
					File file = new File(rootPath + strangerAccessRecord.getPhotoUrl());
					file.delete();
				}
				// 删除访客出入记录
				strangerAccessRecordMapper.delAccessRecordByDate(numberDay);
			}

			List<AccessRecord> alist = accessRecordMapper.getAccessRecordByDate(numberDay);
			if (alist != null && alist.size() > 0) {
				// 删除住户出入图片
				for (AccessRecord accessRecord : alist) {
					File file = new File(rootPath + accessRecord.getPhotoUrl());
					file.delete();
				}
				// 删除住户出入记录
				accessRecordMapper.delAccessRecordByDate(numberDay);
			}

		} catch (Exception e) {
			logger.error("start CleanUp AccessRecord faild", e);
		}

	}

}
