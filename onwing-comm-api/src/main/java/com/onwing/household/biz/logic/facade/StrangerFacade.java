package com.onwing.household.biz.logic.facade;

import com.onwing.household.biz.request.StrangerRequest;
import com.onwing.household.biz.response.StrangerResponse;

public interface StrangerFacade {
	
	
	/**
	 * 录入访客信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public StrangerResponse addStranger(StrangerRequest strangerRequest,String fileUrl) throws Exception;
	
	
	/**
	 * 查询访客列表
	 */
	public StrangerResponse findAllStranger(int page,int pageSize) throws Exception;
	
	/**
	 * 更新访客出入
	 */
	public StrangerResponse updateStrangerByIdentify(StrangerRequest strangerRequest,String fileUrl) throws Exception;
	
	

}
