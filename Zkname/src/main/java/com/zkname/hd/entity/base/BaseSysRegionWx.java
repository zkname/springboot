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
@Table(name = "sys_region_wx")
public class BaseSysRegionWx extends IdEntity<java.lang.Integer>
{
	
	//alias
	public static final String TABLE_ALIAS = "微信区域表";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_NAME = "简称";
	public static final String ALIAS_PARENT_ID = "上级id";
	public static final String ALIAS_LEVEL_TYPE = "级别";
	
	//columns START
	/**
	 * id
	 */
	@Id
	@Column(name = "id")
	
	@Getter @Setter private java.lang.Integer id;
	/**
	 * 简称
	 */
	@Column(name = "name")
	@Getter @Setter private java.lang.String name;
	/**
	 * 上级id
	 */
	@Column(name = "parentId")
	@Getter @Setter private java.lang.Integer parentId;
	/**
	 * 级别
	 */
	@Column(name = "levelType")
	@Getter @Setter private java.lang.Integer levelType;
	//columns END

	public BaseSysRegionWx(){
	}
	
	public BaseSysRegionWx(
		java.lang.Integer id
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
		if(obj instanceof BaseSysRegionWx == false) return false;
		if(this == obj) return true;
		BaseSysRegionWx other = (BaseSysRegionWx)obj;
		return new EqualsBuilder().append(getId(),other.getId()).isEquals();
	}
}

