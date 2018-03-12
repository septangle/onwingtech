package com.onwing.household.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.onwing.household.biz.logic.facade.AdminiStratorFacade;
import com.onwing.household.biz.request.AdminiStratorRequest;
import com.onwing.household.biz.request.UserRoleRequest;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import com.onwing.household.biz.response.AdminiStratorResponse;
import com.onwing.household.util.MD5Util;

/**
 * 管理员信息
 * @author zx
 *
 */
@Controller
@RequestMapping("/adminiStator")
public class AdminiStratorController extends BaseController<AdminiStratorController> {

	@Autowired
	private AdminiStratorFacade adminiStratorFacade;//管理员

	/**
	 * 登录
	 */
	@ApiOperation(value = "登录", httpMethod = "POST", response = AdminiStratorResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "adminiStratorDto.adminName", value = "用户名", required = true, dataType = "string"),
			@ApiImplicitParam(name = "adminiStratorDto.password", value = "密码", required = true, dataType = "string") })
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public @ResponseBody AdminiStratorResponse login(@RequestBody AdminiStratorRequest request,
			HttpServletRequest servletRequest) throws Exception {
		//MD5加密
		request.getAdminiStratorDto().setPassword(MD5Util.encodeMD5String(request.getAdminiStratorDto().getPassword()));
		AdminiStratorResponse adminiStratorResponse = adminiStratorFacade.login(request);		
		
		return adminiStratorResponse;
	}
	
	/**
	 * 创建用户（物业管理员，物业保安）
	 */
	@ApiOperation(value = "创建用户（物业管理员，物业保安）", httpMethod = "POST", response = AdminiStratorResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "string"),
			@ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "string"),
			@ApiImplicitParam(name = "tel", value = "电话", required = true, dataType = "string"),
			@ApiImplicitParam(name = "roleName", value = "角色名称", required = true, dataType = "string"),
			@ApiImplicitParam(name = "communityId", value = "小区id", required = true, dataType = "string")
	})
	@RequestMapping(value = "/addUser.do", method = RequestMethod.POST)
	public @ResponseBody AdminiStratorResponse addUser(@RequestBody UserRoleRequest request,
			HttpServletRequest servletRequest) throws Exception {
		AdminiStratorResponse adminiStratorResponse = adminiStratorFacade.addUser(request);
		return adminiStratorResponse;
	}
	
}
