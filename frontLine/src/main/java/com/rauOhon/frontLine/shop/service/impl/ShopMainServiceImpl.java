package com.rauOhon.frontLine.shop.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.internal.LinkedTreeMap;
import com.rauOhon.frontLine.cmmn.dao.CmmnDao;
import com.rauOhon.frontLine.cmmn.utils.FnlException;
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
	 * 처리내용	: 상점 판매 아이템, 인벤 아이템 조회
	 * @method	: selectShopItemList
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
	
	/**
	 * 처리내용	: 아이템 구매, 판매 리스트 결제
	 * @method	: updateInven
	 * @author	: RAU
	 * @param	: FnlMap
	 * @return	: FnlMap
	 */
	@Override
	public String updateInven(FnlMap fnlMap) throws Exception {
		FnlMap resultMap = new FnlMap();
		ResultVO vo = new ResultVO();
		FnlMap gameInfo = session.selectGameCharaInfo(fnlMap);
		int buyCost = 0;
		int sellCost = 0;
		String errMsg = "아이템 결제를 실패했습니다.";
		
		// 1. 리스트 반복문 돌면서 구매, 판매 아이템 인벤 정리
		List<?> checkoutList = (List<?>) fnlMap.get("checkoutList");
			
		for (int i = 0; i < checkoutList.size(); i++) {
			FnlMap checkoutMap = new FnlMap();
			checkoutMap.putAll((LinkedTreeMap<Object, Object>) checkoutList.get(i));

			// 2. 아이템 정보 조회
			FnlMap itemInfoMap = cmmnDao.selectOneRow("gameNormal.cmmn.FNL1005.selectItemInfo", checkoutMap.getMap());
			
			int buySellFlag = checkoutMap.getInt("sellBuy");
			// 3. 구매/판매 쿼리 실행
			int amount = checkoutMap.getInt("qnty");
			
			if (amount > 0) {
				FnlMap invoiceMap = new FnlMap();
				invoiceMap.put("mcIdno", gameInfo.getString("mcIdno"));
				invoiceMap.put("itCode", checkoutMap.getString("itCode"));
				if (buySellFlag > 0) { // 판매 -> 0 첵크 후 제거
					invoiceMap.put("itAmount", "-" + checkoutMap.getInt("qnty"));
					cmmnDao.update("shop.FNL1009.updateItemBuy", invoiceMap.getMap());
					
					int sellChk = cmmnDao.selectByCnt("shop.FNL1009.selectItemAmonut", invoiceMap.getMap()); 
					if (sellChk <= 0) {
						// 장비여부 체크
						FnlMap equipMap = cmmnDao.selectOneRow("gameNormal.cmmn.FNL1010.selectGameCharaEquipInfo", invoiceMap.getMap());
						if (equipMap != null) {
							int delChk = cmmnDao.delete("shop.FNL1010.deleteLiftEquipment", invoiceMap.getMap());
							if (delChk > 1) {
								throw new FnlException(errMsg);
							}
						}
						int delChk = cmmnDao.delete("shop.FNL1009.deleteInventItem", invoiceMap.getMap());
						if (delChk > 1) {
							throw new FnlException(errMsg);
						}
					}
					sellCost += itemInfoMap.getInt("itCost") * 0.2 * checkoutMap.getInt("qnty") * buySellFlag;
				} else if (buySellFlag < 0) { // 구매
					invoiceMap.put("itAmount", checkoutMap.getString("qnty"));
					cmmnDao.update("shop.FNL1009.updateItemBuy", invoiceMap.getMap());
					
					buyCost += itemInfoMap.getInt("itCost") * checkoutMap.getInt("qnty") * buySellFlag;
				}
			} else {
				throw new FnlException(errMsg);
			}
		}
		
		// 4. 소지금 업데이트
		int myGold = gameInfo.getInt("mcGold");
		int totCost = buyCost + sellCost;
		int resultGold = myGold + totCost;
		if (resultGold >= 0) {
			FnlMap goldMap = new FnlMap();
			goldMap.put("mcIdno", gameInfo.getString("mcIdno"));
			goldMap.put("resultGold", resultGold);
			cmmnDao.update("shop.FNL1002.updateMyGold", goldMap.getMap());
		} else {
			throw new FnlException(errMsg);
		}
		
		return vo.toJsonString();
	}

}
