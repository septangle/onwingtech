package com.onwing.household.biz.logic.core;

import com.onwing.household.biz.dto.StrangerDto;

public interface StrangerBiz {
	
	/**
	 * 新增stranger
	 * @param stranger
	 * @return boolean
	 * @throws BusinessException
	 */
	public boolean addStranger(StrangerDto strangerDto);

}
