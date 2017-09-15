package com.zkname;

import java.nio.charset.Charset;
import java.util.Arrays;

import javax.servlet.ServletContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.zkname.core.util.spring.AvoidDuplicateSubmissionInterceptor;
import com.zkname.core.util.spring.ControllerInterceptor;
import com.zkname.credit.card.util.purview.PurviewInterceptor;

@Configuration
public class SpringMvcConfigurer extends WebMvcConfigurerAdapter {
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 多个拦截器组成一个拦截器链
		registry.addInterceptor(new ControllerInterceptor()).addPathPatterns("/**");
		registry.addInterceptor(new AvoidDuplicateSubmissionInterceptor()).addPathPatterns("/**");
		registry.addInterceptor(new PurviewInterceptor()).addPathPatterns("/**");
		super.addInterceptors(registry);
	}
	
	/**
	 * 线程绑定静态方法获取 request
			public static HttpServletRequest getRequest(){
			    ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			    return servletRequestAttributes.getRequest();
			}
	 * @return
	 */
	@Bean
	public RequestContextListener requestContextListener() {
	    return new RequestContextListener();
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