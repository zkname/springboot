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
@Table(name = "sys_user_module")
public class BaseSysUserModule extends IdEntity<java.lang.Long>
{
	
	//alias
	public static final String TABLE_ALIAS = "模块表";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_NAME = "模块名称";
	public static final String ALIAS_SECURITY_NAME = "安全名称 用于权限标签";
	public static final String ALIAS_RESOURCE_VALUE = "url";
	public static final String ALIAS_ORDER_NUM = "排序";
	public static final String ALIAS_PARENT_ID = "父模块id";
	public static final String ALIAS_PLATFORM_ID = "平台id";
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
	 * 模块名称
	 */
	@Column(name = "name")
	@Getter @Setter private java.lang.String name;
	/**
	 * 安全名称 用于权限标签
	 */
	@Column(name = "securityName")
	@Getter @Setter private java.lang.String securityName;
	/**
	 * url
	 */
	@Column(name = "resourceValue")
	@Getter @Setter private java.lang.String resourceValue;
	/**
	 * 排序
	 */
	@Column(name = "orderNum")
	@Getter @Setter private java.lang.Integer orderNum;
	/**
	 * 父模块id
	 */
	@Column(name = "parentId")
	@Getter @Setter private java.lang.Long parentId;
	/**
	 * 平台id
	 */
	@Column(name = "platformId")
	@Getter @Setter private java.lang.Long platformId;
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

	public BaseSysUserModule(){
	}
	
	public BaseSysUserModule(
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
		if(obj instanceof BaseSysUserModule == false) return false;
		if(this == obj) return true;
		BaseSysUserModule other = (BaseSysUserModule)obj;
		return new EqualsBuilder().append(getId(),other.getId()).isEquals();
	}
}

