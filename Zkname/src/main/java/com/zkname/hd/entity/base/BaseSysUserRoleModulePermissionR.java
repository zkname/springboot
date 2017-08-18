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
@Table(name = "sys_user_role_module_permission_r")
public class BaseSysUserRoleModulePermissionR extends IdEntity<java.lang.Long>
{
	
	//alias
	public static final String TABLE_ALIAS = "权限表";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_MODULE_PERMISSION_ID = "模块权限表";
	public static final String ALIAS_ROLE_ID = "角色表";
	
	//columns START
	/**
	 * 主键
	 */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Getter @Setter private java.lang.Long id;
	/**
	 * 模块权限表
	 */
	@Column(name = "modulePermissionId")
	@Getter @Setter private java.lang.Long modulePermissionId;
	/**
	 * 角色表
	 */
	@Column(name = "roleId")
	@Getter @Setter private java.lang.Long roleId;
	//columns END

	public BaseSysUserRoleModulePermissionR(){
	}
	
	public BaseSysUserRoleModulePermissionR(
		java.lang.Long id
	){
		this.id = id;
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof BaseSysUserRoleModulePermissionR == false) return false;
		if(this == obj) return true;
		BaseSysUserRoleModulePermissionR other = (BaseSysUserRoleModulePermissionR)obj;
		return new EqualsBuilder().append(getId(),other.getId()).isEquals();
	}
}

