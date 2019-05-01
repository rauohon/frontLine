package com.rauOhon.frontLine.cmmn.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SessionInterceptor extends HandlerInterceptorAdapter {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler) throws Exception {
		log.info(">>>>>>>>>>>>>>>>>>>>SessionInterceptor : {}", request.getRequestURL());
		try {
			if (request.getSession().getAttribute("sessionId") == null) {
				response.sendRedirect("main.do");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}
