package com.zkname.hd.page;

import java.util.*;

import com.zkname.hd.entity.*;
import com.zkname.frame.page.BasePage;
import com.zkname.frame.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

//微信用户表
public class PageHdUser extends BasePage<HdUser> {

	private static final long serialVersionUID = -4033946585064541028L;
	

	/** id */
	@Getter
	@Setter
	private java.lang.Long id;
	/** 微信UnionId */
	@Getter
	@Setter
	private java.lang.String unionId;
	/** 微信OpenId */
	@Getter
	@Setter
	private java.lang.String openId;
	/** 昵称 */
	@Getter
	@Setter
	private java.lang.String nickname;
	/** 性别 */
	@Getter
	@Setter
	private java.lang.Integer sex;
	/** 头像 */
	@Getter
	@Setter
	private java.lang.String headimgurl;
	/** 用户类型(0:普通用户,1:配货人员) */
	@Getter
	@Setter
	private java.lang.Integer type;
	/** 省 */
	@Getter
	@Setter
	private java.lang.String province;
	/** 市 */
	@Getter
	@Setter
	private java.lang.String city;
	/** 省id */
	@Getter
	@Setter
	private java.lang.Integer provinceId;
	/** 市Id */
	@Getter
	@Setter
	private java.lang.Integer cityId;
	/** 是否关注：0、未关注；1、关注； */
	@Getter
	@Setter
	private java.lang.Integer subscribe;
	/** 关注时间 */
	@Getter
	@Setter
	private java.lang.String subscribeTimeBegin;
	@Getter
	@Setter
	private java.lang.String subscribeTimeEnd;
	/** 取消关注时间 */
	@Getter
	@Setter
	private java.lang.String unsubscribeTimeBegin;
	@Getter
	@Setter
	private java.lang.String unsubscribeTimeEnd;
	/** 用户sessionkey */
	@Getter
	@Setter
	private java.lang.String sessionKey;
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

	
	
	private StringBuffer getSql() {
		StringBuffer sb=new StringBuffer();
        if(isNotEmpty(this.id)) {
        	sb.append(" and  o.id = ? ");
        	list.add(this.id);
        }
        if(isNotEmpty(this.unionId)) {
        	sb.append(" and  o.unionId = ? ");
        	list.add(this.unionId);
        }
        if(isNotEmpty(this.openId)) {
        	sb.append(" and  o.openId = ? ");
        	list.add(this.openId);
        }
        if(isNotEmpty(this.nickname)) {
        	sb.append(" and  o.nickname = ? ");
        	list.add(this.nickname);
        }
        if(isNotEmpty(this.sex)) {
        	sb.append(" and  o.sex = ? ");
        	list.add(this.sex);
        }
        if(isNotEmpty(this.headimgurl)) {
        	sb.append(" and  o.headimgurl = ? ");
        	list.add(this.headimgurl);
        }
        if(isNotEmpty(this.type)) {
        	sb.append(" and  o.type = ? ");
        	list.add(this.type);
        }
        if(isNotEmpty(this.province)) {
        	sb.append(" and  o.province = ? ");
        	list.add(this.province);
        }
        if(isNotEmpty(this.city)) {
        	sb.append(" and  o.city = ? ");
        	list.add(this.city);
        }
        if(isNotEmpty(this.provinceId)) {
        	sb.append(" and  o.provinceId = ? ");
        	list.add(this.provinceId);
        }
        if(isNotEmpty(this.cityId)) {
        	sb.append(" and  o.cityId = ? ");
        	list.add(this.cityId);
        }
        if(isNotEmpty(this.subscribe)) {
        	sb.append(" and  o.subscribe = ? ");
        	list.add(this.subscribe);
        }
        if(isNotEmpty(this.subscribeTimeBegin)) {
        	sb.append(" and  o.subscribeTime >= ? ");
        	list.add(DateUtil.StrutilDate(this.subscribeTimeBegin+" 00:00:00","yyyy-MM-dd HH:mm:ss"));
        }
        if(isNotEmpty(this.subscribeTimeEnd)) {
        	sb.append(" and  o.subscribeTime <= ? ");
        	list.add(DateUtil.StrutilDate(this.subscribeTimeEnd+" 23:59:59","yyyy-MM-dd HH:mm:ss"));
        }
        if(isNotEmpty(this.unsubscribeTimeBegin)) {
        	sb.append(" and  o.unsubscribeTime >= ? ");
        	list.add(DateUtil.StrutilDate(this.unsubscribeTimeBegin+" 00:00:00","yyyy-MM-dd HH:mm:ss"));
        }
        if(isNotEmpty(this.unsubscribeTimeEnd)) {
        	sb.append(" and  o.unsubscribeTime <= ? ");
        	list.add(DateUtil.StrutilDate(this.unsubscribeTimeEnd+" 23:59:59","yyyy-MM-dd HH:mm:ss"));
        }
        if(isNotEmpty(this.sessionKey)) {
        	sb.append(" and  o.sessionKey = ? ");
        	list.add(this.sessionKey);
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
		return sb;
	}

	public List<HdUser> query() {
		sb.append("select o.* from hd_user o where 1=1 ").append(this.getSql());
		return super.query();
	}

	
}

