package com.rauOhon.frontLine.cmmn.utils;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Intercepts({
	@Signature(type=StatementHandler.class, method="update", args= {Statement.class}),
	@Signature(type=StatementHandler.class, method="query", args= {Statement.class, ResultHandler.class})
})
public class MybatisLogInterceptor implements Interceptor{

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler handler = (StatementHandler) invocation.getTarget();

		String sql = bindSql(handler); //SQL 추출
		
		log.info("| =============================================== |");
		log.info("{} \n", sql);
		log.info("| =============================================== |");
		
		return invocation.proceed();
	}

	private String bindSql(StatementHandler handler) throws NoSuchFieldException, IllegalAccessException {
		BoundSql boundSql = handler.getBoundSql();

		Object param = handler.getParameterHandler().getParameterObject();
		String sql = boundSql.getSql();

		if (param == null) { // 바인딩 파리메터가 없음
			sql = sql.replaceFirst("\\?", "‘’");
			return sql;
		}
		
		// 해당 파라미터의 클래스가 Intger, Long, Float, Double 인 경우
		if (param instanceof Integer || param instanceof Long || param instanceof Float || param instanceof Double) {
			sql = sql.replaceFirst("\\?", param.toString());
		} 
		
		// 해당 파라미터의 클래스가 String 인 경우
		else if (param instanceof String) {
			sql = sql.replaceFirst("\\?", "‘" + param + "'");
		}

		// 해당 파라미터의 클래스가 Map인 경우
		else if (param instanceof Map) {
			List<ParameterMapping> paramMapping = boundSql.getParameterMappings();

			for (ParameterMapping mapping : paramMapping) {
				String propValue = mapping.getProperty();
				Object value = ((Map) param).get(propValue);

				if (value == null) {
					value = boundSql.getAdditionalParameter(propValue);
				}

				if (value == null) {
					continue;
				}
				
				if ("mbPass".equals(propValue)) {
					value = "";
				}

				if (value instanceof String) {
					sql = sql.replaceFirst("\\?", "'" + value + "'");
				} else {
					sql = sql.replaceFirst("\\?", value.toString());
				}
			}
		}
		// 해당 파라미터의 클래스가 사용자 정의 클래스인 경우
		else {
			List<ParameterMapping> paramMapping = boundSql.getParameterMappings();
			Class<? extends Object> paramClass = param.getClass();

			for (ParameterMapping mapping : paramMapping) {
				String propValue = mapping.getProperty();
				Field field = paramClass.getDeclaredField(propValue);
				Object value = ((Map) param).get(propValue);
				field.setAccessible(true);
				Class<?> javaType = mapping.getJavaType();
				if (String.class == javaType) {
					sql = sql.replaceFirst("\\?", "‘" + field.get(param) + "‘");
				} else {
					sql = sql.replaceFirst("\\?", field.get(param).toString());
				}
			}
		}
		return sql;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub
		
	}
}
