package com.zkname.demo.security.springsecurity;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.zkname.core.util.CodeHttpUtil;

/**
 * 登出成功
 * @author Kai
 *
 */
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler{
	
	@Value("spring.security.logout.url")
	private String defaultTargetUrl;

	public MyLogoutSuccessHandler() {
	}

	public MyLogoutSuccessHandler(String defaultTargetUrl) {
		this.defaultTargetUrl = defaultTargetUrl;
	}
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request,HttpServletResponse response, Authentication authentication)throws IOException, ServletException {
		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With")) || request.getParameterMap().containsKey("ajax")){
			CodeHttpUtil.writer(response, CodeHttpUtil.成功);
		}else{
			response.sendRedirect(request.getContextPath()+getDefaultTargetUrl());
		}
	}

	public String getDefaultTargetUrl() {
		return defaultTargetUrl;
	}

	public void setDefaultTargetUrl(String defaultTargetUrl) {
		this.defaultTargetUrl = defaultTargetUrl;
	}
	
}
