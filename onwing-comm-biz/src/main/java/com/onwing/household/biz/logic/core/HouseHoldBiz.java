package com.onwing.household.biz.logic.core;

import java.util.List;

import com.onwing.household.biz.dto.HouseHoldDto;
import com.onwing.household.biz.exception.BusinessException;
import com.onwing.household.comm.dal.model.HouseHold;

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
	public List<HouseHoldDto> findHousehold(int startRow,int pageSize,String fileStr,String searchContent,Long communityId) throws BusinessException;
	
	/**
	 * 根据id查询HouseHold
	 * @param id
	 * @return HouHoldDto
	 * @throws BusineException
	 */
	public HouseHoldDto findHouseholdById(HouseHoldDto houseHoldDto) throws BusinessException;
	
	/**
	 * 根据条件查询HouseHold
	 * @param
	 * @return HouseHoldDto
	 * @throws BusineException
	 */
	public List<HouseHoldDto> queryHouseHold(HouseHoldDto houseHoldDto) throws BusinessException;
	
	/**
	 * 根据身份证查询住户
	 * @param identifyCard
	 * @return
	 * @throws BusinessException
	 */
	public HouseHold findHouseHoldBySelective(HouseHold houseHold) throws BusinessException;
	
	

}
