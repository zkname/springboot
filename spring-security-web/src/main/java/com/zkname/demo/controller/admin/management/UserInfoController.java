package com.zkname.demo.controller.admin.management;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.zkname.core.controller.BaseController;
import com.zkname.core.util.exception.ActionException;
import com.zkname.demo.entity.SysUser;
import com.zkname.demo.security.LoginUser;
import com.zkname.demo.security.springsecurity.SpringSecurityUtils;
import com.zkname.demo.service.SysUserService;

@Controller
@RequestMapping(value = "/admin/management")
public class UserInfoController extends BaseController{
	
	// 用户业务层类
	@Autowired
	private SysUserService service;
	
	/**
	 * userinfo(修改密码)
	 */
	@RequestMapping(value = "/userinfo", method = RequestMethod.GET)
	public ModelAndView userinfo(HttpServletRequest request,HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("admin/management/userinfo");
		return mv;
	}
	
	@RequestMapping(value = "/userinfoUpdate", method = RequestMethod.POST)
	public RedirectView userinfoUpdate(HttpServletRequest request,HttpServletResponse response) {
		
		String password=request.getParameter("password");
		String oldPassword=request.getParameter("oldPassword");
		if(oldPassword==null || password==null ||password.length()<6){
			return new RedirectView("userinfo.do");
		}
		LoginUser loginUser=SpringSecurityUtils.getLoginUser();
		SysUser sysUser=service.findById(loginUser.getId());
		sysUser.setRealName(request.getParameter("realName"));
		if(!sysUser.getPassword().equals(DigestUtils.md5Hex(oldPassword))){
			throw new ActionException("原密码错误!");
		}
		sysUser.setPassword(DigestUtils.md5Hex(password));
		sysUser.setEmail(request.getParameter("email"));
		service.update(sysUser,"password","realName","email");
		
		return new RedirectView(request.getContextPath()+"/admin/logout");
	}
	
	@ResponseBody
	@RequestMapping(value = "/userpassword", produces = "application/text; charset=utf-8")
	public String userpassword(HttpServletRequest request,HttpServletResponse response) {
		if (SpringSecurityUtils.getLoginUser().getPass().equals(DigestUtils.md5Hex(request.getParameter("oldPassword")))) {
			return null;
		} else{
			return "用户密码错误";
		}
	}
	
}
