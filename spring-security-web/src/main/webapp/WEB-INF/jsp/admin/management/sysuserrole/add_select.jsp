<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>

<c:forEach var="menuVoSon" items="${menuVo.lists}">
	<tr>
		<td style="text-align:left;" >&nbsp;
			<c:forEach var="i" items="${Level}">
				&nbsp;&nbsp;&nbsp;
			</c:forEach>
	 		<input type="checkbox" name="moduleId" parentid="${menuVo.id}" id="moduleId" value="${menuVoSon.id}" />${menuVoSon.name}&nbsp
	 		<c:if test="${fn:length(menuVoSon.purviewLists)>0 }">[</c:if>
	 		<c:forEach var="purview" items="${menuVoSon.purviewLists}">
	 			<input type="checkbox" name="purviewId" parentid="${menuVoSon.id}" id="purviewId" value="${purview.id}" />${purview.name}&nbsp
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