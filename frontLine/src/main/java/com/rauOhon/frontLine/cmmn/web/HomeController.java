package com.rauOhon.frontLine.cmmn.web;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.rauOhon.frontLine.cmmn.utils.FnlMap;
import com.rauOhon.frontLine.user.service.UserService;

@Controller
@RequestMapping(value = "/public")
public class HomeController {

	@Resource(name = "userService")
	private UserService userService;
	
	Logger log = LoggerFactory.getLogger(this.getClass());

	private ModelAndView mav = new ModelAndView();
	
	@GetMapping(value = "/main.do")
	public ModelAndView home() throws Exception {
		log.info("Welcome home!");
		mav.setViewName("cmmn/home.tiles");
		return mav;
	}

	@GetMapping(value = "/signUpPage.do")
	public String signUpPage () throws Exception {
		log.info(">>>>>>>>>>signUpPage");
		return "user/signUp.lgon_signUp";
	}

	@PostMapping(value = "/signUp.do")
	public ModelAndView signUp (FnlMap fnlMap) throws Exception {
		log.info(">>>>>>>>>>signUpPage > signUp");
		mav=userService.entrance(0, fnlMap);
		return mav;
	}

	@GetMapping(value = "/lgonPage.do")
	public String lgonPage () throws Exception {
		log.info(">>>>>>>>>>lgonPage");
		return "user/lgon.lgon_signUp";
	}
	
	@PostMapping(value = "/lgon.do")
	public ModelAndView lgon (FnlMap fnlMap) throws Exception {
		log.info(">>>>>>>>>>lgon > controller");
		mav=userService.entrance(1, fnlMap);
		return mav;
	}
	
}
