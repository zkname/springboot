<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
  <header class="main-header">
    <!-- Logo -->
    <a href="${ctx}/admin/index" class="logo">
      <!-- mini logo for sidebar mini 50x50 pixels -->
      <span class="logo-mini"><b>CARD</b></span>
      <!-- logo for regular state and mobile devices -->
      <span class="logo-lg"><b>C_</b>CARD</span>
    </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
      <!-- Sidebar toggle button-->
      <a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </a>
      <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">
          <!-- User Account: style can be found in dropdown.less -->
          <li class="dropdown user user-menu">
          	<a href="${ctx}/admin/management/userinfo">
		    ${user.username}
		    </a>
          </li>
          <li>
            <a href="${ctx}/admin/logout" >
              <span>退出</span>
            </a>
          </li>
        </ul>
      </div>
    </nav>
  </header>