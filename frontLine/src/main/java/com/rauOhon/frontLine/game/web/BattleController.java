package com.rauOhon.frontLine.game.web;

import java.util.HashMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rauOhon.frontLine.cmmn.utils.FnlMap;
import com.rauOhon.frontLine.game.service.BattleService;
import com.rauOhon.frontLine.game.service.GameNormalService;

/**
 * 1. 클래스	: BattleController.java
 * 2. 작성자	: RAU
 * 3. 작성시간	: 2019. 6. 7.
 *
 * <pre>
 * 설명			: 전투 컨트롤러
 * </pre>
 *
 * 	수정일		수정자		수정내용
 * ----------------------------------------
 * 
 */
@Controller
@RequestMapping(value = "/battle")
public class BattleController {

	@Resource(name = "battleService")
	private BattleService battleService;

	@Resource(name = "gameNormalService")
	private GameNormalService gameNormalService;

	Logger log = LoggerFactory.getLogger(this.getClass());
	
	private ModelAndView mav = new ModelAndView();
	
	@GetMapping(value = "/dungeonPage.do")
	public ModelAndView battleHome (FnlMap fnlMap) throws Exception {
		log.info(">>>>>>>>>>battleHome > controller");
		mav.setViewName("battle/battleHome.tiles");// 던전페이지 개발 완료시 교체
		return mav;
	}
	
	@PostMapping(value = "/getBattleInfo.do")
	public String getBattleInfo (ModelMap model) throws Exception {
		log.info(">>>>>>>>>>getBattleInfo > controller");
		FnlMap fnlMap = new FnlMap();
		
		String result = battleService.selectBattleInfo(fnlMap);

		model.addAttribute("jsonString", result);
		
		return "cmmn/jsonString";
	}
	
	@PostMapping(value = "/useItem.do")
	public String useItem (ModelMap model, @RequestBody HashMap<Object, Object> hashMap) throws Exception {
		log.info(">>>>>>>>>>endBattle > controller");
		FnlMap fnlMap = new FnlMap();
		fnlMap.putAll(hashMap);
		
		String result = battleService.updateUseItem(fnlMap);

		model.addAttribute("jsonString", result);
		return "cmmn/jsonString";
	}
	
	@PostMapping(value = "/runAway.do")
	public ModelAndView runAway (FnlMap fnlMap) throws Exception {
		log.info(">>>>>>>>>>runAway > controller");
		
		battleService.entrance(0, fnlMap);
		
		mav = gameNormalService.entrance(0, fnlMap);
		
		return mav;
	}
	
	@PostMapping(value = "/endBattle.do")
	public String endBattle (ModelMap model, @RequestBody HashMap<Object, Object> hashMap) throws Exception {
		log.info(">>>>>>>>>>endBattle > controller");
		FnlMap fnlMap = new FnlMap();
		fnlMap.putAll(hashMap);
		
		String result = battleService.updateEndBattle(fnlMap);

		model.addAttribute("jsonString", result);
		return "cmmn/jsonString";
	}
}
