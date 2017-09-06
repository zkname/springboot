package com.zkname.credit.card.entity.base;

import javax.persistence.*;
import org.apache.commons.lang3.builder.*;
import com.zkname.core.entity.IdEntity;
import com.zkname.core.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import java.util.*;

import java.util.*;


@Entity
@Table(name = "c_card_job")
public class BaseCcardJob extends IdEntity<java.lang.Long>
{
	
	//alias
	public static final String TABLE_ALIAS = "信用卡刷卡任务";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_BANK_ID = "银行id";
	public static final String ALIAS_CARD_INFO_ID = "信用卡id";
	public static final String ALIAS_CARD_RANGE_ID = "信用卡限制id";
	public static final String ALIAS_JOB_DATE = "执行日期";
	public static final String ALIAS_MONEY = "金额";
	public static final String ALIAS_FEE = "手续费率";
	public static final String ALIAS_FEE_VALUE = "手续费";
	public static final String ALIAS_STATUS = "刷卡状态：1、已刷卡";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "修改时间";
	public static final String ALIAS_DELE_STATUS = "可见状态(0:不可见;1:可见)";
	public static final String ALIAS_CREATOR_ID = "创建者id";
	
	//columns START
	/**
	 * 主键
	 */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Getter @Setter private java.lang.Long id;
	/**
	 * 银行id
	 */
	@Column(name = "bankId")
	@Getter @Setter private java.lang.Long bankId;
	/**
	 * 信用卡id
	 */
	@Column(name = "cardInfoId")
	@Getter @Setter private java.lang.Long cardInfoId;
	/**
	 * 信用卡限制id
	 */
	@Column(name = "cardRangeId")
	@Getter @Setter private java.lang.Long cardRangeId;
	/**
	 * 执行日期
	 */
	@Column(name = "jobDate")
	@Getter @Setter private java.util.Date jobDate;
	/**
	 * 金额
	 */
	@Column(name = "money")
	@Getter @Setter private java.lang.Double money;
	/**
	 * 手续费率
	 */
	@Column(name = "fee")
	@Getter @Setter private java.lang.Double fee;
	/**
	 * 手续费
	 */
	@Column(name = "feeValue")
	@Getter @Setter private java.lang.Double feeValue;
	/**
	 * 刷卡状态：1、已刷卡
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

	public BaseCcardJob(){
	}
	
	public BaseCcardJob(
		java.lang.Long id
	){
		this.id = id;
	}
	@Transient
	public String getJobDateString() {
		return DateUtil.Date2Str(getJobDate());
	}
	public void setJobDateString(String value) {
		setJobDate(DateUtil.Str2Date(value));
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
		if(obj instanceof BaseCcardJob == false) return false;
		if(this == obj) return true;
		BaseCcardJob other = (BaseCcardJob)obj;
		return new EqualsBuilder().append(getId(),other.getId()).isEquals();
	}
}

