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
import com.onwing.household.biz.logic.facade.AdminiStratorFacade;
import com.onwing.household.biz.request.AdminiStratorRequest;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import com.onwing.household.biz.response.AdminiStratorResponse;
import com.onwing.household.comm.AppConstants;
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
			@ApiImplicitParam(name = "adminiStratorDto.tel", value = "联系电话", required = true, dataType = "string"),
			@ApiImplicitParam(name = "adminiStratorDto.password", value = "密码", required = true, dataType = "string") })
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public @ResponseBody AdminiStratorResponse login(@RequestBody AdminiStratorRequest request,
			HttpServletRequest servletRequest) throws Exception {
		HttpSession session = servletRequest.getSession();
		AdminiStratorDto adminiStratorDto = (AdminiStratorDto) session.getAttribute("adminiStratorDto");
        if (adminiStratorDto!=null) {
        	throw new BusinessException(AppConstants.QUERY_LOGIN_USERLOING_ERROR_CODE,
					AppConstants.QUERY_LOGIN_USERLOING_ERROR_MESSAGE);
		}
        
		//MD5加密
		request.getAdminiStratorDto().setPassword(MD5Util.encodeMD5String(request.getAdminiStratorDto().getPassword()));
		AdminiStratorResponse adminiStratorResponse = adminiStratorFacade.login(request);		
		//存入session
		if (adminiStratorResponse.getAdminiStratorDto()!=null) {
			servletRequest.getSession().setAttribute("adminiStratorDto", adminiStratorResponse.getAdminiStratorDto());
		}

		return adminiStratorResponse;
	}

	

}
