package com.zkname.frame.util.spring;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.zkname.frame.util.spring.thread.StopWatchLocal;

public class ControllerInterceptor  implements HandlerInterceptor  {
	
	private static Logger logger = LoggerFactory.getLogger(ControllerInterceptor.class);
	
	public void afterCompletion(HttpServletRequest arg0,HttpServletResponse arg1, Object arg2, Exception arg3)throws Exception {
		if(logger.isDebugEnabled()){
			StopWatch stopWatch = StopWatchLocal.get();   
	        stopWatch.stop();   
	        String currentPath = arg0.getRequestURI();   
	        String queryString  = arg0.getQueryString();
	        String method = arg0.getMethod();
			if(StringUtils.equalsIgnoreCase("POST",method)){
		        Map<String, String[]> params = arg0.getParameterMap();  
		        queryString = queryString == null ? "?":"?" + queryString;
		        for (String key : params.keySet()) {  
		            String[] values = params.get(key);  
		            for (int i = 0; i < values.length; i++) {  
		                String value = values[i];  
		                queryString += key + "=" + value + "&";  
		            }  
		        }  
		        // 去掉最后一个空格  
		        queryString = queryString.substring(0, queryString.length() - 1); 
			}else{
				queryString = queryString == null ? "":"?" + queryString;
			}
			logger.debug("ACCESS IP:[{}] Method:[{}] URL PATH:{}{} |TIME-CONSUMING:{} ms",getIpAddr(arg0),method,currentPath , queryString, stopWatch.getTotalTimeMillis());
	        StopWatchLocal.clear();
		}
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response,Object handler, ModelAndView modelAndView) throws Exception {
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception {
		if(logger.isDebugEnabled()){
			StopWatch stopWatch = new StopWatch(handler.toString());   
	        StopWatchLocal.set(stopWatch);   
	        stopWatch.start(handler.toString());
		}
		return true;
	}
	
	public String getIpAddr(HttpServletRequest request) {  
	    String ip = request.getHeader("x-forwarded-for");  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getRemoteAddr();  
	    }  
	    return ip;  
	}
}