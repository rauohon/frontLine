package com.rauOhon.frontLine.cmmn.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
public class Context_SQL {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Bean
    public SqlSessionFactoryBean sqlSession(DataSource dataSource) throws IOException {
    	log.info(">>>>>>>>>>sqlSession");
    	SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    	PathMatchingResourcePatternResolver pmrpr = new PathMatchingResourcePatternResolver();
    	sqlSessionFactoryBean.setDataSource(dataSource);
    	sqlSessionFactoryBean.setConfigLocation(pmrpr.getResource("classpath:/sqlmap/sql-mapper-config.xml"));
    	sqlSessionFactoryBean.setMapperLocations(pmrpr.getResources("classpath:/sqlmap/mappers/**/*.xml"));
    	return sqlSessionFactoryBean;
    }
    
    @Bean
    public MapperScannerConfigurer mapperScanner(DataSource dataSource) {
    	log.info(">>>>>>>>>>mapperScanner");
    	MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
    	mapperScannerConfigurer.setBasePackage("com.rauOhon.frontLine.cmmn.dao;");
    	return mapperScannerConfigurer;
    }
	
}
