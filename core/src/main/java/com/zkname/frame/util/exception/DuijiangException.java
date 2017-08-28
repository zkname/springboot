package com.zkname.frame.util.exception;

/**
 * Action 操作异常类
 *
 * @version
 * @since Ver 1.1
 * @Date 2012-4-12
 */
public class DuijiangException extends BaseException {

	private static final long serialVersionUID = -8799685702021559528L;

	public DuijiangException(String message) {
		super(message);
	}

	public DuijiangException(String message, Exception e) {
		super(message, e);
	}
}
