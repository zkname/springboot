package com.zkname.hd.entity.base;

import javax.persistence.*;
import org.apache.commons.lang3.builder.*;
import com.zkname.frame.entity.IdEntity;
import com.zkname.frame.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import java.util.*;

import java.util.*;


@Entity
@Table(name = "hd_user_pay_log")
public class BaseHdUserPayLog extends IdEntity<java.lang.Long>
{
	
	//alias
	public static final String TABLE_ALIAS = "支付充值流水";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_USER_RECHARGE_LOG_ID = "用户充值日志id";
	public static final String ALIAS_USER_ID = "用户id";
	public static final String ALIAS_SHOP_ID = "店铺id";
	public static final String ALIAS_TOTAL_MONEY = "订单金额";
	public static final String ALIAS_PAY_CODE = "支付单号";
	public static final String ALIAS_PAY_STATUS = "支付状态，0、未付款；1、已付款；2、退款；3、支付失败";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_PAY_TIME = "支付时间";
	public static final String ALIAS_PAY_ID = "支付交易号";
	public static final String ALIAS_PAY_TYPE = "支付类型:1、支付宝，2、微信支付";
	public static final String ALIAS_PAY_RETURN_VALUE = "支付返回内容";
	public static final String ALIAS_OUT_REFUND_TIME = "退款时间";
	public static final String ALIAS_OUT_REFUND_NO = "退款单号";
	public static final String ALIAS_OUT_REFUND_RETURN_VALUE = "退款返回内容";
	
	//columns START
	/**
	 * 主键
	 */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Getter @Setter private java.lang.Long id;
	/**
	 * 用户充值日志id
	 */
	@Column(name = "userRechargeLogId")
	@Getter @Setter private java.lang.Long userRechargeLogId;
	/**
	 * 用户id
	 */
	@Column(name = "userId")
	@Getter @Setter private java.lang.Long userId;
	/**
	 * 店铺id
	 */
	@Column(name = "shopId")
	@Getter @Setter private java.lang.Long shopId;
	/**
	 * 订单金额
	 */
	@Column(name = "totalMoney")
	@Getter @Setter private java.lang.Long totalMoney;
	/**
	 * 支付单号
	 */
	@Column(name = "payCode")
	@Getter @Setter private java.lang.String payCode;
	/**
	 * 支付状态，0、未付款；1、已付款；2、退款；3、支付失败
	 */
	@Column(name = "payStatus")
	@Getter @Setter private java.lang.Integer payStatus;
	/**
	 * 创建时间
	 */
	@Column(name = "createTime")
	@Getter @Setter private java.util.Date createTime;
	/**
	 * 支付时间
	 */
	@Column(name = "payTime")
	@Getter @Setter private java.util.Date payTime;
	/**
	 * 支付交易号
	 */
	@Column(name = "payId")
	@Getter @Setter private java.lang.String payId;
	/**
	 * 支付类型:1、支付宝，2、微信支付
	 */
	@Column(name = "payType")
	@Getter @Setter private java.lang.Integer payType;
	/**
	 * 支付返回内容
	 */
	@Column(name = "payReturnValue")
	@Getter @Setter private java.lang.String payReturnValue;
	/**
	 * 退款时间
	 */
	@Column(name = "outRefundTime")
	@Getter @Setter private java.util.Date outRefundTime;
	/**
	 * 退款单号
	 */
	@Column(name = "outRefundNo")
	@Getter @Setter private java.lang.String outRefundNo;
	/**
	 * 退款返回内容
	 */
	@Column(name = "outRefundReturnValue")
	@Getter @Setter private java.lang.String outRefundReturnValue;
	//columns END

	public BaseHdUserPayLog(){
	}
	
	public BaseHdUserPayLog(
		java.lang.Long id
	){
		this.id = id;
	}
	@Transient
	public String getCreateTimeString() {
		return DateUtil.Date2Str(getCreateTime());
	}
	public void setCreateTimeString(String value) {
		setCreateTime(DateUtil.Str2Date(value));
	}
	@Transient
	public String getPayTimeString() {
		return DateUtil.Date2Str(getPayTime());
	}
	public void setPayTimeString(String value) {
		setPayTime(DateUtil.Str2Date(value));
	}
	@Transient
	public String getOutRefundTimeString() {
		return DateUtil.Date2Str(getOutRefundTime());
	}
	public void setOutRefundTimeString(String value) {
		setOutRefundTime(DateUtil.Str2Date(value));
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof BaseHdUserPayLog == false) return false;
		if(this == obj) return true;
		BaseHdUserPayLog other = (BaseHdUserPayLog)obj;
		return new EqualsBuilder().append(getId(),other.getId()).isEquals();
	}
}

