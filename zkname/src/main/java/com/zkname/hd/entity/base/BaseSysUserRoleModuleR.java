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
@Table(name = "sys_user_role_module_r")
public class BaseSysUserRoleModuleR extends IdEntity<java.lang.Long>
{
	
	//alias
	public static final String TABLE_ALIAS = "角色表于模块表多对多关系表";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_ROLE_ID = "角色表";
	public static final String ALIAS_MODULE_ID = "模块表id";
	
	//columns START
	/**
	 * id
	 */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Getter @Setter private java.lang.Long id;
	/**
	 * 角色表
	 */
	@Column(name = "roleId")
	@Getter @Setter private java.lang.Long roleId;
	/**
	 * 模块表id
	 */
	@Column(name = "moduleId")
	@Getter @Setter private java.lang.Long moduleId;
	//columns END

	public BaseSysUserRoleModuleR(){
	}
	
	public BaseSysUserRoleModuleR(
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
		if(obj instanceof BaseSysUserRoleModuleR == false) return false;
		if(this == obj) return true;
		BaseSysUserRoleModuleR other = (BaseSysUserRoleModuleR)obj;
		return new EqualsBuilder().append(getId(),other.getId()).isEquals();
	}
}

