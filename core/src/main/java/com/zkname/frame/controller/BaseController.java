package com.zkname.frame.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.zkname.frame.util.spring.DateConvertEditor;

/**
 * @version  
 * @since    Ver 1.1
 * @Date	 2013-1-9
 */
public abstract class BaseController {
	public static Logger logger = LoggerFactory.getLogger(BaseController.class);

	/**
	 * 增加了@ModelAttribute的方法可以在本controller的方法调用前执行,可以存放一些共享变量,如枚举值
	 */
	@ModelAttribute
	public void init(ModelMap model,HttpServletRequest request) {
		model.put("ctx", request.getContextPath());
	}
	/**
	 * 初始化Date类型，进行数据类型转换，把页面提交的String类型转为Date类型
	 * @param binder
	 */
	@InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(Date.class, new DateConvertEditor());
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

