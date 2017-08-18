package com.zkname.frame.util.spring;

import java.lang.reflect.Method;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zkname.frame.util.spring.annotation.AvoidDuplicateSubmission;

public class AvoidDuplicateSubmissionInterceptor extends HandlerInterceptorAdapter {
	private static final Logger LOG = LoggerFactory.getLogger(AvoidDuplicateSubmissionInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();

		AvoidDuplicateSubmission annotation = method.getAnnotation(AvoidDuplicateSubmission.class);
		if (annotation != null) {
			boolean needSaveSession = annotation.needSaveToken();
			if (needSaveSession) {
				request.getSession(false).setAttribute("token", getUUID());
			}

			boolean needRemoveSession = annotation.needRemoveToken();
			if (needRemoveSession) {
				if (isRepeatSubmit(request)) {
					LOG.warn("please don't repeat submit,[url:" + request.getServletPath() + "]");
					return false;
//					throw new ActionException("请勿重复提交！");
				}
				request.getSession(false).removeAttribute("token");
			}
		}
		return true;
	}

	private boolean isRepeatSubmit(HttpServletRequest request) {
		String serverToken = (String) request.getSession(false).getAttribute("token");
		if (serverToken == null) {
			return true;
		}
		String clinetToken = request.getParameter("token");
		if (clinetToken == null) {
			return true;
		}
		if (!serverToken.equals(clinetToken)) {
			return true;
		}
		return false;
	}

	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
}
