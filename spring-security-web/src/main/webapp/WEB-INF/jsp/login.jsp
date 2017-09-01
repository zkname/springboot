<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/head.jsp" %>
<script type="text/javascript">
var src;
$(function() {
	var valid = new Validation('inputForm',{immediate:true,useTitles:true});
	$("#inputForm :submit").click(function(){
		   if(valid.validate()){
			    return true;
		   }else{
			    return false;
		   }
	   });
	
	src=$("#codeImg").attr("src");
	
	$("#codeImg").click(function(){
		 $(this).attr("src",src+"?d="+Math.random());
	 });
	
});
</script>
</head>
<body class="hold-transition login-page">
<div class="login-box">
  <div class="login-logo">
    <a href="${ctx}/login"><b>DEMO</b>管理平台</a>
  </div>
  <!-- /.login-logo -->
  <div class="login-box-body">
    <p class="login-box-msg"><c:if test="${param.login_error!=null || SPRING_SECURITY_LAST_EXCEPTION!=null}"><font color="red">${param.login_error}${SPRING_SECURITY_LAST_EXCEPTION.message}</font></c:if></p>

    <form id="inputForm" action="${ctx}/admin/j_spring_security_check" method="post">
      <div class="form-group has-feedback">
        <input type="text" class="form-control" placeholder="username" name="username">
        <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" class="form-control" placeholder="Password" name="password" />
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
      <div class="input-group ">
        <input type="text" class="form-control required" placeholder="验证码" name="code" />
        <span class="input-group-addon"><img id="codeImg" alt="验证码" src="${ctx}/images/code.png" /></span>
      </div>
      <div class="row">
        <div class="col-xs-8">
        </div>
        <!-- /.col -->
        <div class="col-xs-4">
          <button type="submit" class="btn btn-primary btn-block btn-flat">登录</button>
        </div>
        <!-- /.col -->
      </div>
    </form>
  </div>
  <!-- /.login-box-body -->
</div>
<!-- /.login-box -->
</body>
</html>
