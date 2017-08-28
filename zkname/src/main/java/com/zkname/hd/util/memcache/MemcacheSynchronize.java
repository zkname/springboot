package com.zkname.hd.util.memcache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zkname.frame.util.memcache.XMemcacheClient;
import com.zkname.frame.util.spring.SpringContextHolder;

/**
 * 缓存同步类
 * @author ZhangKai
 *
 */
public class MemcacheSynchronize {
	
	private static final Logger logger = LoggerFactory.getLogger(MemcacheSynchronize.class);
	
	public static final int EXP_方法同步超时秒=10;
	
	/**
	 * 同步
	 * @param baseXMemcachedClient
	 * @param key
	 * @param iMemSyn
	 * @return
	 */
	public static boolean memcacheSyn(XMemcacheClient baseXMemcachedClient,String key,int exp,IMemSyn iMemSyn){
		try {
			key = "SYN_" + key;
			Long v = null;
			do {
				if ((v == null || v.longValue()==0L) && baseXMemcachedClient.incr(key, 1, 1, exp) == 1L) {
					break;
				}
				v = baseXMemcachedClient.getCounter(key);
				if (v != null && v.longValue() == 1L) {
					break;
				}
				Thread.sleep(100);
			} while (true);
			return iMemSyn.runMemSyn();
		}catch (Exception e) {
			logger.error("memcache 同错错误:",e);
			return false;
		} finally {
			// 删除缓存
			try {
				baseXMemcachedClient.delete(key);
			} catch (Exception e) {
				logger.error("memcache 同错删除key错误:",e);
			}
		}
	}
	
	public static void test() {
		XMemcacheClient xMemcachedClientImp=SpringContextHolder.getBean("baseXMemcachedClient");
		
		String key="test_aaaaaaaaaaaaaaa";
		IMemSyn iMemSyn = ()->{
			logger.info(Thread.currentThread().getId()+"牛逼"+xMemcachedClientImp.incr(key, 1, 1, 1000));
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			logger.info(Thread.currentThread().getId()+"牛逼"+xMemcachedClientImp.getCounter(key));
			return true;
		};
		Runnable r = () -> {
			MemcacheSynchronize.memcacheSyn(xMemcachedClientImp, "abcd_"+key, MemcacheSynchronize.EXP_方法同步超时秒, iMemSyn);
		};
		
		for(int i=0;i<50;i++){
			new Thread(r).start();
		}
	}
	
}
