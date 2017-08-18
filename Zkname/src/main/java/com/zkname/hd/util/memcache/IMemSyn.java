package com.zkname.hd.util.memcache;

/**
 * 同步执行方法接口
 * @author ZhangKai
 *
 */
@FunctionalInterface
public interface IMemSyn { 
	
	/**
	 * 业务代码
	 * @return
	 */
	public boolean runMemSyn();
}
