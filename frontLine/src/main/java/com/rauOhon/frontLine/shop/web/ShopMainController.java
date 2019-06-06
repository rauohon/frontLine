package com.rauOhon.frontLine.shop.web;

import java.util.HashMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rauOhon.frontLine.cmmn.utils.FnlMap;
import com.rauOhon.frontLine.shop.service.ShopMainService;

/**
 * 1. 클래스	: ShopMainController.java
 * 2. 작성자	: RAU
 * 3. 작성시간	: 2019. 5. 25.
 *
 * <pre>
 * 설명			: 상점 컨트롤러
 * </pre>
 *
 * 	수정일		수정자		수정내용
 * ----------------------------------------
 * 
 */
@Controller
@RequestMapping(value = "/shop")
public class ShopMainController {
	
	@Resource(name = "shopMainService")
	private ShopMainService shopMainService;
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	private ModelAndView mav = new ModelAndView();
	
	
	@GetMapping(value = "/shopHome.do")
	public ModelAndView shopHome (FnlMap fnlMap) throws Exception {
		log.info(">>>>>>>>>>shopHome > controller");
		mav.setViewName("shop/shopHome.tiles");
		mav.addObject("pageName", fnlMap.getString("pageName"));
		return mav;
	}
	
	@PostMapping(value = "/shopItemList.do")
	public String selectShopItemList (ModelMap model, @RequestBody HashMap<Object, Object> hashMap) throws Exception {
		log.info(">>>>>>>>>>selectShopItemList > controller");
		FnlMap fnlMap = new FnlMap();
		fnlMap.putAll(hashMap);
		
		String result = shopMainService.selectShopItemList(fnlMap);
		
		model.addAttribute("jsonString", result);
		
		return "cmmn/jsonString";
	}
	
	@PostMapping(value = "/itemCheckout.do")
	public String itemCheckout (ModelMap model, @RequestBody HashMap<Object, Object> hashMap) throws Exception {
		log.info(">>>>>>>>>>itemCheckout > controller");
		FnlMap fnlMap = new FnlMap();
		fnlMap.putAll(hashMap);
		
		String result = shopMainService.updateInven(fnlMap);
		
		model.addAttribute("jsonString", result);
		return "cmmn/jsonString";
	}
	
}
