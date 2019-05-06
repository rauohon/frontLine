package com.rauOhon.frontLine.game.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.rauOhon.frontLine.cmmn.dao.CmmnDao;
import com.rauOhon.frontLine.cmmn.utils.FnlMap;
import com.rauOhon.frontLine.cmmn.utils.SessionManager;
import com.rauOhon.frontLine.game.service.GameNormalService;

/**
 * 1. 클래스	: GameNormalServiceImpl.java
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
@Service("gameNormalService")
public class GameNormalServiceImpl implements GameNormalService {
	
	@Autowired
	private CmmnDao cmmnDao;

	@Autowired
	private SessionManager session;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private ModelAndView mav = new ModelAndView();

	@Override
	public ModelAndView entrance(int jobCnt, FnlMap fnlMap) throws Exception {
		log.info(">>>>>>>>>>>>>>>>>>>> entrance : {}", jobCnt);
		fnlMap.loggigMap();
		
		switch(jobCnt) {
			case 0 :
				mav = selectGameHome(fnlMap);
				break;
			
			case 1 :
				mav = createCharacter(fnlMap);
				break;
				
			default :
				break;
		}
		return mav;
	}

	/**
	 * 처리내용	: 게임캐릭터 생성
	 * @method	: createCharacter
	 * @author	: RAU
	 * @param	: FnlMap
	 * @return	: ModelAndView
	 */
	private ModelAndView createCharacter(FnlMap fnlMap) throws Exception {
		String trgtPage = "game/village.tiles";
		FnlMap gameInfo = selectGameCharaInfo(fnlMap);
		
		if (gameInfo != null) {
			mav.addObject("msg", "이미 생성된 캐릭터가 있습니다.");
		}
		
		fnlMap.loggigMap();
		// 1. 캐릭터 생성
		// 2. 스킬 생성
		// 3. 서비스 아이템 생성
		
		mav.setViewName(trgtPage);
		return mav;
	}

	/**
	 * 처리내용	: 게임 페이지 접근(마을, 캐릭터 생성 페이지)
	 * @method	: gameHome
	 * @author	: RAU
	 * @param	: FnlMap
	 * @return	: ModelAndView
	 */
	private ModelAndView selectGameHome(FnlMap fnlMap) throws Exception {
		FnlMap gameInfo = selectGameCharaInfo(fnlMap);
		String trgtPage = "game/createCharacter.tiles";
		
		FnlMap paramMap = new FnlMap();
		paramMap.put("cdBigCode", "CD0000000000001");
		List<HashMap<Object, Object>> cmmnCd = fnlMap.getSetList(cmmnDao.select("cmmn.FNL1006.selectCmmnCd", paramMap.getMap()));

		Gson gson = new Gson();
		String statusCmmnCd = gson.toJson(cmmnCd);
		
		mav.addObject("statusCmmnCd", statusCmmnCd);
		
		if (gameInfo != null) {
			mav.addObject("gameInfo", gameInfo.getMap());
			trgtPage = "game/village.tiles";
		}
		
		mav.setViewName(trgtPage);
		return mav;
	}
	
	/**
	 * 처리내용	: 게임 캐릭터 정보 조회
	 * @method	: selectGameCharaInfo
	 * @author	: RAU
	 * @param	: FnlMap
	 * @return	: FnlMap
	 */
	private FnlMap selectGameCharaInfo (FnlMap fnlMap) throws Exception {
		FnlMap gameInfo = new FnlMap();
		FnlMap sessionMap = new FnlMap();
		
		sessionMap.putAll((Map<Object, Object>) session.getAttribute("sessionId"));
		fnlMap.put("mbIdno", sessionMap.getString("mbIdno"));
		
		gameInfo = cmmnDao.selectOneRow("gameNormal.cmmn.FNL1002.selectGameInfo", fnlMap.getMap());
		
		return gameInfo;
	}

}
