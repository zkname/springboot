<%@page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.zkname.credit.card.entity.*"%>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<!-- Select2 -->
<link rel="stylesheet" href="${ctx}/bower_components/select2/dist/css/select2.min.css">
<%@ include file="/WEB-INF/jsp/include/head.jsp" %>
<script src="${ctx}/bower_components/select2/dist/js/select2.full.min.js"></script>
<script src="${ctx}/bower_components/select2/dist/js/i18n/zh-CN.js"></script>

<!-- 添加表单验证,参数为表单ID -->
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
	
	
	$('.select2').select2({multiple: false});
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
        	刷卡明细编辑
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="${ctx}/admin/card/ccardjob/list">刷卡管理</a></li>
        <li class="active">刷卡明细</li>
      </ol>
    </section>
    
<form action="${ctx}/admin/card/ccardjob/updateInput" method="POST" id="inputForm">
<input name="ctx" type="hidden" id="ctx" value="${ctx}">
<input name="token" type="hidden" id="token" value="${token}">
<input name="id" type="hidden" id="id" value="${entity.id}">
<input name="calendarType" type="hidden" id="calendarType" value="${param.calendarType}">

    <!-- Main content -->
    <section class="content">
      <div class="row">
        <!-- left column -->
        <div class="col-md-6">
          <!-- Horizontal Form -->
          <div class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title"> 信用卡刷卡任务编辑</h3>
            </div>
            <!-- /.box-header -->
              <div class="box-body">
                <div class="form-group">
                  <label class="col-sm-2 control-label"><font color="red">*</font>银行</label>
                  <div class="col-sm-10">
                  <div>
      				${cbank.name}
      				</div>
      				<br>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label"><font color="red">*</font>信用卡</label>
                  <div class="col-sm-10">
                  <div>
      				${ccardInfo.name}
      				</div>
      				<br>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label"><font color="red">*</font>规则</label>
                  <div class="col-sm-10">
                  	<div>
      				${ccardRange.name}
      				</div>
      				<br>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label"><font color="red">*</font><%=CcardJob.ALIAS_JOB_DATE%></label>
                  <div class="col-sm-10">
                  <div>
      				<fmt:formatDate value="${entity.jobDate}" pattern="yyyy-MM-dd"/></div>
      				<br>
                  </div>
                </div>
                <c:if test="${fn:length(mccs)>0}">
                <div class="form-group">
                  <label class="col-sm-2 control-label">MCC历史</label>
                  <div class="col-sm-10">
      					<c:forEach var="mcc" items="${mccs}">
      						<li>${mcc}</li>
      					</c:forEach>
      				<br>
                  </div>
                </div>
                </c:if>
                <div class="form-group">
                  <label class="col-sm-2 control-label">MCC</label>
                  <div class="col-sm-10">
                 	 <div class="form-group">
		                <select class="form-control select2 required" style="width: 100%;" id="mcc" name="mcc"  >
							<option value="4733"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[4733]大型景区售票</option>
							<option value="8912"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[8912]装修、装潢、园艺 </option>
							<option value="8911"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[8911]建筑、工程和测量服务 </option>
							<option value="7251"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7251]修鞋店、擦鞋店、帽子清洗店 </option>
							<option value="5950"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5950]玻璃器皿和水晶饰品店 </option>
							<option value="8675"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[8675]汽车协会 </option>
							<option value="0742"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[0742]兽医服务 </option>
							<option value="7841"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7841]音像制品出租商店 </option>
							<option value="4722"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[4722]旅行社</option>
							<option value="7641"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7641]家具维修、翻新 </option>
							<option value="8111"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[8111]法律服务和律师事务所服务</option>
							<option value="5949"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5949]纺织品及针织品零售 </option>
							<option value="5948"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5948]箱包、皮具店</option>
							<option value="5947"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5947]礼品、卡片、装饰品、纪念品商店 </option>
							<option value="5946"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5946]照相器材商店</option>
							<option value="5945"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5945]玩具、游戏店</option>
							<option value="8071"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[8071]医学及牙科实验室 </option>
							<option value="5944"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5944]银器店 </option>
							<option value="5943"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5943]文具用品商店、各类办公用品商店 </option>
							<option value="5942"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5942]书店 </option>
							<option value="5941"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5941]体育用品店 </option>
							<option value="5940"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5940]自行车商店 </option>
							<option value="7999"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7999]未列入其他代码的娱乐服务</option>
							<option value="7998"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7998]水族馆、海洋馆和海豚馆 </option>
							<option value="7997"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7997]会员俱乐部以及私人高尔夫课程班 </option>
							<option value="7996"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7996]游乐园、马戏团、嘉年华、占卜 </option>
							<option value="7995"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7995]彩票销售 </option>
							<option value="7832"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7832]电影院 </option>
							<option value="7994"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7994]大型游戏机和游戏场所 </option>
							<option value="7993"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7993]电子游戏供给</option>
							<option value="7992"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7992]公共高尔夫球场 </option>
							<option value="7991"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7991]旅游与展览 </option>
							<option value="5542"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5542]自助加油站 </option>
							<option value="5541"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5541]加油站、服务站 </option>
							<option value="7631"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7631]手表、钟表和首饰维修店  </option>
							<option value="4511"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[4511]航空售票</option>
							<option value="5937"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5937]古玩复制店 </option>
							<option value="5935"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5935]海上船只遇难救助 </option>
							<option value="7395"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7395]照相洗印服务</option>
							<option value="7394"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7394]设备、工具、家具和电器出租 </option>
							<option value="4119"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[4119]救护车服务 </option>
							<option value="7393"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7393]侦探、保安、安全服务 </option>
							<option value="5932"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5932]古玩店－出售、维修及还原</option>
							<option value="7230"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7230]美容理发店 </option>
							<option value="7392"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7392]管理、咨询和公共关系服务</option>
							<option value="5931"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5931]旧商品店、二手商品店 </option>
							<option value="7829"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7829]电影和录像创作、发行 </option>
							<option value="5735"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5735]音像制品商店</option>
							<option value="7033"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7033]活动房车场及露营场所 </option>
							<option value="5734"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5734]计算机软件商店 </option>
							<option value="7032"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7032]运动和娱乐露营地 </option>
							<option value="5733"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5733]音乐商店－乐器、钢琴、乐谱 </option>
							<option value="5732"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5732]电子设备商店</option>
							<option value="7629"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7629]电器设备、小家电维修 </option>
							<option value="5699"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5699]各类服装及饰物店 </option>
							<option value="5698"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5698]假发商店 </option>
							<option value="5697"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5697]裁缝、修补、改衣店 </option>
							<option value="5533"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5533]汽车零配件商店 </option>
							<option value="5532"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5532]汽车轮胎经销商 </option>
							<option value="7623"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7623]空调、制冷设备维修 </option>
							<option value="7622"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7622]电器设备维修</option>
							<option value="5499"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5499]各类食品店及专门食品零售店 </option>
							<option value="5691"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5691]成人成衣店 </option>
							<option value="5139"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5139]鞋类（批发商） </option>
							<option value="5331"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5331]各类杂货店、便利店 </option>
							<option value="5137"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5137]男女及儿童制服和服装（批发商） </option>
							<option value="4468"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[4468]船舶、海运服务提供商 </option>
							<option value="8050"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[8050]护理和照料服务 </option>
							<option value="7221"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7221]摄影工作室 </option>
							<option value="5921"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5921]瓶装酒零售店</option>
							<option value="5094"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5094]贵重珠宝、首饰，钟表零售</option>
							<option value="5722"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5722]家用电器商店</option>
							<option value="8641"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[8641]市民、社会及友爱组织 </option>
							<option value="5681"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5681]皮货店 </option>
							<option value="8049"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[8049]手足病医生 </option>
							<option value="7217"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7217]室内清洁服务</option>
							<option value="7216"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7216]干洗店 </option>
							<option value="8043"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[8043]光学产品、眼镜店 </option>
							<option value="8042"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[8042]眼科医生 </option>
							<option value="4457"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[4457]出租船只 </option>
							<option value="8041"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[8041]按摩医生 </option>
							<option value="7211"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7211]洗熨服务（自助洗衣服务）</option>
							<option value="5719"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5719]各种家庭装饰专营店 </option>
							<option value="5912"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5912]药店、药房 </option>
							<option value="7210"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7210]洗衣店 </option>
							<option value="7372"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7372]计算机编程、数据处理和系统集成设计服务 </option>
							<option value="5718"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5718]壁炉、壁炉防护网及配件商店 </option>
							<option value="5714"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5714]帏帐、窗帘、室内装潢商店</option>
							<option value="7012"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7012]分时使用的别墅或度假用房</option>
							<option value="5713"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5713]地板商店 </option>
							<option value="7011"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7011]旅馆、酒店、度假村等</option>
							<option value="5712"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5712]家具、家庭摆品、家用设备零售商 </option>
							<option value="5311"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5311]百货商店 </option>
							<option value="5310"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5310]折扣商店 </option>
							<option value="8031"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[8031]正骨医生 </option>
							<option value="5111"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5111]文具、办公用品、复印纸和书写纸（批发商） </option>
							<option value="7361"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7361]职业中介、临时工 </option>
							<option value="5072"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5072]五金器材及用品（批发商）</option>
							<option value="5309"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5309]免税商店 </option>
							<option value="5661"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5661]鞋店 </option>
							<option value="5462"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5462]面包房、糕点商店 </option>
							<option value="8021"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[8021]牙科医生 </option>
							<option value="5261"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5261]草坪、花园用品商店 </option>
							<option value="5065"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5065]电器零件和设备（批发商）</option>
							<option value="7941"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7941]商业体育场馆、运动场和体育推广公司 </option>
							<option value="5655"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5655]运动服饰商店</option>
							<option value="5651"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5651]家庭服装商店</option>
							<option value="7549"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7549]拖车服务 </option>
							<option value="4821"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[4821]电报服务 </option>
							<option value="5451"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5451]乳制品店、冷饮店 </option>
							<option value="7349"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7349]清洁、保养及门卫服务 </option>
							<option value="7542"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7542]洗车 </option>
							<option value="8011"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[8011]其他医疗卫生活动 </option>
							<option value="5251"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5251]五金商店 </option>
							<option value="7342"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7342]灭虫及消毒服务 </option>
							<option value="4582"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[4582]机场服务 </option>
							<option value="4225"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[4225]公共仓储服务－农产品、冷冻品和家用产品 </option>
							<option value="7933"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7933]保龄球馆 </option>
							<option value="7932"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7932]台球、撞球场所 </option>
							<option value="4816"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[4816]计算机网络/信息服务 </option>
							<option value="5641"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5641]婴儿、儿童服装店 </option>
							<option value="4814"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[4814]电信服务， 包括本地和长途电话、信用卡电话、磁卡电话和传真 </option>
							<option value="4812"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[4812]电信设备和销售（商） </option>
							<option value="7538"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7538]汽车服务商店（非经销商）</option>
							<option value="7699"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7699]各类维修店及相关服务 </option>
							<option value="7535"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7535]汽车喷漆店 </option>
							<option value="7534"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7534]轮胎翻新、维修店 </option>
							<option value="5441"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5441]糖果及坚果商店 </option>
							<option value="7339"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7339]速记、秘书服务（包括各类办公服务）</option>
							<option value="7338"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7338]复印及绘图服务 </option>
							<option value="7531"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7531]车体维修店 </option>
							<option value="7692"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7692]焊接维修服务</option>
							<option value="7333"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7333]商业摄影、工艺、绘图服务</option>
							<option value="4411"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[4411]轮船及巡游航线服务 </option>
							<option value="7298"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7298]美容SPA </option>
							<option value="7929"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7929]未列入其他代码的乐队、文艺表演 </option>
							<option value="5999"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5999]其他专门零售店 </option>
							<option value="7297"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7297]按摩店、大保健你懂的</option>
							<option value="5998"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5998]其他批发商 </option>
							<option value="4215"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[4215]快递服务</option>
							<option value="7296"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7296]出租衣物－服装、制服和正式场合服装</option>
							<option value="5997"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5997]电动剃刀商店－销售和服务</option>
							<option value="5996"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5996]游泳池－销售、供应和服务</option>
							<option value="7295"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7295]家政服务 </option>
							<option value="4214"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[4214]货物搬运和托运</option>
							<option value="5995"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5995]宠物商店、宠物食品及用品</option>
							<option value="5993"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5993]香烟、雪茄专卖店 </option>
							<option value="5992"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5992]花店 </option>
							<option value="7922"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7922]戏剧制片（不含电影）、演出和票务 </option>
							<option value="5631"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5631]女性用品商店</option>
							<option value="0780"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[0780]景观美化及园艺服务 </option>
							<option value="8351"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[8351]儿童保育服务（含学前教育） </option>
							<option value="5399"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5399]其他综合零售</option>
							<option value="5039"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5039]未列入其他代码的建材批发（批发商）</option>
							<option value="5231"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5231]玻璃、油漆涂料、墙纸零售</option>
							<option value="7911"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7911]歌舞厅、夜总会、KTV，会所</option>
							<option value="5621"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5621]妇女成衣商店</option>
							<option value="7519"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7519]房车和娱乐车辆出租 </option>
							<option value="5422"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5422]各类肉类零售商 </option>
							<option value="7513"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7513]卡车及拖车出租 </option>
							<option value="7512"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7512]汽车出租 </option>
							<option value="7311"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7311]广告服务 </option>
							<option value="8931"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[8931]会计、审计、务服务 </option>
							<option value="7278"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7278]购物服务及会所（贸易、经纪服务） </option>
							<option value="5978"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5978]打字机商店－销售、服务和出租 </option>
							<option value="7277"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7277]咨询服务－债务、婚姻和私人事务 </option>
							<option value="5977"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5977]化妆品商店 </option>
							<option value="7276"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7276]税收准备服务</option>
							<option value="5976"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5976]假肢店</option>
							<option value="5814"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5814]便民餐饮店 </option>
							<option value="5021"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5021]办公及商务家具（批发商）</option>
							<option value="5975"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5975]助听器－销售、服务和用品</option>
							<option value="5813"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5813]酒吧、酒馆 </option>
							<option value="5812"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5812]就餐场所和餐馆 </option>
							<option value="7273"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7273]婚姻介绍及陪同服务 </option>
							<option value="5973"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5973]宗教品商店 </option>
							<option value="5972"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5972]邮票和纪念币商店 </option>
							<option value="5971"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5971]艺术商和画廊</option>
							<option value="5970"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5970]工艺美术商店</option>
							<option value="0763"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[0763]农业合作 </option>
							<option value="5611"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5611]男子和男童服装及用品商店</option>
							<option value="5411"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5411]超市</option>
							<option value="5211"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5211]建材和木材</option>
							<option value="8099"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[8099]其他医疗保健服务 </option>
							<option value="5969"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5969]其他直销商户</option>
							<option value="5013"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5013]机动车供应及零配件（批发商） </option>
							<option value="5962"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[5962]旅游相关服务直销 </option>
							<option value="7261"  <c:if test="${entity.mcc==key}">selected="selected"</c:if> >[7261]殡葬服务 </option>
		                </select>
      				</div>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label"><%=CcardJob.ALIAS_MONEY%></label>
                  <div class="col-sm-10">
                  <br>
      				<input value="${entity.money}" id="money" name="money" class="required validate-number max-value-2147483647 form-control"  />
      				<br>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label"><%=CcardJob.ALIAS_FEE%></label>
                  <div class="col-sm-10">
      				<input value="${entity.fee}" id="fee" name="fee" class="required validate-number  form-control"  />
      				<br>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">状态</label>
                  <div class="col-sm-10">
		                <select name="status" id="status" class="form-control">
			                <option value="0" <c:if test="${entity.status!=null && entity.status=='0'}">selected="selected"</c:if>>未刷卡</option>
			                <option value="1" <c:if test="${entity.status!=null && entity.status=='1'}">selected="selected"</c:if>>已刷卡</option>
			              </select>
      				
      				<br>
                  </div>
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
	  <!-- =============================================== -->
	<jsp:include page="/WEB-INF/jsp/admin/body_footer.jsp" />
</div>
<!-- ./wrapper -->
</body>
</html>