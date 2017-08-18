package com.zkname.hd.page;

import java.util.*;

import com.zkname.hd.entity.*;
import com.zkname.frame.page.BasePage;
import com.zkname.frame.page.ShardingBasePage;
import com.zkname.frame.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

//支付充值流水
public class PageHdUserPayLog extends ShardingBasePage<HdUserPayLog> {

	private static final long serialVersionUID = -4033946585064541028L;
	

	/** 主键 */
	@Getter
	@Setter
	private java.lang.Long id;
	/** 用户充值日志id */
	@Getter
	@Setter
	private java.lang.Long userRechargeLogId;
	/** 用户id */
	@Getter
	@Setter
	private java.lang.Long userId;
	/** 店铺id */
	@Getter
	@Setter
	private java.lang.Long shopId;
	/** 订单金额 */
	@Getter
	@Setter
	private java.lang.Long totalMoney;
	/** 支付单号 */
	@Getter
	@Setter
	private java.lang.String payCode;
	/** 支付状态，0、未付款；1、已付款；2、退款；3、支付失败 */
	@Getter
	@Setter
	private java.lang.Integer payStatus;
	/** 创建时间 */
	@Getter
	@Setter
	private java.lang.String createTimeBegin;
	@Getter
	@Setter
	private java.lang.String createTimeEnd;
	/** 支付时间 */
	@Getter
	@Setter
	private java.lang.String payTimeBegin;
	@Getter
	@Setter
	private java.lang.String payTimeEnd;
	/** 支付交易号 */
	@Getter
	@Setter
	private java.lang.String payId;
	/** 支付类型:1、支付宝，2、微信支付 */
	@Getter
	@Setter
	private java.lang.Integer payType;
	/** 支付返回内容 */
	@Getter
	@Setter
	private java.lang.String payReturnValue;
	/** 退款时间 */
	@Getter
	@Setter
	private java.lang.String outRefundTimeBegin;
	@Getter
	@Setter
	private java.lang.String outRefundTimeEnd;
	/** 退款单号 */
	@Getter
	@Setter
	private java.lang.String outRefundNo;
	/** 退款返回内容 */
	@Getter
	@Setter
	private java.lang.String outRefundReturnValue;
	
	
	private StringBuffer getSql() {
		StringBuffer sb=new StringBuffer();
        if(isNotEmpty(this.id)) {
        	sb.append(" and  o.id = ? ");
        	list.add(this.id);
        }
        if(isNotEmpty(this.userRechargeLogId)) {
        	sb.append(" and  o.userRechargeLogId = ? ");
        	list.add(this.userRechargeLogId);
        }
        if(isNotEmpty(this.userId)) {
        	sb.append(" and  o.userId = ? ");
        	list.add(this.userId);
        }
        if(isNotEmpty(this.shopId)) {
        	sb.append(" and  o.shopId = ? ");
        	list.add(this.shopId);
        }
        if(isNotEmpty(this.totalMoney)) {
        	sb.append(" and  o.totalMoney = ? ");
        	list.add(this.totalMoney);
        }
        if(isNotEmpty(this.payCode)) {
        	sb.append(" and  o.payCode = ? ");
        	list.add(this.payCode);
        }
        if(isNotEmpty(this.payStatus)) {
        	sb.append(" and  o.payStatus = ? ");
        	list.add(this.payStatus);
        }
        if(isNotEmpty(this.createTimeBegin)) {
        	sb.append(" and  o.createTime between ? ");
        	list.add(DateUtil.StrutilDate(this.createTimeBegin+" 00:00:00","yyyy-MM-dd HH:mm:ss"));
        }
        if(isNotEmpty(this.createTimeEnd)) {
        	sb.append(" and  ? ");
        	list.add(DateUtil.StrutilDate(this.createTimeEnd+" 23:59:59","yyyy-MM-dd HH:mm:ss"));
        }
        if(isNotEmpty(this.payTimeBegin)) {
        	sb.append(" and  o.payTime >= ? ");
        	list.add(DateUtil.StrutilDate(this.payTimeBegin+" 00:00:00","yyyy-MM-dd HH:mm:ss"));
        }
        if(isNotEmpty(this.payTimeEnd)) {
        	sb.append(" and  o.payTime <= ? ");
        	list.add(DateUtil.StrutilDate(this.payTimeEnd+" 23:59:59","yyyy-MM-dd HH:mm:ss"));
        }
        if(isNotEmpty(this.payId)) {
        	sb.append(" and  o.payId = ? ");
        	list.add(this.payId);
        }
        if(isNotEmpty(this.payType)) {
        	sb.append(" and  o.payType = ? ");
        	list.add(this.payType);
        }
        if(isNotEmpty(this.payReturnValue)) {
        	sb.append(" and  o.payReturnValue = ? ");
        	list.add(this.payReturnValue);
        }
        if(isNotEmpty(this.outRefundTimeBegin)) {
        	sb.append(" and  o.outRefundTime >= ? ");
        	list.add(DateUtil.StrutilDate(this.outRefundTimeBegin+" 00:00:00","yyyy-MM-dd HH:mm:ss"));
        }
        if(isNotEmpty(this.outRefundTimeEnd)) {
        	sb.append(" and  o.outRefundTime <= ? ");
        	list.add(DateUtil.StrutilDate(this.outRefundTimeEnd+" 23:59:59","yyyy-MM-dd HH:mm:ss"));
        }
        if(isNotEmpty(this.outRefundNo)) {
        	sb.append(" and  o.outRefundNo = ? ");
        	list.add(this.outRefundNo);
        }
        if(isNotEmpty(this.outRefundReturnValue)) {
        	sb.append(" and  o.outRefundReturnValue = ? ");
        	list.add(this.outRefundReturnValue);
        }
		return sb;
	}

	public List<HdUserPayLog> query() {
		sb.append("select o.* from hd_user_pay_log o where 1=1 ").append(this.getSql()).append(" order by o.createTime desc");
		return super.query();
	}

	
}

