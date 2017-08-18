package com.zkname.hd.page;

import java.util.*;

import com.zkname.hd.entity.*;
import com.zkname.frame.page.BasePage;
import com.zkname.frame.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

//用户钱包
public class PageHdUserWallet extends BasePage<HdUserWallet> {

	private static final long serialVersionUID = -4033946585064541028L;
	

	/** userId */
	@Getter
	@Setter
	private java.lang.Long userId;
	/** 金币 */
	@Getter
	@Setter
	private java.lang.Long goldCoin;
	/** 金豆 */
	@Getter
	@Setter
	private java.lang.Long goldBean;
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

	
	
	private StringBuffer getSql() {
		StringBuffer sb=new StringBuffer();
        if(isNotEmpty(this.userId)) {
        	sb.append(" and  o.userId = ? ");
        	list.add(this.userId);
        }
        if(isNotEmpty(this.goldCoin)) {
        	sb.append(" and  o.goldCoin = ? ");
        	list.add(this.goldCoin);
        }
        if(isNotEmpty(this.goldBean)) {
        	sb.append(" and  o.goldBean = ? ");
        	list.add(this.goldBean);
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
		return sb;
	}

	public List<HdUserWallet> query() {
		sb.append("select o.* from hd_user_wallet o where 1=1 ").append(this.getSql());
		return super.query();
	}

	
}

