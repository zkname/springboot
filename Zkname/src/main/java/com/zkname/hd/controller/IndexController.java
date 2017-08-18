package com.zkname.hd.controller;

import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.zkname.frame.controller.BaseController;
import com.zkname.hd.service.HdUserService;


/**
 * 首页
 */
@Controller
@RequestMapping(value = "/")
public class IndexController extends BaseController {
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response) throws InterruptedException, ExecutionException, Exception {
        ModelAndView mv = new ModelAndView("index");
		return mv;
	}
}	
