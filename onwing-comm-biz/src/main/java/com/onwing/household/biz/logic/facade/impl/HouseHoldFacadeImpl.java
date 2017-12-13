package com.onwing.household.biz.logic.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onwing.household.biz.dto.HouseHoldDto;
import com.onwing.household.biz.logic.core.HouseHoldBiz;
import com.onwing.household.biz.logic.facade.HouseHoldFacade;
import com.onwing.household.biz.request.HouseholdRequest;
import com.onwing.household.biz.response.HouseholdResponse;
import com.onwing.household.comm.AppConstants;

@Service
public class HouseHoldFacadeImpl implements HouseHoldFacade{
	
	@Autowired
	private HouseHoldBiz householdBiz;

	@Override
	public HouseholdResponse addHouseHold(HouseholdRequest householdRequest) throws Exception {
		HouseholdResponse householdResponse= new HouseholdResponse();
		boolean flag=householdBiz.addHousehold(householdRequest.getHouseholdDto());
		String message=flag ? AppConstants.ADD_HOUSE_HOLD_SUCCESS_MESSAGE : AppConstants.ADD_HOUSE_HOLD_FAIL_MESSAGE;
		householdResponse.setMessage(message);
		return householdResponse;
	}

	@Override
	public HouseholdResponse removeHouseHold(HouseholdRequest householdRequest) throws Exception {
		HouseholdResponse householdResponse= new HouseholdResponse();
		boolean flag=householdBiz.removeHousehold(householdRequest.getHouseholdDto());
		String message=flag ? AppConstants.REMOVE_HOUSE_HOLD_SUCCESS_MESSAGE : AppConstants.REMOVE_HOUSE_HOLD_FAIL_MESSAGE;
		householdResponse.setMessage(message);
		return householdResponse;
	}

	@Override
	public HouseholdResponse updateHouseHold(HouseholdRequest householdRequest) throws Exception {
		HouseholdResponse householdResponse= new HouseholdResponse();
		boolean flag=householdBiz.updateHousehold(householdRequest.getHouseholdDto());
		String message=flag ? AppConstants.UPDATE_HOUSE_HOLD_SUCCESS_MESSAGE : AppConstants.UPDATE_HOUSE_HOLD_FAIL_MESSAGE;
		householdResponse.setMessage(message);
		return householdResponse;
	}

	@Override
	public HouseholdResponse findAllHouseHold() throws Exception {
		HouseholdResponse householdResponse= new HouseholdResponse();
		List<HouseHoldDto> householdList = householdBiz.findHousehold();
		householdResponse.setHouseholdlist(householdList);
		return householdResponse;
	}

}
