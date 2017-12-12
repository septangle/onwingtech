package com.onwing.household.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.onwing.household.framework.servlet.ResettableStreamHttpServletRequest;


public class RequestBodyCachedFilter implements Filter {

	public void destroy() {
		// Nothing to do
	}

	public void init(FilterConfig arg0) throws ServletException {
		// Nothing to do
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		ResettableStreamHttpServletRequest wrappedRequest = new ResettableStreamHttpServletRequest(
				(HttpServletRequest) request);
		chain.doFilter(wrappedRequest, response);
	}

}
