package com.zkname.hd.page;

import java.util.List;

import com.zkname.hd.entity.SysUserRole;
import com.zkname.frame.page.BasePage;
import com.zkname.frame.util.DateUtil;

import lombok.Getter;
import lombok.Setter;

//角色表
public class PageadminSysUserRole extends BasePage<SysUserRole> {

	private static final long serialVersionUID = -3153366910036568944L;
	/** id */
	@Getter
	@Setter
	private java.lang.Integer id;
	/** 角色 */
	@Getter
	@Setter
	private java.lang.String name;
	/** 备注 */
	@Getter
	@Setter
	private java.lang.String comment;
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
	private java.lang.Integer creatorId;
	/** 平台id */
	@Getter
	@Setter
	private java.lang.String platformId;
	/**  父类id */
	@Getter
	@Setter
	private java.lang.Integer parentId;
	/** 排序 */
	@Getter
	@Setter
	private String sort="createTime";
	@Getter
	@Setter
	private String order="desc";
	
	@Getter
	@Setter
	private String roleCode;
	
	
	
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
        if(isNotEmpty(this.comment)) {
        	sb.append(" and  o.comment = ? ");
        	list.add(this.comment);
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
        if(isNotEmpty(this.platformId)) {
        	sb.append(" and  o.platformId = ? ");
        	list.add(this.platformId);
        }
        if(isNotEmpty(this.parentId)) {
        	sb.append(" and  o.parentId = ? ");
        	list.add(this.parentId);
        }
        if(isNotEmpty(this.roleCode)) {
        	sb.append("  and o.roleCode!=?  and  o.roleCode like ?");
        	list.add(this.roleCode);
        	list.add(this.roleCode+"%");
        }
        sb.append(" order by o.").append(sort).append(" ").append(order);
		return sb;
	}

	public List<SysUserRole> query() {
		sb.append("select o.* from sys_user_role o where 1=1 ").append(this.getSql());
		return super.query();
	}
	
}

