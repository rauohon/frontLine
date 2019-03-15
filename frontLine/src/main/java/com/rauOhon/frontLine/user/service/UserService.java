package com.rauOhon.frontLine.user.service;

import org.springframework.web.servlet.ModelAndView;

import com.rauOhon.frontLine.cmmn.utils.FnlMap;

/**
 * 1. 클래스	: UserService.java
 * 2. 작성자	: RAU
 * 3. 작성시간	: 2018. 10. 7.
 *
 * <pre>
 * 설명			:
 * </pre>
 *
 * 	수정일		수정자		수정내용
 * ----------------------------------------
 * 
 */
public interface UserService {
	
	/**
	 * 처리내용	:  
	 * @method	: entrance
	 * @author	: RAU
	 * @param	: 
	 * @return	: 
	 */
	public ModelAndView entrance(int jobCnt, FnlMap fnlMap) throws Exception;

}
