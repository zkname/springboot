package com.zkname.hd.util.pay;

import lombok.Getter;
import lombok.Setter;

public class PayInfo {
	
	public static final String APPID="";
	public static final String MCH_ID="";
	public static final String KEY="";
	
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