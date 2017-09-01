package com.zkname.demo.security.springsecurity;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.zkname.core.util.CodeHttpUtil;

@Component
public class TimeoutAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Value("${spring.security.timeout.url}")
	private String defaultTargetUrl;
	
	@Override
	public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		
		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With")) || request.getParameterMap().containsKey("ajax")){
			CodeHttpUtil.writer(response, CodeHttpUtil.登陆超时);
		}else{
			response.sendRedirect(request.getContextPath()+getDefaultTargetUrl());
		}
	}

	public TimeoutAuthenticationEntryPoint() {
	}

	public TimeoutAuthenticationEntryPoint(String defaultTargetUrl) {
		this.defaultTargetUrl = defaultTargetUrl;
	}

	public String getDefaultTargetUrl() {
		return defaultTargetUrl;
	}

	public void setDefaultTargetUrl(String defaultTargetUrl) {
		this.defaultTargetUrl = defaultTargetUrl;
	}
}
