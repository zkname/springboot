package com.zkname.frame.security;

import lombok.Getter;
import lombok.Setter;

/**
 * 登陆session对象
 */
public class LoginUser implements IUser{
	
	private static final long serialVersionUID = 4405044834730876477L;

	//主键	
	@Getter
	@Setter
	private Long id;
	//用户名
	@Getter
	@Setter
	private String username;
	//密码
	@Getter
	@Setter
	private String password;

}

