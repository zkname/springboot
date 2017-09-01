package com.zkname.patchca;

import com.zkname.core.util.exception.BaseException;

public class CaptchaException extends BaseException {

	private static final long serialVersionUID = -8799685702021559528L;

	public CaptchaException(String message) {
		super(message);
	}

	public CaptchaException(String message, Exception e) {
		super(message, e);
	}
}
