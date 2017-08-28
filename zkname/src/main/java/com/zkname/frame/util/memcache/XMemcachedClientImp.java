package com.zkname.frame.util.memcache;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import net.rubyeye.xmemcached.Counter;
import net.rubyeye.xmemcached.MemcachedClient;

/**
 * 
 * 对XMemcached Client的二次封装,提供常用的Get/Set/Delete/Incr/Decr函数的封装.
 * 未提供封装的函数可直接调用getClient()取出Spy的原版MemcachedClient来使用.
 * 
 * @author zhangk@autoradio.cn
 * @version
 * @since Ver 1.1
 * @Date 2012-4-12
 */
public class XMemcachedClientImp implements XMemcacheClient{

	private static Logger logger = LoggerFactory.getLogger(XMemcachedClientImp.class);
	
	private MemcachedClient  memcachedClient;
	
	@Getter
	private String namespace;

	public XMemcachedClientImp(String namespace,MemcachedClient cache){
		this.namespace=namespace;
		this.setMemcachedClient(cache);
		logger.debug("Initializing {}",this.getClass().getCanonicalName());
	}
	
	public Long getCounter(String key) {
		try {
			Counter counter =  this.getMemcachedClient().getCounter(key);
			if(counter!=null){
				return counter.get();
			}
		} catch (TimeoutException e) {
		} catch (Exception e) {
			logger.error("memecache get key:"+key,e);
		}
		return null;
	}
	
	/**
	 * Get方法, 转换结果类型并屏蔽异常, 仅返回Null.
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		try {
			return (T) this.getMemcachedClient().get(key);
		} catch (TimeoutException e) {
		} catch (Exception e) {
			logger.error("memecache get key:"+key,e);
		}
		return null;
	}

	/**
	 * GetBulk方法, 转换结果类型并屏蔽异常.
	 */
	@SuppressWarnings("unchecked")
	public <T> Map<String, T> getBulk(Collection<String> keys) {
		try {
			return (Map<String, T>) this.getMemcachedClient().get(keys);
		} catch (TimeoutException e) {
		} catch (Exception e) {
			logger.error("memecache getBulk key:"+StringUtils.join(keys.iterator(), ","),e);
		}
		return null;
	}

	/**
	 * Set方法.
	 */
	public void set(String key, int expiredTime, Object value) {
		try {
			this.getMemcachedClient().set(key, expiredTime, value);
		} catch (TimeoutException e) {
		} catch (Exception e) {
			logger.error("memecache set key:"+key+" value:"+Objects.toString(value),e);
		}
	}

	/**
	 * Delete方法.
	 */
	public boolean delete(String key) {
		try {
			return this.getMemcachedClient().delete(key);
		} catch (TimeoutException e) {
		} catch (Exception e) {
			logger.error("memecache delete key:"+key,e);
		}
		return false;
	}

	/**
	 * Incr方法.
	 */
	public long incr(String key, int by, long defaultValue,int exp) {
		try {
			return this.getMemcachedClient().incr(key, by,defaultValue,exp);
		} catch (TimeoutException e) {
		} catch (Exception e) {
			logger.error("memecache incr key:"+key+" value:"+defaultValue,e);
		}
		return -1l;
	}

	/**
	 * Decr方法.
	 */
	public long decr(String key, int by, long defaultValue) {
		try {
			return this.getMemcachedClient().decr(key, by);
		} catch (TimeoutException e) {
		} catch (Exception e) {
			logger.error("memecache decr key:"+key+" value:"+defaultValue,e);
		}
		return -1l;
	}

	public MemcachedClient getMemcachedClient() {
		this.memcachedClient.endWithNamespace();
		this.memcachedClient.beginWithNamespace(namespace);
		return memcachedClient;
	}

	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

	public boolean delete(String... keys) {
		try {
			for(String key:keys){
				this.getMemcachedClient().deleteWithNoReply(key);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void destroy() throws Exception {
		try {
			if (this.getMemcachedClient() != null) {
				this.getMemcachedClient().shutdown();
			}
		} catch (Exception e) {
			logger.error("memecache destroy",e);
		}
	}
	
	/**
	 * 清除缓存
	 */
	public void invalidateNamespace(){
		try {
			this.getMemcachedClient().invalidateNamespace(this.getNamespace());
		} catch (Exception e) {
			logger.error("memecache invalidateNamespace",e);
		}
	}
}