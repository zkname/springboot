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
    <a href="${ctx}/index"><b>C_CARD</b>管理平台</a>
  </div>
  <!-- /.login-logo -->
  <div class="login-box-body">
    <p class="login-box-msg"><c:if test="${login_error!=null}"><font color="red">${login_error}</font></c:if></p>

    <form id="inputForm" action="${ctx}/login" method="post">
      <div class="form-group ">
        <input type="text" class="form-control required" placeholder="用户名" name="username">
        <span class="glyphicon form-control-feedback"></span>
        <br>
      </div>
      <div class="form-group ">
        <input type="password" class="form-control required" placeholder="密码" name="password" />
        <span class="glyphicon form-control-feedback"></span>
        <br>
      </div>
      <div class="row">
      	<div class="col-xs-6">
        <input type="text" class="form-control required" placeholder="验证码" name="code" />
        </div>
        <div class="col-xs-6">
        <img id="codeImg" alt="验证码" src="${ctx}/images/code.png" />
        </div>
      </div>
      <div class="row">
        <div class="col-xs-8"><br>
        </div>
        <!-- /.col -->
        <div class="col-xs-4"><br>
          <button type="submit" class="btn btn-primary btn-block btn-flat">登录</button>
        </div>
        <!-- /.col -->
      </div>
    </form>
    <a href="${ctx}/register" class="text-center">注册</a>
  </div>
  <!-- /.login-box-body -->
</div>
<!-- /.login-box -->
</body>
</html>
