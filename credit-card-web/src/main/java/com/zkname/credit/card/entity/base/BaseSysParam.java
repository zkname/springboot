package com.zkname.credit.card.entity.base;

import javax.persistence.*;
import org.apache.commons.lang3.builder.*;
import com.zkname.core.entity.IdEntity;
import com.zkname.core.util.DateUtil;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

import java.util.*;

@Data
@ToString
@Entity
@Table(name = "sys_param")
public class BaseSysParam extends IdEntity<java.lang.Long>
{
	
	//alias
	public static final String TABLE_ALIAS = "系统参数表";
	public static final String ALIAS_ID = "主键id";
	public static final String ALIAS_NAME = "参数名称";
	public static final String ALIAS_TYPE = "参数类型";
	public static final String ALIAS_K = "参数key";
	public static final String ALIAS_V = "参数value";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "修改时间";
	public static final String ALIAS_DELE_STATUS = "可见状态(0:不可见;1:可见)";
	public static final String ALIAS_CREATOR_ID = "创建者id";
	
	//columns START
	/**
	 * 主键id
	 */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private java.lang.Long id;
	/**
	 * 参数名称
	 */
	@Column(name = "name")
	private java.lang.String name;
	/**
	 * 参数类型
	 */
	@Column(name = "type")
	private java.lang.Integer type;
	/**
	 * 参数key
	 */
	@Column(name = "k")
	private java.lang.String k;
	/**
	 * 参数value
	 */
	@Column(name = "v")
	private java.lang.String v;
	/**
	 * 创建时间
	 */
	@Column(name = "createTime")
	private java.util.Date createTime;
	/**
	 * 修改时间
	 */
	@Column(name = "updateTime")
	private java.util.Date updateTime;
	/**
	 * 可见状态(0:不可见;1:可见)
	 */
	@Column(name = "deleStatus")
	private java.lang.String deleStatus;
	/**
	 * 创建者id
	 */
	@Column(name = "creatorId")
	private java.lang.Long creatorId;
	//columns END

	public BaseSysParam(){
	}
	
	public BaseSysParam(
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
	
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof BaseSysParam == false) return false;
		if(this == obj) return true;
		BaseSysParam other = (BaseSysParam)obj;
		return new EqualsBuilder().append(getId(),other.getId()).isEquals();
	}
}

