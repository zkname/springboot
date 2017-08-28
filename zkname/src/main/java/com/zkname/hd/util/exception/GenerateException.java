package com.zkname.hd.util.exception;

/**
 * Action 操作异常类
 *
 * @version
 * @since Ver 1.1
 * @Date 2012-4-12
 */
public class GenerateException extends BaseException {

	private static final long serialVersionUID = -8799685702021559528L;

	public static final String 附件格式错误 = "附件格式错误";
	public static final String 赠品编码重复 = "赠品编码重复";
	public static final String 附件读取异常 = "附件读取异常";
	public static final String 程序异常 = "程序异常";
	
	
	public GenerateException(String message) {
		super(message);
	}

	public GenerateException(String message, Exception e) {
		super(message, e);
	}
}
