package com.onwing.household.biz.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.onwing.household.biz.exception.BusinessException;
import com.onwing.household.biz.response.BaseResponse;
import com.onwing.household.comm.AppConstants;
import com.onwing.household.biz.response.Error;



@Aspect
@Component
public class MethodInvoke {

	private static final Log LOGGER = LogFactory.getLog(MethodInvoke.class);
	@Around("execution(* com.onwing.household.biz.logic.facade.*.*(..))")
	public BaseResponse aroundMethod(ProceedingJoinPoint joinPoint) {

		Signature signature = joinPoint.getSignature();
		Class<?> returnTypeClazz = ((MethodSignature) signature).getReturnType();

		BaseResponse response = null;
		String implClazzName = "";
		String methodName = "";
		String fullMethodName = "";

		try {

			implClazzName = signature.getDeclaringTypeName();
			methodName = joinPoint.getSignature().getName();
			fullMethodName = implClazzName + "." + methodName;

			//			LOGGER = LogFactory.getLog(implClazzName);

			LOGGER.debug(fullMethodName + " begin");

			response = (BaseResponse) joinPoint.proceed();

			LOGGER.debug(fullMethodName + " end");

		} catch (BusinessException be) {
			LOGGER.error(be.getMessage(), be);
			try {
				response = (BaseResponse) returnTypeClazz.newInstance();
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
            Error err = new Error();
			err.setCode(be.getCode());
			err.setMessage(be.getMessage());
			response.setError(err);
		} catch (Throwable te) {
			LOGGER.error(fullMethodName + " err", te);
			try {
				response = (BaseResponse) returnTypeClazz.newInstance();
			} catch (Exception e) {
				LOGGER.error(fullMethodName + " err", e);
			}
			Error err = new Error();
			err.setCode(AppConstants.SYSTEM_ERROR_CODE);
			err.setMessage(te.getMessage());
			response.setError(err);
		}
		return response;
	}

}