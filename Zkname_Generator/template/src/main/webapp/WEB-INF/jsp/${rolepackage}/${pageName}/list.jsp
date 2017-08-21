<#include "/macro.include"/>
<#include "/custom.include"/> 
<#assign className = table.className>  
<#assign actionName = table.actionName>   
<#assign classNameLower = className?uncap_first>
<#assign toclassName = table.toLowerCaseClassName>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="${basepackage}.entity.*"%>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<%@ include file="/WEB-INF/jsp/include/head.jsp" %>
<script type="text/javascript">
jQuery(function($) {
	
	//全选不选结合
	$("#checkbox").click(function(){
		 $(":input[id=ids]").attr("checked",this.checked);
	});
	
	
	//删除
	$("#deleteId").click(function(){
		  if($(":input:checkbox:checked[id=ids]").size()<1){
			  alert("请选择最少一条记录！");
			  return null;
		  }else if(window.confirm("确认是否删除？")){
			  //多选就发布
			  $("#form").attr("action","<@jspEl 'ctx'/>/${rolepackage}/${toclassName}/delete.do");
			  $("#form").submit();
		  }
	});
 });
</script>
</head>
<body class="page list">
<form id="form" action="<@jspEl 'ctx'/>/${rolepackage}/${toclassName}/list.do" method="get">
<input type="hidden" name="pageNo" id="pageNo" value="<@jspEl 'page.pageNo'/>"/>
<input name="token" type="hidden" id="token" value="<@jspEl 'token'/>">
<div class="Mpm">当前位置： <a href="javascript:void(0)">后台管理</a>&gt; <a href="javascript:void(0)">${table.tableAlias}</a></div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" style="border-bottom: 1px solid #7996B0; margin-bottom:6px">
    <tr>
      <td height="31" >
		<table border="0" cellspacing="0" cellpadding="5">
          <#list table.notPkColumns?chunk(4) as row>
          <tr><#list row as column>
            <#if !column.htmlHidden>
            <td><%=${className}.ALIAS_${column.constantName}%></td>
            <td><#if column.isDateTimeColumn><input value="<@jspEl 'page.${column.columnNameLower}Begin'/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="${column.columnNameLower}Begin" name="${column.columnNameLower}Begin" class="Wdate"/><#rt><input value="<@jspEl 'page.${column.columnNameLower}End'/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="${column.columnNameLower}End" name="${column.columnNameLower}End" class="Wdate"/><#rt><#else><input value="<@jspEl 'page.${column.columnNameLower}'/>" id="${column.columnNameLower}" name="${column.columnNameLower}" maxlength="${column.size}"  /><#rt></#if></td>
            </#if>
            </#list></tr></#list>
        </table>
        <input type="submit" value="搜索" class="top-search-btn"/>
		</td>
    </tr>
  </table>

  <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td height="31" bgcolor="#b7daf9" class="STYLE1" style="font-weight: bold;"> 　
      <a href="javascript:void(0);" id="deleteId">删除</a> | <a href="<@jspEl 'ctx'/>/${rolepackage}/${toclassName}/add.do" > 添加</a>
        </td>
    </tr>
  </table>
  <table width="100%" cellpadding="0" cellspacing="1" class="Tablist">
    <tr class="Tit">
      <th><input type="checkbox" id="checkbox" value="checkbox"></th>
      <#list table.columns as column>
      <#if !column.htmlHidden>
      <th><%=${className}.ALIAS_${column.constantName}%></th>
      </#if>
      </#list>
      <th>操作</th>
    </tr>
<c:forEach var="item" items="<@jspEl 'page.result'/>">
    <tr height="27"  valign="middle" >
      <td ><input type="checkbox" name="ids" id="ids" value="<@generateId/>"></td>
      <#list table.columns as column>
      <#if !column.htmlHidden>
      <td ><#rt>
        <#compress>
        <#if column.isDateTimeColumn>
        <#t><fmt:formatDate value="<@jspEl "item."+column.columnNameLower/>" pattern="yyyy-MM-dd HH:mm:ss"/>
        <#else>
        <#t><c:out value='<@jspEl "item."+column.columnNameLower/>'/>
        </#if>
        </#compress>
        <#lt></td>
      </#if>
      </#list>
      <td ><a href="<@jspEl 'ctx'/>/${rolepackage}/${toclassName}/update/<@generateId/>.do">修改</a> </td>
    </tr>
</c:forEach>
  </table>
  <%@ include file="/WEB-INF/jsp/include/page.jsp"%>
</form>
</body>
</html>
<#macro generateIdQueryString>
	<#if table.compositeId>
		<#assign itemPrefix = 'item.id.'>
	<#else>
		<#assign itemPrefix = 'item.'>
	</#if>
	<#compress>
		<#list table.compositeIdColumns as column>
			<#t>id=
			<@jspEl itemPrefix + column.columnNameLower/>
			&
		</#list>				
	</#compress>
</#macro>

<#macro generateId>
	<#if table.compositeId>
		<#assign itemPrefix = 'item.id.'>
	<#else>
		<#assign itemPrefix = 'item.'>
	</#if>
	<#compress>
		<#list table.compositeIdColumns as column>
			<#t><@jspEl itemPrefix + column.columnNameLower/>
		</#list>				
	</#compress>
</#macro>