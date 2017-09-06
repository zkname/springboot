package com.zkname.credit.card.entity;

import com.zkname.core.util.CompuUtils;
import com.zkname.credit.card.entity.base.*;

import lombok.Getter;
import lombok.Setter;

public class CcardJob extends BaseCcardJob {

	private static final long serialVersionUID = -3451888062016242057L;

	/**
	 * 银行名称
	 */
	@Getter
	@Setter
	private String bankName;
	
	/**
	 * 规则名称
	 */
	@Getter
	@Setter
	private String cardRangeName;
	
	/**
	 * 信用卡名称
	 */
	@Getter
	@Setter
	private String cardInfoName;
	
	public CcardJob() {
	}

	public CcardJob(java.lang.Long id) {
		super(id);
	}
}
