package com.zkname.hd.util.exception;

public class CountException extends BaseException {

	private static final long serialVersionUID = -8799685702021559528L;


	public CountException(String message) {
		super(message);
	}
	
	public CountException(String message, Exception e) {
		super(message, e);
	}
	
}
