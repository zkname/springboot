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
	
	
	$("#backId").click(function(){
		window.location.href="${ctx}/";
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

    <form id="inputForm" action="${ctx}/register" method="post">
      <div class="form-group ">
        <input type="text" class="form-control required  min-length-3 max-length-16  validate-ajax-${ctx}/checkLoginName?oldLoginName=" placeholder="用户名" name="username">
        <span class="glyphicon form-control-feedback"></span>
        <br>
      </div>
      <div class="form-group ">
        <input type="password" class="form-control required min-length-6 max-length-32" placeholder="密码" name="password" />
        <span class="glyphicon form-control-feedback"></span>
        <br>
      </div>
      <div class="form-group ">
        <input type="text" class="form-control required validate-email  validate-ajax-${ctx}/checkEmail?oldEmail=" placeholder="Email" name="email">
        <span class="glyphicon form-control-feedback"></span>
        <br>
      </div>
      <div class="form-group ">
        <input type="text" class="form-control required " placeholder="邀请码" name="invitationCode" />
        <span class="glyphicon form-control-feedback"></span>
        <br>
      </div>
      <div class="row">
      	<div class="col-xs-6">
        <input type="text" class="form-control required" placeholder="验证码" name="code" />
        <br>
        </div>
        <div class="col-xs-6">
        <img id="codeImg" alt="验证码" src="${ctx}/images/code.png" />
        </div>
      </div>
      <div class="row">
        <div class="col-xs-4">
        </div>
        <!-- /.col -->
        <div class="col-xs-4">
        	<button type="submit" class="btn btn-primary btn-block btn-flat">注册</button>
        </div>
        <div class="col-xs-4">
           <button type="button" class="btn btn-primary btn-block btn-flat" id="backId">返回</button>
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
