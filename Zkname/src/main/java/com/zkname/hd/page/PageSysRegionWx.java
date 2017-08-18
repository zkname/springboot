package com.zkname.hd.page;

import java.util.*;

import com.zkname.hd.entity.*;
import com.zkname.frame.page.BasePage;
import com.zkname.frame.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

//微信区域表
public class PageSysRegionWx extends BasePage<SysRegionWx> {

	private static final long serialVersionUID = -4033946585064541028L;
	

	/** id */
	@Getter
	@Setter
	private java.lang.Integer id;
	/** 简称 */
	@Getter
	@Setter
	private java.lang.String name;
	/** 上级id */
	@Getter
	@Setter
	private java.lang.Integer parentId;
	/** 级别 */
	@Getter
	@Setter
	private java.lang.Integer levelType;

	
	
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
        if(isNotEmpty(this.parentId)) {
        	sb.append(" and  o.parentId = ? ");
        	list.add(this.parentId);
        }
        if(isNotEmpty(this.levelType)) {
        	sb.append(" and  o.levelType = ? ");
        	list.add(this.levelType);
        }
		return sb;
	}

	public List<SysRegionWx> query() {
		sb.append("select o.* from sys_region_wx o where 1=1 ").append(this.getSql());
		return super.query();
	}

	
}

