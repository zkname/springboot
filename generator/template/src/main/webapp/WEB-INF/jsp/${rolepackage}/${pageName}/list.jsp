<#include "/macro.include"/>
<#include "/custom.include"/> 
<#assign className = table.className>  
<#assign actionName = table.actionName>   
<#assign classNameLower = className?uncap_first>
<#assign toclassName = table.toLowerCaseClassName>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="${basepackage}.entity.*"%>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/head.jsp" %>
<script type="text/javascript">
$(function() {
	
	//全选不选结合
	$("#checkbox").click(function(){
		 $(":input[id=ids]").attr("checked",this.checked);
	});
	
	
	//删除
	$("#deleteId").click(function(){
		  if($(":input:checkbox:checked[id=ids]").length<1){
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
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
	<jsp:include page="/WEB-INF/jsp/admin/body_top.jsp" />
	  <!-- =============================================== -->
	<c:import url="/admin/bodyMenu.do" ></c:import>
	  <!-- =============================================== -->
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">

<!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        	${table.tableAlias}
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="<@jspEl 'ctx'/>/${rolepackage}/${toclassName}/list.do">后台管理</a></li>
        <li class="active">${table.tableAlias}</li>
      </ol>
    </section>
	<form id="form" action="<@jspEl 'ctx'/>/${rolepackage}/${toclassName}/list.do" method="get">
		<input type="hidden" name="pageNo" id="pageNo" value="<@jspEl 'page.pageNo'/>"/>
		<input name="token" type="hidden" id="token" value="<@jspEl 'token'/>" />
	    <!-- Main content -->
	    <section class="content">
	      <div class="row">
	        <div class="col-xs-12">
	          <div class="box">
	            <div class="box-header with-border">
	              <h3 class="box-title">搜索</h3>
	            </div>
	            <div class="box-body">
			<div class="row">
          <#list table.notPkColumns?chunk(4) as row>
          <#list row as column>
            <#if !column.htmlHidden>
            
            <div class="col-xs-6">
            	<#if column.isDateTimeColumn>
            	<input value="<@jspEl 'page.${column.columnNameLower}Begin'/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="${column.columnNameLower}Begin" name="${column.columnNameLower}Begin" class="form-control Wdate"  placeholder="<%=${className}.ALIAS_${column.constantName}%>[BEGIN]"/>
            	<#rt>
            	<input value="<@jspEl 'page.${column.columnNameLower}End'/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="${column.columnNameLower}End" name="${column.columnNameLower}End" class="form-control Wdate" placeholder="<%=${className}.ALIAS_${column.constantName}%>[END]"/>
            	<#rt>
            	<br>
            	<#else>
            	<input value="<@jspEl 'page.${column.columnNameLower}'/>" id="${column.columnNameLower}" name="${column.columnNameLower}" maxlength="${column.size}"  class="form-control"  placeholder="<%=${className}.ALIAS_${column.constantName}%>"/>
            	<br>
            	<#rt>
            	</#if>
            </div>
            </#if>
            </#list></#list>
            <div class="col-xs-6">
            	<input type="submit" value="搜索" class="btn btn-primary btn-block btn-flat"/>
            </div>
	         </div>
	            <hr style="border:none;border-top:1px dotted #185598;" />
	            <div style="margin-left:10px">
					 <a href="javascript:void(0);" id="deleteId">删除</a> | <a href="<@jspEl 'ctx'/>/${rolepackage}/${toclassName}/add.do" > 添加</a>
	            </div>
	            <!-- /.box-header -->
	          <div class="box-body table-responsive no-padding">
	              <table class="table table-hover">
				    <tr>
				      <th><input type="checkbox" id="checkbox" value="checkbox"></th>
				      <#list table.columns as column>
				      <#if !column.htmlHidden>
				      <th><%=${className}.ALIAS_${column.constantName}%></th>
				      </#if>
				      </#list>
				      <th>操作</th>
				    </tr>
					<c:forEach var="item" items="<@jspEl 'page.result'/>">
				    <tr>
				      <td><input type="checkbox" name="ids" id="ids" value="<@generateId/>"></td>
				      <#list table.columns as column>
				      <#if !column.htmlHidden>
				      <td><#rt>
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
				      <td><a href="<@jspEl 'ctx'/>/${rolepackage}/${toclassName}/update/<@generateId/>.do">修改</a> </td>
				    </tr>
					</c:forEach>
			        </table>
			        <%@ include file="/WEB-INF/jsp/include/page.jsp" %>
	            </div>
	            <!-- /.box-body -->
	          </div>
	        </div>
	        <!-- /.col -->
	      </div>
	    	</div>
	      <!-- /.row -->
	    </section>
    </form>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
	  <!-- =============================================== -->
	<jsp:include page="/WEB-INF/jsp/admin/body_footer.jsp" />
</div>
<!-- ./wrapper -->
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