package com.zkname.core.util.spring.thread;

import org.springframework.util.StopWatch;



/**
 * 线程变量保存StopWatch
 */
public class StopWatchLocal {
	
	private static ThreadLocal<StopWatch> THREADLOCAL = new ThreadLocal<StopWatch>();
	
	/**
	 * uuid
	 * @return
	 */
	public static StopWatch get() {
		return StopWatchLocal.THREADLOCAL.get();
	}
	
	/**
	 * 写入线程变量里的数据
	 */
	public static void set(StopWatch value) {
		StopWatchLocal.THREADLOCAL.set(value);
	}
	
	
	/**
	 * 清理线程变量
	 */
	public static void clear() {
		StopWatchLocal.THREADLOCAL.remove();
	}
}