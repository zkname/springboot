package com.zkname.demo.security;

import java.util.Set;

/**
 * Function: 登陆后保存 session 实现接口
 * 
 */
public interface IUser extends ISessionUser<Long> {
	public Long getId();

	public String getEmail();

	public Set<String> getPurview();
}
