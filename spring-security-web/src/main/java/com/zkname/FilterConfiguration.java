package com.zkname;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import com.zkname.patchca.PatchcaFilter;

/**
 * 自定义Filter
 * @author ZhangKai
 *
 */
@Configuration
public class FilterConfiguration {

	private @Value("${spring.security.login.url}") String springSecurityLoginUrl;
	
	private @Value("${spring.security.login.failure.url}") String failureUrl;
	
	/**
	 * 验证码
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws DecoderException
	 * @throws EncoderException
	 */
	@Bean
    public FilterRegistrationBean testFilterRegistration() throws UnsupportedEncodingException, DecoderException, EncoderException {
		PatchcaFilter pf=new PatchcaFilter();
		pf.setErrorUrl(failureUrl+"?login_error="+new URLCodec("UTF-8").encode("验证码错误！"));
		pf.setLoginUrl(springSecurityLoginUrl);
		//开启Debug 不管输入什么都验证通过
		pf.setDebug(true);
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(pf);
        registration.addUrlPatterns("/images/code.png",springSecurityLoginUrl);
        registration.setName("patchcaFilter");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }
}