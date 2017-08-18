package com.zkname.hd.page;

import java.util.*;

import com.zkname.hd.entity.*;
import com.zkname.frame.page.BasePage;
import com.zkname.frame.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

//房间用户表
public class PageHdRoomUser extends BasePage<HdRoomUser> {

	private static final long serialVersionUID = -4033946585064541028L;
	

	/** id */
	@Getter
	@Setter
	private java.lang.Long id;
	/** 房间类型 */
	@Getter
	@Setter
	private java.lang.Long roomId;
	/** 房主id */
	@Getter
	@Setter
	private java.lang.Long userId;
	/** 角色id */
	@Getter
	@Setter
	private java.lang.Long roleId;
	/** 状态(1:活跃,0:) */
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
	/** 用户名 */
	@Getter
	@Setter
	private java.lang.String userName;
	/** 房间名 */
	@Getter
	@Setter
	private java.lang.String roomName;

	
	
	private StringBuffer getSql() {
		StringBuffer sb=new StringBuffer();
        if(isNotEmpty(this.id)) {
        	sb.append(" and  o.id = ? ");
        	list.add(this.id);
        }
        if(isNotEmpty(this.roomId)) {
        	sb.append(" and  o.roomId = ? ");
        	list.add(this.roomId);
        }
        if(isNotEmpty(this.userId)) {
        	sb.append(" and  o.userId = ? ");
        	list.add(this.userId);
        }
        if(isNotEmpty(this.roleId)) {
        	sb.append(" and  o.roleId = ? ");
        	list.add(this.roleId);
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
        if(isNotEmpty(this.userName)) {
        	sb.append(" and  u.nickname like ? ");
        	list.add("%"+this.userName+"%");
        }
        if(isNotEmpty(this.roomName)) {
        	sb.append(" and  r.name like ? ");
        	list.add("%"+this.roomName+"%");
        }
		return sb;
	}

	public List<HdRoomUser> query() {
		sb.append("select o.*,u.nickname as userName,r.name as roomName from hd_room_user o left join hd_user u on o.userId=u.id left join hd_room r on o.roomId=r.id where 1=1 ").append(this.getSql()).append(" order by o.id desc ");
		return super.query();
	}

	
}

