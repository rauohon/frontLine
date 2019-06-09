package com.rauOhon.frontLine.cmmn.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class FnlExceptionController {
	
	Logger log = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(FnlException.class)
	public ModelAndView handlerException(HttpServletRequest requset, HttpServletResponse response,
			Exception e) {
		ModelAndView mav = new ModelAndView("template/error.lgon_signUp");
		mav.addObject("msg", e.getMessage());
		log.debug(e.toString());
		return mav;
	}
	
}
