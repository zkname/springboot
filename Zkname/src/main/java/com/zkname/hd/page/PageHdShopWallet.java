package com.zkname.hd.page;

import java.util.*;

import com.zkname.hd.entity.*;
import com.zkname.frame.page.BasePage;
import com.zkname.frame.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

//店铺钱包
public class PageHdShopWallet extends BasePage<HdShopWallet> {

	private static final long serialVersionUID = -4033946585064541028L;
	

	/** 店铺id */
	@Getter
	@Setter
	private java.lang.Long shopId;
	/** 历史总金额 */
	@Getter
	@Setter
	private java.lang.Long historyMoney;
	/** 当前金额 */
	@Getter
	@Setter
	private java.lang.Long money;
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
        if(isNotEmpty(this.shopId)) {
        	sb.append(" and  o.shopId = ? ");
        	list.add(this.shopId);
        }
        if(isNotEmpty(this.historyMoney)) {
        	sb.append(" and  o.historyMoney = ? ");
        	list.add(this.historyMoney);
        }
        if(isNotEmpty(this.money)) {
        	sb.append(" and  o.money = ? ");
        	list.add(this.money);
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
        if(isNotEmpty(this.shopName)) {
        	sb.append(" and  s.name like ? ");
        	list.add("%"+this.shopName+"%");
        }
		return sb;
	}

	public List<HdShopWallet> query() {
		sb.append("select o.*,s.name as shopName from hd_shop_wallet o left join hd_shop s on o.shopId=s.id where 1=1 ").append(this.getSql()).append(" order by o.shopId desc ");
		return super.query();
	}

	
}

