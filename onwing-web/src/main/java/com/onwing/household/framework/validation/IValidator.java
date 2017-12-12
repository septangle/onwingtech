package com.onwing.household.framework.validation;

import com.onwing.household.biz.response.ValidationH5Response;
import com.onwing.household.framework.servlet.ResettableStreamHttpServletRequest;

public interface IValidator {

	public ValidationH5Response validate(ResettableStreamHttpServletRequest request, Object handler);

}
