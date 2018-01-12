package com.onwing.household.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.onwing.household.biz.logic.facade.StrangerAccessRecordFacade;
import com.onwing.household.biz.response.StrangerAccessRecordResponse;
import com.onwing.household.comm.dal.dao.StrangerAccessRecordMapper;
import com.onwing.household.util.Page;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/strangerAccessRecord")
public class StrangerAccessRecordController extends BaseController<StrangerAccessRecordController>{
	
	@Autowired
	private StrangerAccessRecordFacade strangerAccessRecordFacade;
	
	@Autowired
	private StrangerAccessRecordMapper strangerAccessRecordMapper;
	
	/**
	 * 查询访客出入记录
	 */
	@ApiOperation(value = "查询访客出入记录", httpMethod = "GET", response = StrangerAccessRecordResponse.class)
	@RequestMapping(value = "/findAllStrangerAccessRecord.do", method = RequestMethod.GET)
	public @ResponseBody StrangerAccessRecordResponse findAllStrangerAccessRecord(HttpServletRequest servletRequest) throws Exception {
		int count = strangerAccessRecordMapper.getCountByStrangerAcessRecord();
		Page pageTool=Page.getPageByRequest(servletRequest,count);
 		int startRow=(pageTool.getPage()-1) * Integer.parseInt(servletRequest.getParameter("pageSize"));
		return strangerAccessRecordFacade.findAllStrangerAccessRecord(startRow, pageTool.getPageSize(),count);
	}

}
