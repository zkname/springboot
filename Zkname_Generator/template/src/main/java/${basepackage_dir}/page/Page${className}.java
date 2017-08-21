<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.page;

import java.util.*;

import ${basepackage}.entity.*;
import com.yuewuxian.frame.page.BasePage;
import com.yuewuxian.frame.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

//${table.tableAlias}
public class Page${className} extends BasePage<${className}> {

	private static final long serialVersionUID = -4033946585064541028L;
	
	<@generateFields/>
	
	
	private StringBuffer getSql() {
		StringBuffer sb=new StringBuffer();
        <#list table.columns as column>
        <#if column.isDateTimeColumn>
        if(isNotEmpty(this.${column.columnNameLower}Begin)) {
        	sb.append(" and  o.${column.sqlName} >= ? ");
        	list.add(DateUtil.StrutilDate(this.${column.columnNameLower}Begin+" 00:00:00","yyyy-MM-dd HH:mm:ss"));
        }
        if(isNotEmpty(this.${column.columnNameLower}End)) {
        	sb.append(" and  o.${column.sqlName} <= ? ");
        	list.add(DateUtil.StrutilDate(this.${column.columnNameLower}End+" 23:59:59","yyyy-MM-dd HH:mm:ss"));
        }
        <#else>
        if(isNotEmpty(this.${column.columnNameLower})) {
        	sb.append(" and  o.${column.sqlName} = ? ");
        	list.add(this.${column.columnNameLower});
        }
        </#if>
        </#list>
		return sb;
	}

	public List<${className}> query() {
		sb.append("select o.* from ${table.sqlName} o where 1=1 ").append(this.getSql());
		return super.query();
	}

	
}

<#macro generateFields>

<#if table.compositeId>
	@Getter
	@Setter
	private ${className}Id id;
	<#list table.columns as column>
		<#if !column.pk>
	@Getter
	@Setter
	private ${column.javaType} ${column.columnNameLower};
		</#if>
	</#list>
<#else>
	//columns START
	<#list table.columns as column>
	@Getter
	@Setter
	private ${column.javaType} ${column.columnNameLower};
	</#list>
	//columns END
</#if>

</#macro>



<#macro generateNotPkProperties>
<#list table.columns as column>
	<#if column.isDateTimeColumn>
	
	public String get${column.columnName}Begin() {
		return this.${column.columnNameLower}Begin;
	}
	public void set${column.columnName}Begin(String value) {
		this.${column.columnNameLower}Begin = value;
	}
	public String get${column.columnName}End() {
		return this.${column.columnNameLower}End;
	}
	public void set${column.columnName}End(String value) {
		this.${column.columnNameLower}End = value;
	}
	<#elseif column.columnNameLower!="id">

	public ${column.javaType} get${column.columnName}() {
		return this.${column.columnNameLower};
	}
	
	public void set${column.columnName}(${column.javaType} value) {
		this.${column.columnNameLower} = value;
	}
	</#if>
</#list>
</#macro>



<#macro generateFields>

<#list table.columns as column>
	/** ${column.columnAlias} */
<#if column.isDateTimeColumn && !column.contains("begin,start,end")>
	@Getter
	@Setter
	private java.lang.String ${column.columnNameLower}Begin;
	@Getter
	@Setter
	private java.lang.String ${column.columnNameLower}End;
<#else>
	@Getter
	@Setter
	private ${column.javaType} ${column.columnNameLower};
</#if>
</#list>

</#macro>