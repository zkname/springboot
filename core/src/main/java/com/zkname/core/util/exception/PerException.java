package com.zkname.core.util.exception;

import com.zkname.core.util.exception.ActionException;

/**
 * 权限异常
 * @author Administrator
 */
public class PerException extends ActionException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PerException(String message) {
		super(message);
	}

	public PerException(String message, Exception e) {
		super(message, e);
	}
}
