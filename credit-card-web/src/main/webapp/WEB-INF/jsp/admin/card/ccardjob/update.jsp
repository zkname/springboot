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
        	刷卡明细编辑
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="${ctx}/admin/card/ccardjob/list">刷卡管理</a></li>
        <li class="active">刷卡明细</li>
      </ol>
    </section>
    
<form action="${ctx}/admin/card/ccardjob/updateInput" method="POST" id="inputForm">
<input name="ctx" type="hidden" id="ctx" value="${ctx}">
<input name="token" type="hidden" id="token" value="${token}">
<input name="id" type="hidden" id="id" value="${entity.id}">
<input name="calendarType" type="hidden" id="calendarType" value="${param.calendarType}">

    <!-- Main content -->
    <section class="content">
      <div class="row">
        <!-- left column -->
        <div class="col-md-6">
          <!-- Horizontal Form -->
          <div class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title"> 信用卡刷卡任务编辑</h3>
            </div>
            <!-- /.box-header -->
              <div class="box-body">
                <div class="form-group">
                  <label class="col-sm-2 control-label"><font color="red">*</font>银行</label>
                  <div class="col-sm-10">
                  <div>
      				${cbank.name}
      				</div>
      				<br>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label"><font color="red">*</font>信用卡</label>
                  <div class="col-sm-10">
                  <div>
      				${ccardInfo.name}
      				</div>
      				<br>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label"><font color="red">*</font>规则</label>
                  <div class="col-sm-10">
                  	<div>
      				${ccardRange.name}
      				</div>
      				<br>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label"><font color="red">*</font><%=CcardJob.ALIAS_JOB_DATE%></label>
                  <div class="col-sm-10">
                  <div>
      				<fmt:formatDate value="${entity.jobDate}" pattern="yyyy-MM-dd"/></div>
      				<br>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label"><%=CcardJob.ALIAS_MONEY%></label>
                  <div class="col-sm-10">
      				<input value="${entity.money}" id="money" name="money" class="required validate-number max-value-2147483647 form-control"  />
      				<br>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label"><%=CcardJob.ALIAS_FEE%></label>
                  <div class="col-sm-10">
      				<input value="${entity.fee}" id="fee" name="fee" class="required validate-number  form-control"  />
      				<br>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">状态</label>
                  <div class="col-sm-10">
		                <select name="status" id="status" class="form-control">
			                <option value="0" <c:if test="${entity.status!=null && entity.status=='0'}">selected="selected"</c:if>>未刷卡</option>
			                <option value="1" <c:if test="${entity.status!=null && entity.status=='1'}">selected="selected"</c:if>>已刷卡</option>
			              </select>
      				
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