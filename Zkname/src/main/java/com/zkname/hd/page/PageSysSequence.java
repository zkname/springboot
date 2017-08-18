package com.zkname.hd.page;

import java.util.*;

import com.zkname.hd.entity.*;
import com.zkname.frame.page.BasePage;
import com.zkname.frame.util.DateUtil;

import lombok.Getter;
import lombok.Setter;

//自增序列
public class PageSysSequence extends BasePage<SysSequence> {

	private static final long serialVersionUID = -4033946585064541028L;
	

	/** 名称 */
	@Getter
	@Setter
	private java.lang.String seqName;
	/** 迭代值 */
	@Getter
	@Setter
	private java.lang.Integer currentVal;
	/** 每次递增值 */
	@Getter
	@Setter
	private java.lang.Integer incrementVal;

	
	
	private StringBuffer getSql() {
		StringBuffer sb=new StringBuffer();
        if(isNotEmpty(this.seqName)) {
        	sb.append(" and  o.seq_name = ? ");
        	list.add(this.seqName);
        }
        if(isNotEmpty(this.currentVal)) {
        	sb.append(" and  o.current_val = ? ");
        	list.add(this.currentVal);
        }
        if(isNotEmpty(this.incrementVal)) {
        	sb.append(" and  o.increment_val = ? ");
        	list.add(this.incrementVal);
        }
		return sb;
	}

	public List<SysSequence> query() {
		sb.append("select o.* from sys_sequence o where 1=1 ").append(this.getSql());
		return super.query();
	}

	
}

