package com.zkname.demo.security.springsecurity;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.zkname.core.util.CodeHttpUtil;

/**
 * 登陆失败
 * @author zk
 *
 */
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Value("${spring.security.login.failure.url}")
	private String defaultTargetUrl;

	public MyAuthenticationFailureHandler() {
	}

	public MyAuthenticationFailureHandler(String defaultTargetUrl) {
		this.defaultTargetUrl = defaultTargetUrl;
	}
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//		boolean isAjax = false;
//		if(request.getParameterMap().containsKey("ajax")){
//			isAjax = ParamType.getboolean(request.getParameter("ajax"));
//		}
//		//如果是ajax请求
//		if (isAjax) {
//			String jsonObject = "{\"message\":\"n\"}";
//			String contentType = "application/json";
//			response.setContentType(contentType);
//			PrintWriter out = response.getWriter();
//			out.print(jsonObject);
//			out.flush();
//			out.close();
//			return;
//		}else{
//			response.sendRedirect(request.getContextPath()+this.getDefaultTargetUrl());
//		}
		
		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With")) || request.getParameterMap().containsKey("ajax")){
			CodeHttpUtil.writer(response, CodeHttpUtil.失败);
		}else{
			request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION", exception);
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
