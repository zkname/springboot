package com.zkname.credit.card.util;

import org.apache.commons.lang3.StringUtils;

public class IpUtil {

	/**
	 * 字符串ip转换为long
	 * 
	 * @param 字符串ip
	 * @return
	 */
	public static long getStringIpToLong(String ip) {
		if(StringUtils.isBlank(ip)){
			return 0;
		}
		String[] ips = ip.split("[.]");
		long num = 16777216L * Long.parseLong(ips[0]) + 65536L * Long.parseLong(ips[1]) + 256 * Long.parseLong(ips[2]) + Long.parseLong(ips[3]);
		return num;
	}

	/**
	 * 长整型ip转换为string
	 * 
	 * @param long型ip
	 * @return
	 */
	public static String getLongIpToString(long ipLong) {

		long mask[] = { 0x000000FF, 0x0000FF00, 0x00FF0000, 0xFF000000 };
		long num = 0;
		StringBuffer ipInfo = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			num = (ipLong & mask[i]) >> (i * 8);
			if (i > 0)
				ipInfo.insert(0, ".");
			ipInfo.insert(0, Long.toString(num, 10));
		}
		return ipInfo.toString();
	}
}
