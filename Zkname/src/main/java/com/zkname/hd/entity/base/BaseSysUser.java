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
@Table(name = "sys_user")
public class BaseSysUser extends IdEntity<java.lang.Long>
{
	
	//alias
	public static final String TABLE_ALIAS = "用户表";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_REAL_NAME = "真实姓名";
	public static final String ALIAS_USER_NAME = "用户名";
	public static final String ALIAS_PASSWORD = "密码";
	public static final String ALIAS_EMAIL = "邮箱";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "修改时间";
	public static final String ALIAS_DELE_STATUS = "可见状态(0:不可见;1:可见)";
	public static final String ALIAS_CREATOR_ID = "创建者id";
	public static final String ALIAS_LOGIN_TIME = "登陆时间";
	public static final String ALIAS_PLATFORM_ID = "平台id";
	public static final String ALIAS_ROLE_CODE = "角色编码 xxxx(10001000)格式";
	
	//columns START
	/**
	 * 主键
	 */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Getter @Setter private java.lang.Long id;
	/**
	 * 真实姓名
	 */
	@Column(name = "realName")
	@Getter @Setter private java.lang.String realName;
	/**
	 * 用户名
	 */
	@Column(name = "userName")
	@Getter @Setter private java.lang.String userName;
	/**
	 * 密码
	 */
	@Column(name = "password")
	@Getter @Setter private java.lang.String password;
	/**
	 * 邮箱
	 */
	@Column(name = "email")
	@Getter @Setter private java.lang.String email;
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
	/**
	 * 登陆时间
	 */
	@Column(name = "loginTime")
	@Getter @Setter private java.util.Date loginTime;
	/**
	 * 平台id
	 */
	@Column(name = "platformId")
	@Getter @Setter private java.lang.Long platformId;
	/**
	 * 角色编码 xxxx(10001000)格式
	 */
	@Column(name = "roleCode")
	@Getter @Setter private java.lang.String roleCode;
	//columns END

	public BaseSysUser(){
	}
	
	public BaseSysUser(
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
	@Transient
	public String getLoginTimeString() {
		return DateUtil.Date2Str(getLoginTime());
	}
	public void setLoginTimeString(String value) {
		setLoginTime(DateUtil.Str2Date(value));
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
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

