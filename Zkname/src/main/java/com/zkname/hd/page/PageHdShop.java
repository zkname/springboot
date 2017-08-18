package com.zkname.hd.page;

import java.util.*;

import com.zkname.hd.entity.*;
import com.zkname.frame.page.BasePage;
import com.zkname.frame.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

//店铺表
public class PageHdShop extends BasePage<HdShop> {

	private static final long serialVersionUID = -4033946585064541028L;
	

	/** id */
	@Getter
	@Setter
	private java.lang.Long id;
	/** 店铺名 */
	@Getter
	@Setter
	private java.lang.String name;
	/** 备注 */
	@Getter
	@Setter
	private java.lang.String remark;
	/** uuid */
	@Getter
	@Setter
	private java.lang.String uuid;
	/** 充值比例 */
	@Getter
	@Setter
	private java.lang.Integer rechargePercentage;
	/** 分成比例 */
	@Getter
	@Setter
	private java.lang.Integer returnPercentage;
	/** 地址 */
	@Getter
	@Setter
	private java.lang.String address;
	/** 联系人 */
	@Getter
	@Setter
	private java.lang.String contact;
	/** 电话 */
	@Getter
	@Setter
	private java.lang.String phone;
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
        if(isNotEmpty(this.remark)) {
        	sb.append(" and  o.remark = ? ");
        	list.add(this.remark);
        }
        if(isNotEmpty(this.uuid)) {
        	sb.append(" and  o.uuid = ? ");
        	list.add(this.uuid);
        }
        if(isNotEmpty(this.rechargePercentage)) {
        	sb.append(" and  o.rechargePercentage = ? ");
        	list.add(this.rechargePercentage);
        }
        if(isNotEmpty(this.returnPercentage)) {
        	sb.append(" and  o.returnPercentage = ? ");
        	list.add(this.returnPercentage);
        }
        if(isNotEmpty(this.address)) {
        	sb.append(" and  o.address = ? ");
        	list.add(this.address);
        }
        if(isNotEmpty(this.contact)) {
        	sb.append(" and  o.contact = ? ");
        	list.add(this.contact);
        }
        if(isNotEmpty(this.phone)) {
        	sb.append(" and  o.phone = ? ");
        	list.add(this.phone);
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

	public List<HdShop> query() {
		sb.append("select o.*,w.money from hd_shop o left join hd_shop_wallet w on o.id=w.shopId where 1=1 ").append(this.getSql()).append(" order by o.id desc ");
		return super.query();
	}

	
}

