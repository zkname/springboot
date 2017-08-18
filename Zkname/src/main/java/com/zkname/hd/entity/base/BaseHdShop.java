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
@Table(name = "hd_shop")
public class BaseHdShop extends IdEntity<java.lang.Long>
{
	
	//alias
	public static final String TABLE_ALIAS = "店铺表";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_NAME = "店铺名";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_UUID = "uuid";
	public static final String ALIAS_RECHARGE_PERCENTAGE = "充值比例";
	public static final String ALIAS_RETURN_PERCENTAGE = "分成比例";
	public static final String ALIAS_ADDRESS = "地址";
	public static final String ALIAS_CONTACT = "联系人";
	public static final String ALIAS_PHONE = "电话";
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
	 * 店铺名
	 */
	@Column(name = "name")
	@Getter @Setter private java.lang.String name;
	/**
	 * 备注
	 */
	@Column(name = "remark")
	@Getter @Setter private java.lang.String remark;
	/**
	 * uuid
	 */
	@Column(name = "uuid")
	@Getter @Setter private java.lang.String uuid;
	/**
	 * 充值比例
	 */
	@Column(name = "rechargePercentage")
	@Getter @Setter private java.lang.Integer rechargePercentage;
	/**
	 * 分成比例
	 */
	@Column(name = "returnPercentage")
	@Getter @Setter private java.lang.Integer returnPercentage;
	/**
	 * 地址
	 */
	@Column(name = "address")
	@Getter @Setter private java.lang.String address;
	/**
	 * 联系人
	 */
	@Column(name = "contact")
	@Getter @Setter private java.lang.String contact;
	/**
	 * 电话
	 */
	@Column(name = "phone")
	@Getter @Setter private java.lang.String phone;
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

	public BaseHdShop(){
	}
	
	public BaseHdShop(
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
		if(obj instanceof BaseHdShop == false) return false;
		if(this == obj) return true;
		BaseHdShop other = (BaseHdShop)obj;
		return new EqualsBuilder().append(getId(),other.getId()).isEquals();
	}
}

