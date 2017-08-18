package com.zkname.hd.entity;

import com.zkname.hd.entity.base.*;

import lombok.Getter;
import lombok.Setter;

public class HdShopWalletLog extends BaseHdShopWalletLog{
	
	private static final long serialVersionUID = -3451888062016242057L;
	
	@Getter
	@Setter
	private java.lang.String shopName;
	
	public HdShopWalletLog(){
	}
	
	public HdShopWalletLog(
		java.lang.Long id
	){
		
		super(
				id
			);
		
	}
}



