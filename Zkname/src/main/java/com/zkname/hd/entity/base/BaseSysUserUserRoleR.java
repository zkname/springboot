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
@Table(name = "sys_user_user_role_r")
public class BaseSysUserUserRoleR extends IdEntity<java.lang.Long>
{
	
	//alias
	public static final String TABLE_ALIAS = "用户表于角色表多对多关系表";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_USER_ID = "用户表id";
	public static final String ALIAS_ROLE_ID = "角色表id";
	
	//columns START
	/**
	 * id
	 */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Getter @Setter private java.lang.Long id;
	/**
	 * 用户表id
	 */
	@Column(name = "userId")
	@Getter @Setter private java.lang.Long userId;
	/**
	 * 角色表id
	 */
	@Column(name = "roleId")
	@Getter @Setter private java.lang.Long roleId;
	//columns END

	public BaseSysUserUserRoleR(){
	}
	
	public BaseSysUserUserRoleR(
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
		if(obj instanceof BaseSysUserUserRoleR == false) return false;
		if(this == obj) return true;
		BaseSysUserUserRoleR other = (BaseSysUserUserRoleR)obj;
		return new EqualsBuilder().append(getId(),other.getId()).isEquals();
	}
}

