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
@Table(name = "c_card_range")
public class BaseCcardRange extends IdEntity<java.lang.Long>
{
	
	//alias
	public static final String TABLE_ALIAS = "信用卡刷卡区间";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_NAME = "名称";
	public static final String ALIAS_MONEY_PROP_START_VALUE = "刷卡比例开始";
	public static final String ALIAS_MONEY_PROP_END_VALUE = "刷卡比例结束";
	public static final String ALIAS_FREQUENCY_PROP_START_VALUE = "刷卡次数开始";
	public static final String ALIAS_FREQUENCY_PROP_ENDT_VALUE = "刷卡次数结束";
	public static final String ALIAS_BILL_GAP_DAY = "出账单后几天执行任务";
	public static final String ALIAS_DAY = "刷卡天数";
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
	 * 名称
	 */
	@Column(name = "name")
	private java.lang.String name;
	/**
	 * 刷卡比例开始
	 */
	@Column(name = "moneyPropStartValue")
	private java.lang.Integer moneyPropStartValue;
	/**
	 * 刷卡比例结束
	 */
	@Column(name = "moneyPropEndValue")
	private java.lang.Integer moneyPropEndValue;
	/**
	 * 刷卡次数开始
	 */
	@Column(name = "frequencyPropStartValue")
	private java.lang.Integer frequencyPropStartValue;
	/**
	 * 刷卡次数结束
	 */
	@Column(name = "frequencyPropEndtValue")
	private java.lang.Integer frequencyPropEndtValue;
	/**
	 * 出账单后几天执行任务
	 */
	@Column(name = "billGapDay")
	private java.lang.Integer billGapDay;
	/**
	 * 刷卡天数
	 */
	@Column(name = "day")
	private java.lang.Integer day;
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

	public BaseCcardRange(){
	}
	
	public BaseCcardRange(
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
		if(obj instanceof BaseCcardRange == false) return false;
		if(this == obj) return true;
		BaseCcardRange other = (BaseCcardRange)obj;
		return new EqualsBuilder().append(getId(),other.getId()).isEquals();
	}
}

