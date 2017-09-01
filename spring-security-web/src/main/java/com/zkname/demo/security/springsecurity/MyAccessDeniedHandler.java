package com.zkname.demo.security.springsecurity;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.zkname.core.util.CodeHttpUtil;


/**
 * 无权限
 * @author zk
 *
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
	
	@Value("${spring.security.denied.url}")
	private String defaultTargetUrl;

	public MyAccessDeniedHandler() {
	}

	public MyAccessDeniedHandler(String defaultTargetUrl) {
		this.defaultTargetUrl = defaultTargetUrl;
	}
	
	public String getDefaultTargetUrl() {
		return defaultTargetUrl;
	}

	public void setDefaultTargetUrl(String defaultTargetUrl) {
		this.defaultTargetUrl = defaultTargetUrl;
	}

	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
//		boolean isAjax = false;
//		if(request.getParameterMap().containsKey("ajax")){
//			isAjax = ParamType.getboolean(request.getParameter("ajax"));
//		}
//		//如果是ajax请求
//		if (isAjax) {
//			String jsonObject = "{\"message\":\"403\"}";
//			String contentType = "application/json";
//			response.setContentType(contentType);
//			PrintWriter out = response.getWriter();
//			out.print(jsonObject);
//			out.flush();
//			out.close();
//			return;
//		}
//		else{
//			response.sendRedirect(request.getContextPath()+accessDeniedUrl);
//		}
		
		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With")) || request.getParameterMap().containsKey("ajax")){
			CodeHttpUtil.writer(response, CodeHttpUtil.无权限);
		}else{
			response.sendRedirect(request.getContextPath()+getDefaultTargetUrl());
		}
		
	}

}


