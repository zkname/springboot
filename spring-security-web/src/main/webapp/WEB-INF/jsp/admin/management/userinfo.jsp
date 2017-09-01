<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/head.jsp" %>
<script type="text/javascript">
$(function() {
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
        	用户信息
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li class="active">用户信息</li>
      </ol>
    </section>
   <form method="post" id="inputForm" name="inputForm" action="${ctx}/admin/management/userinfoUpdate">
    <!-- Main content -->
    <section class="content">
      <div class="row">
        <!-- left column -->
        <div class="col-md-6">
          <!-- Horizontal Form -->
          <div class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title"> 用户信息修改</h3>
            </div>
            <!-- /.box-header -->
              <div class="box-body">
                <div class="form-group">
                  <label class="col-sm-2 control-label">用户名称</label>

                  <div class="col-sm-10">
                    <input type="text" class="form-control" value="<security:authentication property="principal.username"/>" disabled="disabled">
                  <br></div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">真实姓名</label>

                  <div class="col-sm-10">
                    <input type="text" class="form-control max-length-20" id="realName" name="realName" value="<security:authentication property="principal.realName"/>" >
                  <br></div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">老密码</label>

                  <div class="col-sm-10">
                    <input type="password" class="form-control required min-length-6  max-length-20 validate-ajax-${ctx}/admin/management/userpassword" id="oldPassword" name="oldPassword"  >
                  <br></div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">新密码</label>

                  <div class="col-sm-10">
                    <input type="password" class="form-control min-length-6  max-length-20" id="password" name="password" >
                  <br></div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">Email</label>

                  <div class="col-sm-10">
                    <input type="text" class="form-control required validate-email max-length-50" id="email" name="email" value="<security:authentication property="principal.email"/>">
                  <br></div>
                </div>
              </div>
              <!-- /.box-body -->
              <div class="box-footer">
                <button type="submit" class="btn btn-info pull-right">提交</button>
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
