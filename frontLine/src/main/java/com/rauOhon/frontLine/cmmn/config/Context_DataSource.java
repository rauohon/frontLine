package com.rauOhon.frontLine.cmmn.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:/properties/local.properties")
public class Context_DataSource implements EnvironmentAware {
	
	private Environment env;
	
	Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void setEnvironment(Environment environment) {
		this.env = environment;
	}
	
    @Bean
    public DataSource dataSourceSpied() {
    	log.info(">>>>>>>>>>dataSource");
    	try {
        	BasicDataSource basicDataSource = new BasicDataSource();
        	basicDataSource.setDriverClassName(env.getProperty("db.driver"));
        	basicDataSource.setUrl(env.getProperty("db.url"));
        	basicDataSource.setUsername(env.getProperty("db.username"));
        	basicDataSource.setPassword(env.getProperty("db.password"));
        	basicDataSource.setMaxTotal(30);
        	basicDataSource.setMaxIdle(30);
        	basicDataSource.setMinIdle(5);
        	return basicDataSource;
		} catch (Exception e) {
			log.error("DataSourceConfig.getDataSource()" + e.getMessage());
		}
    	
    	return null;
    }
	
}
