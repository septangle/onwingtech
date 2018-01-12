package com.onwing.household.controller;

import java.io.File;

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
import com.onwing.household.util.Page;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
@Controller
@RequestMapping("/household")
public class HouseholdController extends BaseController<HouseholdController> {

	@Autowired
	private HouseHoldFacade householdfacade;

	@Autowired
	private HouseHoldMapper householdMapper;

	/**
	 * 增加业主信息
	 */
	@ApiOperation(value = "录入业主信息", httpMethod = "POST", response = HouseholdResponse.class)
	@RequestMapping(value = "/addHouseHold.do", method = RequestMethod.POST)
	public @ResponseBody HouseholdResponse addHouseHold(@RequestParam("householdName") String householdName,
			@RequestParam("gender") String gender, @RequestParam("tel") String tel,
			@RequestParam("identifyCard") String identifyCard,
			@RequestParam("buildingBlockNumber") String buildingBlockNumber,
			@RequestParam("roomNumber") String roomNumber, @RequestParam("cardNumber") String cardNumber,
			@RequestParam("remarks") String remarks, @RequestParam MultipartFile file,
			HttpServletRequest servletRequest) throws Exception {
		String path = System.getProperty("onwing.root") + AppConstants.FILE_PATH;
		File files = new File(path);
		if (!files.exists() && !files.isDirectory()) {
			files.mkdir();
		}
		String  tmpFileName=file.getOriginalFilename();//上传的文件名			
		String extension = tmpFileName.substring(tmpFileName.lastIndexOf("."));
		String nowFileName=identifyCard+extension;
		
		FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path,nowFileName));
        HouseholdRequest householdRequest = new HouseholdRequest();
        HouseHoldDto holdDto = new HouseHoldDto();
        holdDto.setHouseholdName(householdName);
        holdDto.setGender(gender);
        holdDto.setTel(tel);
        holdDto.setIdentifyCard(identifyCard);
        holdDto.setBuildingBlockNumber(buildingBlockNumber);
        holdDto.setRoomNumber(roomNumber);
        holdDto.setCardNumber(cardNumber);
        holdDto.setRemarks(remarks);
        holdDto.setPhotoId(AppConstants.FILE_PATH+nowFileName);
        
        householdRequest.setHouseholdDto(holdDto);
		
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

	/**
	 * 修改业主信息
	 */
	@ApiOperation(value = "修改业主信息", httpMethod = "POST", response = HouseholdResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "householdDto.id", value = "id", required = true, dataType = "string"),
			@ApiImplicitParam(name = "householdDto.householdName", value = "业主姓名", required = false, dataType = "string"),
			@ApiImplicitParam(name = "householdDto.gender", value = "性别", required = false, dataType = "string"),
			@ApiImplicitParam(name = "householdDto.tel", value = "联系电话", required = false, dataType = "string"),
			@ApiImplicitParam(name = "householdDto.remarks", value = "备注", required = false, dataType = "string") })
	@RequestMapping(value = "/updateHouseHold.do", method = RequestMethod.POST)
	public @ResponseBody HouseholdResponse updateHouseHold(HttpServletRequest servletRequest,
			@RequestBody HouseholdRequest request) throws Exception {
		return householdfacade.updateHouseHold(request);
	}

	/**
	 * 查询业主信息
	 */
	@ApiOperation(value = "查询业主信息", httpMethod = "GET", response = HouseholdResponse.class)
	@RequestMapping(value = "/findAllHouseHold.do", method = RequestMethod.GET)
	public @ResponseBody HouseholdResponse findHouseHold(HttpServletRequest servletRequest) throws Exception {
		int count = householdMapper.getCountByHousehold();
		Page pageTool = Page.getPageByRequest(servletRequest, count);
		int startRow = (pageTool.getPage() - 1) * Integer.parseInt(servletRequest.getParameter("pageSize"));
		String fileStr = System.getProperty("onwing.root");
		return householdfacade.findAllHouseHold(startRow, pageTool.getPageSize(), fileStr + AppConstants.FILE_PATH,count);
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
