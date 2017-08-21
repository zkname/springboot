<#include "/macro.include"/>
<#include "/custom.include"/> 
<#assign className = table.className>  
<#assign actionName = table.actionName>   
<#assign classNameLower = className?uncap_first>
<#assign toclassName = table.toLowerCaseClassName>
<%@page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"%>
<%@ page import="${basepackage}.entity.*"%>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/jsp/include/head.jsp" %>
<!-- 添加表单验证,参数为表单ID -->
<script type="text/javascript">
$(document).ready(function(){
	var valid = new Validation('inputForm',{immediate:true,useTitles:true});
	$("#inputForm :submit").click(function(){
	   if(valid.validate()){
			return true;
	   }else{
		    return false;
	   }
   });
});
</script>
</head>
<body class="page input">
<form action="<@jspEl 'ctx'/>/${rolepackage}/${toclassName}/addInput.do" method="POST" id="inputForm">
<input name="ctx" type="hidden" id="ctx" value="<@jspEl 'ctx'/>">
<input name="<@byId/>" type="hidden" id="<@byId/>" value="<@byIdEL/>">
<input name="token" type="hidden" id="token" value="<@jspEl 'token'/>">
<div class="Mpm">当前位置： <a href="javascript:void(0)">后台管理</a>&gt; <a href="javascript:void(0)">${table.tableAlias}</a></div>
<table border="0" cellpadding="4" align="center" cellspacing="1" class="editTab" width="700">
    <tr>
    <th colspan="2">添加</th>
  </tr>
   <#list table.columns as column>
   <#if !column.htmlHidden>
   <tr>
     <td ><#if !column.nullable><font color="red">*</font></#if><%=${className}.ALIAS_${column.constantName}%>: </td>
     <td >
     <#if column.isDateTimeColumn><input value="<@jspEl 'entity.${column.columnNameLower}'/>" id="${column.columnNameLower}" name="${column.columnNameLower}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="${column.validateString} styleInput" /><#else><input value="<@jspEl 'entity.${column.columnNameLower}'/>" id="${column.columnNameLower}" name="${column.columnNameLower}" class="${column.validateString} styleInput"  /></#if>
     </td>
   </tr>
   </#if>
   </#list>
   <tr>
      <td class="btn" colspan="2" align="center">
      <input type="submit" class="subbtn" value="确 定"/> &nbsp;&nbsp;&nbsp; <input type="button" class="subbtn" value="返 回" onClick="history.back();"/> </td>
    </tr>
  </table>
</form>
</body>
</html>

<#macro byId>
<#list table.columns as column><#t>
<#if column.pk>
${column.sqlName}</#if><#t>
</#list>
</#macro>

<#macro byIdEL>
<#list table.columns as column><#t>
<#if column.pk>
${r"${param."}${column.sqlName}}</#if><#t>
</#list>
</#macro>


