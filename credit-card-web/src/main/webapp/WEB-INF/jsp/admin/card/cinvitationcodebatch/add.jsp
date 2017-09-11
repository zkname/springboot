<%@page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.zkname.credit.card.entity.*"%>
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
        	批次添加
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="${ctx}/admin/card/cinvitationcodebatch/list">后台管理</a></li>
        <li class="active">批次</li>
      </ol>
    </section>
  
<form action="${ctx}/admin/card/cinvitationcodebatch/addInput" method="POST" id="inputForm">
<input name="ctx" type="hidden" id="ctx" value="${ctx}">
<input name="id" type="hidden" id="id" value="${param.id}">
<input name="token" type="hidden" id="token" value="${token}">
    <!-- Main content -->
    <section class="content">
      <div class="row">
        <!-- left column -->
        <div class="col-md-6">
          <!-- Horizontal Form -->
          <div class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title"> 批次添加</h3>
            </div>
            <!-- /.box-header -->
              <div class="box-body">
                <div class="form-group">
                  <label class="col-sm-2 control-label"><%=CinvitationCodeBatch.ALIAS_NAME%></label>
                  <div class="col-sm-10">
      				<input value="${entity.name}" id="name" name="name" class="required form-control"  />
      				<br>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label"><font color="red">*</font><%=CinvitationCodeBatch.ALIAS_TOTAL%></label>
                  <div class="col-sm-10">
      				<input value="${entity.total}" id="total" name="total" class="required validate-integer max-value-2147483647 form-control"  />
      				<br>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label"><%=CinvitationCodeBatch.ALIAS_REMARK%></label>
                  <div class="col-sm-10">
      				<textarea rows="5" cols="10" id="remark" name="remark" class=" form-control" >${entity.remark}</textarea>
      				<br>
                  </div>
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
	  <!-- =============================================== -->
	<jsp:include page="/WEB-INF/jsp/admin/body_footer.jsp" />
</div>
<!-- ./wrapper -->
</body>
</html>

