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
import com.onwing.household.comm.dal.model.AccessRecord;
import com.onwing.household.comm.dal.model.Camara;
import com.onwing.household.comm.dal.model.Community;
import com.onwing.household.comm.dal.model.HouseHold;
import com.onwing.household.util.Page;
import com.wordnik.swagger.annotations.ApiOperation;

import javafx.scene.Camera;

/**
 * 出入记录
 * 
 * @author zx
 *
 */
@Controller
@RequestMapping("/accessRecord")
public class AccessRecordController extends BaseController<AccessRecordController> {

	@Autowired
	private AccessRecordFacade accessRecordFacade;

	@Autowired
	private AccessRecordMapper accessRecordMapper;

	@ApiOperation(value = "查询业主出入记录", httpMethod = "GET", response = AccessRecordResponse.class)
	@RequestMapping(value = "/findAllAccessRecord.do", method = RequestMethod.GET)
	public @ResponseBody AccessRecordResponse findAllAccessRecord(HttpServletRequest servletRequest) throws Exception {
		String searchContent = servletRequest.getParameter("searchContent");
		if (searchContent.equals("")) {
			searchContent = null;
		}
		// 查询所有小区出入记录还是某小区出入记录 todo 获取小区id
		String communityId = servletRequest.getParameter("communityId");
		AccessRecord accessRecord = new AccessRecord();
		HouseHold houseHold = new HouseHold();// 设置一个空对象 fix bug
												// entity.houseHold.id in
												// mapper.xml
		Camara camera = new Camara();
		Community community = new Community();

		if (!communityId.equals("-1")) {
			community.setId(Long.parseLong(communityId));
		}
		accessRecord.setCommunity(community);
		accessRecord.setHouseHold(houseHold);
		accessRecord.setCamara(camera);

		int count = accessRecordMapper.getTotalCountBySearchContent(searchContent, accessRecord);
		Page pageTool = Page.getPageByRequest(servletRequest, count);
		int startRow = (pageTool.getPage() - 1) * Integer.parseInt(servletRequest.getParameter("pageSize"));
		return accessRecordFacade.findAllAccessRecord(startRow, pageTool.getPageSize(), count, searchContent,
				communityId);
	}

}
