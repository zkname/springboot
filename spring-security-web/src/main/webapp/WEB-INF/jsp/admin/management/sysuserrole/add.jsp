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
        	新建角色
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="${ctx}/admin/management/sysuserrole/list">角色管理</a></li>
        <li class="active">新建角色</li>
      </ol>
    </section>
   <form method="post" id="inputForm" action="${ctx}/admin/management/sysuserrole/addInput">
   <input type="hidden" name="platformId" id="platformId" value="1"/>
    <!-- Main content -->
    <section class="content">
      <div class="row">
        <!-- left column -->
        <div class="col-md-6">
          <!-- Horizontal Form -->
          <div class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title"> 新建角色</h3>
            </div>
            <!-- /.box-header -->
              <div class="box-body">
                <div class="form-group">
                  <label class="col-sm-2 control-label"><span class="notnull">*</span>上级角色</label>

                  <div class="col-sm-10">
      				<select name="parentId" id="parentId" class="form-control validate-selection">
					  <option value=" ">请选择</option>
					  <c:forEach var="role" items="${sysUserRoleList}">
					  	<c:choose>
					  		<c:when test="${roleId!=null && roleId==role.id}"><option value="${role.id}"   selected="selected" > ${role.name}</option></c:when>
					  		<c:otherwise><option value="${role.id}"  >${role.name}</option></c:otherwise>
					  	</c:choose>
					  </c:forEach>
					</select>
					<br>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label"><span class="notnull">*</span>角色名称</label>

                  <div class="col-sm-10">
                   <input type="text" name="name" value=""  class="form-control required max-length-50 validate-ajax-${ctx}/admin/management/sysuserrole/checkName?oldName="/>
                  <br></div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">角色备注</label>

                  <div class="col-sm-10">
                   <textarea rows="" cols="" id="comment" name="comment" class="form-control max-length-50 form-textarea"></textarea>
                  <br></div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">权限模块</label>

                  <div class="col-sm-10">
                  		<c:forEach var="menuVoSon" items="${menuVoList}">
						 <div class="bxx_col_coltxt">
							<table  border="0" cellpadding="0" cellspacing="0">
							  <tr>
							    <td valign="top"><input type="checkbox" name="moduleId" id="moduleId" value="${menuVoSon.id}" />${menuVoSon.name}
							    <c:if test="${fn:length(menuVoSon.purviewLists)>0}">
							    	[<c:forEach var="purview" items="${menuVoSon.purviewLists}">
							    		<input type="checkbox" name="purviewId" parentid="${menuVoSon.id}" id="purviewId" value="${purview.id}" />${purview.name}&nbsp
							    	</c:forEach>]
							    </c:if>
							    </td>
							    <td valign="top">

								<table border="0" cellpadding="0" cellspacing="0">
											<c:set var="Level" value="1" scope="request"></c:set>
											<c:set var="menuVo" value="${menuVoSon}" scope="request"></c:set>
											<%@include file="/WEB-INF/jsp/admin/management/sysuserrole/add_select.jsp" %>
								 </table>
								  
								</td>
								  </tr>
								</table>
							</div>		    
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


