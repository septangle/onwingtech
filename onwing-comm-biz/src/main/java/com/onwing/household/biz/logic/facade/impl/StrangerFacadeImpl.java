package com.onwing.household.biz.logic.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onwing.household.biz.dto.StrangerDto;
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
	public StrangerResponse addStranger(StrangerRequest strangerRequest,String fileUrl) throws Exception{
		StrangerResponse strangerResponse = new StrangerResponse();
		boolean flag=strangerBiz.addStranger(strangerRequest.getStrangerDto(),fileUrl);
		String message = flag ? AppConstants.ADD_STRANGER_SUCCESS_MESSAGE : AppConstants.ADD_STRANGER_FAIL_MESSAGE;
		String code=flag?AppConstants.SUCCESS_CODE:AppConstants.FAIL_CODE;	
		strangerResponse.setMessage(message);
		strangerResponse.setCode(code);
		return strangerResponse;
	}
	@Override
	public StrangerResponse findAllStranger(int startRow,int pageSize) throws Exception {
		StrangerResponse strangerResponse = new StrangerResponse();
      	List<StrangerDto> strangerList=	strangerBiz.findAllStranger(startRow,pageSize);
      	strangerResponse.setStrangerDtoList(strangerList);
      	return strangerResponse;
	}
	@Override
	public StrangerResponse updateStrangerByIdentify(StrangerRequest strangerRequest, String fileUrl) throws Exception {
		StrangerResponse strangerResponse = new StrangerResponse();
		boolean flag=strangerBiz.updateStrangerByIdentify(strangerRequest.getStrangerDto(), fileUrl);
		String message = flag ? AppConstants.UPDATE_STRANGER_SUCCESS_MESSAGE : AppConstants.UPDATE_STRANGER_FAIL_MESSAGE;
		String code=flag?AppConstants.SUCCESS_CODE:AppConstants.FAIL_CODE;	
		strangerResponse.setMessage(message);
		strangerResponse.setCode(code);
		return strangerResponse;
	}


}
