package com.onwing.household.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onwing.household.biz.dto.AdminiStratorDto;
import com.onwing.household.biz.exception.BusinessException;
import com.onwing.household.biz.logic.facade.HouseHoldFacade;
import com.onwing.household.biz.request.HouseholdRequest;
import com.onwing.household.biz.response.HouseholdResponse;
import com.onwing.household.comm.AppConstants;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/household")
public class HouseholdController extends BaseController<HouseholdController> {

	@Autowired
	private HouseHoldFacade householdfacade;

	/**
	 * 增加业主信息
	 */
	@ApiOperation(value = "录入业主信息", httpMethod = "POST", response = HouseholdResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "householdDto.householdName", value = "业主姓名", required = true, dataType = "string"),
			@ApiImplicitParam(name = "householdDto.gender", value = "性别", required = true, dataType = "string"),
			@ApiImplicitParam(name = "householdDto.tel", value = "联系电话", required = true, dataType = "string"),
			@ApiImplicitParam(name = "householdDto.buildingBlockNumber", value = "楼栋号", required = true, dataType = "string"),
			@ApiImplicitParam(name = "householdDto.roomNumber", value = "门牌号", required = true, dataType = "string"),
			@ApiImplicitParam(name = "householdDto.cardNumber", value = "卡号", required = true, dataType = "string"),
			@ApiImplicitParam(name = "householdDto.remarks", value = "备注", required = true, dataType = "string") })
	@RequestMapping(value = "/addHouseHold.do", method = RequestMethod.POST)
	public @ResponseBody HouseholdResponse addHouseHold(@RequestBody HouseholdRequest request,
			HttpServletRequest servletRequest) throws Exception {
		HttpSession session = servletRequest.getSession();
		AdminiStratorDto adminiStratorDto = (AdminiStratorDto) session.getAttribute("adminiStratorDto");
		if (adminiStratorDto==null) {
        	throw new BusinessException(AppConstants.NOT_LOGIN_CODE,
					AppConstants.NOT_LOGIN_MESSAGE);
		}
		return householdfacade.addHouseHold(request);
	}

	/**
	 * 删除业主信息
	 */
	@ApiOperation(value = "删除业主信息", httpMethod = "POST", response = HouseholdResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "householdDto.id", value = "业主id", required = true, dataType = "string") })
	@RequestMapping(value = "/removeHouseHold.do", method = RequestMethod.POST)
	public @ResponseBody HouseholdResponse removeHouseHold(@RequestBody HouseholdRequest request,
			HttpServletRequest servletRequest) throws Exception {
		HttpSession session = servletRequest.getSession();
		AdminiStratorDto adminiStratorDto = (AdminiStratorDto) session.getAttribute("adminiStratorDto");
		if (adminiStratorDto==null) {
        	throw new BusinessException(AppConstants.NOT_LOGIN_CODE,
					AppConstants.NOT_LOGIN_MESSAGE);
		}
		return householdfacade.removeHouseHold(request);

	}

	/**
	 * 修改业主信息
	 */
	@ApiOperation(value = "修改业主信息", httpMethod = "POST", response = HouseholdResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "householdDto.id", value = "id", required = true, dataType = "string"),
			@ApiImplicitParam(name = "householdDto.householdName", value = "业主姓名", required = true, dataType = "string"),
			@ApiImplicitParam(name = "householdDto.gender", value = "性别", required = true, dataType = "string"),
			@ApiImplicitParam(name = "householdDto.tel", value = "联系电话", required = true, dataType = "string"),
			@ApiImplicitParam(name = "householdDto.remarks", value = "备注", required = true, dataType = "string") })
	@RequestMapping(value = "/updateHouseHold.do", method = RequestMethod.POST)
	public @ResponseBody HouseholdResponse updateHouseHold(@RequestBody HouseholdRequest request,
			HttpServletRequest servletRequest) throws Exception {
		HttpSession session = servletRequest.getSession();
		AdminiStratorDto adminiStratorDto = (AdminiStratorDto) session.getAttribute("adminiStratorDto");
		if (adminiStratorDto==null) {
        	throw new BusinessException(AppConstants.NOT_LOGIN_CODE,
					AppConstants.NOT_LOGIN_MESSAGE);
		}
		return householdfacade.updateHouseHold(request);
	}

	/**
	 * 查询业主信息
	 */
	@ApiOperation(value = "查询业主信息", httpMethod = "GET", response = HouseholdResponse.class)
	@RequestMapping(value = "/findAllHouseHold.do", method = RequestMethod.GET)
	public @ResponseBody HouseholdResponse findHouseHold(HttpServletRequest servletRequest) throws Exception {
		HttpSession session = servletRequest.getSession();
		AdminiStratorDto adminiStratorDto = (AdminiStratorDto) session.getAttribute("adminiStratorDto");
		if (adminiStratorDto==null) {
        	throw new BusinessException(AppConstants.NOT_LOGIN_CODE,
					AppConstants.NOT_LOGIN_MESSAGE);
		}
		return householdfacade.findAllHouseHold();
	}
	
	/**
	 * 根据id查询业主信息
	 */
	@ApiOperation(value = "根据id查询业主信息", httpMethod = "POST", response = HouseholdResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "householdDto.id", value = "业主id", required = true, dataType = "string") })
	@RequestMapping(value = "/findHouseHoldById.do", method = RequestMethod.POST)
	public @ResponseBody HouseholdResponse findHouseHoldById(@RequestBody HouseholdRequest request,
			HttpServletRequest servletRequest) throws Exception {
		HttpSession session = servletRequest.getSession();
		AdminiStratorDto adminiStratorDto = (AdminiStratorDto) session.getAttribute("adminiStratorDto");
		if (adminiStratorDto==null) {
        	throw new BusinessException(AppConstants.NOT_LOGIN_CODE,
					AppConstants.NOT_LOGIN_MESSAGE);
		}	
		return householdfacade.findHouseHoldById(request);
	}

	
}
