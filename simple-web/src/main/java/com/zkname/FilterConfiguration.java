package com.zkname;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import com.zkname.core.util.exception.LoginTimeoutException;
import com.zkname.demo.session.LoginUser;
import com.zkname.patchca.PatchcaFilter;

/**
 * 自定义Filter
 * @author ZhangKai
 *
 */
@Configuration
public class FilterConfiguration {

	@Bean
    public FilterRegistrationBean userFilterRegistration(){
		Filter userFilter=new Filter(){
			@Override
			public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
				HttpServletRequest req=(HttpServletRequest) request;
				HttpServletResponse res=(HttpServletResponse)response;
				try {
					LoginUser.getUser(req.getSession());
				} catch (LoginTimeoutException e) {
					res.sendRedirect(req.getContextPath()+"/index");
					return;
				}
				chain.doFilter(request, response);
			}
		};
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(userFilter);
        registration.addUrlPatterns("/admin/*");
        registration.setName("userFilter");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }
	
	@Bean
    public FilterRegistrationBean testFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new PatchcaFilter());
        registration.addUrlPatterns("/images/code.png");
        registration.setName("patchcaFilter");
        registration.setOrder(1);
        return registration;
    }
}