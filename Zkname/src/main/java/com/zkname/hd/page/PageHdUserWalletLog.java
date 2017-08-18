package com.zkname.hd.page;

import java.util.*;

import com.zkname.hd.entity.*;
import com.zkname.frame.page.BasePage;
import com.zkname.frame.page.ShardingBasePage;
import com.zkname.frame.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

//用户钱包日志
public class PageHdUserWalletLog extends ShardingBasePage<HdUserWalletLog> {

	private static final long serialVersionUID = -4033946585064541028L;
	

	/** id */
	@Getter
	@Setter
	private java.lang.Long id;
	/** 用户id */
	@Getter
	@Setter
	private java.lang.Long userId;
	/** 房间id */
	@Getter
	@Setter
	private java.lang.Long roomId;
	/** 金币类型(1:金币充值,2:兑换减金币) */
	@Getter
	@Setter
	private java.lang.Integer coinType;
	/** 原有金币 */
	@Getter
	@Setter
	private java.lang.Long historyGoldCoin;
	/** 增加金币 */
	@Getter
	@Setter
	private java.lang.Long addGoldCoin;
	/** 原有金豆 */
	@Getter
	@Setter
	private java.lang.Long historyGoldBean;
	/** 增加金豆 */
	@Getter
	@Setter
	private java.lang.Long addGoldBean;
	/** 付款方 */
	@Getter
	@Setter
	private java.lang.Long fromUser;
	/** 收款方 */
	@Getter
	@Setter
	private java.lang.Long toUser;
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
	/** 金豆类型(1:金豆交易加,2:金豆交易减,3:兑换加金豆,4:金豆清空) */
	@Getter
	@Setter
	private java.lang.Integer beanType;
	
	
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
        if(isNotEmpty(this.roomId)) {
        	sb.append(" and  o.roomId = ? ");
        	list.add(this.roomId);
        }
        if(isNotEmpty(this.coinType)) {
        	sb.append(" and  o.coinType = ? ");
        	list.add(this.coinType);
        }
        if(isNotEmpty(this.historyGoldCoin)) {
        	sb.append(" and  o.historyGoldCoin = ? ");
        	list.add(this.historyGoldCoin);
        }
        if(isNotEmpty(this.addGoldCoin)) {
        	sb.append(" and  o.addGoldCoin = ? ");
        	list.add(this.addGoldCoin);
        }
        if(isNotEmpty(this.historyGoldBean)) {
        	sb.append(" and  o.historyGoldBean = ? ");
        	list.add(this.historyGoldBean);
        }
        if(isNotEmpty(this.addGoldBean)) {
        	sb.append(" and  o.addGoldBean = ? ");
        	list.add(this.addGoldBean);
        }
        if(isNotEmpty(this.fromUser)) {
        	sb.append(" and  o.fromUser = ? ");
        	list.add(this.fromUser);
        }
        if(isNotEmpty(this.toUser)) {
        	sb.append(" and  o.toUser = ? ");
        	list.add(this.toUser);
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
        if(isNotEmpty(this.beanType)) {
        	sb.append(" and  o.beanType = ? ");
        	list.add(this.beanType);
        }
		return sb;
	}

	public List<HdUserWalletLog> query() {
		sb.append("select o.* from hd_user_wallet_log o where 1=1 ").append(this.getSql()).append(" order by o.id desc ");
		return super.query();
	}

	
}

