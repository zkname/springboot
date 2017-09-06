package com.zkname.credit.card.page;

import java.util.*;

import com.zkname.credit.card.entity.*;
import com.zkname.core.page.BasePage;
import com.zkname.core.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

//信用卡刷卡区间
public class PageCcardRange extends BasePage<CcardRange> {

	private static final long serialVersionUID = -4033946585064541028L;
	

	/** 主键 */
	@Getter
	@Setter
	private java.lang.Long id;
	/** 名称 */
	@Getter
	@Setter
	private java.lang.String name;
	/** 刷卡比例开始 */
	@Getter
	@Setter
	private java.lang.Integer moneyPropStartValue;
	/** 刷卡比例结束 */
	@Getter
	@Setter
	private java.lang.Integer moneyPropEndValue;
	/** 刷卡次数开始 */
	@Getter
	@Setter
	private java.lang.Integer frequencyPropStartValue;
	/** 刷卡次数结束 */
	@Getter
	@Setter
	private java.lang.Integer frequencyPropEndtValue;
	/** 备注 */
	@Getter
	@Setter
	private java.lang.String remarks;
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
        if(isNotEmpty(this.name)) {
        	sb.append(" and  o.name like ? ");
        	list.add("%"+this.name+"%");
        }
        if(isNotEmpty(this.moneyPropStartValue)) {
        	sb.append(" and  o.moneyPropStartValue = ? ");
        	list.add(this.moneyPropStartValue);
        }
        if(isNotEmpty(this.moneyPropEndValue)) {
        	sb.append(" and  o.moneyPropEndValue = ? ");
        	list.add(this.moneyPropEndValue);
        }
        if(isNotEmpty(this.frequencyPropStartValue)) {
        	sb.append(" and  o.frequencyPropStartValue = ? ");
        	list.add(this.frequencyPropStartValue);
        }
        if(isNotEmpty(this.frequencyPropEndtValue)) {
        	sb.append(" and  o.frequencyPropEndtValue = ? ");
        	list.add(this.frequencyPropEndtValue);
        }
        if(isNotEmpty(this.remarks)) {
        	sb.append(" and  o.remarks = ? ");
        	list.add(this.remarks);
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

	public List<CcardRange> query() {
		sb.append("select o.* from c_card_range o where 1=1 ").append(this.getSql());
		return super.query();
	}

	
}

