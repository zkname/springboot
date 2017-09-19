package com.zkname;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.zkname.redis.CacheUtils;
import com.zkname.redis.ListOps;

import redis.clients.jedis.JedisShardInfo;


/**
 * memcache 配置
 * @author ZhangKai
 *
 */
@Configuration
public class RedisConfiguration {
	
	private static final Logger logger = LoggerFactory.getLogger(RedisConfiguration.class);
	
	@Value("${spring.data.redis.host}")
	private String springDataRedisHost;
	@Value("${spring.data.redis.port}")
	private int springDataRedisPort;
	
	/**
	 * jedis
	 */
	@Bean
	public RedisConnectionFactory jedisConnectionFactory() {
		logger.info("init Redis Configuration ");
		JedisShardInfo sentinelConfig = new JedisShardInfo(springDataRedisHost, springDataRedisPort);
		return new JedisConnectionFactory(sentinelConfig);
	}
	
	@Bean
	public StringRedisTemplate stringRedisTemplate(){
		return new StringRedisTemplate(jedisConnectionFactory());
	}
	
	@Bean
	public ListOps listOps(){
		return new ListOps(stringRedisTemplate());
	}
	
	@Bean
	public CacheUtils cacheUtils(){
		RedisTemplate<String, Object> rt=new RedisTemplate<String, Object>();
		rt.setConnectionFactory(jedisConnectionFactory());
		rt.setKeySerializer(new StringRedisSerializer());
		rt.setHashKeySerializer(new StringRedisSerializer());
		rt.setHashValueSerializer(new StringRedisSerializer());
		rt.afterPropertiesSet();
		return new CacheUtils(stringRedisTemplate(),rt);
	}
}