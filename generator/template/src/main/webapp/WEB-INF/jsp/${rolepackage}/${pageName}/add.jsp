<#include "/macro.include"/>
<#include "/custom.include"/> 
<#assign className = table.className>  
<#assign actionName = table.actionName>   
<#assign classNameLower = className?uncap_first>
<#assign toclassName = table.toLowerCaseClassName>
<%@page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"%>
<%@ page import="${basepackage}.entity.*"%>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/head.jsp" %>
<!-- 添加表单验证,参数为表单ID -->
<script type="text/javascript">
$(function() {
	var valid = new Validation('inputForm',{immediate:true,useTitles:true});
	$(".btn-info").click(function(){
	   if(valid.validate()){
			$("#inputForm").submit();
	   }else{
		    return false;
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
	<c:import url="/admin/bodyMenu" ></c:import>
	  <!-- =============================================== -->
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
<!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        	${table.tableAlias}添加
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="<@jspEl 'ctx'/>/${rolepackage}/${toclassName}/list">后台管理</a></li>
        <li class="active">${table.tableAlias}</li>
      </ol>
    </section>
  
<form action="<@jspEl 'ctx'/>/${rolepackage}/${toclassName}/addInput" method="POST" id="inputForm">
<input name="ctx" type="hidden" id="ctx" value="<@jspEl 'ctx'/>">
<input name="<@byId/>" type="hidden" id="<@byId/>" value="<@byIdEL/>">
<input name="token" type="hidden" id="token" value="<@jspEl 'token'/>">
    <!-- Main content -->
    <section class="content">
      <div class="row">
        <!-- left column -->
        <div class="col-md-6">
          <!-- Horizontal Form -->
          <div class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title"> ${table.tableAlias}添加</h3>
            </div>
            <!-- /.box-header -->
              <div class="box-body">
			   <#list table.columns as column>
			   <#if !column.htmlHidden>
                <div class="form-group">
                  <label class="col-sm-2 control-label"><#if !column.nullable><font color="red">*</font></#if><%=${className}.ALIAS_${column.constantName}%></label>
                  <div class="col-sm-10">
      				<#if column.isDateTimeColumn><input value="<@jspEl 'entity.${column.columnNameLower}'/>" id="${column.columnNameLower}" name="${column.columnNameLower}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="${column.validateString} form-control" /><#else><input value="<@jspEl 'entity.${column.columnNameLower}'/>" id="${column.columnNameLower}" name="${column.columnNameLower}" class="${column.validateString} form-control"  /></#if>
      				<br>
                  </div>
                </div>
			   </#if>
			   </#list>
              </div>
              <!-- /.box-body -->
              <div class="box-footer ">
                <input type="button" value="返回" class="btn btn-default pull-right"  onClick="history.back();"/>
                <input type="button" value="保存" class="btn btn-info pull-right" style="margin-right: 5px;"/>
              </div>
          </div>
          </div>
          </div>
       </section>
</form>

  </div>
  <!-- /.content-wrapper -->
	  <!-- =============================================== -->
	<jsp:include page="/WEB-INF/jsp/admin/body_footer.jsp" />
</div>
<!-- ./wrapper -->
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


