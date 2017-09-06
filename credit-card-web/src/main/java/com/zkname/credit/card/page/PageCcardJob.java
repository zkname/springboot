package com.zkname.credit.card.page;

import java.util.*;

import com.zkname.credit.card.entity.*;
import com.zkname.core.page.BasePage;
import com.zkname.core.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

//信用卡刷卡任务
public class PageCcardJob extends BasePage<CcardJob> {

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
	/** 信用卡规则id */
	@Getter
	@Setter
	private java.lang.Long cardRuleId;
	/** 执行日期 */
	@Getter
	@Setter
	private java.lang.String jobDateBegin;
	@Getter
	@Setter
	private java.lang.String jobDateEnd;
	/** 金额 */
	@Getter
	@Setter
	private java.lang.Integer money;
	/** 手续费 */
	@Getter
	@Setter
	private java.lang.Double fee;
	/** 刷卡状态：1、已刷卡 */
	@Getter
	@Setter
	private java.lang.Integer status;
	/** 创建时间 */
	@Getter
	@Setter
	private java.lang.String createTimeBegin;
	@Getter
	@Setter
	private java.lang.String createTimeEnd;
	/** 修改时间 */
	@Getter
	@Setter
	private java.lang.String updateTimeBegin;
	@Getter
	@Setter
	private java.lang.String updateTimeEnd;
	/** 可见状态(0:不可见;1:可见) */
	@Getter
	@Setter
	private java.lang.String deleStatus;
	/** 创建者id */
	@Getter
	@Setter
	private java.lang.Long creatorId;

	
	
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
        if(isNotEmpty(this.cardRuleId)) {
        	sb.append(" and  o.cardRuleId = ? ");
        	list.add(this.cardRuleId);
        }
        if(isNotEmpty(this.jobDateBegin)) {
        	sb.append(" and  o.jobDate >= ? ");
        	list.add(DateUtil.StrutilDate(this.jobDateBegin+" 00:00:00","yyyy-MM-dd HH:mm:ss"));
        }
        if(isNotEmpty(this.jobDateEnd)) {
        	sb.append(" and  o.jobDate <= ? ");
        	list.add(DateUtil.StrutilDate(this.jobDateEnd+" 23:59:59","yyyy-MM-dd HH:mm:ss"));
        }
        if(isNotEmpty(this.money)) {
        	sb.append(" and  o.money = ? ");
        	list.add(this.money);
        }
        if(isNotEmpty(this.fee)) {
        	sb.append(" and  o.fee = ? ");
        	list.add(this.fee);
        }
        if(isNotEmpty(this.status)) {
        	sb.append(" and  o.status = ? ");
        	list.add(this.status);
        }
        if(isNotEmpty(this.createTimeBegin)) {
        	sb.append(" and  o.createTime >= ? ");
        	list.add(DateUtil.StrutilDate(this.createTimeBegin+" 00:00:00","yyyy-MM-dd HH:mm:ss"));
        }
        if(isNotEmpty(this.createTimeEnd)) {
        	sb.append(" and  o.createTime <= ? ");
        	list.add(DateUtil.StrutilDate(this.createTimeEnd+" 23:59:59","yyyy-MM-dd HH:mm:ss"));
        }
        if(isNotEmpty(this.updateTimeBegin)) {
        	sb.append(" and  o.updateTime >= ? ");
        	list.add(DateUtil.StrutilDate(this.updateTimeBegin+" 00:00:00","yyyy-MM-dd HH:mm:ss"));
        }
        if(isNotEmpty(this.updateTimeEnd)) {
        	sb.append(" and  o.updateTime <= ? ");
        	list.add(DateUtil.StrutilDate(this.updateTimeEnd+" 23:59:59","yyyy-MM-dd HH:mm:ss"));
        }
        if(isNotEmpty(this.deleStatus)) {
        	sb.append(" and  o.deleStatus = ? ");
        	list.add(this.deleStatus);
        }
        if(isNotEmpty(this.creatorId)) {
        	sb.append(" and  o.creatorId = ? ");
        	list.add(this.creatorId);
        }
		return sb;
	}

	public List<CcardJob> query() {
		sb.append("select o.*,a.name as bankName,b.name as cardRangeName,c.name as cardInfoName from c_card_job o,c_bank as a,c_card_range as b,c_card_info c where o.bankId=a.id and b.id=o.cardRangeId and c.id=o.cardInfoId ").append(this.getSql());
		return super.query();
	}
}

