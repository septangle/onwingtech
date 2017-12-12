package com.onwing.household.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onwing.household.biz.exception.BusinessException;
import com.onwing.household.biz.response.BaseResponse;
import com.onwing.household.biz.response.Error;
import com.onwing.household.comm.AppConstants;

import java.lang.reflect.Type;
import java.lang.reflect.ParameterizedType;

public class BaseController<T> {

	protected Log logger;

	public BaseController() {
		decideLogger();
	}

	@SuppressWarnings("unchecked")
	private void decideLogger() {
		Type genType = getClass().getGenericSuperclass();
		if (genType instanceof ParameterizedType) {
			Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
			Class<T> concreteClazz = (Class<T>) params[0];
			logger = LogFactory.getLog(concreteClazz);
		} else {
			logger = LogFactory.getLog(this.getClass());
		}
	}

	@ExceptionHandler(Exception.class)
	private @ResponseBody BaseResponse exceptionHandler(Exception e) {
		BaseResponse response = new BaseResponse();
		
		String errCode = "";
		String errMessage = "";
		if(e instanceof BusinessException) {
			errCode = ((BusinessException)e).getCode();
			errMessage = ((BusinessException)e).getMessage();
		} else {
			errCode = AppConstants.FAIL_CODE;
			errMessage = e.getMessage();
		}
		response.setError(new Error(errCode, errMessage));
		logger.error(e.getMessage(), e.getCause());
		return response;
	}
}
