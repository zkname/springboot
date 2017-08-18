package com.zkname.hd.page;

import java.util.*;

import com.zkname.hd.entity.*;
import com.zkname.frame.page.BasePage;
import com.zkname.frame.page.ShardingBasePage;
import com.zkname.frame.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

//用户充值日志
public class PageHdUserRechargeLog extends ShardingBasePage<HdUserRechargeLog> {

	private static final long serialVersionUID = -4033946585064541028L;
	

	/** id */
	@Getter
	@Setter
	private java.lang.Long id;
	/** 用户id */
	@Getter
	@Setter
	private java.lang.Long userId;
	/** 店铺id */
	@Getter
	@Setter
	private java.lang.Long shopId;
	/** 充值金额 */
	@Getter
	@Setter
	private java.lang.Integer money;
	/** 充值比例 */
	@Getter
	@Setter
	private java.lang.Integer rechargePercentage;
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
	/** 用户名 */
	@Getter
	@Setter
	private java.lang.String nickname;
	/** 店铺名 */
	@Getter
	@Setter
	private java.lang.String shopName;
	
	
	private StringBuffer getSql() {
		StringBuffer sb=new StringBuffer();
        if(isNotEmpty(this.id)) {
        	sb.append(" and  o.id = ? ");
        	list.add(this.id);
        }
        if(isNotEmpty(this.userId)) {
        	sb.append(" and  o.userId = ? ");
        	list.add(this.userId);
        }
        if(isNotEmpty(this.shopId)) {
        	sb.append(" and  o.shopId = ? ");
        	list.add(this.shopId);
        }
        if(isNotEmpty(this.money)) {
        	sb.append(" and  o.money = ? ");
        	list.add(this.money);
        }
        if(isNotEmpty(this.rechargePercentage)) {
        	sb.append(" and  o.rechargePercentage = ? ");
        	list.add(this.rechargePercentage);
        }
        if(isNotEmpty(this.createTimeBegin)) {
        	sb.append(" and  o.createTime between ? ");
        	list.add(DateUtil.StrutilDate(this.createTimeBegin+" 00:00:00","yyyy-MM-dd HH:mm:ss"));
        }
        if(isNotEmpty(this.createTimeEnd)) {
        	sb.append(" and  ? ");
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
        if(isNotEmpty(this.nickname)) {
        	sb.append(" and  u.nickname like ? ");
        	list.add("%"+this.nickname+"%");
        }
        if(isNotEmpty(this.shopName)) {
        	sb.append(" and  s.name like ? ");
        	list.add("%"+this.shopName+"%");
        }
		return sb;
	}

	public List<HdUserRechargeLog> query() {
		sb.append("select o.*,s.name as shopName,u.nickname from hd_user_recharge_log o left join hd_user u on o.userId=u.id left join hd_shop s on o.shopId=s.id where 1=1 ").append(this.getSql()).append(" order by o.id desc ");
		return super.query();
	}

	
}

