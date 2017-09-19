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
        	信用卡信息编辑
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="${ctx}/admin/card/ccardinfo/list">信息管理</a></li>
        <li class="active">信用卡信息</li>
      </ol>
    </section>
    
<form action="${ctx}/admin/card/ccardinfo/updateInput" method="POST" id="inputForm">
<input name="ctx" type="hidden" id="ctx" value="${ctx}">
<input name="token" type="hidden" id="token" value="${token}">
<input name="id" type="hidden" id="id" value="${entity.id}">

    <!-- Main content -->
    <section class="content">
      <div class="row">
        <!-- left column -->
        <div class="col-md-6">
          <!-- Horizontal Form -->
          <div class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title"> 信用卡信息编辑</h3>
            </div>
            <!-- /.box-header -->
              <div class="box-body">
                <div class="form-group">
                  <label class="col-sm-2 control-label"><font color="red">*</font>银行信息</label>
                  <div class="col-sm-10">
	                <select name="bankId" id="bankId" class="validate-selection form-control">
		                <option value=" ">请选择</option>
		                <c:forEach var="obj" items="${cbanks}">
		                	<option value="${obj.id}" ${entity.bankId!=null && entity.bankId==obj.id?'selected':''}>${obj.name}</option>
		                </c:forEach>
	                </select>
      				<br>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label"><font color="red">*</font>规则信息</label>
                  <div class="col-sm-10">
		                <select name="cardRangeId" id="cardRangeId" class="validate-selection form-control">
			                <option value=" ">规则信息</option>
			                <c:forEach var="obj" items="${ccardRanges}">
			                	<option value="${obj.id}" ${entity.cardRangeId!=null && entity.cardRangeId==obj.id?'selected':''}>${obj.name}</option>
			                </c:forEach>
		                </select>
      				<br>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label"><font color="red">*</font><%=CcardInfo.ALIAS_NAME%></label>
                  <div class="col-sm-10">
      				<input value="${entity.name}" id="name" name="name" class="required  form-control"  />
      				<br>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label"><font color="red">*</font><%=CcardInfo.ALIAS_MONEY%></label>
                  <div class="col-sm-10">
      				<input value="${entity.money}" id="money" name="money" class="required validate-integer max-value-2147483647 form-control"  />
      				<br>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label"><font color="red">*</font>年费类型</label>
                  <div class="col-sm-10">
	                <select name="annualFeeType" id="annualFeeType" class="validate-selection form-control">
		                <option value=" ">请选择</option>
		                <option value="1" ${entity.annualFeeType=='1'?'selected':''}>强制收费</option>
		                <option value="2" ${entity.annualFeeType=='2'?'selected':''}>刷卡次数</option>
		                <option value="3" ${entity.annualFeeType=='3'?'selected':''}>免年费</option>
		              </select>
      				<br>
      				
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label"><font color="red">*</font><%=CcardInfo.ALIAS_ANNUAL_FEE%></label>
                  <div class="col-sm-10">
      				<input value="${entity.annualFee}" id="annualFee" name="annualFee" class="required validate-integer max-value-2147483647 form-control"  />
      				<br>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label"><font color="red">*</font><%=CcardInfo.ALIAS_CARD_NUM%></label>
                  <div class="col-sm-10">
      				<input value="${entity.cardNum}" id="cardNum" name="cardNum" class="required validate-integer max-value-2147483647 form-control"  />
      				<br>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label"><font color="red">*</font><%=CcardInfo.ALIAS_BILL_DATE%></label>
                  <div class="col-sm-10">
      				<input value="<fmt:formatNumber value="${entity.billDate}" pattern="#00" />" id="billDate" name="billDate" onclick="WdatePicker({dateFmt:'dd'})" class="required validate-digits min-length-2 max-length-2 form-control"  />
      				<br>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label"><%=CcardInfo.ALIAS_NEXT_UP%></label>
                  <div class="col-sm-10">
      				<input value="<fmt:formatDate value="${entity.nextUp}" pattern="yyyy-MM-dd"/>" id="nextUp" name="nextUp" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class=" form-control" />
      				<br>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label"><%=CcardInfo.ALIAS_REMARKS%></label>
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