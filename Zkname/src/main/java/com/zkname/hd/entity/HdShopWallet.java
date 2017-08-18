package com.zkname.hd.entity;

import com.zkname.hd.entity.base.*;

import lombok.Getter;
import lombok.Setter;

public class HdShopWallet extends BaseHdShopWallet {

	private static final long serialVersionUID = -3451888062016242057L;

	public static final int 返利 = 1;
	public static final int 提现 = 2;

	@Getter
	@Setter
	private java.lang.String shopName;

	public HdShopWallet() {
	}

	public HdShopWallet(java.lang.Long shopId) {

		super(shopId);

	}
}
