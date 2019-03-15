package com.rauOhon.frontLine.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.rauOhon.frontLine.cmmn.dao.CmmnDao;
import com.rauOhon.frontLine.cmmn.utils.FnlMap;
import com.rauOhon.frontLine.cmmn.utils.SessionManager;
import com.rauOhon.frontLine.user.service.UserService;

/**
 * 1. 클래스	: UserServiceImpl.java
 * 2. 작성자	: RAU
 * 3. 작성시간	: 2018. 10. 14.
 *
 * <pre>
 * 설명			: 사용자 Service 관련
 * </pre>
 *
 * 	수정일		수정자		수정내용
 * ----------------------------------------
 * 
 */
@Service("userService")
public class UserServiceImpl implements UserService{
	
	@Autowired
	private CmmnDao cmmnDao;
	@Autowired
	private SessionManager session;
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	private ModelAndView mav = new ModelAndView();
	
	@Override
	public ModelAndView entrance (int jobCnt, FnlMap fnlMap) throws Exception {
		log.info(">>>>>>>>>>>>>>>>>>>>signUp > entrance : {}", jobCnt);
		fnlMap.loggigMap();
		
		switch(jobCnt) {
			case 0 :
				mav = signUp(fnlMap);
				break;
			
			case 1 :
				mav = lgon(fnlMap);
				break;
			
			case 2 :
				mav = lgout(fnlMap);
				break;
			
			case 3 :
				mav = qksqhr(fnlMap);
				break;
				
			default :
				
				break;
		}
		
		return mav;
	}

	private ModelAndView qksqhr(FnlMap fnlMap) {
		log.debug(">>>>>>>>>>>>>>>>>>>>qksqhr > service");
		String pageName = "cmmn/home.tiles";
		mav.setViewName(pageName);
		ArrayList <String> qksqhrList = new ArrayList<String>();
		qksqhrList.add("abcd");
		qksqhrList.add("ㄱㄴㄷㄹ");
		qksqhrList.add("zyxw");
		qksqhrList.add("ㅎㅍㅌㅊ");
		
		fnlMap.put("qksqhrList", qksqhrList);
		
		cmmnDao.select("user.FNL1001.qksqhr", fnlMap.getMap());
		return mav;
	}

	/**
	 * 처리내용	: 회원가입
	 * @method	: signUp
	 * @author	: RAU
	 * @param	: FnlMap
	 * @return	: ModelAndView
	 */
	private ModelAndView signUp (FnlMap fnlMap) throws Exception {
		log.debug(">>>>>>>>>>>>>>>>>>>>signUp > service");
		String pageName = "user/lgon";
		
		if (idCheck(fnlMap)) {
			pageName = "user/signUp";
			mav.addObject("errorDetail", "이미 가입된 아이디에요.");
		} else {
			String mbIdno = getMbIdno(fnlMap);
			fnlMap.put("mbIdno", mbIdno);
			
			cmmnDao.insert("user.FNL1001.signUp", fnlMap.getMap());
		}
		
		mav.setViewName(pageName);
		
		return mav;
	}
	
	// 사용자 idno 추출
	private String getMbIdno(FnlMap fnlMap) {
		return (String) cmmnDao.selectByPk("user.FNL1001.getMbIdno", fnlMap.getMap());
	}

	/**
	 * 처리내용	: 로그인
	 * @method	: lgon
	 * @author	: RAU
	 * @param	: FnlMap
	 * @return	: ModelAndView
	 */
	private ModelAndView lgon (FnlMap fnlMap) throws Exception {
		log.debug(">>>>>>>>>>>>>>>>>>>>lgon > service");
		String pageName = "cmmn/home.tiles";
		
		if (!idCheck(fnlMap)) {
			pageName = "user/lgon.lgon_signUp";
			mav.addObject("errorDetail", "일치하는 아이디가 없어요.");
		} else {
			if (!lgonCheck(fnlMap)) {
				pageName = "user/lgon.lgon_signUp";
				mav.addObject("errorDetail", "비밀번호가 이상해요.");
			} else { // 로그인 성공
				session.setAttribute("sessionId", fnlMap.get("mbIdno"));
			}
		}
		mav.setViewName(pageName);

		return mav;
	}

	/**
	 * 처리내용	: 로그아웃
	 * @method	: lgout
	 * @author	: RAU
	 * @param	: FnlMap
	 * @return	: ModelAndView
	 * @throws Exception 
	 */
	private ModelAndView lgout(FnlMap fnlMap) throws Exception {
		log.debug(">>>>>>>>>>>>>>>>>>>>lgout > service");
		String pageName = "cmmn/home.tiles";
		session.removeAttribute("sessionId");
		mav.setViewName(pageName);
		return mav;
	}
	
	/**
	 * 처리내용	: 아이디 유무확인
	 * @method	: idCheck
	 * @author	: RAU
	 * @param	: FnlMap
	 * @return	: 유 - ture, 무 - false
	 */
	private boolean idCheck (FnlMap fnlMap) throws Exception {
		int flag = cmmnDao.selectByCnt("user.cmmn.idCheck", fnlMap.getMap());
		return intToBoolean(flag);
	}
	
	/**
	 * 처리내용	: 아이디, 비밀번호 확인
	 * @method	: lgonCheck
	 * @author	: RAU
	 * @param	: FnlMap
	 * @return	: 일치 - ture, 불일치 - false
	 */
	private boolean lgonCheck (FnlMap fnlMap) throws Exception {
		int flag = cmmnDao.selectByCnt("user.FNL1001.lgonCheck", fnlMap.getMap());
		return intToBoolean(flag);
	}
	
	// 0 - false, 1 - true
	private boolean intToBoolean (int flag) throws Exception {
		if (flag > 0) {
			return true;
		}
		return false;
	}

}
