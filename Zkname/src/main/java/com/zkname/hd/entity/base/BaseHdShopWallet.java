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
@Table(name = "hd_shop_wallet")
public class BaseHdShopWallet extends IdEntity<java.lang.Long>
{
	
	//alias
	public static final String TABLE_ALIAS = "店铺钱包";
	public static final String ALIAS_SHOP_ID = "店铺id";
	public static final String ALIAS_HISTORY_MONEY = "历史总金额";
	public static final String ALIAS_MONEY = "当前金额";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "修改时间";
	
	//columns START
	/**
	 * 店铺id
	 */
	@Id
	@Column(name = "shopId")
	
	@Getter @Setter private java.lang.Long shopId;
	/**
	 * 历史总金额
	 */
	@Column(name = "historyMoney")
	@Getter @Setter private java.lang.Long historyMoney;
	/**
	 * 当前金额
	 */
	@Column(name = "money")
	@Getter @Setter private java.lang.Long money;
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

	public BaseHdShopWallet(){
	}
	
	public BaseHdShopWallet(
		java.lang.Long shopId
	){
		this.shopId = shopId;
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
		if(obj instanceof BaseHdShopWallet == false) return false;
		if(this == obj) return true;
		BaseHdShopWallet other = (BaseHdShopWallet)obj;
		return new EqualsBuilder().append(getId(),other.getId()).isEquals();
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		
	}
}

