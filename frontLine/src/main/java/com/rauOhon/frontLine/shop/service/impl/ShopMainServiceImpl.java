package com.rauOhon.frontLine.shop.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.rauOhon.frontLine.cmmn.dao.CmmnDao;
import com.rauOhon.frontLine.cmmn.utils.FnlMap;
import com.rauOhon.frontLine.cmmn.utils.ResultVO;
import com.rauOhon.frontLine.cmmn.utils.SessionManager;
import com.rauOhon.frontLine.shop.service.ShopMainService;

/**
 * 1. 클래스	: ShopMainServiceImpl.java
 * 2. 작성자	: RAU
 * 3. 작성시간	: 2019. 5. 25.
 *
 * <pre>
 * 설명			: 상점 서비스 임플
 * </pre>
 *
 * 	수정일		수정자		수정내용
 * ----------------------------------------
 * 
 */
@Service("shopMainService")
public class ShopMainServiceImpl implements ShopMainService{
	
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
				break;
		}
		
		return mav;
	}
	
	/**
	 * 처리내용	: 게임 캐릭터 마이페이지 용 정보 조회
	 * @method	: getCharacterDtlInfo
	 * @author	: RAU
	 * @param	: FnlMap
	 * @return	: FnlMap
	 */
	@Override
	public String selectShopItemList(FnlMap fnlMap) throws Exception {
		FnlMap resultMap = new FnlMap();
		ResultVO vo = new ResultVO();
		FnlMap gameInfo = session.selectGameCharaInfo(fnlMap);
		// 각 항목 아이템 조회
		String trgtId = fnlMap.getString("pageName");
		fnlMap.put("itSellYn", "Y");
		
		switch (trgtId) {
			case "weaponShop" :
				fnlMap.put("itTypeCd", "AT");
				gameInfo.put("itTypeCd", "AT");
				break;
			
			case "armourShop" :
				fnlMap.put("itTypeCd", "DF");
				gameInfo.put("itTypeCd", "DF");
				break;
			
			case "potionShop" :
				fnlMap.put("itTypeCd", "PO");
				gameInfo.put("itTypeCd", "PO");
				break;
		}
		
		FnlMap charaDtlInfo = cmmnDao.selectOneRow("gameNormal.cmmn.FNL1002.selectGameCharaDtlInfo", gameInfo.getMap());
		List<FnlMap> sellItemList = cmmnDao.select("gameNormal.cmmn.FNL1005.selectItemInfo", fnlMap.getMap());
		List<FnlMap> myInvenList = cmmnDao.select("gameNormal.cmmn.FNL1009.selectGameCharaInvenInfo", gameInfo.getMap());
		
		resultMap.put("charaDtlInfo", charaDtlInfo.getMap());
		resultMap.put("sellItemList", fnlMap.getSetList(sellItemList));
		resultMap.put("myInvenList", fnlMap.getSetList(myInvenList));
		
		vo.setResultData(resultMap);
		
		return vo.toJsonString();
	}

}
