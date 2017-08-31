package com.zkname.core.util.classinfo;

public class CacheExceptionUtils {

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
