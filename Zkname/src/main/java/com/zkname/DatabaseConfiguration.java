package com.zkname;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.jdbcdslog.ConnectionPoolDataSourceProxy;
import org.jdbcdslog.JDBCDSLogException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import com.google.common.collect.Lists;
import com.zkname.frame.dao.sql.SqlFactory;
import com.zkname.hd.util.dangdang.sharding.CommentKeyModuloTableShardingAlgorithm;

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
	
	
	@Bean("jdbcdslogDataSource")
	public DataSource jdbcdslogDataSource() throws JDBCDSLogException {
		log.info("init Jdbcdslog DataSource Configuration");
		ConnectionPoolDataSourceProxy connectionPoolDataSourceProxy = new ConnectionPoolDataSourceProxy();
		connectionPoolDataSourceProxy.setTargetDSDirect(dataSource());
		return connectionPoolDataSourceProxy;
	}
	
	@Bean
	public SqlFactory getSqlFactory(){
		return new SqlFactory();
	}

	@Primary
	@Bean("jdbcTemplate")
	public JdbcTemplate getJdbcTemplate() throws JDBCDSLogException  {
		return new JdbcTemplate(jdbcdslogDataSource());
	}
	
	@Bean("jdbcTemplateSharding")
	public JdbcTemplate getJdbcTemplateSharding() throws JDBCDSLogException  {
		return new JdbcTemplate(getDataSource());
	}
	
	@Primary
	@Bean("transactionManager")
	public DataSourceTransactionManager getDataSourceTransactionManager() throws JDBCDSLogException  {
		return new DataSourceTransactionManager(jdbcdslogDataSource());
	}
	
	@Bean("transactionManager1")
	public DataSourceTransactionManager getDataSourceTransactionManager1() throws JDBCDSLogException  {
		return new DataSourceTransactionManager(getDataSource());
	}
	
	@Bean("chainedTransactionManager")
	public ChainedTransactionManager getChainedTransactionManager() throws JDBCDSLogException  {
		return new ChainedTransactionManager(getDataSourceTransactionManager(),getDataSourceTransactionManager1());
	}
	
	@Bean(name="shardingDataSource")
	public DataSource getDataSource() throws JDBCDSLogException {
		log.info("init Sharding DataSource Configuration");
        //设置分库映射
        Map<String, DataSource> dataSourceMap = new HashMap<>(1);
        dataSourceMap.put("dataSource", jdbcdslogDataSource());
        DataSourceRule dataSourceRule = new DataSourceRule(dataSourceMap, "dataSource");
        List<TableRule> List= Lists.newArrayList();
        List.add(TableRule.builder("hd_user_pay_log").dynamic(true).dataSourceRule(dataSourceRule).build());
        List.add(TableRule.builder("hd_user_recharge_log").dynamic(true).dataSourceRule(dataSourceRule).build());
        List.add(TableRule.builder("hd_shop_wallet_log").dynamic(true).dataSourceRule(dataSourceRule).build());
        List.add(TableRule.builder("hd_user_wallet_log").dynamic(true).dataSourceRule(dataSourceRule).build());

        //具体分库分表策略，按什么规则来分
        ShardingRule shardingRule = ShardingRule.builder().dataSourceRule(dataSourceRule).tableRules(List).tableShardingStrategy(new TableShardingStrategy("createTime", new CommentKeyModuloTableShardingAlgorithm())).build();
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(shardingRule);
        return dataSource;
    }
	
}
