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
@Table(name = "hd_user")
public class BaseHdUser extends IdEntity<java.lang.Long>
{
	
	//alias
	public static final String TABLE_ALIAS = "微信用户表";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_UNION_ID = "微信UnionId";
	public static final String ALIAS_OPEN_ID = "微信OpenId";
	public static final String ALIAS_NICKNAME = "昵称";
	public static final String ALIAS_SEX = "性别";
	public static final String ALIAS_HEADIMGURL = "头像";
	public static final String ALIAS_TYPE = "用户类型(0:普通用户,1:配货人员)";
	public static final String ALIAS_PROVINCE = "省";
	public static final String ALIAS_CITY = "市";
	public static final String ALIAS_PROVINCE_ID = "省id";
	public static final String ALIAS_CITY_ID = "市Id";
	public static final String ALIAS_SUBSCRIBE = "是否关注：0、未关注；1、关注；";
	public static final String ALIAS_SUBSCRIBE_TIME = "关注时间";
	public static final String ALIAS_UNSUBSCRIBE_TIME = "取消关注时间";
	public static final String ALIAS_SESSION_KEY = "用户sessionkey";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "修改时间";
	public static final String ALIAS_DELE_STATUS = "可见状态(0:不可见;1:可见)";
	
	//columns START
	/**
	 * id
	 */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Getter @Setter private java.lang.Long id;
	/**
	 * 微信UnionId
	 */
	@Column(name = "unionId")
	@Getter @Setter private java.lang.String unionId;
	/**
	 * 微信OpenId
	 */
	@Column(name = "openId")
	@Getter @Setter private java.lang.String openId;
	/**
	 * 昵称
	 */
	@Column(name = "nickname")
	@Getter @Setter private java.lang.String nickname;
	/**
	 * 性别
	 */
	@Column(name = "sex")
	@Getter @Setter private java.lang.Integer sex;
	/**
	 * 头像
	 */
	@Column(name = "headimgurl")
	@Getter @Setter private java.lang.String headimgurl;
	/**
	 * 用户类型(0:普通用户,1:配货人员)
	 */
	@Column(name = "type")
	@Getter @Setter private java.lang.Integer type;
	/**
	 * 省
	 */
	@Column(name = "province")
	@Getter @Setter private java.lang.String province;
	/**
	 * 市
	 */
	@Column(name = "city")
	@Getter @Setter private java.lang.String city;
	/**
	 * 省id
	 */
	@Column(name = "provinceId")
	@Getter @Setter private java.lang.Integer provinceId;
	/**
	 * 市Id
	 */
	@Column(name = "cityId")
	@Getter @Setter private java.lang.Integer cityId;
	/**
	 * 是否关注：0、未关注；1、关注；
	 */
	@Column(name = "subscribe")
	@Getter @Setter private java.lang.Integer subscribe;
	/**
	 * 关注时间
	 */
	@Column(name = "subscribeTime")
	@Getter @Setter private java.util.Date subscribeTime;
	/**
	 * 取消关注时间
	 */
	@Column(name = "unsubscribeTime")
	@Getter @Setter private java.util.Date unsubscribeTime;
	/**
	 * 用户sessionkey
	 */
	@Column(name = "sessionKey")
	@Getter @Setter private java.lang.String sessionKey;
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
	//columns END

	public BaseHdUser(){
	}
	
	public BaseHdUser(
		java.lang.Long id
	){
		this.id = id;
	}
	@Transient
	public String getSubscribeTimeString() {
		return DateUtil.Date2Str(getSubscribeTime());
	}
	public void setSubscribeTimeString(String value) {
		setSubscribeTime(DateUtil.Str2Date(value));
	}
	@Transient
	public String getUnsubscribeTimeString() {
		return DateUtil.Date2Str(getUnsubscribeTime());
	}
	public void setUnsubscribeTimeString(String value) {
		setUnsubscribeTime(DateUtil.Str2Date(value));
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
		if(obj instanceof BaseHdUser == false) return false;
		if(this == obj) return true;
		BaseHdUser other = (BaseHdUser)obj;
		return new EqualsBuilder().append(getId(),other.getId()).isEquals();
	}
}

