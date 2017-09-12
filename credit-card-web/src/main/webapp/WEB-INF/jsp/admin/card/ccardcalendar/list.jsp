<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.zkname.credit.card.entity.*"%>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/head.jsp" %>
<!-- fullCalendar -->
<link rel="stylesheet" href="${ctx}/bower_components/fullcalendar/dist/fullcalendar.min.css">
<link rel="stylesheet" href="${ctx}/bower_components/fullcalendar/dist/fullcalendar.print.min.css" media="print">
<!-- fullCalendar -->

<script src="${ctx}/bower_components/moment/moment.js"></script>
<script src="${ctx}/bower_components/fullcalendar/dist/fullcalendar.js"></script>
<script src="${ctx}/bower_components/fullcalendar/dist/locale/zh-cn.js"></script>
<script type="text/javascript">
$(function () {

	var initialLangCode = 'zk-cn';


    /* initialize the calendar
     -----------------------------------------------------------------*/
    //Date for the calendar events (dummy data)
    var date = new Date()
    var d    = date.getDate(),
        m    = date.getMonth(),
        y    = date.getFullYear()
    $('#calendar').fullCalendar({
      header    : {
        left  : 'prev,next today',
        center: 'title',
        right : 'month,agendaWeek,agendaDay'
      },
      buttonText: {
        today: '今天',
        month: '月',
        week : '周',
        day  : '日'
      },
	  eventLimit: true, // allow "more" link when too many events
	  navLinks: true,
      events: function(start,end,timezone,callback){
    	  $.getJSON("${ctx}/admin/card/ccardcalendar/data",{'start': start.format("YYYY-MM-DD"),'end': end.format("YYYY-MM-DD")}, function(json){
    		  var events = [];
              if (json!=null) {
                  $.each(json,function(i,c) {
                      if (c.status == '1') {
                          events.push({
                              title: c.title,
                              start: c.start , // will be parsed
                              color: '#82D900'
                          });
                      } else if (c.status == '2') {
                          events.push({
                              title: "已刷："+c.money+"元，手续费："+c.fee+"元，未完："+c.nomoney+"元",
                              start: c.start , // will be parsed
                              color: '#FFC78E'
                          });
                      } else {
                          events.push({
                              title: c.title,
                              start: c.start , // will be parsed
                              color: 'RED' ,
                              url:'${ctx}/admin/card/ccardjob/update/'+c.id+"?calendarType=1",
                          });
                      }
                  });
              }
              callback(events);   		  
    	  });
      }
    });
  })
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
        	刷卡日历
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="${ctx}/admin/card/ccardjob/list">刷卡管理</a></li>
        <li class="active">刷卡日历</li>
      </ol>
    </section>



 <!-- Main content -->
    <section class="content">
      <div class="row">
        <!-- /.col -->
        <div class="col-md-12">
          <div class="box box-primary">
            <div class="box-body no-padding">
              <!-- THE CALENDAR -->
              <div id="calendar"></div>
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /. box -->
        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->
    </section>
    <!-- /.content -->

    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
	  <!-- =============================================== -->
	<jsp:include page="/WEB-INF/jsp/admin/body_footer.jsp" />
</div>
<!-- ./wrapper -->
</body>
</html>
