<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
 <!-- Left side column. contains the sidebar -->
  <aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
      <!-- sidebar menu: : style can be found in sidebar.less -->
      <ul class="sidebar-menu" data-widget="tree">
        <li class="header">MAIN NAVIGATION</li>
        	<c:if test="${user.id==1}">
		    	<li class="treeview">
		          <a href='#'>
		            <i class="fa fa-gears"></i> <span>用户管理</span>
		            <span class="pull-right-container">
		              <i class="fa fa-angle-left pull-right"></i>
		            </span>
		          </a>
		    		<ul class="treeview-menu">
		    			<li><a href="${ctx}/admin/card/cinvitationcodebatch/list"><i class="fa fa-circle-o"></i>邀请</a></li>
						<li><a href="${ctx}/admin/management/sysuser/list"><i class="fa fa-circle-o"></i>用户列表</a></li>
					</ul>
				</li>
			</c:if>
			
	    	<li class="treeview">
	          <a href='#'>
	            <i class="fa fa-edit"></i> <span>信息管理</span>
	            <span class="pull-right-container">
	              <i class="fa fa-angle-left pull-right"></i>
	            </span>
	          </a>
	    		<ul class="treeview-menu">
					<li><a href="${ctx}/admin/card/cbank/list"><i class="fa fa-circle-o"></i>银行信息</a></li>
					<li><a href="${ctx}/admin/card/ccardrange/list"><i class="fa fa-circle-o"></i>规则信息</a></li>
					<li><a href="${ctx}/admin/card/ccardinfo/list"><i class="fa fa-circle-o"></i>信用卡信息</a></li>
				</ul>
			</li>
	    	<li class="treeview">
	          <a href='#'>
	            <i class="fa fa-ticket"></i> <span>刷卡管理</span>
	            <span class="pull-right-container">
	              <i class="fa fa-angle-left pull-right"></i>
	            </span>
	          </a>
	    		<ul class="treeview-menu">
					<li><a href="${ctx}/admin/card/ccardcalendar/list"><i class="fa fa-circle-o"></i>刷卡日历</a></li>
					<li><a href="${ctx}/admin/card/ccardjob/list"><i class="fa fa-circle-o"></i>刷卡明细</a></li>
				</ul>
			</li>
			<li class="treeview">
	          <a href='#'>
	            <i class="fa fa-bar-chart-o"></i> <span>统计管理</span>
	            <span class="pull-right-container">
	              <i class="fa fa-angle-left pull-right"></i>
	            </span>
	          </a>
	    		<ul class="treeview-menu">
					<li><a href="${ctx}/admin/card/ccardstatistics/list"><i class="fa fa-circle-o"></i>统计信息</a></li>
				</ul>
			</li>
			<c:if test="${user.id==1}">
		    	<li class="treeview">
		          <a href='#'>
		            <i class="fa fa-gear"></i> <span>系统管理</span>
		            <span class="pull-right-container">
		              <i class="fa fa-angle-left pull-right"></i>
		            </span>
		          </a>
		    		<ul class="treeview-menu">
						<li><a href="${ctx}/admin/system/sysparam/list"><i class="fa fa-circle-o"></i>系统参数</a></li>
					</ul>
				</li>
			</c:if>
			
      </ul>
    </section>
    <!-- /.sidebar -->
  </aside>
  
<script>
$(function() {
	  
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