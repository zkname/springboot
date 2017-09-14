package com.zkname.credit.card.entity.base;

import javax.persistence.*;
import org.apache.commons.lang3.builder.*;
import com.zkname.core.entity.IdEntity;
import com.zkname.core.util.DateUtil;
import lombok.Data;
import lombok.ToString;
import java.util.*;

import java.util.*;




@Data
@ToString
@Entity
@Table(name = "sys_user")
public class BaseSysUser extends IdEntity<java.lang.Long>
{
	
	//alias
	public static final String TABLE_ALIAS = "用户表";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_USERNAME = "用户名";
	public static final String ALIAS_PASSWORD = "密码";
	public static final String ALIAS_REAL_NAME = "真实姓名";
	public static final String ALIAS_EMAIL = "邮箱";
	public static final String ALIAS_VALID_PERIOD_TIME = "有效期";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "修改时间";
	public static final String ALIAS_LOGIN_TIME = "登陆时间";
	public static final String ALIAS_DELE_STATUS = "可见状态(0:不可见;1:可见)";
	public static final String ALIAS_CREATOR_ID = "创建者id";
	public static final String ALIAS_RESET_CODE = "重置密码code";
	public static final String ALIAS_RESET_OUT_DATE = "重置超时时间";
	
	//columns START
	/**
	 * 主键
	 */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private java.lang.Long id;
	/**
	 * 用户名
	 */
	@Column(name = "username")
	private java.lang.String username;
	/**
	 * 密码
	 */
	@Column(name = "password")
	private java.lang.String password;
	/**
	 * 真实姓名
	 */
	@Column(name = "realName")
	private java.lang.String realName;
	/**
	 * 邮箱
	 */
	@Column(name = "email")
	private java.lang.String email;
	/**
	 * 有效期
	 */
	@Column(name = "validPeriodTime")
	private java.util.Date validPeriodTime;
	/**
	 * 创建时间
	 */
	@Column(name = "createTime")
	private java.util.Date createTime;
	/**
	 * 修改时间
	 */
	@Column(name = "updateTime")
	private java.util.Date updateTime;
	/**
	 * 登陆时间
	 */
	@Column(name = "loginTime")
	private java.util.Date loginTime;
	/**
	 * 可见状态(0:不可见;1:可见)
	 */
	@Column(name = "deleStatus")
	private java.lang.String deleStatus;
	/**
	 * 创建者id
	 */
	@Column(name = "creatorId")
	private java.lang.Long creatorId;
	/**
	 * 重置密码code
	 */
	@Column(name = "resetCode")
	private java.lang.String resetCode;
	/**
	 * 重置超时时间
	 */
	@Column(name = "resetOutDate")
	private java.util.Date resetOutDate;
	//columns END

	public BaseSysUser(){
	}
	
	public BaseSysUser(
		java.lang.Long id
	){
		this.id = id;
	}
	@Transient
	public String getValidPeriodTimeString() {
		return DateUtil.Date2Str(getValidPeriodTime());
	}
	public void setValidPeriodTimeString(String value) {
		setValidPeriodTime(DateUtil.Str2Date(value));
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
	@Transient
	public String getLoginTimeString() {
		return DateUtil.Date2Str(getLoginTime());
	}
	public void setLoginTimeString(String value) {
		setLoginTime(DateUtil.Str2Date(value));
	}
	@Transient
	public String getResetOutDateString() {
		return DateUtil.Date2Str(getResetOutDate());
	}
	public void setResetOutDateString(String value) {
		setResetOutDate(DateUtil.Str2Date(value));
	}
	
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof BaseSysUser == false) return false;
		if(this == obj) return true;
		BaseSysUser other = (BaseSysUser)obj;
		return new EqualsBuilder().append(getId(),other.getId()).isEquals();
	}
}

