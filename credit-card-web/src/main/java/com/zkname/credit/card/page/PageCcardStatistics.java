package com.zkname.credit.card.page;

import java.util.*;

import com.zkname.credit.card.entity.*;
import com.zkname.core.page.BasePage;
import com.zkname.core.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

//信用卡信息
public class PageCcardStatistics extends BasePage<CcardInfo> {

	private static final long serialVersionUID = -4033946585064541028L;
	

	/** 主键 */
	@Getter
	@Setter
	private java.lang.Long id;
	/** 银行id */
	@Getter
	@Setter
	private java.lang.Long bankId;
	/** 信用规则id */
	@Getter
	@Setter
	private java.lang.Long cardRangeId;
	/** 卡名 */
	@Getter
	@Setter
	private java.lang.String name;
	/** 额度 */
	@Getter
	@Setter
	private java.lang.Integer money;
	/** 年费类型：1、强制收费，2、刷卡次数，3、免年费 */
	@Getter
	@Setter
	private java.lang.Integer annualFeeType;
	/** 年费 */
	@Getter
	@Setter
	private java.lang.Integer annualFee;
	/** 刷卡次数 */
	@Getter
	@Setter
	private java.lang.Integer cardNum;
	/** 账单日 */
	@Getter
	@Setter
	private java.lang.Integer billDate;
	/** 下次提额时间 */
	@Getter
	@Setter
	private java.lang.String nextUpBegin;
	@Getter
	@Setter
	private java.lang.String nextUpEnd;
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
	
	/** 月份 */
	@Getter
	@Setter
	private java.lang.String date;

	
	
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
        if(isNotEmpty(this.cardRangeId)) {
        	sb.append(" and  o.cardRangeId = ? ");
        	list.add(this.cardRangeId);
        }
        if(isNotEmpty(this.name)) {
        	sb.append(" and  o.name like ? ");
        	list.add("%"+this.name+"%");
        }
        if(isNotEmpty(this.money)) {
        	sb.append(" and  o.money = ? ");
        	list.add(this.money);
        }
        if(isNotEmpty(this.annualFeeType)) {
        	sb.append(" and  o.annualFeeType = ? ");
        	list.add(this.annualFeeType);
        }
        if(isNotEmpty(this.annualFee)) {
        	sb.append(" and  o.annualFee = ? ");
        	list.add(this.annualFee);
        }
        if(isNotEmpty(this.cardNum)) {
        	sb.append(" and  o.cardNum = ? ");
        	list.add(this.cardNum);
        }
        if(isNotEmpty(this.billDate)) {
        	sb.append(" and  o.billDate = ? ");
        	list.add(this.billDate);
        }
        if(isNotEmpty(this.nextUpBegin)) {
        	sb.append(" and  o.nextUp >= ? ");
        	list.add(DateUtil.StrutilDate(this.nextUpBegin+" 00:00:00","yyyy-MM-dd HH:mm:ss"));
        }
        if(isNotEmpty(this.nextUpEnd)) {
        	sb.append(" and  o.nextUp <= ? ");
        	list.add(DateUtil.StrutilDate(this.nextUpEnd+" 23:59:59","yyyy-MM-dd HH:mm:ss"));
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

	public List<CcardInfo> query() {
		String v=" k.jobDate>='"+date+"-01"+"' and k.jobDate<'"+DateUtil.Date2Str(DateUtil.addMonth(DateUtil.Str2Date(date+"-01"),1),"yyyy-MM-dd")+"' ";
		sb.append("select o.*,a.name as bankName,b.name as cardRangeName,");
		sb.append(" (SELECT sum(k.money) FROM c_card_job as k WHERE k.cardInfoId=o.id and k.deleStatus='1' and k.status='0' and ").append(v).append(") as totalAmount  ,");
		sb.append(" (SELECT sum(k.money) FROM c_card_job as k WHERE k.cardInfoId=o.id and k.deleStatus='1' and k.status='1' and ").append(v).append(") as amount  ,");
		sb.append(" (SELECT sum(k.fee) FROM c_card_job as k WHERE k.cardInfoId=o.id and k.deleStatus='1' and k.status='1' and ").append(v).append(") as fee  ");
		sb.append(" from c_card_info as o,c_bank as a,c_card_range as b where o.bankId=a.id and b.id=o.cardRangeId").append(this.getSql());
		return super.query();
	}

	
}

