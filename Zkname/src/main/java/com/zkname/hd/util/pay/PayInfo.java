package com.zkname.hd.util.pay;

import lombok.Getter;
import lombok.Setter;

public class PayInfo {
	
	public static final String APPID="wx14b534f75ea4296e";
	public static final String MCH_ID="1487189112";
	public static final String KEY="cfb1e8537345b22bc974b723af694ccc";
	
	@Getter
	@Setter
	public String body;
	@Getter
	@Setter
	public String nonce_str;
	@Getter
	@Setter
	public Long total_fee;
	@Getter
	@Setter
	public String appid;
	@Getter
	@Setter
	public String mch_id;
	@Getter
	@Setter
	public String device_info="WEB";
	@Getter
	@Setter
	public String attach;

}