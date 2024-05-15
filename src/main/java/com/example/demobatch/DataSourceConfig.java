package com.example.demobatch;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Another way to have two datasource with batch
 * 
 * https://docs.spring.io/spring-boot/how-to/batch.html#howto.batch.specifying-a-data-source
 *
 */
@Configuration
public class DataSourceConfig {

	@Bean(name = "h2DataSource")
	@BatchDataSource
	public DataSource h2DataSource() {
		EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();
		return embeddedDatabaseBuilder.addScript("classpath:org/springframework/batch/core/schema-drop-h2.sql")
				.addScript("classpath:org/springframework/batch/core/schema-h2.sql").setType(EmbeddedDatabaseType.H2)
				.build();
	}
	
	@Bean("dataSource2")
	@Primary
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
}
