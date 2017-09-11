package com.zkname.credit.card.page;

import java.util.*;

import com.zkname.credit.card.entity.*;
import com.zkname.core.page.BasePage;
import com.zkname.core.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

//批次
public class PageCinvitationCodeBatch extends BasePage<CinvitationCodeBatch> {

	private static final long serialVersionUID = -4033946585064541028L;
	

	/** id */
	@Getter
	@Setter
	private java.lang.Long id;
	/** 批次名称 */
	@Getter
	@Setter
	private java.lang.String name;
	/** 总数 */
	@Getter
	@Setter
	private java.lang.Integer total;
	/** 1锁定、0正常 */
	@Getter
	@Setter
	private java.lang.String updateLock;
	/** 是否生成1、已生成，0未生成 */
	@Getter
	@Setter
	private java.lang.String isGenerate;
	/** 备注 */
	@Getter
	@Setter
	private java.lang.String remark;
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
        	sb.append(" and  o.name = ? ");
        	list.add(this.name);
        }
        if(isNotEmpty(this.total)) {
        	sb.append(" and  o.total = ? ");
        	list.add(this.total);
        }
        if(isNotEmpty(this.updateLock)) {
        	sb.append(" and  o.updateLock = ? ");
        	list.add(this.updateLock);
        }
        if(isNotEmpty(this.isGenerate)) {
        	sb.append(" and  o.isGenerate = ? ");
        	list.add(this.isGenerate);
        }
        if(isNotEmpty(this.remark)) {
        	sb.append(" and  o.remark = ? ");
        	list.add(this.remark);
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

	public List<CinvitationCodeBatch> query() {
		sb.append("select o.* from c_invitation_code_batch o where 1=1 ").append(this.getSql());
		return super.query();
	}

	
}

