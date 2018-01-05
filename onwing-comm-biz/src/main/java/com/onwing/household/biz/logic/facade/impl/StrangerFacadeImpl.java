package com.onwing.household.biz.logic.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onwing.household.biz.logic.core.StrangerBiz;
import com.onwing.household.biz.logic.facade.StrangerFacade;
import com.onwing.household.biz.request.StrangerRequest;
import com.onwing.household.biz.response.StrangerResponse;
import com.onwing.household.comm.AppConstants;

@Service
public class StrangerFacadeImpl implements StrangerFacade{

	@Autowired
	private StrangerBiz strangerBiz;
	@Override
	public StrangerResponse addStranger(StrangerRequest strangerRequest) {
		StrangerResponse strangerResponse = new StrangerResponse();
		boolean flag=strangerBiz.addStranger(strangerRequest.getStrangerDto());
		String message = flag ? AppConstants.ADD_STRANGER_SUCCESS_MESSAGE : AppConstants.ADD_STRANGER_FAIL_MESSAGE;
		strangerResponse.setMessage(message);
		return strangerResponse;
	}

}
