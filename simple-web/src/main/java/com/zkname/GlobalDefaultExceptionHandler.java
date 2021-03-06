package com.zkname;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.zkname.core.util.CodeHttpUtil;
import com.zkname.core.util.exception.ActionException;
import com.zkname.core.util.exception.LoginTimeoutException;

/**
 * 默认抛出异常输出
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);
	
	@ExceptionHandler(value = Exception.class)
	public String defaultErrorHandler(HttpServletRequest req, HttpServletResponse rep,Exception arg) {
	    // 根据不同错误转向不同页面  
		if(arg instanceof ActionException){
		    if(!req.getParameterMap().containsKey("ajax")){
		    	req.setAttribute("error", arg.getMessage());
		        return "include/error";
		    }
			CodeHttpUtil.writer(rep, CodeHttpUtil.失败,arg.getMessage());
		}else if(arg instanceof LoginTimeoutException){
			return "redirect:/index";
		}else{
			logger.error("错误",arg);
			if(!req.getParameterMap().containsKey("ajax")){
				return "include/error";
          }
			CodeHttpUtil.writer(rep, CodeHttpUtil.错误);
		}
		return null;
	}
}