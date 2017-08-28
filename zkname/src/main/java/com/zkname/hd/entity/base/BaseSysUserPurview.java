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
@Table(name = "sys_user_purview")
public class BaseSysUserPurview extends IdEntity<java.lang.Long>
{
	
	//alias
	public static final String TABLE_ALIAS = "SysUserPurview";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_USER_ID = "用户id";
	public static final String ALIAS_PURVIEW_TEXT = "权限";
	
	//columns START
	/**
	 * 主键
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
	 * 权限
	 */
	@Column(name = "purviewText")
	@Getter @Setter private java.lang.String purviewText;
	//columns END

	public BaseSysUserPurview(){
	}
	
	public BaseSysUserPurview(
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
		if(obj instanceof BaseSysUserPurview == false) return false;
		if(this == obj) return true;
		BaseSysUserPurview other = (BaseSysUserPurview)obj;
		return new EqualsBuilder().append(getId(),other.getId()).isEquals();
	}
}

