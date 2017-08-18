package com.zkname.hd.entity;

import com.zkname.hd.entity.base.*;

public class HdUserPayLog extends BaseHdUserPayLog{
	
	private static final long serialVersionUID = -3451888062016242057L;
	
	public static final int 支付宝 = 1;
	public static final int 微信 = 2;
	
	
	public HdUserPayLog(){
	}
	
	public HdUserPayLog(
		java.lang.Long id
	){
		
		super(
				id
			);
		
	}
}



