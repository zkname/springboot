<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<jsp:useBean id="map" class="java.util.HashMap" scope="request">
	<c:set target="${map}" property="A_PURVIEW" value="fa-gears" />
	<c:set target="${map}" property="A_INFO" value="fa-life-buoy" />
	<c:set target="${map}" property="A_ORDER" value="fa-edit" />
	<c:set target="${map}" property="A_INVOICE" value="fa-ticket" />
	<c:set target="${map}" property="A_PAYMENT" value="fa-cny" />
	<c:set target="${map}" property="A_STATISTICS" value="fa-bar-chart-o" />
	<c:set target="${map}" property="A_SYSTEM" value="fa-gear" />
	
</jsp:useBean>
 <!-- Left side column. contains the sidebar -->
  <aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
      <!-- sidebar menu: : style can be found in sidebar.less -->
      <ul class="sidebar-menu" data-widget="tree">
        <li class="header">MAIN NAVIGATION</li>
	    <c:forEach items="${MenuVos}" var="item">
	    	<li class="treeview">
	    	<c:set var="url" value="href='#'"></c:set>
	    	<c:choose>
	    		<c:when test="${item!=null && empty item.url && item.url!=''}">
	    			<c:choose>
	    				<c:when test="${fn:indexOf(item.url, '?')>0}">
	    					<c:set var="url" value=" id='menuKey${item.id}' href='${ctx}${item.url}&sessionRemove=true' "></c:set>
	    				</c:when>
	    				<c:otherwise>
							<c:set var="url" value=" id='menuKey${item.id}' href='${ctx}${item.url}?sessionRemove=true' "></c:set>
	    				</c:otherwise>
	    			</c:choose>
	    		</c:when>
	    		<c:otherwise>
	    			
		          <a ${url}>
		            <i class="fa ${map[item.securityName]}"></i> <span>${item.name}</span>
		            <span class="pull-right-container">
		              <i class="fa fa-angle-left pull-right"></i>
		            </span>
		          </a>
	    		</c:otherwise>
	    	</c:choose>
	    	
	    	<c:if test="${fn:length(item.lists) > 0}">  
	    		<ul class="treeview-menu">
					<c:set var="list" value="${item.lists}" scope="request" />
					<jsp:include page="/WEB-INF/jsp/admin/menu.jsp"/>
				</ul>
			</c:if>
			</li>
	    </c:forEach>
      </ul>
    </section>
    <!-- /.sidebar -->
  </aside>
  
<script>
  $(document).ready(function () {
	  
	  var obj=$('.treeview').find("a");
	  $.each(obj,function(){
	    var href=$(this).attr("href");
	    if(href!=null && href!="" && href!="#"){
		    href=href.substr(0,href.lastIndexOf("/")+1);
		    if(window.location.href.indexOf(href)!=-1){
		    	$(this).closest("ul").show();
		    	$(this).closest("ul").parent().addClass("menu-open");
		    	$(this).closest("li").addClass("active");
		    	return;
		    }
	    }
	  });
	  $('.sidebar-menu').tree();
  })
</script>