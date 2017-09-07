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
@Table(name = "c_card_info")
public class BaseCcardInfo extends IdEntity<java.lang.Long>
{
	
	//alias
	public static final String TABLE_ALIAS = "信用卡信息";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_BANK_ID = "银行id";
	public static final String ALIAS_CARD_RANGE_ID = "信用规则id";
	public static final String ALIAS_JOB_DATE = "生成任务日期";
	public static final String ALIAS_NAME = "卡名";
	public static final String ALIAS_MONEY = "额度";
	public static final String ALIAS_ANNUAL_FEE_TYPE = "年费类型：1、强制收费，2、刷卡次数，3、免年费";
	public static final String ALIAS_ANNUAL_FEE = "年费";
	public static final String ALIAS_CARD_NUM = "刷卡次数";
	public static final String ALIAS_BILL_DATE = "账单日";
	public static final String ALIAS_NEXT_UP = "下次提额时间";
	public static final String ALIAS_REMARKS = "备注";
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
	private java.lang.Long id;
	/**
	 * 银行id
	 */
	@Column(name = "bankId")
	private java.lang.Long bankId;
	/**
	 * 信用规则id
	 */
	@Column(name = "cardRangeId")
	private java.lang.Long cardRangeId;
	/**
	 * 生成任务日期
	 */
	@Column(name = "jobDate")
	private java.util.Date jobDate;
	/**
	 * 卡名
	 */
	@Column(name = "name")
	private java.lang.String name;
	/**
	 * 额度
	 */
	@Column(name = "money")
	private java.lang.Integer money;
	/**
	 * 年费类型：1、强制收费，2、刷卡次数，3、免年费
	 */
	@Column(name = "annualFeeType")
	private java.lang.Integer annualFeeType;
	/**
	 * 年费
	 */
	@Column(name = "annualFee")
	private java.lang.Integer annualFee;
	/**
	 * 刷卡次数
	 */
	@Column(name = "cardNum")
	private java.lang.Integer cardNum;
	/**
	 * 账单日
	 */
	@Column(name = "billDate")
	private java.lang.Integer billDate;
	/**
	 * 下次提额时间
	 */
	@Column(name = "nextUp")
	private java.util.Date nextUp;
	/**
	 * 备注
	 */
	@Column(name = "remarks")
	private java.lang.String remarks;
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

	public BaseCcardInfo(){
	}
	
	public BaseCcardInfo(
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
	public String getNextUpString() {
		return DateUtil.Date2Str(getNextUp());
	}
	public void setNextUpString(String value) {
		setNextUp(DateUtil.Str2Date(value));
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
		if(obj instanceof BaseCcardInfo == false) return false;
		if(this == obj) return true;
		BaseCcardInfo other = (BaseCcardInfo)obj;
		return new EqualsBuilder().append(getId(),other.getId()).isEquals();
	}
}

