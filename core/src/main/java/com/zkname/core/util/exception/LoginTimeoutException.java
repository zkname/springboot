package com.zkname.core.util.exception;

/**
 * 登录超时
 *
 * @version
 * @since Ver 1.1
 * @Date 2012-4-12
 */
public class LoginTimeoutException extends BaseException {

	private static final long serialVersionUID = -8799685702021559528L;

	public LoginTimeoutException(String message) {
		super(message);
	}

	public LoginTimeoutException(String message, Exception e) {
		super(message, e);
	}
}
