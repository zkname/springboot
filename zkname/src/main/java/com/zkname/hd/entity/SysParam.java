package com.zkname.hd.entity;

import com.zkname.hd.entity.base.BaseSysParam;
import com.zkname.frame.util.ParamType;

public class SysParam extends BaseSysParam { 

	private static final long serialVersionUID = -3451888062016242057L;

	public static final String 人民币兑换金币默认值 = "RMB_DEFAULT";
	
	public static final String 金币兑换金豆比例 = "GOLDCOIN_TO_GOLDBEAN";
	
	
	public SysParam() {
	}

	public SysParam(java.lang.Long id) {
		super(id);
	}

	public int getIntV() {
		return ParamType.getint(super.getV());
	}

	public long getLongV() {
		return ParamType.getlong(super.getV());
	}
}
