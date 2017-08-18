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
@Table(name = "hd_user_wallet_log")
public class BaseHdUserWalletLog extends IdEntity<java.lang.Long>
{
	
	//alias
	public static final String TABLE_ALIAS = "用户钱包日志";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_USER_ID = "用户id";
	public static final String ALIAS_ROOM_ID = "房间id";
	public static final String ALIAS_COIN_TYPE = "金币类型(1:金币充值,2:兑换减金币)";
	public static final String ALIAS_HISTORY_GOLD_COIN = "原有金币";
	public static final String ALIAS_ADD_GOLD_COIN = "增加金币";
	public static final String ALIAS_HISTORY_GOLD_BEAN = "原有金豆";
	public static final String ALIAS_ADD_GOLD_BEAN = "增加金豆";
	public static final String ALIAS_FROM_USER = "付款方";
	public static final String ALIAS_TO_USER = "收款方";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "修改时间";
	public static final String ALIAS_BEAN_TYPE = "金豆类型(1:金豆交易加,2:金豆交易减,3:兑换加金豆,4:金豆清空)";
	
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
	 * 房间id
	 */
	@Column(name = "roomId")
	@Getter @Setter private java.lang.Long roomId;
	/**
	 * 金币类型(1:金币充值,2:兑换减金币)
	 */
	@Column(name = "coinType")
	@Getter @Setter private java.lang.Integer coinType=0;
	/**
	 * 原有金币
	 */
	@Column(name = "historyGoldCoin")
	@Getter @Setter private java.lang.Long historyGoldCoin;
	/**
	 * 增加金币
	 */
	@Column(name = "addGoldCoin")
	@Getter @Setter private java.lang.Long addGoldCoin;
	/**
	 * 原有金豆
	 */
	@Column(name = "historyGoldBean")
	@Getter @Setter private java.lang.Long historyGoldBean;
	/**
	 * 增加金豆
	 */
	@Column(name = "addGoldBean")
	@Getter @Setter private java.lang.Long addGoldBean;
	/**
	 * 付款方
	 */
	@Column(name = "fromUser")
	@Getter @Setter private java.lang.Long fromUser;
	/**
	 * 收款方
	 */
	@Column(name = "toUser")
	@Getter @Setter private java.lang.Long toUser;
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
	 * 金豆类型(1:金豆交易加,2:金豆交易减,3:兑换加金豆,4:金豆清空)
	 */
	@Column(name = "beanType")
	@Getter @Setter private java.lang.Integer beanType=0;
	//columns END

	public BaseHdUserWalletLog(){
	}
	
	public BaseHdUserWalletLog(
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
		if(obj instanceof BaseHdUserWalletLog == false) return false;
		if(this == obj) return true;
		BaseHdUserWalletLog other = (BaseHdUserWalletLog)obj;
		return new EqualsBuilder().append(getId(),other.getId()).isEquals();
	}
}

