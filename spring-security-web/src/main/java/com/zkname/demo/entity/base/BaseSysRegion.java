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
@Table(name = "sys_region")
public class BaseSysRegion extends IdEntity<java.lang.Integer>
{
	
	//alias
	public static final String TABLE_ALIAS = "SysRegion";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_NAME = "名称";
	public static final String ALIAS_PARENT_ID = "上级id";
	public static final String ALIAS_SHORT_NAME = "简称";
	public static final String ALIAS_LEVEL_TYPE = "级别";
	public static final String ALIAS_CITY_CODE = "城市编码";
	public static final String ALIAS_ZIP_CODE = "邮政编码";
	public static final String ALIAS_MERGER_NAME = "合并名称";
	public static final String ALIAS_LNG = "lng";
	public static final String ALIAS_LAT = "lat";
	public static final String ALIAS_PINYIN = "pinyin";
	
	//columns START
	/**
	 * id
	 */
	@Id
	@Column(name = "id")
	
	@Getter @Setter private java.lang.Integer id;
	/**
	 * 名称
	 */
	@Column(name = "name")
	@Getter @Setter private java.lang.String name;
	/**
	 * 上级id
	 */
	@Column(name = "parentId")
	@Getter @Setter private java.lang.Integer parentId;
	/**
	 * 简称
	 */
	@Column(name = "shortName")
	@Getter @Setter private java.lang.String shortName;
	/**
	 * 级别
	 */
	@Column(name = "levelType")
	@Getter @Setter private java.lang.Integer levelType;
	/**
	 * 城市编码
	 */
	@Column(name = "cityCode")
	@Getter @Setter private java.lang.String cityCode;
	/**
	 * 邮政编码
	 */
	@Column(name = "zipCode")
	@Getter @Setter private java.lang.String zipCode;
	/**
	 * 合并名称
	 */
	@Column(name = "mergerName")
	@Getter @Setter private java.lang.String mergerName;
	/**
	 * lng
	 */
	@Column(name = "lng")
	@Getter @Setter private java.lang.String lng;
	/**
	 * lat
	 */
	@Column(name = "lat")
	@Getter @Setter private java.lang.String lat;
	/**
	 * pinyin
	 */
	@Column(name = "pinyin")
	@Getter @Setter private java.lang.String pinyin;
	//columns END

	public BaseSysRegion(){
	}
	
	public BaseSysRegion(
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
		if(obj instanceof BaseSysRegion == false) return false;
		if(this == obj) return true;
		BaseSysRegion other = (BaseSysRegion)obj;
		return new EqualsBuilder().append(getId(),other.getId()).isEquals();
	}
}

