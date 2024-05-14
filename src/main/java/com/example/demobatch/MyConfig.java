package com.example.demobatch;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class MyConfig {

	// for spring
	@Primary // trick spring to inject this every time it looks for a DataSource because it doesn't use @Qualifier
	@Bean(name = "h2DataSource")
	public DataSource h2DataSource() {
		EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();
		return embeddedDatabaseBuilder.addScript("classpath:org/springframework/batch/core/schema-drop-h2.sql")
				.addScript("classpath:org/springframework/batch/core/schema-h2.sql").setType(EmbeddedDatabaseType.H2)
				.build();
	}
	
	@Primary // // trick spring to inject this every time it looks for a PlatformTransactionManager because it doesn't use @Qualifier
	@Bean(name = "platformTransactionManager")
	public PlatformTransactionManager platformTransactionManager() {
		return new DataSourceTransactionManager(h2DataSource());
	}

	// -- end
	
	// for jobs
	@Bean("dataSource2")
	public DataSource getDataSource2() {
		String url = "jdbc:h2:tcp://localhost/~/coffee";

		HikariConfig config = new HikariConfig();

		config.setJdbcUrl(url);
//        config.setUsername( "" );
//        config.setPassword( "database_password" );
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		return new HikariDataSource(config);
	}

	@Bean
	public PlatformTransactionManager mySQLDataSourceTransactionManager() {
		return new DataSourceTransactionManager(getDataSource2());
	}

	// This is a wrapper of JdbcTemplate
	@Bean
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
		return new NamedParameterJdbcTemplate(getDataSource2());
	}

	// This and above will work
//	@Bean("getJdbcTemplate")
//	public JdbcTemplate getJdbcTemplate() {
//		return new JdbcTemplate(getDataSource2());
//	}
	
	// end




}
