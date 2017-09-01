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
@Table(name = "sys_user_role")
public class BaseSysUserRole extends IdEntity<java.lang.Long>
{
	
	//alias
	public static final String TABLE_ALIAS = "角色表";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_NAME = "角色";
	public static final String ALIAS_COMMENT = "备注";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "修改时间";
	public static final String ALIAS_DELE_STATUS = "可见状态(0:不可见;1:可见)";
	public static final String ALIAS_CREATOR_ID = "创建者id";
	public static final String ALIAS_PLATFORM_ID = "平台id";
	public static final String ALIAS_PARENT_ID = " 父类id";
	public static final String ALIAS_ROLE_CODE = "角色编码 xxxx(10001000)格式";
	
	//columns START
	/**
	 * id
	 */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Getter @Setter private java.lang.Long id;
	/**
	 * 角色
	 */
	@Column(name = "name")
	@Getter @Setter private java.lang.String name;
	/**
	 * 备注
	 */
	@Column(name = "comment")
	@Getter @Setter private java.lang.String comment;
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
	 *  父类id
	 */
	@Column(name = "parentId")
	@Getter @Setter private java.lang.Long parentId;
	/**
	 * 角色编码 xxxx(10001000)格式
	 */
	@Column(name = "roleCode")
	@Getter @Setter private java.lang.String roleCode;
	//columns END

	public BaseSysUserRole(){
	}
	
	public BaseSysUserRole(
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
		if(obj instanceof BaseSysUserRole == false) return false;
		if(this == obj) return true;
		BaseSysUserRole other = (BaseSysUserRole)obj;
		return new EqualsBuilder().append(getId(),other.getId()).isEquals();
	}
}

