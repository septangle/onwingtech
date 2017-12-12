package com.onwing.household.framework.validation;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.onwing.household.biz.response.ValidationH5Response;
import com.onwing.household.framework.servlet.ResettableStreamHttpServletRequest;



public class UserTokenAccessValidator implements IValidator {

	@SuppressWarnings("unused")
	private static final Log LOGGER = LogFactory.getLog(UserTokenAccessValidator.class);


	@Override
	public ValidationH5Response validate(ResettableStreamHttpServletRequest request, Object handler) {
		ValidationH5Response vH5Response = new ValidationH5Response();
		//TODO 
		return vH5Response;
	}


}
