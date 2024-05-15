package com.example.demobatch;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class MyConfig {

	// for spring
	
	
//	@Primary // // trick spring to inject this every time it looks for a PlatformTransactionManager because it doesn't use @Qualifier
//	@Bean(name = "platformTransactionManager")
//	public PlatformTransactionManager platformTransactionManager() {
//		return new DataSourceTransactionManager(h2DataSource());
//	}

	// -- end
	
	// for jobs
	

	@Bean
	public PlatformTransactionManager mySQLDataSourceTransactionManager(@Qualifier("dataSource2") DataSource ds) {
		return new DataSourceTransactionManager(ds);
	}

	// This is a wrapper of JdbcTemplate
	@Bean
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate(@Qualifier("dataSource2") DataSource ds) {
		return new NamedParameterJdbcTemplate(ds);
	}

	// This and above will work
//	@Bean("getJdbcTemplate")
//	public JdbcTemplate getJdbcTemplate() {
//		return new JdbcTemplate(getDataSource2());
//	}
	
	// end




}
