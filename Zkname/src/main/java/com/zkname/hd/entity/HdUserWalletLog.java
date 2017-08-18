package com.zkname.hd.entity;

import com.zkname.hd.entity.base.*;

public class HdUserWalletLog extends BaseHdUserWalletLog{
	
	private static final long serialVersionUID = -3451888062016242057L;
	
	public static final Integer BEAN_TYPE_金豆交易加 = 1;
	public static final Integer BEAN_TYPE_金豆交易减 = 2;
	public static final Integer BEAN_TYPE_兑换金豆 = 3;
	public static final Integer BEAN_TYPE_金豆清空 = 4;
	public static final Integer BEAN_TYPE_游戏赢金豆 = 5;
	public static final Integer BEAN_TYPE_游戏输金豆 = 6;
	
	public static final Integer COIN_TYPE_金币充值 = 1;
	public static final Integer COIN_TYPE_兑换金豆 = 2;
	
	public HdUserWalletLog(){
	}
	
	public HdUserWalletLog(
		java.lang.Long id
	){
		
		super(
				id
			);
		
	}
}



