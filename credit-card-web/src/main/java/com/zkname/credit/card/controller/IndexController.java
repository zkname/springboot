package com.zkname.credit.card.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zkname.core.controller.BaseController;
import com.zkname.core.util.DateUtil;
import com.zkname.core.util.exception.ActionException;
import com.zkname.core.util.exception.DaoException;
import com.zkname.core.util.spring.SpringHttpServletRequest;
import com.zkname.credit.card.entity.CinvitationCode;
import com.zkname.credit.card.entity.SysUser;
import com.zkname.credit.card.service.CinvitationCodeService;
import com.zkname.credit.card.service.SysParamService;
import com.zkname.credit.card.service.SysUserService;
import com.zkname.credit.card.util.EncodeUtils;
import com.zkname.credit.card.util.email.MailUtil;
import com.zkname.patchca.PatchcaFilter;


/**
 * 首页
 */
@Controller
@RequestMapping(value = "/")
public class IndexController extends BaseController {
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private CinvitationCodeService cinvitationCodeService;
	
	public static int OPEN_REGISTER=0;
	
	public static String RESET_PASSWORD_URL;
	
	
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
	public String login(String code,String username,String password,ModelMap model)  {
		if(StringUtils.isAnyBlank(username,password,code)){
        	model.put("login_error", "参数错误！");
        	return "index";
        }
		if(!PatchcaFilter.isValidationCode(SpringHttpServletRequest.getRequest(), code)){
			model.put("login_error", "验证码错误！");
        	return "index";
		}
		SysUser loginUser=sysUserService.login(username);
		if(loginUser==null){
        	model.put("login_error", "用户不存在！");
        	return "index";
		}
		if(!StringUtils.equals(loginUser.getPassword(),EncodeUtils.md5(password))){
        	model.put("login_error", "用户密码错误！");
        	return "index";
		}
		loginUser.setLoginTime(new Date());
		sysUserService.updateLoginTime(loginUser);
		//登录
		loginUser.getLoginUser().login();
        return "redirect:/admin/index"; 
	}
	
	
	
