package com.onwing.household.biz.logic.core;

import java.util.List;
import com.onwing.household.comm.dal.model.AccessRecord;

/**
 * 查询出入记录
 * 
 * @author zx
 *
 */
public interface AccessRecordBiz {

	/**
	 * 业主出入记录
	 * 
	 * @param
	 * @return list<HouseAccessRecordDto>
	 * @throws Exception
	 */
	public List<AccessRecord> selectAccessRecord(int startRow, int pageSize, String searchContent,
			AccessRecord accessRecord);

	public void addAccessRecord(AccessRecord accessRecord);

}
