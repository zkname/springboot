package com.zkname.frame.util.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.zkname.frame.util.CodeHttpUtil;
import com.zkname.frame.util.exception.ActionException;

/**
 * 异常处理
 * @author Administrator
 *
 */
public class WebExceptionResolver implements HandlerExceptionResolver {
	
	public ModelAndView resolveException(HttpServletRequest arg0,HttpServletResponse arg1, Object arg2, Exception arg3) {
		
      // 根据不同错误转向不同页面  
		if(arg3 instanceof ActionException){
		    if(!arg0.getParameterMap().containsKey("isAjax")){
		    	arg0.setAttribute("error", arg3.getMessage());
		        return new ModelAndView("include/error");
		    }
			CodeHttpUtil.writer(arg1, CodeHttpUtil.失败,arg3.getMessage());
		}else{
			LoggerFactory.getLogger(arg2.getClass()).error("错误",arg3);
			if(!arg0.getParameterMap().containsKey("isAjax")){
				return new ModelAndView("include/error");
          }
			CodeHttpUtil.writer(arg1, CodeHttpUtil.错误);
		}
		return null;
	}
}
