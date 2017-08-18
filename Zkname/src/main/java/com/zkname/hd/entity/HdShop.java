package com.zkname.hd.entity;

import com.zkname.hd.entity.base.*;

import lombok.Getter;
import lombok.Setter;

public class HdShop extends BaseHdShop{
	
	private static final long serialVersionUID = -3451888062016242057L;
	
	/**
	 * 店铺余额
	 */
	@Getter
	@Setter
	private Long money;
	
	public HdShop(){
	}
	
	public HdShop(
		java.lang.Long id
	){
		
		super(
				id
			);
		
	}
}



