package com.zkname.demo.security.springsecurity;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.zkname.core.util.CodeHttpUtil;
import com.zkname.demo.security.LoginUser;
import com.zkname.demo.service.SysUserService;

/**
 * 权限登录成功
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	@Value("${spring.security.login.success.url}")
	private String defaultTargetUrl;
	
	@Autowired
	private SysUserService iadminSysUserService; 

	/**
	 * 登录成功后具体跳转页面
	 */
	public void onAuthenticationSuccess(HttpServletRequest request,HttpServletResponse response, Authentication authentication)throws IOException, ServletException {
		LoginUser user=(LoginUser) authentication.getPrincipal();
		if (user != null) {
			//登陆成功后修改数据
			iadminSysUserService.loginSuccess(Long.valueOf(user.getId()),1l);
		}
		
//		boolean isAjax = false;
//		if(request.getParameterMap().containsKey("ajax")){
//			isAjax = ParamType.getboolean(request.getParameter("ajax"));
//		}
//		//如果是ajax请求
//		if (isAjax) {
//			String jsonObject = "{\"message\":\"y\"}";
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
		;
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