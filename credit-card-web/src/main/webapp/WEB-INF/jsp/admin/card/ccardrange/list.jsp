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
			  $("#form").attr("action","${ctx}/admin/card/ccardrange/delete");
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
        	规则信息
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="${ctx}/admin/card/ccardrange/list">信息管理</a></li>
        <li class="active">规则信息</li>
      </ol>
    </section>
	<form id="form" action="${ctx}/admin/card/ccardrange/list" method="get">
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
            	<input value="${page.name}" id="name" name="name" maxlength="50"  class="form-control"  placeholder="<%=CcardRange.ALIAS_NAME%>"/>
            	<br>
            </div>
            <div class="col-xs-6">
            	<input type="submit" value="搜索" class="btn btn-primary btn-block btn-flat"/>
            </div>
	         </div>
	            <hr style="border:none;border-top:1px dotted #185598;" />
	            <div style="margin-left:10px">
					 <a href="javascript:void(0);" id="deleteId">删除</a> | <a href="${ctx}/admin/card/ccardrange/add" > 添加</a>
	            </div>
	            <!-- /.box-header -->
	          <div class="box-body table-responsive no-padding">
	              <table class="table table-hover">
				    <tr>
				      <th><input type="checkbox" id="checkbox" value="checkbox"></th>
				      <th><%=CcardRange.ALIAS_NAME%></th>
				      <th>刷卡比例（<font color="red">区间随机</font>）</th>
				      <th>刷卡次数（<font color="red">区间随机</font>）</th>
				      <th>账单日后几天</th>
				      <th><%=CcardRange.ALIAS_DAY%></th>
				      <th><%=CcardRange.ALIAS_CREATE_TIME%></th>
				      <th><%=CcardRange.ALIAS_UPDATE_TIME%></th>
				      <th>操作</th>
				    </tr>
					<c:forEach var="item" items="${page.result}">
				    <tr>
				      <td><c:if test="${item.creatorId!=0}"><input type="checkbox" name="ids" id="ids" value="${item.id}"></c:if></td>
				      <td><c:out value='${item.name}'/><c:if test="${item.creatorId==0}">(系统默认)</c:if></td>
				      <td><c:out value='${item.moneyPropStartValue}'/>%~<c:out value='${item.moneyPropEndValue}'/>%</td>
				      <td><c:out value='${item.frequencyPropStartValue}'/>~<c:out value='${item.frequencyPropEndtValue}'/>次</td>
				      <td><c:out value='${item.billGapDay}'/>天</td>
				      <td><c:out value='${item.day}'/>天</td>
				      <td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				      <td><fmt:formatDate value="${item.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				      <td><c:if test="${item.creatorId!=0}"><a href="${ctx}/admin/card/ccardrange/update/${item.id}">修改</a></c:if></td>
				    </tr>
					</c:forEach>
			        </table>
			        <%@ include file="/WEB-INF/jsp/include/page.jsp" %>
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
