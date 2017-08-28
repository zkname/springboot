package com.zkname.frame.util.exception;

/**
 * Action 操作异常类
 *
 * @version
 * @since Ver 1.1
 * @Date 2012-4-12
 */
public class DuanxinException extends BaseException {

	private static final long serialVersionUID = -8799685702021559528L;

	public DuanxinException(String message) {
		super(message);
	}

	public DuanxinException(String message, Exception e) {
		super(message, e);
	}
}
