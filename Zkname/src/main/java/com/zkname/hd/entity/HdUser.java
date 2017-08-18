package com.zkname.hd.entity;

import com.zkname.hd.entity.base.BaseHdUser;

import lombok.Getter;
import lombok.Setter;

public class HdUser extends BaseHdUser {

	private static final long serialVersionUID = -3451888062016242057L;

	/** 金币 */
	@Getter
	@Setter
	private java.lang.Long goldCoin;
	/** 金豆 */
	@Getter
	@Setter
	private java.lang.Long goldBean;
	
	public HdUser() {
	}

	public HdUser(java.lang.Long id) {
		super(id);
	}
}
