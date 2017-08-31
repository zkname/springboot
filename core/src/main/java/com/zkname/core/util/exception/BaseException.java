package com.zkname.core.util.exception;

/**
 * 
 *
 * @version
 * @since Ver 1.1
 * @Date 2012-5-24
 */
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = -4754801552696365817L;

	public BaseException(String message, Exception e) {
		super(message, e);
	}

	public BaseException() {
		super();
	}

	public BaseException(Exception e) {
		super(e);
	}

	public BaseException(String message) {
		super(message);
	}
}
