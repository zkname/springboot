package com.zkname;

import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;


/**
 * memcache 配置
 * @author ZhangKai
 *
 */
@Configuration
public class MongodbConfiguration {
	
	private static final Logger logger = LoggerFactory.getLogger(MongodbConfiguration.class);
	
	@Value("${spring.data.mongodb.host}")
	private String springDataMongodbHost;
	@Value("${spring.data.mongodb.port}")
	private int springDataMongodbPort;

	@Bean
	public MongoTemplate mongoTemplate() throws UnknownHostException {
		logger.info("init Mongodb Configuration ");
		return new MongoTemplate(new MongoClient(springDataMongodbHost, springDataMongodbPort), "test");
	}
}