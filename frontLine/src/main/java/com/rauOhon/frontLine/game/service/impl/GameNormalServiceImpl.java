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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rauOhon.frontLine.cmmn.dao.CmmnDao;
import com.rauOhon.frontLine.cmmn.utils.FnlMap;
import com.rauOhon.frontLine.cmmn.utils.ResultVO;
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
	public String insertCharacter(FnlMap fnlMap) throws Exception {
		FnlMap gameInfo = selectGameCharaInfo(fnlMap);
		FnlMap sessionMap = new FnlMap();
		
		if (gameInfo != null) {
			mav.addObject("msg", "이미 생성된 캐릭터가 있습니다.");
		}
		sessionMap.putAll((Map<Object, Object>) session.getAttribute("sessionId"));
		
		// 1. 캐릭터 생성
		String mcIdno = createCharacter(fnlMap, sessionMap);
		// 2. 스킬 생성
		createSkillToCharacter(mcIdno, 1);
		// 3. 최초 서비스 아이템 생성
		createItemToCharacter(mcIdno, 1);
		
		ResultVO vo = new ResultVO();
		
		String result = vo.toJson();
		log.debug("result vo to json : {}", result);
		return result;
	}

	// 1. 캐릭터 생성
	private String createCharacter(FnlMap fnlMap, FnlMap sessionMap) throws Exception {
		FnlMap createCharaMap = new FnlMap();
		String mcIdno = getMcIdno(fnlMap);
		createCharaMap.put("mcIdno", mcIdno);
		createCharaMap.put("mbIdno", sessionMap.getString("mbIdno"));
		createCharaMap.put("mcName", fnlMap.getString("charaNm"));

		fnlMap.loggigMap();
		
		String status = fnlMap.getString("charaStat");
		
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(status);
		String strs = element.getAsJsonObject().get("strs").getAsString();
		String dexs = element.getAsJsonObject().get("dexs").getAsString();
		String ints = element.getAsJsonObject().get("ints").getAsString();
		String cons = element.getAsJsonObject().get("cons").getAsString();
		
		String characterSex = fnlMap.getString("charaSex");
		String mcImgname = "";

		mcImgname = setCharacterSex(characterSex, mcImgname);
		
		String mcImglocation = "/resources/images/characters/";
		
		createCharaMap.put("strs", strs);
		createCharaMap.put("dexs", dexs);
		createCharaMap.put("ints", ints);
		createCharaMap.put("cons", cons);
		createCharaMap.put("mcImgname", mcImgname);
		createCharaMap.put("mcImglocation", mcImglocation);
		
		cmmnDao.insert("gameNormal.FNL1002.insertCharacter", createCharaMap.getMap());
		
		return mcIdno;
	}
	
	// 2. 캐릭터-스킬 생성
	private void createSkillToCharacter(String mcIdno, int level) {
		FnlMap fnlMap = new FnlMap();
		
		if (level == 1) {
			// 첫 정권 공격 생성
			fnlMap.put("csMcidno", mcIdno);
			fnlMap.put("csSkcode", "SK0000000000001");
		} else {
			// 이후 로직 생성
		}

		cmmnDao.insert("gameNormal.FNL1008.createSkillToCharacter", fnlMap.getMap());
	}
	
	// 3. 캐릭터-아이템 생성
	private void createItemToCharacter(String mcIdno, int level) {
		FnlMap fnlMap = new FnlMap();
		
		if (level == 1) {
			// 첫 아이템 생성
			fnlMap.put("ivMcidno", mcIdno);
			fnlMap.put("ivItcode", "IT0000000000001");
			
			cmmnDao.insert("gameNormal.FNL1009.createItemToCharacter", fnlMap.getMap());
			
			fnlMap.put("ivItcode", "IT0000000000002");
			
			cmmnDao.insert("gameNormal.FNL1009.createItemToCharacter", fnlMap.getMap());
			
			
		}
	}

	// 1-1 캐릭터 성별 사진 셋팅
	private String setCharacterSex(String characterSex, String mcImgname) throws Exception {
		String characterFirst;
		String characterLast;
		if (!"".equals(characterSex)) {
			characterFirst = characterSex.substring(0, 2);
			characterLast = characterSex.substring(3, 4);
			
			if ("남자".equals(characterFirst)) {
				
				switch (characterLast) {
					case "A" :
						mcImgname = "1";
						break;
					case "B" :
						mcImgname = "2";
						break;
					case "C" :
						mcImgname = "3";
						break;
					case "D" :
						mcImgname = "4";
						break;
				}
				
			} else if ("여자".equals(characterFirst)) {
				
				switch (characterLast) {
					case "A" :
						mcImgname = "5";
						break;
					case "B" :
						mcImgname = "6";
						break;
					case "C" :
						mcImgname = "7";
						break;
					case "D" :
						mcImgname = "8";
						break;
				}
			}
			mcImgname = mcImgname + ".jpg";
		}
		return mcImgname;
	}

	private String getMcIdno(FnlMap fnlMap) throws Exception {
		return (String) cmmnDao.selectByPk("gameNormal.FNL1002.getMcIdno", fnlMap.getMap());
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
