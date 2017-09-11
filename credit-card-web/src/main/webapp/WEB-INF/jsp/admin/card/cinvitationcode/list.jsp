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
			  $("#form").attr("action","${ctx}/admin/card/cinvitationcode/delete");
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
        	邀请码
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="${ctx}/admin/card/cinvitationcode/list">后台管理</a></li>
        <li class="active">邀请码</li>
      </ol>
    </section>
	<form id="form" action="${ctx}/admin/card/cinvitationcode/list" method="get">
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
            	<input value="${page.invitationCodeBatch}" id="invitationCodeBatch" name="invitationCodeBatch" maxlength="19"  class="form-control"  placeholder="<%=CinvitationCode.ALIAS_INVITATION_CODE_BATCH%>"/>
            	<br>
            </div>
            
            <div class="col-xs-6">
            	<input value="${page.invitationCode}" id="invitationCode" name="invitationCode" maxlength="64"  class="form-control"  placeholder="<%=CinvitationCode.ALIAS_INVITATION_CODE%>"/>
            	<br>
            </div>
            
            <div class="col-xs-6">
            	<input value="${page.userId}" id="userId" name="userId" maxlength="19"  class="form-control"  placeholder="<%=CinvitationCode.ALIAS_USER_ID%>"/>
            	<br>
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
				      <th><%=CinvitationCode.ALIAS_INVITATION_CODE_BATCH%></th>
				      <th><%=CinvitationCode.ALIAS_INVITATION_CODE%></th>
				      <th><%=CinvitationCode.ALIAS_USER_ID%></th>
				      <th><%=CinvitationCode.ALIAS_CREATE_TIME%></th>
				      <th><%=CinvitationCode.ALIAS_UPDATE_TIME%></th>
				      <th><%=CinvitationCode.ALIAS_DELE_STATUS%></th>
				      <th><%=CinvitationCode.ALIAS_CREATOR_ID%></th>
				    </tr>
					<c:forEach var="item" items="${page.result}">
				    <tr>
				      <td><input type="checkbox" name="ids" id="ids" value="${item.id}"></td>
				      <td><c:out value='${item.invitationCodeBatch}'/></td>
				      <td><c:out value='${item.invitationCode}'/></td>
				      <td><c:out value='${item.userId}'/></td>
				      <td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				      <td><fmt:formatDate value="${item.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				      <td><c:out value='${item.deleStatus}'/></td>
				      <td><c:out value='${item.creatorId}'/></td>
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
