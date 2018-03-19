package com.onwing.household.controller;

import java.io.File;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.onwing.household.biz.dto.HouseHoldDto;
import com.onwing.household.biz.logic.facade.HouseHoldFacade;
import com.onwing.household.biz.request.HouseholdRequest;
import com.onwing.household.biz.response.HouseholdResponse;
import com.onwing.household.comm.AppConstants;
import com.onwing.household.comm.dal.dao.HouseHoldMapper;
import com.onwing.household.comm.dal.model.Community;
import com.onwing.household.comm.dal.model.HouseHold;
import com.onwing.household.util.Page;
import com.onwing.socket.client.CplusClient;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/household")
public class HouseholdController extends BaseController<HouseholdController> {
	@Resource
	private Map<String, String> cplusClientProperties;

	@Autowired
	private HouseHoldFacade householdfacade;

	@Autowired
	private HouseHoldMapper householdMapper;

	/**
	 * 增加业主信息
	 */
	@ApiOperation(value = "录入业主信息", httpMethod = "POST", response = HouseholdResponse.class)
	@RequestMapping(value = "/addHouseHold.do", method = RequestMethod.POST)
	public @ResponseBody HouseholdResponse addHouseHold(@RequestParam("communityId") String communityId,
			@RequestParam("communityName") String communityName, @RequestParam("householdName") String householdName,
			@RequestParam("gender") String gender, @RequestParam("tel") String tel,
			@RequestParam("identifyCard") String identifyCard, @RequestParam("roomPath") String roomPath,
			@RequestParam(value = "cardNumber", required = false) String cardNumber,
			@RequestParam(value = "householdType", required = false) String householdType,
			@RequestParam(value = "remarks", required = false) String remarks, @RequestParam MultipartFile file,
			HttpServletRequest servletRequest) throws Exception {
		String path = System.getProperty("onwing.root") + AppConstants.FILE_PATH;
		File files = new File(path);
		if (!files.exists() && !files.isDirectory()) {
			files.mkdir();
		}
		String tmpFileName = file.getOriginalFilename();//上传的文件名	
		//调用摄像头直接拍摄 无后缀名，默认加上.png
		if (!tmpFileName.contains(".")) {
			tmpFileName = tmpFileName + ".png";
		}
		String extension = tmpFileName.substring(tmpFileName.lastIndexOf("."));
		String nowFileName = identifyCard + extension;

		FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path, nowFileName));
		HouseholdRequest householdRequest = new HouseholdRequest();
		HouseHoldDto holdDto = new HouseHoldDto();
		holdDto.setCommunityId(Long.parseLong(communityId));
		holdDto.setCommunityName(communityName);
		holdDto.setHouseholdName(householdName);
		holdDto.setGender(gender);
		holdDto.setTel(tel);
		holdDto.setIdentifyCard(identifyCard);
		holdDto.setroomPath(roomPath);
		holdDto.setCardNumber(cardNumber);
		holdDto.setHouseholdType(householdType);
		holdDto.setRemarks(remarks);
		holdDto.setPhotoId(AppConstants.FILE_PATH + nowFileName);

		householdRequest.setHouseholdDto(holdDto);

		// 发送消息给c++，让其重新加载白名单图片库
		CplusClient cplusClient = new CplusClient(cplusClientProperties);
		cplusClient.sendReloadPictureMsgMain(nowFileName);
		// end

		return householdfacade.addHouseHold(householdRequest);
	}

	/**
	 * 删除业主信息
	 */
	@ApiOperation(value = "删除业主信息", httpMethod = "POST", response = HouseholdResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "householdDto.id", value = "业主id", required = true, dataType = "string") })
	@RequestMapping(value = "/removeHouseHold.do", method = RequestMethod.POST)
	public @ResponseBody HouseholdResponse removeHouseHold(HttpServletRequest servletRequest,
			@RequestBody HouseholdRequest request) throws Exception {
		return householdfacade.removeHouseHold(request);

	}

	/*	*//**
			* 修改业主信息
			*//*
			@ApiOperation(value = "修改业主信息", httpMethod = "POST", response = HouseholdResponse.class)
			@ApiImplicitParams({
				@ApiImplicitParam(name = "householdDto.id", value = "id", required = true, dataType = "string"),
				@ApiImplicitParam(name = "householdDto.tel", value = "联系电话", required = true, dataType = "string"),
				@ApiImplicitParam(name = "householdDto.cardNumber", value = "卡号", required = true, dataType = "string"),
				@ApiImplicitParam(name = "householdDto.roomPath", value = "门号路径", required = true, dataType = "string"),
				@ApiImplicitParam(name = "householdDto.remarks", value = "备注", required = false, dataType = "string") })
			@RequestMapping(value = "/updateHouseHold.do", method = RequestMethod.POST)
			public @ResponseBody HouseholdResponse updateHouseHold(HttpServletRequest servletRequest,
				@RequestBody HouseholdRequest request) throws Exception {
			return householdfacade.updateHouseHold(request);
			}*/

	/**
	 * 修改业主信息
	 */
	@ApiOperation(value = "修改业主信息", httpMethod = "POST", response = HouseholdResponse.class)
	@RequestMapping(value = "/updateHouseHold.do", method = RequestMethod.POST)
	public @ResponseBody HouseholdResponse updateHouseHold(@RequestParam("id") String id,
			@RequestParam(value = "householdName", required = false) String householdName,
			@RequestParam(value = "gender", required = false) String gender,
			@RequestParam(value = "tel", required = false) String tel,
			@RequestParam(value = "identifyCard", required = true) String identifyCard,
			@RequestParam(value = "roomPath", required = false) String roomPath,
			@RequestParam(value = "cardNumber", required = false) String cardNumber,
			@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam(value = "householdType", required = false) String householdType,
			@RequestParam(value = "remarks", required = false) String remarks, HttpServletRequest servletRequest)
			throws Exception {
		String path = System.getProperty("onwing.root") + AppConstants.FILE_PATH;
		String nowFileName="";
		HouseholdRequest householdRequest = new HouseholdRequest();
		HouseHoldDto holdDto = new HouseHoldDto();
		if (file!=null) {
			String tmpFileName = file.getOriginalFilename();//上传的文件名	
			//调用摄像头直接拍摄 无后缀名，默认加上.png
			if (!tmpFileName.contains(".")) {
				tmpFileName = tmpFileName + ".png";
			}
			String extension = tmpFileName.substring(tmpFileName.lastIndexOf("."));
		    nowFileName = identifyCard + extension;
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path, nowFileName));
			holdDto.setPhotoId(AppConstants.FILE_PATH + nowFileName);

		}
		
		holdDto.setId(Long.parseLong(id));
		holdDto.setHouseholdName(householdName);
		holdDto.setGender(gender);
		holdDto.setTel(tel);
		holdDto.setIdentifyCard(identifyCard);
		holdDto.setroomPath(roomPath);
		holdDto.setCardNumber(cardNumber);
		holdDto.setHouseholdType(householdType);
		holdDto.setRemarks(remarks);

		householdRequest.setHouseholdDto(holdDto);
		
		// 发送消息给c++，让其重新加载白名单图片库
		CplusClient cplusClient = new CplusClient(cplusClientProperties);
		cplusClient.sendReloadPictureMsgMain(nowFileName);
		// end
		return householdfacade.updateHouseHold(householdRequest);
	}

	/**
	 * 查询业主信息
	 */
	@ApiOperation(value = "查询业主信息", httpMethod = "GET", response = HouseholdResponse.class)
	@RequestMapping(value = "/findAllHouseHold.do", method = RequestMethod.GET)
	public @ResponseBody HouseholdResponse findHouseHold(HttpServletRequest servletRequest) throws Exception {
		String searchContent = servletRequest.getParameter("searchContent");
		String communityId = servletRequest.getParameter("communityId");

		if (searchContent.equals("")) {
			searchContent = null;
		}
		HouseHold houseHold = new HouseHold();
		Community community = new Community();
		if (!communityId.equals("-1")) {
			community.setId(Long.parseLong(communityId));

		}
		houseHold.setCommunity(community);
		int count = householdMapper.getTotalCountBySearchContent(searchContent, community.getId());
		Page pageTool = Page.getPageByRequest(servletRequest, count);
		int startRow = (pageTool.getPage() - 1) * Integer.parseInt(servletRequest.getParameter("pageSize"));
		return householdfacade.findAllHouseHold(startRow, pageTool.getPageSize(), searchContent, count,
				community.getId());
	}

	/**
	 * 根据id查询业主信息
	 */
	@ApiOperation(value = "根据id查询业主信息", httpMethod = "POST", response = HouseholdResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "householdDto.id", value = "业主id", required = true, dataType = "string") })
	@RequestMapping(value = "/findHouseHoldById.do", method = RequestMethod.POST)
	public @ResponseBody HouseholdResponse findHouseHoldById(HttpServletRequest servletRequest,
			@RequestBody HouseholdRequest request) throws Exception {
		return householdfacade.findHouseHoldById(request);
	}

}
