package com.zkname.redis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

public class ListOps {
	
	@Getter
	private StringRedisTemplate stringRedisTemplate;

	public ListOps(StringRedisTemplate stringRedisTemplate){
		this.stringRedisTemplate=stringRedisTemplate;
	}
	
	/**
	 * 压栈 [push{11},1,2,3,4,5,6,7,8,9,10]
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Long push(String key, String... value) {
		return stringRedisTemplate.opsForList().leftPushAll(key, value);
	}

	/**
	 * 出栈 [pop{1},2,3,4,5,6,7,8,9,10]
	 * 
	 * @param key
	 * @return
	 */
	public String pop(String key) {
		return stringRedisTemplate.opsForList().leftPop(key);
	}

	/**
	 * 入队 [1,2,3,4,5,6,7,8,9,10,in{11}]
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Long in(String key, String... value) {
		return stringRedisTemplate.opsForList().rightPushAll(key, value);
	}

	/**
	 * 出队 [1,2,3,4,5,6,7,8,9,10,out{11}]
	 * 
	 * @param key
	 * @return
	 */
	public String out(String key) {
		return stringRedisTemplate.opsForList().rightPop(key);
	}

	/**
	 * 栈/队列长
	 * 
	 * @param key
	 * @return
	 */
	public Long length(String key) {
		return stringRedisTemplate.opsForList().size(key);
	}

	/**
	 * 范围检索
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<String> range(String key, int start, int end) {
		return stringRedisTemplate.opsForList().range(key, start, end);
	}

	/**
	 * 移除
	 * 
	 * @param key
	 *            key
	 * @param i
	 *            数量
	 * @param value
	 *            map key值
	 */
	public void remove(String key, long i, String value) {
		stringRedisTemplate.opsForList().remove(key, i, value);
	}

	/**
	 * 检索
	 * 
	 * @param key
	 * @param index
	 * @return
	 */
	public String index(String key, long index) {
		return stringRedisTemplate.opsForList().index(key, index);
	}

	/**
	 * 置值
	 * 
	 * @param key
	 * @param index
	 * @param value
	 */
	public void set(String key, long index, String value) {
		stringRedisTemplate.opsForList().set(key, index, value);
	}

	/**
	 * 裁剪
	 * 
	 * @param key
	 * @param start
	 * @param end
	 */
	public void trim(String key, long start, int end) {
		stringRedisTemplate.opsForList().trim(key, start, end);
	}
}
