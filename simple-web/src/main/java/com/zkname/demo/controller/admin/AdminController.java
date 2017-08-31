package com.zkname.demo.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zkname.demo.session.LoginUser;
import com.zkname.core.controller.BaseController;


/**
 * 首页
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController extends BaseController {
	
	@RequestMapping(value = {"/","/index"}, method = RequestMethod.GET)
	public String index(HttpServletRequest request,HttpServletResponse response){
		LoginUser.getUser();
		return "admin/index";
	}
	
	@RequestMapping(value = "/bodyMenu")
	public String bodyMenu(){
		return "admin/body_menu";
	}
	
	/**
	 * 退出
	 */
	@RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
	public String logout(){
		LoginUser.logout();
		return "redirect:/index";
	}
	
}	
