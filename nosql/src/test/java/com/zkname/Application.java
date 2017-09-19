package com.zkname;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zkname.redis.CacheUtils;

@Configuration
@ComponentScan("com.zkname")
@PropertySource(encoding = "UTF-8", value = { "classpath:applicationContext.properties" })
public class Application {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) throws Exception {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Application.class,RedisConfiguration.class);
		String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
		for (String profile : activeProfiles) {
			logger.info("Spring Boot 使用profile为:{}", profile);
		}
	}

}
