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
@Table(name = "hd_room")
public class BaseHdRoom extends IdEntity<java.lang.Long>
{
	
	//alias
	public static final String TABLE_ALIAS = "房间表";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_NAME = "房间名称";
	public static final String ALIAS_REMARK = "注释";
	public static final String ALIAS_TYPE = "房间类型";
	public static final String ALIAS_MAX_NUM = "最大人数";
	public static final String ALIAS_USER_ID = "房主id";
	public static final String ALIAS_STATUS = "房间状态(1:开启,0:关闭)";
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
	 * 房间名称
	 */
	@Column(name = "name")
	@Getter @Setter private java.lang.String name;
	/**
	 * 注释
	 */
	@Column(name = "remark")
	@Getter @Setter private java.lang.String remark;
	/**
	 * 房间类型
	 */
	@Column(name = "type")
	@Getter @Setter private java.lang.Integer type;
	/**
	 * 最大人数
	 */
	@Column(name = "maxNum")
	@Getter @Setter private java.lang.Integer maxNum;
	/**
	 * 房主id
	 */
	@Column(name = "userId")
	@Getter @Setter private java.lang.Long userId;
	/**
	 * 房间状态(1:开启,0:关闭)
	 */
	@Column(name = "status")
	@Getter @Setter private java.lang.Integer status;
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

	public BaseHdRoom(){
	}
	
	public BaseHdRoom(
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
		if(obj instanceof BaseHdRoom == false) return false;
		if(this == obj) return true;
		BaseHdRoom other = (BaseHdRoom)obj;
		return new EqualsBuilder().append(getId(),other.getId()).isEquals();
	}
}

