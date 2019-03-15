package com.rauOhon.frontLine.cmmn.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@ImportResource("classpath:/spring/context_transaction.xml")
public class Context_Transaction {

	Logger log = LoggerFactory.getLogger(this.getClass());
	
    @Bean
    public DataSourceTransactionManager txManager(DataSource dataSource) {
    	log.info(">>>>>>>>>>txManager");
    	DataSourceTransactionManager txManager = new DataSourceTransactionManager();
    	txManager.setDataSource(dataSource);
    	return txManager;
    }
}
