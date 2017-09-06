<%@page import="com.zkname.credit.card.entity.SysParam"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	
	
	//废止
	$("#deleteId").click(function(){
		  if($(":input:checkbox:checked[id=ids]").length<1){
			  alert("请选择最少一条记录！");
			  return null;
		  }else if(window.confirm("确认是否废止？")){
			  //多选就发布
			  $("#form").attr("action","${ctx}/admin/system/sysparam/delete");
			  $("#form").submit();
		  }
	});
	
	//启用
	$("#recoveryId").click(function(){
		  if($(":input:checkbox:checked[id=ids]").length<1){
			  alert("请选择最少一条记录！");
			  return null;
		  }else if(window.confirm("确认是否恢复？")){
			  //多选就发布
			  $("#form").attr("action","${ctx}/admin/system/sysparam/recovery");
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
        	参数管理
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="#">系统管理</a></li>
        <li class="active">参数管理</li>
      </ol>
    </section>
<form id="form" action="${ctx}/admin/system/sysparam/list" method="get">
<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}"/>
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
                  <input type="text" class="form-control" name="name" value="${page.name}"  placeholder="参数名">
                </div>
                <div class="col-xs-6">
                  	<select name="deleStatus" id="deleStatus" class="form-control">
	                <option value="">状态</option>
	                <option value="1" <c:if test="${page.deleStatus!=null && page.deleStatus=='1'}">selected="selected"</c:if>>正常</option>
	                <option value="0" <c:if test="${page.deleStatus!=null && page.deleStatus=='0'}">selected="selected"</c:if>>废止
	                </option>
	              </select>
	              <br>
	              <input type="submit" value="搜索" class="btn btn-primary btn-block btn-flat"/>
                </div>
              </div>
            <hr style="border:none;border-top:1px dotted #185598;" />
            <div style="margin-left:10px">
				      <a href="javascript:void(0);" id="deleteId">废止</a> | <a href="javascript:void(0);" id="recoveryId">恢复</a> | <a href="${ctx}/admin/system/sysparam/add" > 添加</a>
            </div>
            <!-- /.box-header -->
          <div class="box-body table-responsive no-padding">
              <table class="table table-hover">
				    <tr >
				      <th ><input type="checkbox" id="checkbox" value="checkbox"></th>
				      <th ><%=SysParam.ALIAS_NAME%></th>
				      <th ><%=SysParam.ALIAS_K%></th>
				      <th ><%=SysParam.ALIAS_V%></th>
				      <th ><%=SysParam.ALIAS_CREATE_TIME%></th>
				      <th ><%=SysParam.ALIAS_UPDATE_TIME%></th>
				      <th >是否启用</th>
				      <th >操作</th>
				    </tr>
				<c:forEach var="item" items="${page.result}">
				    <tr height="27"  valign="middle" class="STYLE1">
				      <td><input type="checkbox" name="ids" id="ids" value="${item.id}"></td>
				      <td><c:out value='${item.name}'/></td>
				      <td><c:out value='${item.k}'/></td>
				      <td><c:out value='${item.v}'/></td>
				      <td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				      <td><fmt:formatDate value="${item.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				      <td><c:if test="${item.deleStatus=='0'}"><font color="#FF0000">废止</font></c:if><c:if test="${item.deleStatus=='1'}">正常</c:if></td>
				      <td><a href="${ctx}/admin/system/sysparam/update/${item.id}">修改</a> </td>
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
<jsp:include page="/WEB-INF/jsp/admin/body_footer.jsp" />

</div>
<!-- ./wrapper -->

</body>
</html>



    


