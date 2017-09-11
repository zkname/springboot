package com.zkname.credit.card.entity.base;

import javax.persistence.*;
import org.apache.commons.lang3.builder.*;
import com.zkname.core.entity.IdEntity;
import com.zkname.core.util.DateUtil;
import lombok.Data;
import lombok.ToString;
import java.util.*;

import java.util.*;




@Data
@ToString
@Entity
@Table(name = "c_invitation_code_batch")
public class BaseCinvitationCodeBatch extends IdEntity<java.lang.Long>
{
	
	//alias
	public static final String TABLE_ALIAS = "批次";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_NAME = "批次名称";
	public static final String ALIAS_TOTAL = "总数";
	public static final String ALIAS_UPDATE_LOCK = "1锁定、0正常";
	public static final String ALIAS_IS_GENERATE = "是否生成1、已生成，0未生成";
	public static final String ALIAS_REMARK = "备注";
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
	private java.lang.Long id;
	/**
	 * 批次名称
	 */
	@Column(name = "name")
	private java.lang.String name;
	/**
	 * 总数
	 */
	@Column(name = "total")
	private java.lang.Integer total;
	/**
	 * 1锁定、0正常
	 */
	@Column(name = "updateLock")
	private java.lang.String updateLock;
	/**
	 * 是否生成1、已生成，0未生成
	 */
	@Column(name = "isGenerate")
	private java.lang.String isGenerate;
	/**
	 * 备注
	 */
	@Column(name = "remark")
	private java.lang.String remark;
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

	public BaseCinvitationCodeBatch(){
	}
	
	public BaseCinvitationCodeBatch(
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
		if(obj instanceof BaseCinvitationCodeBatch == false) return false;
		if(this == obj) return true;
		BaseCinvitationCodeBatch other = (BaseCinvitationCodeBatch)obj;
		return new EqualsBuilder().append(getId(),other.getId()).isEquals();
	}
}

