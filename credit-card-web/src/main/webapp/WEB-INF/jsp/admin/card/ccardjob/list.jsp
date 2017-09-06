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
			  $("#form").attr("action","${ctx}/admin/card/ccardjob/delete");
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
        	刷卡明细
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="${ctx}/admin/card/ccardjob/list">刷卡管理</a></li>
        <li class="active">刷卡明细</li>
      </ol>
    </section>
	<form id="form" action="${ctx}/admin/card/ccardjob/list" method="get">
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
                <select name="bankId" id="bankId" class="form-control">
	                <option value="">银行信息</option>
	                <c:forEach var="obj" items="${cbanks}">
	                	<option value="${obj.id}" <c:if test="${page.bankId!=null && page.bankId==obj.id}">selected="selected"</c:if>>${obj.name}</option>
	                </c:forEach>
                </select>
            	<br>
            </div>
            
            <div class="col-xs-6">
                <select name="cardInfoId" id="cardInfoId" class="form-control">
	                <option value="">信用卡信息</option>
	                <c:forEach var="obj" items="${ccardInfos}">
	                	<option value="${obj.id}" <c:if test="${page.cardInfoId!=null && page.cardInfoId==obj.id}">selected="selected"</c:if>>${obj.name}</option>
	                </c:forEach>
                </select>
            	<br>
            </div>
            
            <div class="col-xs-6">
                <select name="cardRangeId" id="cardRangeId" class="form-control">
	                <option value="">规则信息</option>
	                <c:forEach var="obj" items="${ccardRanges}">
	                	<option value="${obj.id}" <c:if test="${page.cardRangeId!=null && page.cardRangeId==obj.id}">selected="selected"</c:if>>${obj.name}</option>
	                </c:forEach>
                </select>            	<br>
            </div>
            
            <div class="col-xs-6">
                 <select name="status" id="status" class="form-control">
	                <option value="">刷卡状态</option>
	                <option value="1" <c:if test="${page.status!=null && page.status=='1'}">selected="selected"</c:if>>已刷卡</option>
	                <option value="0" <c:if test="${page.status!=null && page.status=='0'}">selected="selected"</c:if>>未刷卡</option>
	              </select>
            	<br>
            </div>
            
             <div class="col-xs-6">
             </div>
            
            <div class="col-xs-6">
            	<input type="submit" value="搜索" class="btn btn-primary btn-block btn-flat"/>
            </div>
	         </div>
	            <hr style="border:none;border-top:1px dotted #185598;" />
	            <div style="margin-left:10px">
					 <a href="javascript:void(0);" id="deleteId">删除</a>
	            </div>
	            <!-- /.box-header -->
	          <div class="box-body table-responsive no-padding">
	              <table class="table table-hover">
				    <tr>
				      <th><input type="checkbox" id="checkbox" value="checkbox"></th>
				      <th><%=CcardJob.ALIAS_JOB_DATE%></th>
				      <th>银行名称</th>
				      <th>规则名称</th>
				      <th>信用卡</th>
				      <th><%=CcardJob.ALIAS_MONEY%></th>
				      <th>手续费</th>
				      <th><%=CcardJob.ALIAS_FEE%></th>
				      <th>状态</th>
				      <th><%=CcardJob.ALIAS_CREATE_TIME%></th>
				      <th><%=CcardJob.ALIAS_UPDATE_TIME%></th>
				      <th>操作</th>
				    </tr>
					<c:forEach var="item" items="${page.result}">
				    <tr>
				      <td><input type="checkbox" name="ids" id="ids" value="${item.id}"></td>
				      <td><fmt:formatDate value="${item.jobDate}" pattern="yyyy-MM-dd"/></td>
				      <td><c:out value='${item.bankName}'/></td>
				      <td><c:out value='${item.cardRangeName}'/></td>
				      <td><c:out value='${item.cardInfoName}'/></td>
				      <td><c:out value='${item.money}'/></td>
				      <td><c:out value='${item.feeValue}'/></td>
				      <td><c:out value='${item.fee}'/></td>
				      <td>
				      <c:choose>
				      	<c:when test="${item.status=='1'}">已刷卡</c:when>
				      	<c:otherwise><font color="red">未刷卡</font></c:otherwise>
				      </c:choose>
				      </td>
				      <td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				      <td><fmt:formatDate value="${item.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				      <td><a href="${ctx}/admin/card/ccardjob/update/${item.id}">修改</a> </td>
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
