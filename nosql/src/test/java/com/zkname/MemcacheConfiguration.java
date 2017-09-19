package com.zkname;

import java.io.IOException;
import java.net.InetSocketAddress;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.google.common.collect.Lists;
import com.zkname.memcache.XMemcachedClientImp;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.auth.AuthInfo;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.impl.KetamaMemcachedSessionLocator;
import net.rubyeye.xmemcached.transcoders.SerializingTranscoder;


/**
 * memcache 配置
 * @author ZhangKai
 *
 */
@Configuration
public class MemcacheConfiguration {
	
	private static final Logger logger = LoggerFactory.getLogger(MemcacheConfiguration.class);
	
	@Resource
    private Environment env;
	
	@Bean("defaultMemcachedClient")
	public MemcachedClient getDefaultMemcachedClient() throws IOException {
		logger.info("init Memcache Configuration ");
		logger.info("init Memcache AuthInfo host:{} port:{}",env.getProperty("memecache-0.host"),env.getProperty("memecache-0.port"));
		InetSocketAddress inetSocketAddress=new InetSocketAddress(env.getProperty("memecache-0.host"),Integer.parseInt(env.getProperty("memecache-0.port")));
		XMemcachedClientBuilder xcb=new XMemcachedClientBuilder(Lists.newArrayList(inetSocketAddress));
		if(StringUtils.isNoneBlank(env.getProperty("memecache-0.username"),env.getProperty("memecache-0.password"))){
			xcb.addAuthInfo(inetSocketAddress, AuthInfo.plain(env.getProperty("memecache-0.username"),env.getProperty("memecache-0.password")));
			logger.info("init Memcache AuthInfo username:{} password:{}",env.getProperty("memecache-0.username"),env.getProperty("memecache-0.password"));
		}
		xcb.setConnectionPoolSize(1);
		xcb.setCommandFactory(new BinaryCommandFactory());
		xcb.setSessionLocator(new KetamaMemcachedSessionLocator());
		xcb.setTranscoder(new SerializingTranscoder());
		return xcb.build();
	}
	
	
	@Bean("baseXMemcachedClient")
	public XMemcachedClientImp getBaseXMemcachedClient() throws IOException{
		return new XMemcachedClientImp(env.getProperty("memecache-0.key"),this.getDefaultMemcachedClient());
	}
	
	@Bean("userXMemcachedClient")
	public XMemcachedClientImp getUserXMemcachedClient() throws IOException{
		return new XMemcachedClientImp(env.getProperty("memecache-user.key"),this.getDefaultMemcachedClient());
	}
}