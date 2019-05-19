package com.rauOhon.frontLine.game.web;

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
import com.rauOhon.frontLine.game.service.GameNormalService;

/**
 * 1. 클래스	: GameHomeController.java
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
@RequestMapping(value = "/game")
public class GameHomeController {

	@Resource(name = "gameNormalService")
	private GameNormalService gameNormalService;
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	private ModelAndView mav = new ModelAndView();
	
	@PostMapping(value = "/gameHome.do")
	public ModelAndView gameHome (FnlMap fnlMap) throws Exception {
		log.info(">>>>>>>>>>gameHome > controller");
		
		mav = gameNormalService.entrance(0, fnlMap);
		
		return mav;
	}
	
	@PostMapping(value = "/createCharacter.do")
	public String createCharacter (ModelMap model, @RequestBody HashMap<Object, Object> hashMap) throws Exception {
		log.info(">>>>>>>>>>gameHome > controller");
		FnlMap fnlMap = new FnlMap();
		fnlMap.putAll(hashMap);
		
		String result = gameNormalService.insertCharacter(fnlMap);
		
		model.addAttribute("jsonString", result);
		
		return "cmmn/jsonString";
	}
	
	@GetMapping(value = "/myCharacterInfo.do")
	public ModelAndView myCharacterInfo (FnlMap fnlMap) throws Exception {
		log.info(">>>>>>>>>>myCharacterInfo > controller");
		mav.setViewName("game/characterInfo.tiles");
		
		return mav;
	}
	
	@PostMapping(value = "/getCharacterDtlInfo.do")
	public String getCharacterDtlInfo(ModelMap model, @RequestBody HashMap<Object, Object> hashMap) throws Exception {
		log.info(">>>>>>>>>>gameHome > controller");
		FnlMap fnlMap = new FnlMap();
		fnlMap.putAll(hashMap);
		
		String result = gameNormalService.getCharacterDtlInfo(fnlMap);
		
		model.addAttribute("jsonString", result);
		
		return "cmmn/jsonString";
	}
	
	@PostMapping(value = "/liftEquipment.do") 
	public String liftEquipment(ModelMap model, @RequestBody HashMap<Object, Object> hashMap) throws Exception {
		log.info(">>>>>>>>>>gameHome > controller");
		FnlMap fnlMap = new FnlMap();
		fnlMap.putAll(hashMap);
		
		String result = gameNormalService.deleteLiftEquipment(fnlMap);
		
		model.addAttribute("jsonString", result);
		
		return "cmmn/jsonString";
	}
	
	@PostMapping(value = "/equipItem.do") 
	public String equipItem(ModelMap model, @RequestBody HashMap<Object, Object> hashMap) throws Exception {
		log.info(">>>>>>>>>>gameHome > controller");
		FnlMap fnlMap = new FnlMap();
		fnlMap.putAll(hashMap);
		
		String result = gameNormalService.updateEquipItem(fnlMap);
		
		model.addAttribute("jsonString", result);
		
		return "cmmn/jsonString";
	}

}
