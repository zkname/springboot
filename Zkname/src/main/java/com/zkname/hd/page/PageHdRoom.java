package com.zkname.hd.page;

import java.util.*;

import com.zkname.hd.entity.*;
import com.zkname.frame.page.BasePage;
import com.zkname.frame.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

//房间表
public class PageHdRoom extends BasePage<HdRoom> {

	private static final long serialVersionUID = -4033946585064541028L;
	

	/** id */
	@Getter
	@Setter
	private java.lang.Long id;
	/** 房间名称 */
	@Getter
	@Setter
	private java.lang.String name;
	/** 注释 */
	@Getter
	@Setter
	private java.lang.String remark;
	/** 房间类型 */
	@Getter
	@Setter
	private java.lang.Integer type;
	/** 最大人数 */
	@Getter
	@Setter
	private java.lang.Integer maxNum;
	/** 房主id */
	@Getter
	@Setter
	private java.lang.Long userId;
	/** 房间状态(1:开启,0:关闭) */
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
        if(isNotEmpty(this.name)) {
        	sb.append(" and  o.name like ? ");
        	list.add("%"+this.name+"%");
        }
        if(isNotEmpty(this.remark)) {
        	sb.append(" and  o.remark = ? ");
        	list.add(this.remark);
        }
        if(isNotEmpty(this.type)) {
        	sb.append(" and  o.type = ? ");
        	list.add(this.type);
        }
        if(isNotEmpty(this.maxNum)) {
        	sb.append(" and  o.maxNum = ? ");
        	list.add(this.maxNum);
        }
        if(isNotEmpty(this.userId)) {
        	sb.append(" and  o.userId = ? ");
        	list.add(this.userId);
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

	public List<HdRoom> query() {
		sb.append("select o.* from hd_room o where 1=1 ").append(this.getSql()).append(" order by o.id desc ");
		return super.query();
	}

	
}

