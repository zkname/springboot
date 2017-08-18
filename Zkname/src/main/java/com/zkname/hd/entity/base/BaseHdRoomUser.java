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
@Table(name = "hd_room_user")
public class BaseHdRoomUser extends IdEntity<java.lang.Long>
{
	
	//alias
	public static final String TABLE_ALIAS = "房间用户表";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_ROOM_ID = "房间id";
	public static final String ALIAS_USER_ID = "房主id";
	public static final String ALIAS_ROLE_ID = "角色id";
	public static final String ALIAS_STATUS = "状态(1:活跃,0:)";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "修改时间";
	
	//columns START
	/**
	 * id
	 */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Getter @Setter private java.lang.Long id;
	/**
	 * 房间类型
	 */
	@Column(name = "roomId")
	@Getter @Setter private java.lang.Long roomId;
	/**
	 * 房主id
	 */
	@Column(name = "userId")
	@Getter @Setter private java.lang.Long userId;
	/**
	 * 角色id
	 */
	@Column(name = "roleId")
	@Getter @Setter private java.lang.Long roleId;
	/**
	 * 状态(1:活跃,0:)
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
	//columns END

	public BaseHdRoomUser(){
	}
	
	public BaseHdRoomUser(
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
		if(obj instanceof BaseHdRoomUser == false) return false;
		if(this == obj) return true;
		BaseHdRoomUser other = (BaseHdRoomUser)obj;
		return new EqualsBuilder().append(getId(),other.getId()).isEquals();
	}
}

