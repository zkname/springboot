package com.zkname;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.google.common.collect.Lists;
import com.zkname.core.dao.sql.SqlFactory;

@Configuration
@EnableTransactionManagement(proxyTargetClass=true)
public class DatabaseConfiguration {

	private static final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);

	@Primary
	@Bean("dataSource")
	@ConfigurationProperties("spring.datasource.druid")
	public DataSource dataSource()  {
		log.info("init Druid DataSource Configuration");
		DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
		dataSource.setConnectionInitSqls(Lists.newArrayList("set names utf8mb4;"));
		return dataSource;
	}
	
	@Bean
	public SqlFactory getSqlFactory(){
		return new SqlFactory();
	}

	@Primary
	@Bean("jdbcTemplate")
	public JdbcTemplate getJdbcTemplate()   {
		return new JdbcTemplate(dataSource());
	}
	
	@Primary
	@Bean("transactionManager")
	public DataSourceTransactionManager getDataSourceTransactionManager()   {
		return new DataSourceTransactionManager(dataSource());
	}
	
}
