package com.zkname.frame.util.memcache;

import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;

import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

/**
 * memcache key 统获取地址
 * @author LOK
 */
public class XMemcacheKey {
	/**
	 * 微信jsdk授权ticket key
	 */
	public static final String 微信JSDK="WAT_JSDK_";
	
	/**
	 * 微信授权ticket key
	 */
	public static final String 微信TOKEN="WAT_TOKEN_";
	
	/**
	 * 用户微信信息
	 */
	public static final String 微信用户信息="WEIXIN_INFO_";
	
	/**
	 * 对局信息
	 */
	public static final String 对局信息="GAME_INFO_";
	
	/**
	 * 用户状态
	 */
	public static final String 用户状态="USER_STATUS_";
	
	public static String getKey(String key){
		if(StringUtils.isNoneBlank(key) && key.length()>200){
			return md5(key);
		}
		return key;
	}
	
	/**
	 * MD5加密
	 * 
	 * @param str
	 * @return
	 */
	private static String md5(String str) {
		Hasher hasher = Hashing.md5().newHasher();  
		hasher.putString(str,Charset.forName("UTF-8")); 
		return hasher.hash().toString();
	}
}
