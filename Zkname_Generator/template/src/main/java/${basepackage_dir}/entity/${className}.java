<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.entity;

import ${basepackage}.entity.base.*;

public class ${className} extends Base${className}{
	
	private static final long serialVersionUID = -3451888062016242057L;
	
	<@generateCompositeIdConstructorIfis/>
}



<#macro generateCompositeIdConstructorIfis>
	public ${className}(){
	}
	
	public ${className}(
	<#list table.compositeIdColumns as column>
		${column.javaType} ${column.columnNameLower}<#if column_has_next>,</#if>
	</#list>){
		
		super(
				<#list table.compositeIdColumns as column>
				${column.columnNameLower}<#if column_has_next>,</#if>
			</#list>
			);
		
	}
</#macro>