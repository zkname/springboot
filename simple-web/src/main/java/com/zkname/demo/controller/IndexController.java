package com.zkname.demo.controller;

import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.zkname.demo.entity.SysUser;
import com.zkname.demo.service.SysUserService;
import com.zkname.demo.session.LoginUser;
import com.zkname.demo.util.EncodeUtils;
import com.zkname.frame.controller.BaseController;
import com.zkname.frame.util.spring.SpringHttpServletRequest;
import com.zkname.patchca.PatchcaFilter;


/**
 * 首页
 */
@Controller
@RequestMapping(value = "/")
public class IndexController extends BaseController {
	
	@Autowired
	private SysUserService sysUserService;
	
	@RequestMapping(value = {"/","/index"}, method = RequestMethod.GET)
	public String index(HttpServletRequest request,HttpServletResponse response){
		return "index";
	}
	
	/**
	 * 登录
	 * @param username
	 * @param password
	 * @param code
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login")
	public String login(String username,String password,String code,ModelMap model)  {
		if(StringUtils.isAnyBlank(username,password,code)){
        	model.put("login_error", "参数错误！");
        	return "index";
        }
		if(!PatchcaFilter.isValidationCode(SpringHttpServletRequest.getRequest(), code)){
        	model.put("login_error", "验证码错误！");
        	return "index";
        }
		LoginUser loginUser=sysUserService.login(username);
		if(loginUser==null){
        	model.put("login_error", "用户不存在！");
        	return "index";
		}
		if(!StringUtils.equals(loginUser.getPassword(),EncodeUtils.md5(password))){
        	model.put("login_error", "用户密码错误！");
        	return "index";
		}
		//登录
		loginUser.login();
        return "redirect:/admin/index"; 
	}
}	
