package com.zkname.demo.session;

import com.zkname.frame.util.exception.LoginTimeoutException;
import com.zkname.frame.util.spring.SpringHttpServletRequest;

import lombok.Getter;
import lombok.Setter;

/**
 * 登陆session对象
 * 
 * @version  
 * @since    Ver 1.1
 */
public class LoginUser implements IUser{
	
	
	/**
	 * serialVersionUID:TODO
	 *
	 * @since 1.0.0
	 */
	private static final long serialVersionUID = 4405044834730876477L;

	//主键
	@Getter
	@Setter
	private Long id;
	//email地址
	@Getter
	@Setter
	private String username;
	//密码
	@Getter
	@Setter
	private String password;

	
	public static IUser getUser(){
		IUser user = (IUser) SpringHttpServletRequest.getRequest().getSession().getAttribute(LoginUser.SESSION_KEY);
		if(user==null){
			throw new LoginTimeoutException("登录超时！");
		}
		return user;
	}
	
	/**
	 * 登录
	 */
	public void login(){
		SpringHttpServletRequest.getRequest().getSession().setAttribute(LoginUser.SESSION_KEY,this);
	}
	
	
	/**
	 * 退出
	 */
	public static void logout(){
		SpringHttpServletRequest.getRequest().getSession().removeAttribute(LoginUser.SESSION_KEY);
	}
}

