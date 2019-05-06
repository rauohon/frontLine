package com.rauOhon.frontLine.user.web;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rauOhon.frontLine.cmmn.utils.FnlMap;
import com.rauOhon.frontLine.user.service.UserService;

/**
 * 1. 클래스	: UserController.java
 * 2. 작성자	: RAU
 * 3. 작성시간	: 2019. 5. 4.
 *
 * <pre>
 * 설명			: 
 * </pre>
 *
 * 	수정일		수정자		수정내용
 * ----------------------------------------
 * 
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
	
	@Resource(name = "userService")
	private UserService userService;
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	private ModelAndView mav = new ModelAndView();
	
	@PostMapping(value = "/lgout.do")
	public ModelAndView lgout (FnlMap fnlMap) throws Exception {
		log.info(">>>>>>>>>>lgon > controller");
		mav=userService.entrance(2, fnlMap);
		return mav;
	}
	
}
