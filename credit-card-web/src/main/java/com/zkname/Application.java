package com.zkname;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Configuration
@EnableScheduling
@ComponentScan("com.zkname")
@PropertySource(encoding="UTF-8", value = { "classpath:applicationContext.properties" })
public class Application   extends SpringBootServletInitializer {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Application.class);
	}
	   
	public static void main(String[] args) throws Exception {
		SpringApplication ctx = new SpringApplication(Application.class);
//		ctx.setInitializers(Lists.newArrayList(new CustomerApplicationContextInitializer()));
		ApplicationContext applicationContext = ctx.run(args);
		String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
		for (String profile : activeProfiles) {
			logger.info("Spring Boot 使用profile为:{}", profile);
		}
	}

}
