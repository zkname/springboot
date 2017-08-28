package com.zkname;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.google.common.collect.Lists;
import com.zkname.frame.util.spring.CustomerApplicationContextInitializer;
import com.zkname.frame.util.spring.filter.EmbeddedWebApplicationContextExtend;

@SpringBootApplication
@Configuration
@ComponentScan("com.zkname")
@PropertySource(encoding="UTF-8", value = { "classpath:applicationContext.properties" })
public class Application  {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	@Bean
	public CommonsMultipartResolver getCommonsMultipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setMaxUploadSize(1024000000);
		return commonsMultipartResolver;
	}
	
	
//	@Value("${spring.data.redis.host}")
//	private String springDataRedisHost;
//	@Value("${spring.data.redis.port}")
//	private int springDataRedisPort;
//	
//	/**
//	 * jedis
//	 */
//	@Bean
//	public RedisConnectionFactory jedisConnectionFactory() {
//		JedisShardInfo sentinelConfig = new JedisShardInfo(springDataRedisHost, springDataRedisPort);
//		return new JedisConnectionFactory(sentinelConfig);
//	}
//	
//	@Value("${spring.data.mongodb.host}")
//	private String springDataMongodbHost;
//	@Value("${spring.data.mongodb.port}")
//	private int springDataMongodbPort;
//	
//	/**
//	 * mongo
//	 * @return
//	 */
//	@Bean
//	public MongoTemplate mongoTemplate() {
//		MongoTemplate sentinelConfig = null;
//		try {
//			sentinelConfig = new MongoTemplate(new com.mongodb.MongoClient(springDataMongodbHost,springDataMongodbPort),"test");
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return sentinelConfig;
//	}
	   
	public static void main(String[] args) throws Exception {
		SpringApplication ctx = new SpringApplication(Application.class);
		ctx.setApplicationContextClass(EmbeddedWebApplicationContextExtend.class);
		ctx.setInitializers(Lists.newArrayList(new CustomerApplicationContextInitializer()));
		ApplicationContext applicationContext = ctx.run(args);
		String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
		for (String profile : activeProfiles) {
			logger.info("Spring Boot 使用profile为:{}", profile);
		}
	}

}
