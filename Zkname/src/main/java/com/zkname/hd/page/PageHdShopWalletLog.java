package com.zkname.hd.page;

import java.util.*;

import com.zkname.hd.entity.*;
import com.zkname.frame.page.BasePage;
import com.zkname.frame.page.ShardingBasePage;
import com.zkname.frame.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

//店铺钱包日志
public class PageHdShopWalletLog extends ShardingBasePage<HdShopWalletLog> {

	private static final long serialVersionUID = -4033946585064541028L;
	

	/** id */
	@Getter
	@Setter
	private java.lang.Long id;
	/** 店铺id */
	@Getter
	@Setter
	private java.lang.Long shopId;
	/** 类型(1:返利,2:提现) */
	@Getter
	@Setter
	private java.lang.Integer type;
	/** 原有金额 */
	@Getter
	@Setter
	private java.lang.Long originalMoney;
	/** 当前金额 */
	@Getter
	@Setter
	private java.lang.Long addMoney;
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
	@Getter
	@Setter
	private java.lang.String shopName;

	
	
	private StringBuffer getSql() {
		StringBuffer sb=new StringBuffer();
        if(isNotEmpty(this.id)) {
        	sb.append(" and  o.id = ? ");
        	list.add(this.id);
        }
        if(isNotEmpty(this.shopId)) {
        	sb.append(" and  o.shopId = ? ");
        	list.add(this.shopId);
        }
        if(isNotEmpty(this.type)) {
        	sb.append(" and  o.type = ? ");
        	list.add(this.type);
        }
        if(isNotEmpty(this.originalMoney)) {
        	sb.append(" and  o.originalMoney = ? ");
        	list.add(this.originalMoney);
        }
        if(isNotEmpty(this.addMoney)) {
        	sb.append(" and  o.addMoney = ? ");
        	list.add(this.addMoney);
        }
        if(isNotEmpty(this.createTimeBegin)) {
        	sb.append(" and  o.createTime between ? ");
        	list.add(DateUtil.StrutilDate(this.createTimeBegin+" 00:00:00","yyyy-MM-dd HH:mm:ss"));
        }
        if(isNotEmpty(this.createTimeEnd)) {
        	sb.append(" and ? ");
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
        if(isNotEmpty(this.shopName)) {
        	sb.append(" and  s.name like ? ");
        	list.add("%"+this.shopName+"%");
        }
		return sb;
	}

	public List<HdShopWalletLog> query() {
		sb.append("select o.*,s.name as shopName from hd_shop_wallet_log o left join hd_shop s on o.shopId=s.id where 1=1 ").append(this.getSql()).append(" ORDER BY o.createTime desc ");
		return super.query();
	}

	
}

