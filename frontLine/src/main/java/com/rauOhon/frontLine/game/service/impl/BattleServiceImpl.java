package com.rauOhon.frontLine.game.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.rauOhon.frontLine.cmmn.dao.CmmnDao;
import com.rauOhon.frontLine.cmmn.utils.FnlException;
import com.rauOhon.frontLine.cmmn.utils.FnlMap;
import com.rauOhon.frontLine.cmmn.utils.ResultVO;
import com.rauOhon.frontLine.cmmn.utils.SessionManager;
import com.rauOhon.frontLine.game.service.BattleService;

@Service("battleService")
public class BattleServiceImpl implements BattleService {

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
			updateRunAway(fnlMap);
			break;
		
		case 1 :
			
			break;
			
		default :
			break;
	}
	return mav;
	}
	
	/**
	 * 처리내용	: 전투 중 아이템 사용
	 * @method	: updateRunAway
	 * @author	: RAU
	 * @param	: FnlMap
	 * @return	: ModelAndView
	 * @throws Exception 
	 */
	@Override
	public String updateUseItem(FnlMap fnlMap) throws Exception {
		FnlMap gameInfo = session.selectGameCharaInfo(fnlMap);
		ResultVO vo = new ResultVO();
		fnlMap.put("mcIdno",gameInfo.getString("mcIdno"));
		String ivAmount = fnlMap.getString("ivAmount");
		
		int judgeCnt = 0;
		if (!"0.0".equals(ivAmount)) {
			judgeCnt = cmmnDao.update("battle.FNL1009.updateUseItem", fnlMap.getMap());
		} else {
			judgeCnt = cmmnDao.delete("battle.FNL1009.deleteUseItem", fnlMap.getMap());
		}
		
		if (judgeCnt != 1) {
			vo.setErrorCode(-1);
			vo.setErrorMsg("아이템 사용에 실패했습니다.");
		}
		
		return vo.toJsonString();
	}

	/**
	 * 처리내용	: 전투 도망가기
	 * @method	: updateRunAway
	 * @author	: RAU
	 * @param	: FnlMap
	 * @return	: ModelAndView
	 * @throws Exception 
	 */
	private void updateRunAway(FnlMap fnlMap) throws Exception {
		FnlMap gameInfo = session.selectGameCharaInfo(fnlMap);
		gameInfo.put("mcBattYn","N");
		cmmnDao.update("battle.FNL1002.updateBattleState", gameInfo.getMap());
	}

	/**
	 * 처리내용	: 전투에 필요한 정보 조회
	 * @method	: selectBattleInfo
	 * @author	: RAU
	 * @param	: FnlMap
	 * @return	: String
	 */
	@Override
	public String selectBattleInfo(FnlMap fnlMap) throws Exception {
		FnlMap resultMap = new FnlMap();
		FnlMap gameInfo = session.selectGameCharaInfo(fnlMap);
		FnlMap sessionMap = new FnlMap();
		ResultVO vo = new ResultVO();
		sessionMap.putAll((Map<Object, Object>) session.getAttribute("sessionId"));
				
		// 1. 캐릭터 상세 정보 조회
		FnlMap charaDtlInfo = cmmnDao.selectOneRow("cmmn.FNL1002.selectGameCharaDtlInfo", gameInfo.getMap());
		// 2. 몬스터 상세 정보 조회
		FnlMap monsInfoSrchMap = new FnlMap();
		monsInfoSrchMap.put("moCode", "1"); // 차후 랜덤
		FnlMap monsDtlInfo = cmmnDao.selectOneRow("cmmn.FNL1003.selectMonsDtlInfo", monsInfoSrchMap.getMap());
		
		// 2. 캐릭터 스킬 조회
		List<FnlMap> charaSkillList = cmmnDao.select("battle.FNL1008.selectMcSkillInfo", gameInfo.getMap());
		// 3. 몬스터 스킬 조회
		List<FnlMap> monsSkillList = cmmnDao.select("battle.FNL1011.selectMoSkillInfo", monsDtlInfo.getMap());
		
		// 4. 캐릭터 소지 아이템 조회(포션류)
		gameInfo.put("itTypeCd","PO");
		List<FnlMap> charaInvenList = cmmnDao.select("cmmn.FNL1009.selectGameCharaInvenInfo", gameInfo.getMap());
		// 5. 몬스터 소지 아이템 조회
		List<FnlMap> monsItemList = cmmnDao.select("battle.FNL1012.selectMoItemInfo", monsDtlInfo.getMap());
		
		// 6. 캐릭터 전투상태 변경
		gameInfo.put("mcBattYn", "Y");
		int flagCnt = cmmnDao.update("battle.FNL1002.updateBattleState", gameInfo.getMap());
		
		if (flagCnt > 1 || flagCnt < 1) {
			throw new FnlException("전투 진입에 실패했습니다. 잠시후 다시 시도해 주세요.");
		}
		
		resultMap.put("charaDtlInfo", charaDtlInfo.getMap());
		resultMap.put("monsDtlInfo", monsDtlInfo.getMap());
		resultMap.put("charaSkillList", fnlMap.getSetList(charaSkillList));
		resultMap.put("monsSkillList", fnlMap.getSetList(monsSkillList));
		resultMap.put("charaInvenList", fnlMap.getSetList(charaInvenList));
		resultMap.put("monsItemList", fnlMap.getSetList(monsItemList));
		
		vo.setResultData(resultMap);
		
		return vo.toJsonString();
	}

	/**
	 * 처리내용	: 전투 종료
	 * @method	: selectBattleInfo
	 * @author	: RAU
	 * @param	: FnlMap
	 * @return	: String
	 */
	@Override
	public String updateEndBattle(FnlMap fnlMap) throws Exception {
		FnlMap resultMap = new FnlMap();
		ResultVO vo = new ResultVO();
		FnlMap gameInfo = session.selectGameCharaInfo(fnlMap);
		FnlMap sessionMap = new FnlMap();
		sessionMap.putAll((Map<Object, Object>) session.getAttribute("sessionId"));
		
		// 0. 승리 패배 분기
		FnlMap updateMap = new FnlMap();
		int addExp = fnlMap.getInt("addExp");
		
		// 1. 경험치 갱신
		updateMap.put("mcIdno", gameInfo.getString("mcIdno"));
		updateMap.put("addExp", addExp);
		cmmnDao.update("battle.FNL1002.updateEndBattle", updateMap.getMap());
		
		// 2. 아이템 삽입
		if ("VICTORY".equals("clsf")) {
			FnlMap itemMap = null;
			if (!"".equals(fnlMap.getString("getItemCd"))) {
				updateMap.put("itCode", fnlMap.getString("getItemCd"));
				updateMap.put("itAmount", 1);
				cmmnDao.update("battle.FNL1009.updateEndBattleItem", updateMap.getMap());
				
				itemMap = cmmnDao.selectOneRow("cmmn.FNL1005.selectItemInfo", updateMap.getMap());			
			}
			resultMap.put("itemMap", itemMap.getMap());
		}
		
		// 3. 배틀중 상태 갱신
		updateMap.put("mcBattYn", "N");
		cmmnDao.update("battle.FNL1002.updateBattleState", updateMap.getMap());
		vo.setErrorCode(0);
		vo.setErrorMsg("전투가 종료되었습니다.");
		vo.setResultData(resultMap);
		
		return vo.toJsonString();
	}
}
