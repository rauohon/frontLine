package com.rauOhon.frontLine.cmmn.dao;

import java.util.List;

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
		return getSqlSession().selectList(queryId, fnlMap);
	}
}
