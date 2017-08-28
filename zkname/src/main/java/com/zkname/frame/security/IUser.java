package com.zkname.frame.security;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Function: 登陆后保存 session 实现接口
 */
@JsonAutoDetect
@JsonIgnoreProperties
public interface IUser extends ISessionUser<Long> {
}
