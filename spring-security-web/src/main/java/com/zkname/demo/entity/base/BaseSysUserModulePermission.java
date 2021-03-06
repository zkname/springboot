package com.zkname.demo.entity.base;

import javax.persistence.*;
import org.apache.commons.lang3.builder.*;
import com.zkname.core.entity.IdEntity;
import com.zkname.core.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import java.util.*;

import java.util.*;


@Entity
@Table(name = "sys_user_module_permission")
public class BaseSysUserModulePermission extends IdEntity<java.lang.Long>
{
	
	//alias
	public static final String TABLE_ALIAS = "模块表于权限表 多对多关联表";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_NAME = "操作名称";
	public static final String ALIAS_SECURITY_NAME = "安全名称 用于权限标签";
	public static final String ALIAS_RESOURCE_VALUE = "权限url";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "修改时间";
	public static final String ALIAS_DELE_STATUS = "可见状态(0:不可见;1:可见)";
	public static final String ALIAS_CREATOR_ID = "创建者id";
	public static final String ALIAS_PLATFORM_ID = "平台id";
	public static final String ALIAS_MODULE_ID = "模块id";
	
	//columns START
	/**
	 * id
	 */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Getter @Setter private java.lang.Long id;
	/**
	 * 操作名称
	 */
	@Column(name = "name")
	@Getter @Setter private java.lang.String name;
	/**
	 * 安全名称 用于权限标签
	 */
	@Column(name = "securityName")
	@Getter @Setter private java.lang.String securityName;
	/**
	 * 权限url
	 */
	@Column(name = "resourceValue")
	@Getter @Setter private java.lang.String resourceValue;
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
	 * 平台id
	 */
	@Column(name = "platformId")
	@Getter @Setter private java.lang.Long platformId;
	/**
	 * 模块id
	 */
	@Column(name = "moduleId")
	@Getter @Setter private java.lang.Long moduleId;
	//columns END

	public BaseSysUserModulePermission(){
	}
	
	public BaseSysUserModulePermission(
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
		if(obj instanceof BaseSysUserModulePermission == false) return false;
		if(this == obj) return true;
		BaseSysUserModulePermission other = (BaseSysUserModulePermission)obj;
		return new EqualsBuilder().append(getId(),other.getId()).isEquals();
	}
}

