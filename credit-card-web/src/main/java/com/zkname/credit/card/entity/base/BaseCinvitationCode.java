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
@Table(name = "c_invitation_code")
public class BaseCinvitationCode extends IdEntity<java.lang.Long>
{
	
	//alias
	public static final String TABLE_ALIAS = "邀请码";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_INVITATION_CODE_BATCH = "批次ID";
	public static final String ALIAS_INVITATION_CODE = "邀请码";
	public static final String ALIAS_USER_ID = "用户id";
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
	 * 批次id
	 */
	@Column(name = "invitationCodeBatch")
	private java.lang.Long invitationCodeBatch;
	/**
	 * 邀请码InvitationCode
	 */
	@Column(name = "invitationCode")
	private java.lang.String invitationCode;
	/**
	 * 用户id
	 */
	@Column(name = "userId")
	private java.lang.Long userId;
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

	public BaseCinvitationCode(){
	}
	
	public BaseCinvitationCode(
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
		if(obj instanceof BaseCinvitationCode == false) return false;
		if(this == obj) return true;
		BaseCinvitationCode other = (BaseCinvitationCode)obj;
		return new EqualsBuilder().append(getId(),other.getId()).isEquals();
	}
}

