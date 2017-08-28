package com.zkname.hd.util.pay;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.zkname.frame.util.conf.ConfigurationProperties;
import com.zkname.frame.util.jackson.XmlUtil;
import com.zkname.hd.util.EncodeUtils;
import com.zkname.hd.util.http.ClientConnectionManager;

public class PlayUnifiedorder {

	@SuppressWarnings("rawtypes")
	public static String unifiedorder(PayInfo payinfo,String orderId, String ip,String openid)  {
		String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		String entity = genProductArgs(payinfo, orderId, ip ,openid);
		String json = ClientConnectionManager.getInstance().postEntityJson(url, entity);
		Map map = XmlUtil.xmlToObject(json,HashMap.class);
//		Map<String,String> returnMap=Maps.newHashMap();
		if (map != null && map.get("result_code") != null && map.get("result_code").toString().equals("SUCCESS") && map.get("return_code") != null && map.get("return_code").toString().equals("SUCCESS")) {
//			returnMap.put("paySign", map.get("sign").toString());
//			returnMap.put("prepay_id",map.get("prepay_id").toString());
//			returnMap.put("signType", "MD5");
			return map.get("prepay_id").toString();
		}
		return null;
	}

	private static String genProductArgs(PayInfo payinfo, String orderId,  String ip,String openid) {
		Map<String, String> map = Maps.newHashMap();
		try {
			map.put("appid", payinfo.getAppid());
			map.put("openid", openid);
			map.put("mch_id", payinfo.getMch_id());
			map.put("device_info", payinfo.getDevice_info());
			map.put("nonce_str", payinfo.getNonce_str());
			map.put("body", payinfo.getBody());
			map.put("out_trade_no", orderId);
			map.put("total_fee", payinfo.getTotal_fee().toString());
			map.put("spbill_create_ip", ip);
			map.put("attach", payinfo.getAttach());
			map.put("notify_url", ConfigurationProperties.getInstance().getProperty("SERVER_HOST")+"/api/weixinOrderCallback.do");
			map.put("trade_type", "JSAPI");
			getSign(map);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return XmlUtil.objectToXml(map);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void getSign(Map map) {
		List<String> params = new ArrayList<String>();
		Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			params.add(entry.getKey() + "=" + entry.getValue());
		}
		Collections.sort(params, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		StringBuffer sb = new StringBuffer();
		for (String v : params) {
			if (sb.length() != 0) {
				sb.append("&");
			}
			sb.append(v);
		}
		String temp = sb.append("&key=").append(PayInfo.KEY).toString();
		map.put("sign", EncodeUtils.md5(temp).toUpperCase());
	}
	

	/**
	 * 将元数据前补零，补后的总长度为指定的长度，以字符串的形式返回
	 * 
	 * @param sourceDate
	 * @param formatLength
	 * @return 重组后的数据
	 */
	public static String frontCompWithZore(long sourceDate, int formatLength) {
		/*
		 * 0 指前面补充零 formatLength 字符总长度为 formatLength d 代表为正数。
		 */
		String newString = String.format("%0" + formatLength + "d", sourceDate);
		return newString;
	}

	
	public static String getBodyString(BufferedReader br) {
		String inputLine;
		String str = "";
		try {
			while ((inputLine = br.readLine()) != null) {
				str += inputLine;
			}
		} catch (IOException e) {
			str=null;
		}finally{
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
				}
			}
		}
		return str;
	}
	
}
