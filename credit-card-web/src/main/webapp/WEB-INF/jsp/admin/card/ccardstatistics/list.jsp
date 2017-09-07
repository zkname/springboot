<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.zkname.credit.card.entity.*"%>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/head.jsp" %>
<script type="text/javascript">
$(function() {
	
	//全选不选结合
	$("#checkbox").click(function(){
		 $(":input[id=ids]").attr("checked",this.checked);
	});
	
	
	//删除
	$("#deleteId").click(function(){
		  if($(":input:checkbox:checked[id=ids]").length<1){
			  alert("请选择最少一条记录！");
			  return null;
		  }else if(window.confirm("确认是否删除？")){
			  //多选就发布
			  $("#form").attr("action","${ctx}/admin/card/ccardstatistics/delete");
			  $("#form").submit();
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
        	统计信息
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="${ctx}/admin/card/ccardstatistics/list">统计管理</a></li>
        <li class="active">统计信息</li>
      </ol>
    </section>
	<form id="form" action="${ctx}/admin/card/ccardstatistics/list" method="get">
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}"/>
		<input name="token" type="hidden" id="token" value="${token}" />
	    <!-- Main content -->
	    <section class="content">
	      <div class="row">
	        <div class="col-xs-12">
	          <div class="box">
	            <div class="box-header with-border">
	              <h3 class="box-title">搜索</h3>
	            </div>
	            <div class="box-body">
			<div class="row">
            
            <div class="col-xs-6">
					<input value="${page.date}" id="date" name="date" onclick="WdatePicker({dateFmt:'yyyy-MM'})" class="form-control"  />
            	<br>
            </div>
            <div class="col-xs-6">
            	<input type="submit" value="搜索" class="btn btn-primary btn-block btn-flat"/>
            </div>
	         </div>
	            <hr style="border:none;border-top:1px dotted #185598;" />
	            <!-- /.box-header -->
	          <div class="box-body table-responsive no-padding"> 
	              <table class="table table-hover">
				    <tr>
				      <th><input type="checkbox" id="checkbox" value="checkbox"></th>
				      <th>ID</th>
				      <th>银行名称</th>
				      <th>规则名称</th>
				      <th><%=CcardInfo.ALIAS_NAME%></th>
				      <th><%=CcardInfo.ALIAS_MONEY%></th>
				      <th>未刷金额</th>
				      <th>已刷金额</th>
				      <th>已刷手续费</th>
				    </tr>
				   <c:set value="0" var="m"></c:set>
				   <c:set value="0" var="f"></c:set>
					<c:forEach var="item" items="${page.result}">
				    <tr>
				      <td><input type="checkbox" name="ids" id="ids" value="${item.id}"></td>
				      <td>${item.id}</td>
				      <td><c:out value='${item.bankName}'/></td>
				      <td><c:out value='${item.cardRangeName}'/></td>
				      <td><c:out value='${item.name}'/></td>
				      <td><c:out value='${item.money}'/>元</td>
				      <td><c:out value='${item.totalAmount}'/>元</td>
				      <td><c:out value='${item.amount}'/>元</td>
				      <td><c:out value='${item.fee}'/>元</td>
				    </tr>
				    <c:set value="${f+item.fee}" var="f"></c:set>
				    <c:set value="${m+item.amount}" var="m"></c:set>
					</c:forEach>
			        </table>
	            </div>
	            
	            
	            <div style="margin-left:10px">
					 总刷卡金额：${m}元，总手续费：${f}元
	            </div>
	            
	            
	            <!-- /.box-body -->
	          </div>
	        </div>
	        <!-- /.col -->
	      </div>
	    	</div>
	      <!-- /.row -->
	    </section>
    </form>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
	  <!-- =============================================== -->
	<jsp:include page="/WEB-INF/jsp/admin/body_footer.jsp" />
</div>
<!-- ./wrapper -->
</body>
</html>