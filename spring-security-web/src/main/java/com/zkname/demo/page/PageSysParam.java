package com.zkname.demo.page;

import java.util.List;

import com.zkname.demo.entity.SysParam;
import com.zkname.core.page.BasePage;
import com.zkname.core.util.DateUtil;

import lombok.Getter;
import lombok.Setter;

//系统参数表
public class PageSysParam extends BasePage<SysParam> {

	private static final long serialVersionUID = -4033946585064541028L;
	

	/** 主键id */
	@Getter
	@Setter
	private java.lang.Long id;
	/** 参数名称 */
	@Getter
	@Setter
	private java.lang.String name;
	/** 参数类型 */
	@Getter
	@Setter
	private java.lang.Integer type;
	/** 参数key */
	@Getter
	@Setter
	private java.lang.String k;
	/** 参数value */
	@Getter
	@Setter
	private java.lang.String v;
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
        if(isNotEmpty(this.type)) {
        	sb.append(" and  o.type = ? ");
        	list.add(this.type);
        }
        if(isNotEmpty(this.k)) {
        	sb.append(" and  o.k = ? ");
        	list.add(this.k);
        }
        if(isNotEmpty(this.v)) {
        	sb.append(" and  o.v = ? ");
        	list.add(this.v);
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

	public List<SysParam> query() {
		sb.append("select o.* from sys_param o where 1=1 ").append(this.getSql());
		return super.query();
	}

	
}

