<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
 <!-- Left side column. contains the sidebar -->
  <aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
      <!-- sidebar menu: : style can be found in sidebar.less -->
      <ul class="sidebar-menu" data-widget="tree">
        <li class="header">MAIN NAVIGATION</li>
	    	<li class="treeview">
	          <a href='#'>
	            <i class="fa fa-dashboard"></i> <span>用户管理</span>
	            <span class="pull-right-container">
	              <i class="fa fa-angle-left pull-right"></i>
	            </span>
	          </a>
	    		<ul class="treeview-menu">
					<li><a href="${ctx}/admin/management/sysuser/list"><i class="fa fa-circle-o"></i>用户列表</a></li>
				</ul>
			</li>
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