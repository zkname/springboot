package com.zkname.credit.card.session;

/**
 * Function: 登陆后保存 session 实现接口
 * 
 * 
 * @version
 * @since Ver 1.1
 */
public interface IUser extends ISessionUser<Long> {
	public Long getId();
}
