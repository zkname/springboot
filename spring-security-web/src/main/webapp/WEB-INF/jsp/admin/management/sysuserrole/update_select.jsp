<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>

<c:forEach var="menuVoSon" items="${menuVo.lists}">
	<tr>
		<td style="text-align:left;">&nbsp;
			<c:forEach var="i" items="${Level}">
				&nbsp;&nbsp;&nbsp;
			</c:forEach>
			<c:set var="isChecked" value="false" />
	    	<c:forEach var="menuVoSelect" items="${menuVoSelectList}" varStatus="obj">
	    		<c:if test="${menuVoSelect.moduleId==menuVoSon.id}">
	    			<c:set var="isChecked" value="true" />
	    			<c:set var="obj.index" value="${fn:length(menuVoSelectList)}" />
	    		</c:if>
	    	</c:forEach>
	 		<input type="checkbox" name="moduleId" parentid="${menuVo.id}" id="moduleId" value="${menuVoSon.id}" <c:if test="${isChecked==true}"> checked="checked" </c:if>  />${menuVoSon.name}&nbsp
	 		<c:if test="${fn:length(menuVoSon.purviewLists)>0 }">[</c:if>
	 		<c:forEach var="purview" items="${menuVoSon.purviewLists}">
				<c:set var="isChecked" value="false" />
		    	<c:forEach var="menuVoPermissionSelect" items="${menuVoPermissionSelectList}" varStatus="obj1">
		    		<c:if test="${menuVoPermissionSelect.modulePermissionId==purview.id}">
		    			<c:set var="isChecked" value="true" />
		    			<c:set var="obj1.index" value="${fn:length(menuVoPermissionSelectList)}" />
		    		</c:if>
		    	</c:forEach>
	 			<input type="checkbox" name="purviewId" parentid="${menuVoSon.id}" id="purviewId" value="${purview.id}" <c:if test="${isChecked==true}"> checked="checked" </c:if> />${purview.name}&nbsp
	 		</c:forEach>
		 	<c:if test="${fn:length(menuVoSon.purviewLists)>0 }">]</c:if>
		</td>
	</tr>
	<c:if test="${fn:length(menuVoSon.lists)>0 }">]
		<c:set var="Level" value="${Level+1}" scope="request"></c:set>
		<c:set var="menuVo" value="${menuVoSon}" scope="request"></c:set>
		<jsp:include page="/WEB-INF/jsp/admin/management/sysuserrole/add_select.jsp"/> 
	</c:if>
 	
</c:forEach>

