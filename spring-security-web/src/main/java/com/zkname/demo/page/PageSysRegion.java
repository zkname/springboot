package com.zkname.demo.page;

import java.util.*;

import com.zkname.demo.entity.*;
import com.zkname.core.page.BasePage;
import com.zkname.core.util.DateUtil;

import lombok.Getter;
import lombok.Setter;

//SysRegion
public class PageSysRegion extends BasePage<SysRegion> {

	private static final long serialVersionUID = -4033946585064541028L;
	

	/** id */
	@Getter
	@Setter
	private java.lang.Integer id;
	/** 名称 */
	@Getter
	@Setter
	private java.lang.String name;
	/** 上级id */
	@Getter
	@Setter
	private java.lang.Integer parentId;
	/** 简称 */
	@Getter
	@Setter
	private java.lang.String shortName;
	/** 级别 */
	@Getter
	@Setter
	private java.lang.Integer levelType;
	/** 城市编码 */
	@Getter
	@Setter
	private java.lang.String cityCode;
	/** 邮政编码 */
	@Getter
	@Setter
	private java.lang.String zipCode;
	/** 合并名称 */
	@Getter
	@Setter
	private java.lang.String mergerName;
	/** lng */
	@Getter
	@Setter
	private java.lang.String lng;
	/** lat */
	@Getter
	@Setter
	private java.lang.String lat;
	/** pinyin */
	@Getter
	@Setter
	private java.lang.String pinyin;

	
	
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
        if(isNotEmpty(this.shortName)) {
        	sb.append(" and  o.shortName = ? ");
        	list.add(this.shortName);
        }
        if(isNotEmpty(this.levelType)) {
        	sb.append(" and  o.levelType = ? ");
        	list.add(this.levelType);
        }
        if(isNotEmpty(this.cityCode)) {
        	sb.append(" and  o.cityCode = ? ");
        	list.add(this.cityCode);
        }
        if(isNotEmpty(this.zipCode)) {
        	sb.append(" and  o.zipCode = ? ");
        	list.add(this.zipCode);
        }
        if(isNotEmpty(this.mergerName)) {
        	sb.append(" and  o.mergerName = ? ");
        	list.add(this.mergerName);
        }
        if(isNotEmpty(this.lng)) {
        	sb.append(" and  o.lng = ? ");
        	list.add(this.lng);
        }
        if(isNotEmpty(this.lat)) {
        	sb.append(" and  o.lat = ? ");
        	list.add(this.lat);
        }
        if(isNotEmpty(this.pinyin)) {
        	sb.append(" and  o.pinyin = ? ");
        	list.add(this.pinyin);
        }
		return sb;
	}

	public List<SysRegion> query() {
		sb.append("select o.* from sys_region o where 1=1 ").append(this.getSql());
		return super.query();
	}

	
}

