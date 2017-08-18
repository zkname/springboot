package com.zkname.hd.util.wechat;

import com.zkname.hd.util.http.ClientConnectionManager;

/**
 * 微信小程序
 * 
 * @author ZhangKai
 */
public class WeChatSmallProgramUtil {
	
	private static final String APP_ID = "wx14b534f75ea4296e";
	private static final String APP_SECRET = "aa0170dd1fbc10d7016c7f821f54c5f9";
	
	public static final String CODE_换取_SESSION_KEY = "https://api.weixin.qq.com/sns/jscode2session";
	
	/**
	 * code 换取 session_key
		appid	是	小程序唯一标识
		secret	是	小程序的 app secret
		js_code	是	登录时获取的 code
		grant_type	是	填写为 authorization_code
	 * @return
		openid	用户唯一标识
		session_key	会话密钥
		unionid	用户在开放平台的唯一标识符。本字段在满足一定条件的情况下才返回。具体参看UnionID机制说明
		//正常返回的JSON数据包
		{
		      "openid": "OPENID",
		      "session_key": "SESSIONKEY",
		      "unionid": "UNIONID"
		}
		//错误时返回JSON数据包(示例为Code无效)
		{
		    "errcode": 40029,
		    "errmsg": "invalid code"
		}
	 */
	public static String getCode_exchange_session_key(String appid,String secret,String code){
		String url=CODE_换取_SESSION_KEY+"?appid="+appid+"&secret="+secret+"&js_code="+code+"&grant_type=authorization_code";
		return ClientConnectionManager.getInstance().get(url);
	}
	
	/**
	 * 
	 * @param code
	 * @return
	 */
	public static String getCode_exchange_session_key(String code){
		String url=CODE_换取_SESSION_KEY+"?appid="+APP_ID+"&secret="+APP_SECRET+"&js_code="+code+"&grant_type=authorization_code";
		return ClientConnectionManager.getInstance().get(url);
	}
	
	public static void main(String[] args) {
		System.out.println(CODE_换取_SESSION_KEY+String.join("=","&appid","asd","&secret", "secret","&js_code"));	
	}
}
