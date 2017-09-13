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
@Table(name = "c_card_job_generate")
public class BaseCcardJobGenerate extends IdEntity<java.lang.Long>
{
	
	//alias
	public static final String TABLE_ALIAS = "信用卡刷卡任务生成";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_BANK_ID = "银行id";
	public static final String ALIAS_CARD_INFO_ID = "信用卡id";
	public static final String ALIAS_CARD_RANGE_ID = "信用卡限制id";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	
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
	 * 信用卡id
	 */
	@Column(name = "cardInfoId")
	private java.lang.Long cardInfoId;
	/**
	 * 信用卡限制id
	 */
	@Column(name = "cardRangeId")
	private java.lang.Long cardRangeId;
	/**
	 * 创建时间
	 */
	@Column(name = "createTime")
	private java.util.Date createTime;
	//columns END

	public BaseCcardJobGenerate(){
	}
	
	public BaseCcardJobGenerate(
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
	
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof BaseCcardJobGenerate == false) return false;
		if(this == obj) return true;
		BaseCcardJobGenerate other = (BaseCcardJobGenerate)obj;
		return new EqualsBuilder().append(getId(),other.getId()).isEquals();
	}
}

