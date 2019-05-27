package com.rauOhon.frontLine.cmmn.utils;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.rauOhon.frontLine.cmmn.dao.CmmnDao;

/**
 * 1. 클래스	: SessionManager.java
 * 2. 작성자	: RAU
 * 3. 작성시간	: 2018. 10. 14.
 *
 * <pre>
 * 설명			: session 관리
 * </pre>
 *
 * 	수정일		수정자		수정내용
 * ----------------------------------------
 * 
 */
@Component
public class SessionManager {

	@Autowired
	private CmmnDao cmmnDao;
	
    /**
     * 처리내용	: Session의 attribute 값을 반환
     * @method	: getAttribute
     * @author	: RAU
     * @param	: String
     * @return	: Object
     */
    public Object getAttribute(String name) throws Exception {
        return (Object) RequestContextHolder.getRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
    }

    /**
     * 처리내용	: Session에 attribute 설정
     * @method	: setAttribute
     * @author	: RAU
     * @param	: String, Object
     */
    public void setAttribute(String name, Object object) throws Exception {
        RequestContextHolder.getRequestAttributes().setAttribute(name, object, RequestAttributes.SCOPE_SESSION);
    }
    
    /**
     * 처리내용	: Session에 설정된 attribute 삭제
     * @method	: removeAttribute
     * @author	: RAU
     * @param	: String
     */
    public void removeAttribute(String name) throws Exception {
        RequestContextHolder.getRequestAttributes().removeAttribute(name, RequestAttributes.SCOPE_SESSION);
    }

    /**
     * 처리내용	: SessionId값을 반환
     * @method	: getSessionId
     * @author	: RAU
     * @return	: String
     */
    public String getSessionId() throws Exception  {
        return RequestContextHolder.getRequestAttributes().getSessionId();
    }
	
	/**
	 * 처리내용	: 게임 캐릭터 정보 조회
	 * @method	: selectGameCharaInfo
	 * @author	: RAU
	 * @param	: FnlMap
	 * @return	: FnlMap
	 */
	public FnlMap selectGameCharaInfo (FnlMap fnlMap) throws Exception {
		FnlMap gameInfo = new FnlMap();
		FnlMap sessionMap = new FnlMap();
		
		sessionMap.putAll((Map<Object, Object>) this.getAttribute("sessionId"));
		fnlMap.put("mbIdno", sessionMap.getString("mbIdno"));
		
		gameInfo = cmmnDao.selectOneRow("gameNormal.cmmn.FNL1002.selectGameInfo", fnlMap.getMap());
		
		return gameInfo;
	}
}
