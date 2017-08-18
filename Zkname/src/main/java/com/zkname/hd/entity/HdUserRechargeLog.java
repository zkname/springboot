package com.zkname.hd.entity;

import com.zkname.hd.entity.base.*;

import lombok.Getter;
import lombok.Setter;

public class HdUserRechargeLog extends BaseHdUserRechargeLog{
	
	private static final long serialVersionUID = -3451888062016242057L;
	
	/** 用户名 */
	@Getter
	@Setter
	private java.lang.String nickname;
	/** 店铺名 */
	@Getter
	@Setter
	private java.lang.String shopName;
	
	public HdUserRechargeLog(){
	}
	
	public HdUserRechargeLog(
		java.lang.Long id
	){
		
		super(
				id
			);
		
	}
}



