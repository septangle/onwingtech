package com.onwing.household.biz.logic.facade;

import com.onwing.household.biz.request.HouseholdRequest;
import com.onwing.household.biz.response.HouseholdResponse;

public interface HouseHoldFacade {
	/**
	 * 录入住户信息
	 * @param request
	 * @return
	 * @throws Exception
	 * @author zx
	 */
	public HouseholdResponse addHouseHold(HouseholdRequest householdRequest) throws Exception;
	
	/**
	 * 删除住户信息
	 * @param request
	 * @return
	 * @throws Exception
	 * @author zx
	 */
	public HouseholdResponse removeHouseHold(HouseholdRequest householdRequest)throws Exception;
	
	/**
	 * 更新住户信息
	 * @param request
	 * @return
	 * @throws Exception
	 * @author zx
	 */
    public HouseholdResponse updateHouseHold(HouseholdRequest householdRequest) throws Exception;
    
    /**
     * 查询业主信息
     * @param
     * @return
     * @throws Exception
	 * @author zx
     */
    public HouseholdResponse findAllHouseHold(int startRow,int pageSize,String fileStr,String searchContent,int count,Long communityId) throws Exception;
    
    /**
     * 根据id查询业主信息
     * @param id
     * @return
     * @throws Exception
     */
    public HouseholdResponse findHouseHoldById(HouseholdRequest householdRequest) throws Exception;
    
    /**
     * 根据条件查询用户信息
     * @param
     * @return
     * @throws Exception
     */
    public HouseholdResponse queryHouseHold(HouseholdRequest householdRequest) throws Exception;
    
}
