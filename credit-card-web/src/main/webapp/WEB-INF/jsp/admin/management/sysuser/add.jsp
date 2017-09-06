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
        	用户添加
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="#">用户管理</a></li>
        <li class="active">用户添加</li>
      </ol>
    </section>
   <form method="post" id="inputForm" action="${ctx}/admin/management/sysuser/addInput">
    <!-- Main content -->
    <section class="content">
      <div class="row">
        <!-- left column -->
        <div class="col-md-6">
          <!-- Horizontal Form -->
          <div class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title"> 用户添加</h3>
            </div>
            <!-- /.box-header -->
              <div class="box-body">
                <div class="form-group">
                  <label class="col-sm-2 control-label"><span class="notnull">*</span>用户名称</label>

                  <div class="col-sm-10">
                    <input type="text" name="username" id="username" value="" class="form-control required max-length-50 validate-ajax-${ctx}/admin/management/sysuser/checkLoginName?oldLoginName="/>
                  <br></div>
                  
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">真实姓名</label>

                  <div class="col-sm-10">
                    <input type="text" name="realName" id="realName" value="" class="form-control max-length-20"/>
                  <br></div>
                  
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label"><span class="notnull">*</span>用户密码</label>

                  <div class="col-sm-10">
                   <input type="password" name="password" value="" class="form-control required min-length-6  max-length-20"/>
                  <br></div>
                  
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label"><span class="notnull">*</span>Email</label>

                  <div class="col-sm-10">
                    <input type="text" name="email" value="" class="form-control required validate-email max-length-50"/>
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