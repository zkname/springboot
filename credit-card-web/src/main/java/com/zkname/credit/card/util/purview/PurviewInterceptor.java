package com.zkname.credit.card.util.purview;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.zkname.credit.card.session.LoginUser;

public class PurviewInterceptor  implements HandlerInterceptor  {
	
	private static Logger logger = LoggerFactory.getLogger(PurviewInterceptor.class);
	
	public void afterCompletion(HttpServletRequest arg0,HttpServletResponse arg1, Object arg2, Exception arg3)throws Exception {
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response,Object handler, ModelAndView modelAndView) throws Exception {
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler){
		HandlerMethod hm=(HandlerMethod) handler;
		if(hm.hasMethodAnnotation(Purview.class)){
			Purview p=hm.getMethodAnnotation(Purview.class);
			int role=LoginUser.getUser().getRole();
			for(int r:p.roles()){
				if(r==role){
					return true;
				}
			}
			return false;
		}
		return true;
	}
}