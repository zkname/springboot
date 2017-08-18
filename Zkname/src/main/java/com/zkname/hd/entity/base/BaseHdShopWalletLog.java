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
@Table(name = "hd_shop_wallet_log")
public class BaseHdShopWalletLog extends IdEntity<java.lang.Long>
{
	
	//alias
	public static final String TABLE_ALIAS = "店铺钱包日志";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_SHOP_ID = "店铺id";
	public static final String ALIAS_TYPE = "类型(1:返利,2:提现)";
	public static final String ALIAS_ORIGINAL_MONEY = "原有金额";
	public static final String ALIAS_ADD_MONEY = "变动金额";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "修改时间";
	
	//columns START
	/**
	 * id
	 */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Getter @Setter private java.lang.Long id;
	/**
	 * 店铺id
	 */
	@Column(name = "shopId")
	@Getter @Setter private java.lang.Long shopId;
	/**
	 * 类型(1:返利,2:提现)
	 */
	@Column(name = "type")
	@Getter @Setter private java.lang.Integer type;
	/**
	 * 原有金额
	 */
	@Column(name = "originalMoney")
	@Getter @Setter private java.lang.Long originalMoney;
	/**
	 * 当前金额
	 */
	@Column(name = "addMoney")
	@Getter @Setter private java.lang.Long addMoney;
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
	//columns END

	public BaseHdShopWalletLog(){
	}
	
	public BaseHdShopWalletLog(
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
		if(obj instanceof BaseHdShopWalletLog == false) return false;
		if(this == obj) return true;
		BaseHdShopWalletLog other = (BaseHdShopWalletLog)obj;
		return new EqualsBuilder().append(getId(),other.getId()).isEquals();
	}
}

