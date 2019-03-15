package com.rauOhon.frontLine.user.web;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rauOhon.frontLine.cmmn.utils.FnlMap;
import com.rauOhon.frontLine.user.service.UserService;

@Controller
public class UserController {
	
	@Resource(name = "userService")
	private UserService userService;
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	private ModelAndView mav = new ModelAndView();
	
	@PostMapping(value = "lgout.do")
	public ModelAndView lgout (FnlMap fnlMap) throws Exception {
		log.info(">>>>>>>>>>lgon > controller");
		mav=userService.entrance(2, fnlMap);
		return mav;
	}
	
	@GetMapping(value = "qksqhr.do")
	public ModelAndView qksqhr (FnlMap fnlMap) throws Exception {
		log.info(">>>>>>>>>>반복문");
		mav=userService.entrance(3, fnlMap);
		return mav;
	}
}
