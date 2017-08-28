package com.zkname;

import java.nio.charset.Charset;
import java.util.Arrays;

import javax.servlet.ServletContext;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.zkname.frame.util.spring.AvoidDuplicateSubmissionInterceptor;
import com.zkname.frame.util.spring.ControllerInterceptor;
import com.zkname.frame.util.spring.WebExceptionResolver;

@Configuration
public class SpringMvcConfigurer extends WebMvcConfigurerAdapter {
	
	@Bean
	public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
		ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
		registration.getUrlMappings().clear();
		registration.addUrlMappings("*.do");
		return registration;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 多个拦截器组成一个拦截器链
		registry.addInterceptor(new ControllerInterceptor()).addPathPatterns("*.do").addPathPatterns("/**");
		registry.addInterceptor(new AvoidDuplicateSubmissionInterceptor()).addPathPatterns("*.do").addPathPatterns("/**");
		super.addInterceptors(registry);
	}
	
	@Bean
	public WebExceptionResolver getExceptionResolver() {
		return new com.zkname.frame.util.spring.WebExceptionResolver();
	}
	
    @Bean
    public CommonsMultipartResolver multipartResolver(ServletContext servletContext) {
        return new CommonsMultipartResolver(servletContext);
    }
    
	@Bean
	public HttpMessageConverter<String> responseBodyConverter() {
		StringHttpMessageConverter converter = new StringHttpMessageConverter();
		converter.setSupportedMediaTypes(Arrays.asList(new MediaType("text", "plain", Charset.forName("UTF-8"))));
		return converter;
	}
}