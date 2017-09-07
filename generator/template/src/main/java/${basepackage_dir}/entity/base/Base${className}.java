<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.entity.base;

import javax.persistence.*;
import org.apache.commons.lang3.builder.*;
import com.zkname.core.entity.IdEntity;
import com.zkname.core.util.DateUtil;
import lombok.Data;
import java.util.*;

<#include "/java_imports.include">

@Data
@Entity
@Table(name = "${table.sqlName}")
public class Base${className} extends IdEntity<@idkey/>{
	
	//alias
	public static final String TABLE_ALIAS = "${table.tableAlias}";
	<#list table.columns as column>
	public static final String ALIAS_${column.constantName} = "${column.columnAlias}";
	</#list>
	
	<@generateFields/>
	<@generateCompositeIdConstructorIfis/>
	<@generateNotPkProperties/>
	<@newIdkey/>
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Base${className} == false) return false;
		if(this == obj) return true;
		Base${className} other = (Base${className})obj;
		return new EqualsBuilder().append(getId(),other.getId()).isEquals();
	}
}

<#macro generateFields>
	//columns START
	<#list table.columns as column>
	<#if column.pk>
	/**
	 * ${column.columnAlias}
	 */
	@Id
	@Column(name = "${column.sqlName}")
	<#if column.auto>@GeneratedValue(strategy=GenerationType.AUTO)</#if>
	private ${column.javaType} ${column.columnNameLower};
	<#else>
	/**
	 * ${column.columnAlias}
	 */
	@Column(name = "${column.sqlName}")
	private ${column.javaType} ${column.columnNameLower};
	</#if>
	</#list>
	//columns END

</#macro>

<#macro generateCompositeIdConstructorIfis>
	public Base${className}(){
	}
	
	public Base${className}(
	<#list table.compositeIdColumns as column>
		${column.javaType} ${column.columnNameLower}<#if column_has_next>,</#if>
	</#list>		
	){
	<#list table.compositeIdColumns as column>
		<#if column.pk>
		this.${column.columnNameLower} = ${column.columnNameLower};
		</#if>
	</#list>	
	}
</#macro>

<#macro generateNotPkProperties>
	<#list table.columns as column>
		<#if !column.pk>
			<#if column.isDateTimeColumn>
	@Transient
	public String get${column.columnName}String() {
		return DateUtil.Date2Str(get${column.columnName}());
	}
	public void set${column.columnName}String(String value) {
		set${column.columnName}(DateUtil.Str2Date(value));
	}
	</#if>
		</#if>
	</#list>
</#macro>


<#macro idkey>
<#local idjavaType="" />
<#list table.columns as column>
<#if column.pk>
<#if idjavaType=="">
<#local idjavaType='true'/>
<#else>
<#local idjavaType="false"/>
</#if>
</#if>
</#list>
<#if idjavaType="false">
<Object>
<#else>
<${table.idColumn.javaType}>
</#if>
</#macro>


<#macro newIdkey>
<#local idjavaType="" />
<#assign columnNames=""/> 
<#list table.columns as column>
<#if column.pk>
<#if columnNames=="">
<#assign columnNames=columnNames+column.columnNameLower /> 
<#else>
<#assign columnNames=columnNames+","+column.columnNameLower /> 
</#if>
<#if idjavaType=="">
<#local idjavaType='true'/>
<#else>
<#local idjavaType="false"/>
</#if>
</#if>
</#list>
<#if idjavaType="false">
	public Object getId() {
		return new Object[]{${columnNames}};
	}
	
	public void setId(Object id) {
	}
</#if>
</#macro>
