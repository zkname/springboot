package com.zkname.frame.util.memcache;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.DisposableBean;

/**
 *
 * @author zhangk@autoradio.cn
 * @version
 * @since Ver 1.1
 * @Date 2012-4-12
 */
public interface XMemcacheClient extends DisposableBean {

	/**
	 * Get方法, 转换结果类型并屏蔽异常, 仅返回Null.
	 */
	public abstract <T> T get(String key);

	/**
	 * GetBulk方法, 转换结果类型并屏蔽异常.
	 */
	public abstract <T> Map<String, T> getBulk(Collection<String> keys);

	/**
	 * Set方法.
	 */
	public abstract void set(String key, int expiredTime, Object value);

	/**
	 * Delete方法.
	 */
	public abstract boolean delete(String... key);

	/**
	 * Incr方法.
	 */
	public abstract long incr(String key, int by, long defaultValue, int exp);

	/**
	 * Decr方法.
	 */
	public abstract long decr(String key, int by, long defaultValue);

	/**
	 * 获取计数器
	 * 
	 * @param key
	 * @return
	 */
	public Long getCounter(String key);

	/**
	 * 清除缓存
	 */
	public void invalidateNamespace();

}
