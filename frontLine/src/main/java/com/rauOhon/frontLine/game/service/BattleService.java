package com.rauOhon.frontLine.game.service;

import org.springframework.web.servlet.ModelAndView;

import com.rauOhon.frontLine.cmmn.utils.FnlMap;

public interface BattleService {

	ModelAndView entrance(int jobCnt, FnlMap fnlMap) throws Exception;

	String selectBattleInfo(FnlMap fnlMap) throws Exception;

	String updateEndBattle(FnlMap fnlMap) throws Exception;

	String updateUseItem(FnlMap fnlMap) throws Exception;
}
