package com.onwing.household.biz.logic.core;

import java.util.List;

import com.onwing.household.biz.dto.HouseHoldDto;
import com.onwing.household.biz.exception.BusinessException;

/**
 * 住户信息
 * @author zx
 *
 */
public interface HouseHoldBiz {
	
	/**
	 * 新增HouseHold
	 * 
	 * @param HouseHold 
	 * @return boolean
	 * @throws BusinessException
	 */
	public boolean addHousehold(HouseHoldDto householdDto) throws BusinessException;
	
	/**
	 * 删除HouseHold
	 * 
	 * @param id
	 * @return boolean
	 * @throws BusinessException
	 */
	public boolean removeHousehold(HouseHoldDto householdDto) throws BusinessException;
	
	/**
	 * 更新HouseHold
	 * 
	 * @param HouseHoldDto
	 * @return boolean
	 * @throws BusinessException
	 */
	public boolean updateHousehold(HouseHoldDto householdDto) throws BusinessException;
	
	/**
	 * 查询HouseHold
	 * 
	 * @return list<HouseHoldDto>
	 * @throws BusinessException
	 */
	public List<HouseHoldDto> findHousehold() throws BusinessException;

}
