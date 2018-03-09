package com.onwing.household.biz.logic.core;

import java.util.List;
import com.onwing.household.biz.dto.HouseAccessRecordDto;
import com.onwing.household.comm.dal.model.AccessRecord;

/**
 * 查询出入记录
 * @author zx
 *
 */
public interface AccessRecordBiz {
	
	/**
	 * 业主出入记录
	 * @param 
	 * @return list<HouseAccessRecordDto>
	 * @throws Exception
	 */
	public List<HouseAccessRecordDto> selectAccessRecord(int page,int pageSize,String communityId);
	
	public void addAccessRecord(AccessRecord accessRecord);
	
	

}
