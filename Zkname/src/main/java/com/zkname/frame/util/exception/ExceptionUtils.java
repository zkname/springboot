package com.zkname.frame.util.exception;

public class ExceptionUtils {

	/**
	 * 将CheckedException转换为UnCheckedException.
	 */
	public static RuntimeException unchecked(Exception e) {
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		return new RuntimeException(e.getMessage(), e);
	}
}
