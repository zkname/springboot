package com.zkname.hd.page;

import java.util.List;

import com.zkname.hd.entity.SysUser;
import com.zkname.frame.page.BasePage;
import com.zkname.frame.util.DateUtil;

import lombok.Getter;
import lombok.Setter;

//用户表
public class PageBusinessSysUser extends BasePage<SysUser> {

	private static final long serialVersionUID = -798933923926140876L;
	
	/** 主键 */
	@Getter
	@Setter
	private java.lang.Long id;
	/** 用户名 */
	@Getter
	@Setter
	private java.lang.String userName;
	/** 客户名 */
	@Getter
	@Setter
	private java.lang.String name;
	
	
	/** 密码 */
	@Getter
	@Setter
	private java.lang.String password;
	/** 邮箱 */
	@Getter
	@Setter
	private java.lang.String email;
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
	/** 登陆时间 */
	@Getter
	@Setter
	private java.lang.String loginTimeBegin;
	@Getter
	@Setter
	private java.lang.String loginTimeEnd;
	/**制作方**/
	@Getter
	@Setter
	private java.lang.String platformId;
	/**角色**/
	@Getter
	@Setter
	private java.lang.String roleCode;
	
	/** 排序 */
	@Getter
	@Setter
	private String sort="createTime";
	@Getter
	@Setter
	private String order="desc";
	//角色
	@Getter
	@Setter
	private int roleId;
	@Getter
	@Setter
	private java.lang.String businessName;
	
	
	private StringBuffer getSql() {
		StringBuffer sb=new StringBuffer();
        if(isNotEmpty(this.id)) {
        	sb.append(" and  o.id != ? ");
        	list.add(this.id);
        }
        if(isNotEmpty(this.userName)) {
        	sb.append(" and  (o.userName like ? ");
        	list.add("%"+this.userName+"%");
        	
        	sb.append(" or  o.realName like ?) ");
        	list.add("%"+this.userName+"%");
        }
        if(isNotEmpty(this.password)) {
        	sb.append(" and  o.password = ? ");
        	list.add(this.password);
        }
        if(isNotEmpty(this.email)) {
        	sb.append(" and  o.email like ? ");
        	list.add("%"+this.email+"%");
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
        if(isNotEmpty(this.loginTimeBegin)) {
        	sb.append(" and  o.loginTime >= ? ");
        	list.add(DateUtil.StrutilDate(this.loginTimeBegin+" 00:00:00","yyyy-MM-dd HH:mm:ss"));
        }
        if(isNotEmpty(this.loginTimeEnd)) {
        	sb.append(" and  o.loginTime <= ? ");
        	list.add(DateUtil.StrutilDate(this.loginTimeEnd+" 23:59:59","yyyy-MM-dd HH:mm:ss"));
        }
        if(isNotEmpty(this.platformId)) {
            sb.append(" and  exists(select * from sys_user_producers_r suurr where suurr.userId=o.id and suurr.producersId="+this.platformId+") ");
        }
        
        if(isNotEmpty(this.roleCode)) {
        	sb.append(" and  o.roleCode like ? ");
        	list.add(this.roleCode+"%");
//        	sb.append(" and  o.roleCode = ? ");
//        	list.add(this.roleCode);
        }
        
        if(isNotEmpty(this.name)) {
        	sb.append(" and  a.name like ? ");
        	list.add("%"+this.name+"%");
        }
        sb.append(" order by o.").append(sort).append(" ").append(order);
		return sb;
	}

	public List<SysUser> query() {
		sb.append("select o.*,a.* from sys_user o,qrhb_admin as a where a.id=o.id ").append(this.getSql());
		return super.query();
	}

}

