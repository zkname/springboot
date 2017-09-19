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
			  $("#form").attr("action","${ctx}/admin/card/cinvitationcodebatch/delete");
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
        	批次
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="${ctx}/admin/card/cinvitationcodebatch/list">后台管理</a></li>
        <li class="active">批次</li>
      </ol>
    </section>
	<form id="form" action="${ctx}/admin/card/cinvitationcodebatch/list" method="get">
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
            	<input value="${page.name}" id="name" name="name" maxlength="30"  class="form-control"  placeholder="<%=CinvitationCodeBatch.ALIAS_NAME%>"/>
            	<br>
            </div>
            
            <div class="col-xs-6">
            	<select name="updateLock" id="updateLock" class="form-control">
                <option value="">锁定状态</option>
                <option value="1" ${page.updateLock!=null && page.updateLock=='1' ?'selected':''}>锁定</option>
                <option value="0" ${page.updateLock!=null && page.updateLock=='0' ?'selected':''}>正常</option>
              </select>
            	<br>
            </div>
            
            <div class="col-xs-6">
            	<select name="deleStatus" id="deleStatus" class="form-control">
                <option value="">状态</option>
                <option value="1" ${page.deleStatus!=null && page.deleStatus=='1'?'selected':''}>正常</option>
                <option value="0" ${page.deleStatus!=null && page.deleStatus=='0'?'selected':''}>废止</option>
              </select>
            	<br>
            </div>
            
            <div class="col-xs-6">
            	<input type="submit" value="搜索" class="btn btn-primary btn-block btn-flat"/>
            </div>
	         </div>
	            <hr style="border:none;border-top:1px dotted #185598;" />
	            <div style="margin-left:10px">
					 <a href="javascript:void(0);" id="deleteId">删除</a> | <a href="${ctx}/admin/card/cinvitationcodebatch/add" > 添加</a>
	            </div>
	            <!-- /.box-header -->
	          <div class="box-body table-responsive no-padding">
	              <table class="table table-hover">
				    <tr>
				      <th><input type="checkbox" id="checkbox" value="checkbox"></th>
				      <th><%=CinvitationCodeBatch.ALIAS_NAME%></th>
				      <th><%=CinvitationCodeBatch.ALIAS_TOTAL%></th>
				      <th>锁定状态</th>
				      <th>生成状态</th>
				      <th><%=CinvitationCodeBatch.ALIAS_REMARK%></th>
				      <th><%=CinvitationCodeBatch.ALIAS_CREATE_TIME%></th>
				      <th><%=CinvitationCodeBatch.ALIAS_UPDATE_TIME%></th>
				      <th>删除状态</th>
				      <th>操作</th>
				    </tr>
					<c:forEach var="item" items="${page.result}">
				    <tr>
				      <td><input type="checkbox" name="ids" id="ids" value="${item.id}"></td>
				      <td><c:out value='${item.name}'/></td>
				      <td><c:out value='${item.total}'/></td>
				      <td>
		            <c:choose>
		            	<c:when test="${item.updateLock=='1'}">锁定</c:when>
		            	<c:otherwise>正常</c:otherwise>
		            </c:choose>
				     </td>
				      <td>
		            <c:choose>
		            	<c:when test="${item.isGenerate=='1'}">已生成</c:when>
		            	<c:otherwise>未生成</c:otherwise>
		            </c:choose>
				      </td>
				      <td><c:out value='${item.remark}'/></td>
				      <td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				      <td><fmt:formatDate value="${item.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				      <td>
				     <c:choose>
		            	<c:when test="${item.deleStatus=='1'}">正常</c:when>
		            	<c:otherwise><font color="red">废止</font></c:otherwise>
		            </c:choose></td>
				      <td><a href="${ctx}/admin/card/cinvitationcode/list?invitationCodeBatch=${item.id}">详细</a> | 
				      <a href="${ctx}/admin/card/cinvitationcodebatch/listDow?id=${item.id}">下载</a>
				      
				      </td>
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
