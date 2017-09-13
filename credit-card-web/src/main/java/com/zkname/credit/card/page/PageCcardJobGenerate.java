package com.zkname.credit.card.page;

import java.util.*;

import com.zkname.credit.card.entity.*;
import com.zkname.core.page.BasePage;
import com.zkname.core.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

//信用卡刷卡任务生成
public class PageCcardJobGenerate extends BasePage<CcardJobGenerate> {

	private static final long serialVersionUID = -4033946585064541028L;
	

	/** 主键 */
	@Getter
	@Setter
	private java.lang.Long id;
	/** 银行id */
	@Getter
	@Setter
	private java.lang.Long bankId;
	/** 信用卡id */
	@Getter
	@Setter
	private java.lang.Long cardInfoId;
	/** 信用卡限制id */
	@Getter
	@Setter
	private java.lang.Long cardRangeId;
	/** 创建时间 */
	@Getter
	@Setter
	private java.lang.String createTimeBegin;
	@Getter
	@Setter
	private java.lang.String createTimeEnd;

	
	
	private StringBuffer getSql() {
		StringBuffer sb=new StringBuffer();
        if(isNotEmpty(this.id)) {
        	sb.append(" and  o.id = ? ");
        	list.add(this.id);
        }
        if(isNotEmpty(this.bankId)) {
        	sb.append(" and  o.bankId = ? ");
        	list.add(this.bankId);
        }
        if(isNotEmpty(this.cardInfoId)) {
        	sb.append(" and  o.cardInfoId = ? ");
        	list.add(this.cardInfoId);
        }
        if(isNotEmpty(this.cardRangeId)) {
        	sb.append(" and  o.cardRangeId = ? ");
        	list.add(this.cardRangeId);
        }
        if(isNotEmpty(this.createTimeBegin)) {
        	sb.append(" and  o.createTime >= ? ");
        	list.add(DateUtil.StrutilDate(this.createTimeBegin+" 00:00:00","yyyy-MM-dd HH:mm:ss"));
        }
        if(isNotEmpty(this.createTimeEnd)) {
        	sb.append(" and  o.createTime <= ? ");
        	list.add(DateUtil.StrutilDate(this.createTimeEnd+" 23:59:59","yyyy-MM-dd HH:mm:ss"));
        }
		return sb;
	}

	public List<CcardJobGenerate> query() {
		sb.append("select o.* from c_card_job_generate o where 1=1 ").append(this.getSql());
		return super.query();
	}

	
}

