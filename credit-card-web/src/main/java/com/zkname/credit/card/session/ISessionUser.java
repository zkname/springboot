package com.zkname.credit.card.session;

import java.io.Serializable;

/**
 * Function: 登陆后保存 session 实现接口
 * 
 *
 * @version
 * @since Ver 1.1
 * @Date 2011-8-3
 */
public interface ISessionUser<T> extends Serializable {
	// 用户保存session名
	public static final String SESSION_KEY = "user";

	// 主键
	public T getId();

	// 用户名
	public String getUsername();

	// 密码
	public String getPassword();
}
