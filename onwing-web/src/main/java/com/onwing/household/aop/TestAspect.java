package com.onwing.household.aop;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import com.onwing.household.biz.dto.AdminiStratorDto;
import com.onwing.household.biz.response.BaseResponse;
import com.onwing.household.biz.response.Error;
import com.onwing.household.comm.AppConstants;

/**
 * 
 * @author zx
 *  登录验证
 */
@Aspect
@Component
public class TestAspect {
	private static Logger logger = Logger.getLogger(TestAspect.class);

	@Around("execution(* com.onwing.household.controller.*.*(..)) "
			+ "and !execution(* com.onwing.household.controller.AdminiStratorController.login(..))")
	public Object beforeMethod(ProceedingJoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();

		Class<?> returnTypeClazz = ((MethodSignature) signature).getReturnType();

		BaseResponse response = new BaseResponse();

		logger.info("----------------执行方法-----------------");

		//获取httpServletRequest
		Object[] servletRequest = joinPoint.getArgs();
		HttpServletRequest request = (HttpServletRequest) servletRequest[0];
		AdminiStratorDto adminiStratorDto = (AdminiStratorDto) request.getSession().getAttribute("adminiStratorDto");

		if (adminiStratorDto == null) {
			try {
				response = (BaseResponse) returnTypeClazz.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			Error error = new Error();
			error.setCode(AppConstants.NOT_LOGIN_CODE);
			error.setMessage(AppConstants.NOT_LOGIN_MESSAGE);
			response.setError(error);
		} else {
			try {
				response = (BaseResponse) joinPoint.proceed();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		return response;

	}

}
