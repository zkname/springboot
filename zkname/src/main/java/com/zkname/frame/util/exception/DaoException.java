package com.zkname.frame.util.exception;

/**
 * Dao 异常 
 * 
 *
 * @version
 * @since Ver 1.1
 * @Date 2011-6-28
 */
public class DaoException extends BaseException {
	/**
	 * serialVersionUID:TODO
	 * 
	 * @since 1.0.0
	 */
	private static final long serialVersionUID = 8768167783906747195L;

	public DaoException(String message, Exception e) {
		super(message, e);
	}

	public DaoException(String message) {
		super(message);
	}

}
