package com.zkname.credit.card.page;

import java.util.*;

import com.zkname.credit.card.entity.*;
import com.zkname.core.page.BasePage;
import com.zkname.core.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

//邀请码
public class PageCinvitationCode extends BasePage<CinvitationCode> {

	private static final long serialVersionUID = -4033946585064541028L;
	

	/** id */
	@Getter
	@Setter
	private java.lang.Long id;
	/** 批次id */
	@Getter
	@Setter
	private java.lang.Long invitationCodeBatch;
	/** 邀请码InvitationCode */
	@Getter
	@Setter
	private java.lang.String invitationCode;
	/** 用户id */
	@Getter
	@Setter
	private java.lang.Long userId;
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
        if(isNotEmpty(this.invitationCodeBatch)) {
        	sb.append(" and  o.invitationCodeBatch = ? ");
        	list.add(this.invitationCodeBatch);
        }
        if(isNotEmpty(this.invitationCode)) {
        	sb.append(" and  o.invitationCode = ? ");
        	list.add(this.invitationCode);
        }
        if(isNotEmpty(this.userId)) {
        	sb.append(" and  o.userId = ? ");
        	list.add(this.userId);
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

	public List<CinvitationCode> query() {
		sb.append("select o.* from c_invitation_code o where 1=1 ").append(this.getSql());
		return super.query();
	}

	
}

