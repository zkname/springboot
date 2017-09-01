<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/head.jsp" %>
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
        	模块列表
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="#">权限管理</a></li>
        <li class="active">模块列表</li>
      </ol>
    </section>
    <!-- Main content -->
    <section class="content">
      <div class="row">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-body">
            <!-- /.box-header -->
          <div class="box-body table-responsive no-padding">
              <table class="table table-hover">
				<tr>
					<th>ID</th>
                    <th>模块色名</th>
                    <th>权限名</th>
					<th>权限URL</th>
				</tr>


				<c:forEach var="item" items="${menuVo.lists}">
				    <tr>
				    	<td>${item.id}</td>
				     	<td>${item.name}</td>
				     	<td>${item.securityName}</td>
				     	<td>${fn:replace(item.url,"","**")}</td>
				     	<!--<td>编辑</td>-->
				    </tr>
					<c:forEach var="purview" items="${item.purviewLists}">
					    <tr>
					    	<td>&nbsp;</td>
					     	<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${purview.name}</td>
					     	<td>${item.securityName}_${purview.securityName}</td>
					     	<td>${purview.resourceValue}</td>
					     	<!--<td>编辑</td>-->
					    </tr>
					</c:forEach>
					
					<c:forEach var="itemSon" items="${item.lists}">
					    <tr>
					    	<td>${itemSon.id}</td>
					     	<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${itemSon.name}</td>
					     	<td>${itemSon.securityName}</td>
					     	<td>${fn:replace(itemSon.url,"","**")}</td>
					     	<!--<td>编辑</td>-->
					    </tr>
					    
						<c:forEach var="purview" items="${itemSon.purviewLists}">
						    <tr>
						    	<td>&nbsp;</td>
						     	<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${purview.name}</td>
						     	<td>${itemSon.securityName}_${purview.securityName}</td>
						     	<td>${purview.resourceValue}</td>
						     	<!--<td>编辑</td>-->
						    </tr>
						</c:forEach>
						<c:forEach var="itemSon1" items="${itemSon.lists}">
	 						<tr>
                                <td>${itemSon1.id}</td>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${itemSon1.name}</td>
                                <td>${itemSon1.securityName}</td>
                                <td>${fn:replace(itemSon1.url,"","**")}</td>
                                <!--<td>编辑</td>-->
                            </tr>
							<c:forEach var="purview" items="${itemSon1.purviewLists}">
                                <tr>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${purview.name}</td>
                                    <td>${itemSon1.securityName}_${purview.securityName}</td>
                                    <td>${purview.resourceValue}</td>
                                    <!--<td>编辑</td>-->
                                </tr>
							</c:forEach>
                            
					    </c:forEach>
						
					</c:forEach>
					

				</c:forEach>
		         

		        </table>
            </div>
            <!-- /.box-body -->
          </div>
        </div>
        <!-- /.col -->
      </div>
    	</div>
      <!-- /.row -->
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
<jsp:include page="/WEB-INF/jsp/admin/body_footer.jsp" />

</div>
<!-- ./wrapper -->

</body>
</html>