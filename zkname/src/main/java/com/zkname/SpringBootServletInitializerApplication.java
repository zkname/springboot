package com.zkname;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

public class SpringBootServletInitializerApplication extends SpringBootServletInitializer {

	private static final Logger logger = LoggerFactory.getLogger(SpringBootServletInitializerApplication.class);

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Application.class);
	}

}