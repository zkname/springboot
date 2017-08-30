package com.zkname;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

/**
 * druid监控
 */
@Configuration
public class DruidWebStatConfiguration {

	private static final Logger log = LoggerFactory.getLogger(DruidWebStatConfiguration.class);

	@Bean
	public ServletRegistrationBean druidServlet() {
		log.info("init Druid Servlet Configuration ");
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
		servletRegistrationBean.setServlet(new StatViewServlet());
		servletRegistrationBean.addUrlMappings("/druid/*");
		Map<String, String> initParameters = new HashMap<String, String>();
		initParameters.put("loginUsername", "admin");// 用户名
		initParameters.put("loginPassword", "admin");// 密码
		initParameters.put("resetEnable", "false");// 禁用HTML页面上的“Reset All”功能
		initParameters.put("allow", ""); // IP白名单 (没有配置或者为空，则允许所有访问)
		servletRegistrationBean.setInitParameters(initParameters);
		return servletRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean druidWebStatFilter() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new WebStatFilter());
		registration.addUrlPatterns("/*");
		registration.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*,*.html,*.ico,/druid/*");
		registration.addInitParameter("sessionStatEnable", "false");
		registration.addInitParameter("profileEnable", "false");
		registration.setName("DruidWebStatFilter");
		registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
		return registration;
	}
}
