package com.rauOhon.frontLine.user.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.rauOhon.frontLine.cmmn.dao.CmmnDao;
import com.rauOhon.frontLine.cmmn.utils.Encryption;
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
	
	@Autowired
	private Encryption encrtption;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private ModelAndView mav = new ModelAndView();
	
	@Override
	public ModelAndView entrance (int jobCnt, FnlMap fnlMap) throws Exception {
		log.info(">>>>>>>>>>>>>>>>>>>> entrance : {}", jobCnt);
		fnlMap.loggigMap();
		
		switch(jobCnt) {
			case 0 :
				mav = insertSignUp(fnlMap);
				break;
			
			case 1 :
				mav = lgon(fnlMap);
				break;
			
			case 2 :
				mav = lgout(fnlMap);
				break;
			
			case 3 :

				break;
				
			default :
				
				break;
		}
		
		return mav;
	}

	/**
	 * 처리내용	: 회원가입
	 * @method	: signUp
	 * @author	: RAU
	 * @param	: FnlMap
	 * @return	: ModelAndView
	 */
	private ModelAndView insertSignUp (FnlMap fnlMap) throws Exception {
		log.debug(">>>>>>>>>>>>>>>>>>>>signUp > service");
		String trgtPage = "user/lgon.lgon_signUp";
		
		if (idCheck(fnlMap)) {
			trgtPage = "user/signUp";
			mav.addObject("errorDetail", "이미 가입된 아이디에요.");
		} else {
			String mbIdno = getMbIdno(fnlMap);
			fnlMap.put("mbIdno", mbIdno);
			
			String mbPwd = encrtption.encode(fnlMap.getString("mbPwd"));
			
			fnlMap.put("mbPass", mbPwd);
			
			fnlMap.loggigMap();
			
			cmmnDao.insert("user.FNL1001.signUp", fnlMap.getMap());
		}
		
		mav.setViewName(trgtPage);
		
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
		String trgtPage = "cmmn/home.tiles";
		
		if (!idCheck(fnlMap)) {
			trgtPage = "user/lgon.lgon_signUp";
			mav.addObject("errorDetail", "로그인에 실패했습니다. 아이디와 비밀번호를 확인해 주세요.");
		} else {
			if (!lgonCheck(fnlMap)) {
				trgtPage = "user/lgon.lgon_signUp";
				cmmnDao.update("user.FNL1001.updateMbErrCnt", fnlMap.getMap());
				mav.addObject("errorDetail", "로그인에 실패했습니다. 아이디와 비밀번호를 확인해 주세요.");
			} else { // 로그인 성공
				FnlMap sesseionMap = cmmnDao.selectOneRow("user.FNL1001.getMbInfo", fnlMap.getMap());
				session.setAttribute("sessionId", sesseionMap.getMap());
				int mbErrCnt = sesseionMap.getInt("mbErrCnt");
				if (mbErrCnt > 5) {
					trgtPage = "user/lgon.lgon_signUp";
					mav.addObject("errorDetail", "로그인 실패 횟수가 초과 되었습니다. 관리자에게 문의 바랍니다.");
				}
				
			}
		}
		mav.setViewName(trgtPage);

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
		String trgtPage = "cmmn/home.tiles";
		session.removeAttribute("sessionId");
		mav.setViewName(trgtPage);
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
		String mbPwd = fnlMap.getString("mbPwd");
		String encPwd = cmmnDao.selectByPk("user.FNL1001.getPwd", fnlMap.getMap());
		boolean flag = encrtption.matches(mbPwd, encPwd);
		return flag;
	}
	
	// 0 - false, 1 - true
	private boolean intToBoolean (int flag) throws Exception {
		if (flag > 0) {
			return true;
		}
		return false;
	}

}
