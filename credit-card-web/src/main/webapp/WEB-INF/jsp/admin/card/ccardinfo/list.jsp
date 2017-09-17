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
			  $("#form").attr("action","${ctx}/admin/card/ccardinfo/delete");
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
        	信用卡信息
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="${ctx}/admin/card/ccardinfo/list">信息管理</a></li>
        <li class="active">信用卡信息</li>
      </ol>
    </section>
	<form id="form" action="${ctx}/admin/card/ccardinfo/list" method="get">
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
                <select name="annualFeeType" id="annualFeeType" class="form-control">
                <option value="">年费类型</option>
                <option value="1" <c:if test="${page.annualFeeType!=null && page.annualFeeType=='1'}">selected="selected"</c:if>>强制收费</option>
                <option value="2" <c:if test="${page.annualFeeType!=null && page.annualFeeType=='2'}">selected="selected"</c:if>>刷卡次数</option>
                <option value="3" <c:if test="${page.annualFeeType!=null && page.annualFeeType=='3'}">selected="selected"</c:if>>免年费</option>
              </select>
            <br>
            </div>
            
            <div class="col-xs-6">
            	<input value="${page.name}" id="name" name="name" maxlength="50"  class="form-control"  placeholder="<%=CcardInfo.ALIAS_NAME%>"/>
            	<br>
                 <select name="deleStatus" id="deleStatus" class="form-control">
                <option value="">状态</option>
                <option value="1" <c:if test="${page.deleStatus!=null && page.deleStatus=='1'}">selected="selected"</c:if>>正常</option>
                <option value="0" <c:if test="${page.deleStatus!=null && page.deleStatus=='0'}">selected="selected"</c:if>>废止</option>
              </select>
            	<br>
            </div>
            <div class="col-xs-6">
                <select name="cardRangeId" id="cardRangeId" class="form-control">
	                <option value="">规则信息</option>
	                <c:forEach var="obj" items="${ccardRanges}">
	                	<option value="${obj.id}" <c:if test="${page.cardRangeId!=null && page.cardRangeId==obj.id}">selected="selected"</c:if>>${obj.name}</option>
	                </c:forEach>
                </select>
            </div>
            <div class="col-xs-6">
            	<input type="submit" value="搜索" class="btn btn-primary btn-block btn-flat"/>
            </div>
	         </div>
	            <hr style="border:none;border-top:1px dotted #185598;" />
	            <div style="margin-left:10px">
					 <a href="javascript:void(0);" id="deleteId">删除</a> | <a href="${ctx}/admin/card/ccardinfo/add" > 添加</a>
	            </div>
	            <!-- /.box-header -->
	          <div class="box-body table-responsive no-padding"> 
	              <table class="table table-hover">
				    <tr>
				      <th><input type="checkbox" id="checkbox" value="checkbox"></th>
				      <th>ID</th>
				      <th>名称</th>
				      <th>规则</th>
				      <th>任务时间</th>
				      <th><%=CcardInfo.ALIAS_MONEY%></th>
				      <th>未刷金额</th>
				      <th>年费类型</th>
				      <th><%=CcardInfo.ALIAS_ANNUAL_FEE%></th>
				      <th><%=CcardInfo.ALIAS_CARD_NUM%></th>
				      <th><%=CcardInfo.ALIAS_BILL_DATE%></th>
				      <th><%=CcardInfo.ALIAS_NEXT_UP%></th>
				      <th>操作</th>
				    </tr>
					<c:forEach var="item" items="${page.result}">
				    <tr>
				      <td><input type="checkbox" name="ids" id="ids" value="${item.id}"></td>
				      <td>${item.id}</td>
				      <td><c:out value='${item.bankName}'/>-<c:out value='${item.name}'/></td>
				      <td><c:out value='${item.cardRangeName}'/></td>
				      <td>
				      <c:choose>
				      	<c:when test="${item.jobDate==null}">创建中</c:when>
				      	<c:otherwise><fmt:formatDate value="${item.jobDate}" pattern="yyyy-MM-dd"/></c:otherwise>
				      </c:choose>
				      </td>
				      <td><c:out value='${item.money}'/>元</td>
				      <td><c:out value='${item.totalAmount}'/>元</td>
				      
				      
				      <td>
				      <c:choose>
				      	<c:when test="${item.annualFeeType==1}">强制收费</c:when>
				      	<c:when test="${item.annualFeeType==2}">刷卡次数</c:when>
				      	<c:when test="${item.annualFeeType==3}">免年费</c:when>
				      </c:choose>
				      </td>
				      <td><c:out value='${item.annualFee}'/></td>
				      <td><c:out value='${item.creditCardNumber}'/>/<c:out value='${item.cardNum}'/>次</td>
				      <td><c:out value='${item.billDate}'/></td>
				      <td><fmt:formatDate value="${item.nextUp}" pattern="yyyy-MM-dd"/></td>
				      <td><a href="${ctx}/admin/card/ccardinfo/update/${item.id}">修改</a> </td>
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
