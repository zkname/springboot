package com.zkname.credit.card.entity;

import com.zkname.credit.card.entity.base.*;

import lombok.Getter;
import lombok.Setter;

public class CcardInfo extends BaseCcardInfo {

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
	 * 未刷总金额
	 */
	@Getter
	@Setter
	private double totalAmount;
	
	/**
	 * 已刷金额
	 */
	@Getter
	@Setter
	private double amount;
	
	/**
	 * 手续费
	 */
	@Getter
	@Setter
	private double fee;
	
	public CcardInfo() {
	}

	public CcardInfo(java.lang.Long id) {
		super(id);
	}
}