	/**
	 * 注册登录
	 * @param username
	 * @param password
	 * @param code
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerGet(ModelMap model)  {
		model.put("open_register", OPEN_REGISTER);
        return "register";
	}
	
	
	/**
	 * 注册登录
	 * @param username
	 * @param password
	 * @param code
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerPost(HttpServletRequest request,String username,String password,String email,String code,ModelMap model)  {
		String invitationCode=request.getParameter("invitationCode");
		if(OPEN_REGISTER==0){
			if(StringUtils.isAnyBlank(invitationCode)){
	        	model.put("login_error", "参数错误！");
	        	return "register";
	        }
		}
		if(StringUtils.isAnyBlank(username,password,email,code)){
        	model.put("login_error", "参数错误！");
        	return "register";
        }
		if(!PatchcaFilter.isValidationCode(SpringHttpServletRequest.getRequest(), code)){
			model.put("login_error", "验证码错误！");
        	return "register";
		}
		CinvitationCode cinvitationCode=cinvitationCodeService.getDAO().findByInvitationCode(invitationCode);
		if(OPEN_REGISTER==0 && cinvitationCode==null){
			model.put("login_error", "邀请码错误！");
        	return "register";
		}
		if(sysUserService.findUserByUserName(username)!=null){
			model.put("login_error", "用户名已存在！");
        	return "register";
		}
		try {
			SysUser sysUser=new SysUser();
			sysUser.setCreateTime(new Date());
			sysUser.setCreatorId(1L);
			sysUser.setDeleStatus("1");
			sysUser.setEmail(email);
			sysUser.setLoginTime(new Date());
			sysUser.setRole(0);
			sysUser.setPassword(DigestUtils.md5Hex(password));
			sysUser.setUsername(username);
			sysUser.setValidPeriodTime(DateUtil.addDate(DateUtil.getNowDate(), 90));
			sysUser.setRealName(username);
			sysUserService.register(sysUser,cinvitationCode,OPEN_REGISTER);
		} catch (ActionException e) {
			model.put("login_error", e.getMessage());
        	return "register";
		}
		return "redirect:/index"; 
	}
	
	
	/**
	 * checkLoginName(查询用户是否已经存在)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param oldLoginName
	 * @param username
	 * @param request
	 * @param response
	 * @return 
	 * String
	 * @throws UnsupportedEncodingException 
	 * @exception 
	 * @since  1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/checkLoginName", produces = "application/text; charset=utf-8")
	public String checkLoginName(@RequestParam("oldLoginName") String oldLoginName,@RequestParam("username") String username,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		
		if (username.equals(oldLoginName)) {
			return null;
		} else if (sysUserService.findUserByUserName(username) != null) {
			return "用户名已存在";
		}
		return null;
	}
	
	
	
	/**
	 * checkLoginName(查询用户是否已经存在)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param oldLoginName
	 * @param username
	 * @param request
	 * @param response
	 * @return 
	 * String
	 * @throws UnsupportedEncodingException 
	 * @exception 
	 * @since  1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/checkEmail", produces = "application/text; charset=utf-8")
	public String checkEmail(@RequestParam("oldEmail") String oldLoginName,@RequestParam("email") String email,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		
		if (email.equals(oldLoginName)) {
			return null;
		} else if (sysUserService.findUserByEmail(email) != null) {
			return "Email已存在";
		}
		return null;
	}
	
	
	
	
	/**
	 * 忘记密码
	 * @param username
	 * @param password
	 * @param code
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/forgotten", method = RequestMethod.GET)
	public String forgottenGet()  {
        return "forgotten";
	}
	

	/**
	 * 找回密码
	 * @param email
	 * @param code
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/forgotten", method = RequestMethod.POST)
	public String forgottenPost(String email,String code,ModelMap model)  {
		if(StringUtils.isAnyBlank(email,code)){
        	model.put("login_error", "参数错误！");
        	return "forgotten";
        }
		if(!PatchcaFilter.isValidationCode(SpringHttpServletRequest.getRequest(), code)){
			model.put("login_error", "验证码错误！");
        	return "forgotten";
		}
		SysUser sysUser=sysUserService.findUserByEmail(email);
		if(sysUser==null){
			model.put("login_error", "email不存在！");
        	return "forgotten";
		}
		try {
			sysUser.setResetCode(UUID.randomUUID().toString().replace("-", ""));
			sysUser.setResetOutDate(new Date(System.currentTimeMillis()+1800000));
			sysUserService.update(sysUser, "resetCode","resetOutDate");
			String sid=DigestUtils.md5Hex(sysUser.getId()+"|"+sysUser.getResetCode()+"|"+sysUser.getResetOutDateString());
			long num=sysUser.getId();
			Thread t = new Thread(new Runnable(){  
	            public void run(){  
	            	MailUtil.sendResetPassword(email, IndexController.RESET_PASSWORD_URL+"?sid="+sid+"&num="+num);
	        }});  
	        t.start();
			model.put("login_error", "邮件已发送“"+sysUser.getEmail()+"”邮箱!");
        	return "index";			
		} catch (ActionException e) {
			model.put("login_error", e.getMessage());
        	return "forgotten";
		}
	}
	
	/**
	 * 修改密码
	 * @param num
	 * @param sid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/reset_password", produces = "text/html; charset=utf-8")
	public String resetPassword(long num,String sid){
		if(num==0L || StringUtils.isAnyBlank(sid)){
        	return "参数错误！";
        }
		SysUser sysUser=sysUserService.findById(num);
		if(sysUser==null){
			return "参数错误！";
		}
		if(sysUser.getResetCode()==null || sysUser.getResetOutDate()==null){
			return "链接已失效！";
		}
		if(!StringUtils.equals(DigestUtils.md5Hex(sysUser.getId()+"|"+sysUser.getResetCode()+"|"+sysUser.getResetOutDateString()), sid)){
			return "链接已失效！";
		}
		String password=RandomUtils.nextLong(100000L, 1000000L)+"";
		sysUser.setPassword(DigestUtils.md5Hex(password));
		sysUser.setResetCode(null);
		sysUser.setResetOutDate(null);
		sysUserService.update(sysUser,  "resetCode","resetOutDate","password");
		return "新密码为："+password;
	}
}	
