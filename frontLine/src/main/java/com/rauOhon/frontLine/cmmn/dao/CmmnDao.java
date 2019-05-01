package com.rauOhon.frontLine.cmmn.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.rauOhon.frontLine.cmmn.utils.FnlMap;

@Repository
public class CmmnDao extends SqlSessionDaoSupport {
	
	@Resource(name = "sqlSession")
	public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
		super.setSqlSessionFactory(sqlSession);
	}
	
	FnlMap resultMap = new FnlMap();
	
	public int insert(String queryId, Object fnlMap) {
		return getSqlSession().insert(queryId, fnlMap);
	}
	
	public int update(String queryId, Object fnlMap) {
		return getSqlSession().update(queryId, fnlMap);
	}

	public int delete(String queryId, Object fnlMap) {
		return getSqlSession().delete(queryId, fnlMap);
	}
	
	public String selectByPk(String queryId, Object fnlMap) {
		return getSqlSession().selectOne(queryId, fnlMap);
	}
	
	public int selectByCnt(String queryId, Object fnlMap) {
		return getSqlSession().selectOne(queryId, fnlMap);
	}
	
	public List<FnlMap> select(String queryId, Object fnlMap) {
		return getSetmap(getSqlSession().selectList(queryId, fnlMap));
	}
	
	private List<FnlMap> getSetmap(List<HashMap<Object, Object>> selectList) {
		List<FnlMap> resultList = new LinkedList<FnlMap>();
		Iterator<HashMap<Object, Object>> listIter = selectList.iterator();
		
		while (listIter.hasNext()) {
			resultList.add(getSetMap(listIter.next()));
		}
		
		return resultList;
	}

	public FnlMap selectOneRow(String queryId, Object fnlMap) {
		return getSetMap(getSqlSession().selectOne(queryId, fnlMap));
	}
	
	private FnlMap getSetMap(Map<Object, Object> hashMap) {
		FnlMap resultMap = new FnlMap();
		Iterator<Object> mapIter = hashMap.keySet().iterator();
		
		while (mapIter.hasNext()) {
			String key = (String) mapIter.next();
			resultMap.put(key, hashMap.get(key));
		}
		
		return resultMap;
	}
	
	
	
}
