package com.onwing.household.biz.logic.core.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.onwing.household.biz.logic.core.AccessRecordBiz;
import com.onwing.household.comm.dal.dao.AccessRecordMapper;
import com.onwing.household.comm.dal.model.AccessRecord;

@Service
public class AccessRecordimpl implements AccessRecordBiz {

	private final Logger logger = LoggerFactory.getLogger(AccessRecordimpl.class);
	@Autowired
	private AccessRecordMapper accessRecordMapper;

	@Override
	public List<AccessRecord> selectAccessRecord(int startRow, int pageSize, String searchContent,
			AccessRecord accessRecord) {
		List<AccessRecord> houseAccessRecordList = accessRecordMapper.selectAccessRecord(startRow, pageSize,
				searchContent, accessRecord);

		return houseAccessRecordList;
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
