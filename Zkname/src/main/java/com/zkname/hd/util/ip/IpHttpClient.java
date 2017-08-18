package com.zkname.hd.util.ip;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.zkname.hd.util.http.ClientConnectionManager;
import com.zkname.frame.util.json.JsonUtil;

public class IpHttpClient {

	public static final String URL = "http://ip.taobao.com/service/getIpInfo.php?ip=";

	public static Map<String,String> getCity(String ip) {
		String value = ClientConnectionManager.getInstance().get(URL + ip);
		HashMap<String, Object> map1 = JsonUtil.jsonToObject(value, HashMap.class);
		if((Integer)map1.get("code")==0){
			Map map2=(Map) map1.get("data");
			return ImmutableMap.of("region",map2.get("region").toString(),"city",map2.get("city").toString());
		}
		return ImmutableMap.of("city","其他","region","其他");
	}
	
	public static void main(String[] args) {
		Map map=IpHttpClient.getCity("115.200.227.161");
		System.out.println(map);
	}
}
