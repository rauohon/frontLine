package com.rauOhon.frontLine.cmmn.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(FnlException.class)
	public ModelAndView handlerException(HttpServletRequest requset, HttpServletResponse response,
			Exception e) {
		ModelAndView mav = new ModelAndView("template/error");
		mav.addObject("msg", e.getMessage());
		return mav;
	}
	
}
