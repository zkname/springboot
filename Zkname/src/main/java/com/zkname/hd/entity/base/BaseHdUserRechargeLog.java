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
@Table(name = "hd_user_recharge_log")
public class BaseHdUserRechargeLog extends IdEntity<java.lang.Long>
{
	
	//alias
	public static final String TABLE_ALIAS = "用户充值日志";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_USER_ID = "用户id";
	public static final String ALIAS_SHOP_ID = "店铺id";
	public static final String ALIAS_MONEY = "充值金额";
	public static final String ALIAS_RECHARGE_PERCENTAGE = "充值比例";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "修改时间";
	public static final String ALIAS_DELE_STATUS = "可见状态(0:不可见;1:可见)";
	public static final String ALIAS_CREATOR_ID = "创建者id";
	
	//columns START
	/**
	 * id
	 */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Getter @Setter private java.lang.Long id;
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
	 * 充值金额
	 */
	@Column(name = "money")
	@Getter @Setter private java.lang.Integer money;
	/**
	 * 充值比例
	 */
	@Column(name = "rechargePercentage")
	@Getter @Setter private java.lang.Integer rechargePercentage;
	/**
	 * 创建时间
	 */
	@Column(name = "createTime")
	@Getter @Setter private java.util.Date createTime;
	/**
	 * 修改时间
	 */
	@Column(name = "updateTime")
	@Getter @Setter private java.util.Date updateTime;
	/**
	 * 可见状态(0:不可见;1:可见)
	 */
	@Column(name = "deleStatus")
	@Getter @Setter private java.lang.String deleStatus;
	/**
	 * 创建者id
	 */
	@Column(name = "creatorId")
	@Getter @Setter private java.lang.Long creatorId;
	//columns END

	public BaseHdUserRechargeLog(){
	}
	
	public BaseHdUserRechargeLog(
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
	public String getUpdateTimeString() {
		return DateUtil.Date2Str(getUpdateTime());
	}
	public void setUpdateTimeString(String value) {
		setUpdateTime(DateUtil.Str2Date(value));
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof BaseHdUserRechargeLog == false) return false;
		if(this == obj) return true;
		BaseHdUserRechargeLog other = (BaseHdUserRechargeLog)obj;
		return new EqualsBuilder().append(getId(),other.getId()).isEquals();
	}
}

