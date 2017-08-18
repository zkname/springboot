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
@Table(name = "hd_user_wallet")
public class BaseHdUserWallet extends IdEntity<java.lang.Long>
{
	
	//alias
	public static final String TABLE_ALIAS = "用户钱包";
	public static final String ALIAS_USER_ID = "userId";
	public static final String ALIAS_GOLD_COIN = "金币";
	public static final String ALIAS_GOLD_BEAN = "金豆";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "修改时间";
	
	//columns START
	/**
	 * userId
	 */
	@Id
	@Column(name = "userId")
	
	@Getter @Setter private java.lang.Long userId;
	/**
	 * 金币
	 */
	@Column(name = "goldCoin")
	@Getter @Setter private java.lang.Long goldCoin;
	/**
	 * 金豆
	 */
	@Column(name = "goldBean")
	@Getter @Setter private java.lang.Long goldBean;
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

	public BaseHdUserWallet(){
	}
	
	public BaseHdUserWallet(
		java.lang.Long userId
	){
		this.userId = userId;
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
		if(obj instanceof BaseHdUserWallet == false) return false;
		if(this == obj) return true;
		BaseHdUserWallet other = (BaseHdUserWallet)obj;
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

