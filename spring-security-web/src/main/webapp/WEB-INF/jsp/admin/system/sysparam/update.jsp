<%@page import="com.yuewuxian.biz.entity.SysParam"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/head.jsp" %>
<script type="text/javascript">
var valid;
$(function() {
	valid = new Validation('inputForm',{immediate:true,useTitles:true});
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
        	参数编辑
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a>
        <li><a href="${ctx}/admin/system/sysparam/list">系统管理</a></li>
        <li class="active">参数管理</li>
      </ol>
    </section>
<form action="${ctx}/admin/system/sysparam/updateInput" method="POST" id="inputForm">
<input name="ctx" type="hidden" id="ctx" value="${ctx}">
<input name="id" type="hidden" id="id" value="${entity.id}">
    <!-- Main content -->
    <section class="content">
      <div class="row">
        <!-- left column -->
        <div class="col-md-6">
          <!-- Horizontal Form -->
          <div class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title"> 参数编辑</h3>
            </div>
            <!-- /.box-header -->
              <div class="box-body">
                <div class="form-group">
                  <label class="col-sm-2 control-label"><font color="red">*</font><%=SysParam.ALIAS_NAME%></label>

                  <div class="col-sm-10">
                    <input value="${entity.name}" id="name" name="name" class="required  form-control"  />
                  <br></div>
                  
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label"><font color="red">*</font><%=SysParam.ALIAS_K%></label>

                  <div class="col-sm-10">
                    <input value="${entity.k}" id="k" name="k" class="required  form-control"  />
                  <br></div>
                  
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label"><font color="red">*</font><%=SysParam.ALIAS_V%></label>

                  <div class="col-sm-10">
                   <input value="${entity.v}" id="v" name="v" class="required  form-control"  />
                  <br></div>
                  
                </div>
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
<jsp:include page="/WEB-INF/jsp/admin/body_footer.jsp" />

</div>
<!-- ./wrapper -->

</body>
</html>
