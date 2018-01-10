package com.onwing.household.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.onwing.household.biz.logic.facade.AccessRecordFacade;
import com.onwing.household.biz.response.AccessRecordResponse;
import com.onwing.household.comm.dal.dao.AccessRecordMapper;
import com.onwing.household.util.Page;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * 出入记录
 * @author zx
 *
 */
@Controller
@RequestMapping("/accessRecord")
public class AccessRecordController extends BaseController<AccessRecordController>{
	
	@Autowired
	private AccessRecordFacade accessRecordFacade;
	
	@Autowired
	private AccessRecordMapper accessRecordMapper;
	
	@ApiOperation(value = "查询业主出入记录", httpMethod = "GET", response = AccessRecordResponse.class)
	@RequestMapping(value = "/findAllAccessRecord.do", method = RequestMethod.GET)
	public @ResponseBody AccessRecordResponse findAllAccessRecord(HttpServletRequest servletRequest) throws Exception {		
		int count=accessRecordMapper.getCountByHouseHold(null);
 		Page pageTool=Page.getPageByRequest(servletRequest,count);
 		int startRow=(pageTool.getPage()-1) * Integer.parseInt(servletRequest.getParameter("pageSize"));
		return accessRecordFacade.findAllAccessRecord(startRow,pageTool.getPageSize());
	}
	
	
	

}
