package com.zkname;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.google.common.collect.Lists;
import com.zkname.core.util.spring.CustomerApplicationContextInitializer;

@SpringBootApplication
@Configuration
@ComponentScan("com.zkname")
@PropertySource(encoding="UTF-8", value = { "classpath:applicationContext.properties" })
public class Application  {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	   
	public static void main(String[] args) throws Exception {
		SpringApplication ctx = new SpringApplication(Application.class);
		ctx.setInitializers(Lists.newArrayList(new CustomerApplicationContextInitializer()));
		ApplicationContext applicationContext = ctx.run(args);
		String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
		for (String profile : activeProfiles) {
			logger.info("Spring Boot 使用profile为:{}", profile);
		}
	}

}
