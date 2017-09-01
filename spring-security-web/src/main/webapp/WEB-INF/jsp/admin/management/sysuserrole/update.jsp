<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/head.jsp" %>
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
	
	$("input[type=checkbox][name=moduleId]").click(function(){
		if($(this).prop("checked")){
			$(this).parent().find("input[type=checkbox]").prop("checked",true);
			$(this).parent().next().find("input[type=checkbox]").prop("checked",true);
		}else{
			$(this).parent().find("input[type=checkbox]").prop("checked",false);
			$(this).parent().next().find("input[type=checkbox]").prop("checked",false);
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
        	编辑角色
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="${ctx}/admin/management/sysuserrole/list">角色管理</a></li>
        <li class="active">编辑角色</li>
      </ol>
    </section>
<form method="post" id="inputForm" action="${ctx}/admin/management/sysuserrole/updateInput">
<input type="hidden" name="id" id="id" value="${entity.id}"/>
<input name="ctx" type="hidden" id="ctx" value="${ctx}">
<input name="platformId" type="hidden" id="platformId" value="${entity.platformId}">
    <!-- Main content -->
    <section class="content">
      <div class="row">
        <!-- left column -->
        <div class="col-md-6">
          <!-- Horizontal Form -->
          <div class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title"> 编辑角色</h3>
            </div>
            <!-- /.box-header -->
              <div class="box-body">
              	<c:if test="${entity.id!=1}">
	                <div class="form-group">
	                  <label class="col-sm-2 control-label"><span class="notnull">*</span>上级角色</label>
	                  <div class="col-sm-10">
	      				<select name="parentId" id="parentId" class="form-control">
						  <c:forEach var="role" items="${sysUserRoleList}">
						  	<c:choose>
						  		<c:when test="${entity.parentId!=null && entity.parentId==role.id}"><option value="${role.id}"   selected="selected" > ${role.name}</option></c:when>
						  		<c:otherwise><option value="${role.id}"  >${role.name}</option></c:otherwise>
						  	</c:choose>
						  </c:forEach>
						</select>
	                  <br></div>
	                </div>
                </c:if>
                <div class="form-group">
                  <label class="col-sm-2 control-label"><span class="notnull">*</span>角色名称</label>

                  <div class="col-sm-10">
                  <input type="text" name="name" value="${entity.name}"  class="form-control required max-length-50 validate-ajax-${ctx}/admin/management/sysuserrole/checkName?oldName=${entity.name}"/>
                  <br></div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">角色备注</label>

                  <div class="col-sm-10">
                   <textarea rows="" cols="" id="comment" name="comment" class="form-control max-length-50 form-textarea">${entity.comment}</textarea>
                  <br></div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">权限模块</label>

                  <div class="col-sm-10">
                  <c:forEach var="menuVoSon" items="${menuVoList}">
						<table  border="0" cellpadding="0" cellspacing="0">
						  <tr>
						    <td valign="top">	
						    <c:set var="isChecked" value="false" />
						    	<c:forEach var="menuVoSelect" items="${menuVoSelectList}" varStatus="obj">
						    		<c:if test="${menuVoSelect.moduleId==menuVoSon.id}">
						    			<c:set var="isChecked" value="true" />
						    			<c:set var="obj.index" value="${fn:length(menuVoSelectList)}" />
						    		</c:if>
						    	</c:forEach>
								<input type="checkbox" name="moduleId" id="moduleId" value="${menuVoSon.id}" <c:if test="${isChecked==true}"> checked="checked" </c:if> />${menuVoSon.name}
								
								<c:if test="${fn:length(menuVoSon.purviewLists)>0}">[
									<c:forEach var="purview" items="${menuVoSon.purviewLists}" varStatus="obj">
										<c:set var="isChecked" value="false" />
								    	<c:forEach var="menuVoPermissionSelect" items="${menuVoPermissionSelectList}" varStatus="obj1">
								    		<c:if test="${menuVoPermissionSelect.modulePermissionId==purview.id}">
								    			<c:set var="isChecked" value="true" />
								    			<c:set var="obj1.index" value="${fn:length(menuVoPermissionSelectList)}" />
								    		</c:if>
								    	</c:forEach>
									
									<input type="checkbox" name="purviewId" parentid="${menuVoSon.id}" id="purviewId" value="${purview.id}" <c:if test="${isChecked==true}"> checked="checked" </c:if> />${purview.name}&nbsp
									</c:forEach>
								]</c:if>
							    </td>
							    <td valign="top">
								<table border="0" cellpadding="0" cellspacing="0">
									<c:set var="Level" value="1" scope="request"></c:set>
									<c:set var="menuVo" value="${menuVoSon}" scope="request"></c:set>
									<%@include file="/WEB-INF/jsp/admin/management/sysuserrole/update_select.jsp" %>
								 </table>
							</td>
						  </tr>
						</table>
					</c:forEach>
                  <br></div>
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
<jsp:include page="/WEB-INF/jsp/admin/body_footer.jsp" />

</div>
<!-- ./wrapper -->

</body>
</html>


