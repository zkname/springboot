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
@Table(name = "c_card_rule")
public class BaseCcardRule extends IdEntity<java.lang.Long>
{
	
	//alias
	public static final String TABLE_ALIAS = "信用卡刷卡规则";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_CARD_INFO_ID = "信用卡id";
	public static final String ALIAS_CARD_RANGE_ID = "信用规则id";
	
	//columns START
	/**
	 * 主键
	 */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Getter @Setter private java.lang.Long id;
	/**
	 * 信用卡id
	 */
	@Column(name = "cardInfoId")
	@Getter @Setter private java.lang.Long cardInfoId;
	/**
	 * 信用规则id
	 */
	@Column(name = "cardRangeId")
	@Getter @Setter private java.lang.Long cardRangeId;
	//columns END

	public BaseCcardRule(){
	}
	
	public BaseCcardRule(
		java.lang.Long id
	){
		this.id = id;
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof BaseCcardRule == false) return false;
		if(this == obj) return true;
		BaseCcardRule other = (BaseCcardRule)obj;
		return new EqualsBuilder().append(getId(),other.getId()).isEquals();
	}
}

