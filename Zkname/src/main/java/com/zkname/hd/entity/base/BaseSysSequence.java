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
@Table(name = "sys_sequence")
public class BaseSysSequence extends IdEntity<java.lang.String>
{
	
	//alias
	public static final String TABLE_ALIAS = "自增序列";
	public static final String ALIAS_SEQ_NAME = "名称";
	public static final String ALIAS_CURRENT_VAL = "迭代值";
	public static final String ALIAS_INCREMENT_VAL = "每次递增值";
	
	//columns START
	/**
	 * 名称
	 */
	@Id
	@Column(name = "seq_name")
	@Getter @Setter private java.lang.String seqName;
	/**
	 * 迭代值
	 */
	@Column(name = "current_val")
	@Getter @Setter private java.lang.Long currentVal;
	/**
	 * 每次递增值
	 */
	@Column(name = "increment_val")
	@Getter @Setter private java.lang.Integer incrementVal;
	//columns END

	public BaseSysSequence(){
	}
	
	public BaseSysSequence(
		java.lang.String seqName
	){
		this.seqName = seqName;
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof BaseSysSequence == false) return false;
		if(this == obj) return true;
		BaseSysSequence other = (BaseSysSequence)obj;
		return new EqualsBuilder().append(getId(),other.getId()).isEquals();
	}

	@Override
	public String getId() {
		return seqName;
	}

	@Override
	public void setId(String id) {
		this.seqName=id;
	}
}

