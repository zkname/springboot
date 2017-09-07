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
        	规则信息添加
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="${ctx}/admin/card/ccardrange/list">信息管理</a></li>
        <li class="active">规则信息</li>
      </ol>
    </section>
  
<form action="${ctx}/admin/card/ccardrange/addInput" method="POST" id="inputForm">
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
              <h3 class="box-title"> 规则信息添加</h3>
            </div>
            <!-- /.box-header -->
              <div class="box-body">
                <div class="form-group">
                  <label class="col-sm-2 control-label"><font color="red">*</font><%=CcardRange.ALIAS_NAME%></label>
                  <div class="col-sm-10">
      				<input value="${entity.name}" id="name" name="name" class="required  form-control"  />
      				<br>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label"><font color="red">*</font><%=CcardRange.ALIAS_MONEY_PROP_START_VALUE%></label>
                  <div class="col-sm-10">
                  	<div class="input-group">
      				<input value="35" id="moneyPropStartValue" name="moneyPropStartValue" class=" required validate-integer min-value-10 max-value-80 form-control"  />
      				<span class="input-group-addon">%</span>
      				</div>
      				<br>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label"><font color="red">*</font><%=CcardRange.ALIAS_MONEY_PROP_END_VALUE%></label>
                  <div class="col-sm-10">
                  	<div class="input-group">
      				<input value="50" id="moneyPropEndValue" name="moneyPropEndValue" class="required validate-integer min-value-10 max-value-80 form-control"  />
      				<span class="input-group-addon">%</span>
      				</div>
      				<br>
                  </div>
                </div> 
                <div class="form-group">
                  <label class="col-sm-2 control-label"><font color="red">*</font><%=CcardRange.ALIAS_FREQUENCY_PROP_START_VALUE%></label>
                  <div class="col-sm-10">
      				<input value="22" id="frequencyPropStartValue" name="frequencyPropStartValue" class="required validate-integer min-value-5 max-value-30 form-control"  />
      				<br>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label"><font color="red">*</font><%=CcardRange.ALIAS_FREQUENCY_PROP_ENDT_VALUE%></label>
                  <div class="col-sm-10">
      				<input value="35" id="frequencyPropEndtValue" name="frequencyPropEndtValue" class="required validate-integer min-value-5 max-value-35 form-control"  />
      				<br>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label"><font color="red">*</font><%=CcardRange.ALIAS_DAY%></label>
                  <div class="col-sm-10">
      				<input value="25" id="day" name="day" class="required validate-integer min-value-5 max-value-31 form-control"  />
      				<br>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label"><%=CcardRange.ALIAS_REMARKS%></label>
                  <div class="col-sm-10">
      				<textarea rows="15" cols="80" id="remarks" name="remarks" class=" form-control" >${entity.remarks}</textarea>
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

