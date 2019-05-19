package com.rauOhon.frontLine.game.service;

import org.springframework.web.servlet.ModelAndView;

import com.rauOhon.frontLine.cmmn.utils.FnlMap;

/**
 * 1. 클래스	: GameNomalService.java
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
public interface GameNormalService {

	public ModelAndView entrance(int jobCnt, FnlMap fnlMap) throws Exception;

	public String insertCharacter(FnlMap fnlMap) throws Exception;

	public String getCharacterDtlInfo(FnlMap fnlMap) throws Exception;

	public String deleteLiftEquipment(FnlMap fnlMap) throws Exception ;

	public String updateEquipItem(FnlMap fnlMap) throws Exception;
}
