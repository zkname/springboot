<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/head.jsp" %>
<script type="text/javascript">
//废止操作
function jsDeleStatus(type){
	  if ($("input[name='ids']:checked").size() < 1) {
		  alert("请选择最少一条记录！");
		  return false;
	  } else if (window.confirm("您确实要"+$(this).text()+"所选的信息？")) {
		$("#type").val(type);
		$("#form").attr("method","post");
		$("#form").attr("action","deleStatus");
		$("#form").submit();
	}
}
//编辑
function edit(id){
	window.location.href="update/"+id+"";
}
function dele(id){
	if (window.confirm("您确实要删除所选的信息？")) {
		window.location.href="delete?ids="+id;
	}
}
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
        	角色管理
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="#">权限管理</a></li>
        <li class="active">角色管理</li>
      </ol>
    </section>
    <form method="get" id="form" action="list" >
		<input type="hidden" id="type" name="type" value="0">
        <input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo}">
        <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
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
                  <input type="text" class="form-control" name="name" value="${page.name}"  placeholder="角色名">
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
                	<security:authorize access="hasRole('A_PURVIEW_ROLE_DEL')"> <a href="#" onclick="jsDeleStatus('0')">废止</a> | <a href="#" onclick="jsDeleStatus('1')">恢复</a> | </security:authorize>
                     <!--<a href="#" title="删除" onclick=dele()>删除</a>--> 
                     <security:authorize access="hasRole('A_PURVIEW_USER_ADD')"> <a href="${ctx}/admin/management/sysuserrole/add">新建角色</a> </security:authorize>
            </div>
            <!-- /.box-header -->
          <div class="box-body table-responsive no-padding">
              <table class="table table-hover">
              <tr>
                <th >&nbsp; </th>
                <th >ID </th>
                <th >角色名</th>
                <th >上级角色</th>
                <th >备注</th>
                <th >状态</th>
                <th >操作者</th>
                <th >编辑时间</th>
                <th >操作</th>
              </tr>
              <c:forEach var="item" items="${page.result}">
              <tr>
                <td><input type="checkbox" name="ids" id="ids" value="${item.id}" /></td>
                <td>${item.id}</td>
                <td>${item.name}</td>
                <td>-</td>
                <td>${item.comment}</td>
                <td>
                <c:choose>
                	<c:when test="${item.deleStatus=='1'}">
                		 正常
                	</c:when>
                	<c:otherwise>
                		<font color="red">废止</font>
                	</c:otherwise>
                </c:choose>
                </td>
                <td>
                  -
                  </td>
                <td>${item.updateTime}</td>
                <td>
                	<c:if test="${item.creatorId!=0}">
                		<security:authorize access="hasRole('A_PURVIEW_ROLE_UPDATE')"> <a href="#" title="编辑" onclick=edit(${item.id})>编辑</a> </security:authorize>
                	</c:if>
                  <c:if test="${item.deleStatus!='1'}">
                  	<security:authorize access="hasRole('A_PURVIEW_ROLE_DEL')"> <a href="#" title="删除" onclick=dele(${item.id})>删除</a> </security:authorize>
                  </c:if>
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
<jsp:include page="/WEB-INF/jsp/admin/body_footer.jsp" />

</div>
<!-- ./wrapper -->

</body>
</html>



    

