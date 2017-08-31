package com.yuewuxian.frame.security;

import java.util.Set;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Function: 登陆后保存 session 实现接口
 * 
 * 
 * @version
 * @since Ver 1.1
 * @Date 2011-8-3
 */
@JsonAutoDetect
@JsonIgnoreProperties(value = { "passWord" })
public interface IUser extends ISessionUser<Long> {
	public Long getId();

	public String getEmail();

	public Set<String> getPurview();
}
