package com.onwing.household.inteceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class DefaultInteceptor extends HandlerInterceptorAdapter {

	@SuppressWarnings("all")
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		String uri = request.getRequestURI().replace(request.getContextPath(), "");
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//		WebUtils.setRequest(request);
		//		request.setAttribute("now", new Date());
		//		request.setAttribute("URL", FreemarkerUtils.getStaticTemplateModel(URLUtils.class));
		//		request.setAttribute("String", FreemarkerUtils.getStaticTemplateModel(StringUtils.class));
		//		request.setAttribute("req", new Arequest(request));
		//		request.setAttribute("Dict", FreemarkerUtils.getStaticTemplateModel(DictCache.class));
		//		request.setAttribute("File", fileReferDomain);
		//		request.setAttribute("App", appContext);
		//		request.setAttribute("Version", "1.0");

		// ****************************************************
		String[] uris = request.getRequestURI().replaceFirst(request.getContextPath(), "").split("/");
		if (uris.length > 1) {
			request.setAttribute("module", uris[1]);
		}
		if (uris.length > 2) {
			request.setAttribute("nav", uris[2]);
		}
		// ****************************************************

		String ctx = request.getContextPath();
		String res = ctx + "/resources";
		request.setAttribute("ctx", ctx);
		request.setAttribute("res", res);
		request.setAttribute("js", res + "/js");
		request.setAttribute("css", res + "/css");
		request.setAttribute("images", res + "/images");

		response.setHeader("Pragma", "No-cache");
		return true;
	}

}
