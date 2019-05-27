package com.rauOhon.frontLine.shop.service;

import org.springframework.web.servlet.ModelAndView;

import com.rauOhon.frontLine.cmmn.utils.FnlMap;

/**
 * 1. 클래스	: ShopMainService.java
 * 2. 작성자	: RAU
 * 3. 작성시간	: 2019. 5. 25.
 *
 * <pre>
 * 설명			: 상점 서비스
 * </pre>
 *
 * 	수정일		수정자		수정내용
 * ----------------------------------------
 * 
 */
public interface ShopMainService {

	public ModelAndView entrance(int jobCnt, FnlMap fnlMap) throws Exception;

	public String selectShopItemList(FnlMap fnlMap) throws Exception;
}
