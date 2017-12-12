package com.onwing.household.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.onwing.household.biz.request.HouseholdRequest;
import com.onwing.household.biz.response.HouseholdResponse;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/household")
public class HouseholdController extends BaseController<HouseholdController>{
 
	/**
	 * 增加业主信息
	 */
	@ApiOperation(value = "录入业主信息", httpMethod = "POST", response = HouseholdResponse.class)
	@ApiImplicitParams({
		    @ApiImplicitParam(name = "houseHoldDto.householdName", value = "业主姓名", required = true, dataType = "string"),
			@ApiImplicitParam(name = "houseHoldDto.gender", value = "性别", required = true, dataType = "string"),
			@ApiImplicitParam(name = "houseHoldDto.tel", value = "联系电话", required = true, dataType = "string"),
			@ApiImplicitParam(name = "houseHoldDto.buildingBlockNumber", value = "楼栋号", required = true, dataType = "string"),
			@ApiImplicitParam(name = "houseHoldDto.roomNumber", value = "门牌号", required = true, dataType = "string"),
			@ApiImplicitParam(name = "houseHoldDto.cardNumber", value = "联系电话", required = true, dataType = "string"),
			@ApiImplicitParam(name = "houseHoldDto.remarks", value = "联系电话", required = true, dataType = "string")})
	@RequestMapping(value = "/addHouseHold.do", method = RequestMethod.POST)
	public @ResponseBody HouseholdResponse addHouseHold(@RequestBody HouseholdRequest request,
			HttpServletRequest servletRequest) throws Exception {
		//拼接照片id字段

		return null;
	}
	
	/**
	 * 删除业主信息
	 */
	@ApiOperation(value = "删除业主信息", httpMethod = "POST", response = HouseholdResponse.class)
	@ApiImplicitParams({
		    @ApiImplicitParam(name = "houseHoldDto.id", value = "业主id", required = true, dataType = "string")})
	@RequestMapping(value = "/delHouseHold.do", method = RequestMethod.POST)
	public @ResponseBody HouseholdResponse delHouseHold(@RequestBody HouseholdRequest request,
			HttpServletRequest servletRequest) throws Exception {
		return null;
	}
	
	/**
	 * 修改业主信息
	 */
	
}
