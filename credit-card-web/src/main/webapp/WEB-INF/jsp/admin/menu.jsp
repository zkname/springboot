<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<c:forEach items="${list}" var="itemSon">
	<c:if test="${itemSon.deleStatus!='-'}">
		<c:choose>
			<c:when test="${!itemSon.url && itemSon.url!=''}">
				<c:choose>
					<c:when test="${fn:indexOf(itemSon.url, '?')>0}">
						<li><a href="${ctx}${itemSon.url}&sessionRemove=true" ><i class="fa fa-circle-o"></i> ${itemSon.name}</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="${ctx}${itemSon.url}?sessionRemove=true" ><i class="fa fa-circle-o"></i> ${itemSon.name}</a></li>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<li><a href="#"><i class="fa fa-circle-o"></i> ${itemSon.name}</a></li>
			</c:otherwise>
		</c:choose>
		<c:if test="${fn:length(itemSon.lists)>0} ">
			<c:set var="list" value="${itemSon.lists}" scope="request" />
			<jsp:include page="/WEB-INF/jsp/admin/menu.jsp"/> 
		</c:if>
	</c:if>
</c:forEach>