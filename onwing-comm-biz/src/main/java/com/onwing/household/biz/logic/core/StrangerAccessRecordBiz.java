package com.onwing.household.biz.logic.core;

import java.util.List;

import com.onwing.household.biz.dto.StrangerAccessRecordMapDto;
import com.onwing.household.biz.exception.BusinessException;

/**
 * 访客出入记录
 * @author zx
 *
 */
public interface StrangerAccessRecordBiz {

	public List<StrangerAccessRecordMapDto> findAllStrangerAccessRecord(int startRow, int pageSize)
			throws BusinessException;

}
