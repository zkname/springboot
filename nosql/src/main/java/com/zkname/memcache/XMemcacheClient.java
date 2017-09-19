package com.zkname.memcache;

import java.util.Collection;
import java.util.Map;
import org.springframework.beans.factory.DisposableBean;
import net.rubyeye.xmemcached.MemcachedClient;

public interface XMemcacheClient extends DisposableBean{
	
	public abstract MemcachedClient getMemcachedClient();
	
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
	public abstract long incr(String key, int by, long defaultValue,int exp);

	/**
	 * Decr方法.
	 */
	public abstract long decr(String key, int by, long defaultValue);

	/**
	 * 获取incr值
	 * @param key
	 * @return
	 */
	public Long getCounter(String key);
	
	/**
	 * 清除缓存
	 */
	public void invalidateNamespace();

}
