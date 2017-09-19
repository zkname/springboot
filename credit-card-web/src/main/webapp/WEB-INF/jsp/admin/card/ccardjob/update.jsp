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
      				<fmt:formatDate value="${entity.jobDate}" pattern="yyyy-MM-dd"/>
                  </div>
                  <br>
                </div>
                <c:if test="${fn:length(mccs)>0}">
                <div class="form-group">
                  <label class="col-sm-2 control-label">MCC历史↓</label>
                  <div class="col-sm-10">
      					<c:forEach var="mcc" items="${mccs}" >
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
<option value="4733" ${entity.mcc=='4733'?'selected':''} >[4733]大型景区售票</option>
<option value="8912" ${entity.mcc=='8912'?'selected':''} >[8912]装修、装潢、园艺 </option>
<option value="8911" ${entity.mcc=='8911'?'selected':''} >[8911]建筑、工程和测量服务 </option>
<option value="7251" ${entity.mcc=='7251'?'selected':''} >[7251]修鞋店、擦鞋店、帽子清洗店 </option>
<option value="5950" ${entity.mcc=='5950'?'selected':''} >[5950]玻璃器皿和水晶饰品店 </option>
<option value="8675" ${entity.mcc=='8675'?'selected':''} >[8675]汽车协会 </option>
<option value="0742" ${entity.mcc=='0742'?'selected':''} >[0742]兽医服务 </option>
<option value="7841" ${entity.mcc=='7841'?'selected':''} >[7841]音像制品出租商店 </option>
<option value="4722" ${entity.mcc=='4722'?'selected':''} >[4722]旅行社</option>
<option value="7641" ${entity.mcc=='7641'?'selected':''} >[7641]家具维修、翻新 </option>
<option value="8111" ${entity.mcc=='8111'?'selected':''} >[8111]法律服务和律师事务所服务</option>
<option value="5949" ${entity.mcc=='5949'?'selected':''} >[5949]纺织品及针织品零售 </option>
<option value="5948" ${entity.mcc=='5948'?'selected':''} >[5948]箱包、皮具店</option>
<option value="5947" ${entity.mcc=='5947'?'selected':''} >[5947]礼品、卡片、装饰品、纪念品商店 </option>
<option value="5946" ${entity.mcc=='5946'?'selected':''} >[5946]照相器材商店</option>
<option value="5945" ${entity.mcc=='5945'?'selected':''} >[5945]玩具、游戏店</option>
<option value="8071" ${entity.mcc=='8071'?'selected':''} >[8071]医学及牙科实验室 </option>
<option value="5944" ${entity.mcc=='5944'?'selected':''} >[5944]银器店 </option>
<option value="5943" ${entity.mcc=='5943'?'selected':''} >[5943]文具用品商店、各类办公用品商店 </option>
<option value="5942" ${entity.mcc=='5942'?'selected':''} >[5942]书店 </option>
<option value="5941" ${entity.mcc=='5941'?'selected':''} >[5941]体育用品店 </option>
<option value="5940" ${entity.mcc=='5940'?'selected':''} >[5940]自行车商店 </option>
<option value="7999" ${entity.mcc=='7999'?'selected':''} >[7999]未列入其他代码的娱乐服务</option>
<option value="7998" ${entity.mcc=='7998'?'selected':''} >[7998]水族馆、海洋馆和海豚馆 </option>
<option value="7997" ${entity.mcc=='7997'?'selected':''} >[7997]会员俱乐部以及私人高尔夫课程班 </option>
<option value="7996" ${entity.mcc=='7996'?'selected':''} >[7996]游乐园、马戏团、嘉年华、占卜 </option>
<option value="7995" ${entity.mcc=='7995'?'selected':''} >[7995]彩票销售 </option>
<option value="7832" ${entity.mcc=='7832'?'selected':''} >[7832]电影院 </option>
<option value="7994" ${entity.mcc=='7994'?'selected':''} >[7994]大型游戏机和游戏场所 </option>
<option value="7993" ${entity.mcc=='7993'?'selected':''} >[7993]电子游戏供给</option>
<option value="7992" ${entity.mcc=='7992'?'selected':''} >[7992]公共高尔夫球场 </option>
<option value="7991" ${entity.mcc=='7991'?'selected':''} >[7991]旅游与展览 </option>
<option value="5542" ${entity.mcc=='5542'?'selected':''} >[5542]自助加油站 </option>
<option value="5541" ${entity.mcc=='5541'?'selected':''} >[5541]加油站、服务站 </option>
<option value="7631" ${entity.mcc=='7631'?'selected':''} >[7631]手表、钟表和首饰维修店  </option>
<option value="4511" ${entity.mcc=='4511'?'selected':''} >[4511]航空售票</option>
<option value="5937" ${entity.mcc=='5937'?'selected':''} >[5937]古玩复制店 </option>
<option value="5935" ${entity.mcc=='5935'?'selected':''} >[5935]海上船只遇难救助 </option>
<option value="7395" ${entity.mcc=='7395'?'selected':''} >[7395]照相洗印服务</option>
<option value="7394" ${entity.mcc=='7394'?'selected':''} >[7394]设备、工具、家具和电器出租 </option>
<option value="4119" ${entity.mcc=='4119'?'selected':''} >[4119]救护车服务 </option>
<option value="7393" ${entity.mcc=='7393'?'selected':''} >[7393]侦探、保安、安全服务 </option>
<option value="5932" ${entity.mcc=='5932'?'selected':''} >[5932]古玩店－出售、维修及还原</option>
<option value="7230" ${entity.mcc=='7230'?'selected':''} >[7230]美容理发店 </option>
<option value="7392" ${entity.mcc=='7392'?'selected':''} >[7392]管理、咨询和公共关系服务</option>
<option value="5931" ${entity.mcc=='5931'?'selected':''} >[5931]旧商品店、二手商品店 </option>
<option value="7829" ${entity.mcc=='7829'?'selected':''} >[7829]电影和录像创作、发行 </option>
<option value="5735" ${entity.mcc=='5735'?'selected':''} >[5735]音像制品商店</option>
<option value="7033" ${entity.mcc=='7033'?'selected':''} >[7033]活动房车场及露营场所 </option>
<option value="5734" ${entity.mcc=='5734'?'selected':''} >[5734]计算机软件商店 </option>
<option value="7032" ${entity.mcc=='7032'?'selected':''} >[7032]运动和娱乐露营地 </option>
<option value="5733" ${entity.mcc=='5733'?'selected':''} >[5733]音乐商店－乐器、钢琴、乐谱 </option>
<option value="5732" ${entity.mcc=='5732'?'selected':''} >[5732]电子设备商店</option>
<option value="7629" ${entity.mcc=='7629'?'selected':''} >[7629]电器设备、小家电维修 </option>
<option value="5699" ${entity.mcc=='5699'?'selected':''} >[5699]各类服装及饰物店 </option>
<option value="5698" ${entity.mcc=='5698'?'selected':''} >[5698]假发商店 </option>
<option value="5697" ${entity.mcc=='5697'?'selected':''} >[5697]裁缝、修补、改衣店 </option>
<option value="5533" ${entity.mcc=='5533'?'selected':''} >[5533]汽车零配件商店 </option>
<option value="5532" ${entity.mcc=='5532'?'selected':''} >[5532]汽车轮胎经销商 </option>
<option value="7623" ${entity.mcc=='7623'?'selected':''} >[7623]空调、制冷设备维修 </option>
<option value="7622" ${entity.mcc=='7622'?'selected':''} >[7622]电器设备维修</option>
<option value="5499" ${entity.mcc=='5499'?'selected':''} >[5499]各类食品店及专门食品零售店 </option>
<option value="5691" ${entity.mcc=='5691'?'selected':''} >[5691]成人成衣店 </option>
<option value="5139" ${entity.mcc=='5139'?'selected':''} >[5139]鞋类（批发商） </option>
<option value="5331" ${entity.mcc=='5331'?'selected':''} >[5331]各类杂货店、便利店 </option>
<option value="5137" ${entity.mcc=='5137'?'selected':''} >[5137]男女及儿童制服和服装（批发商） </option>
<option value="4468" ${entity.mcc=='4468'?'selected':''} >[4468]船舶、海运服务提供商 </option>
<option value="8050" ${entity.mcc=='8050'?'selected':''} >[8050]护理和照料服务 </option>
<option value="7221" ${entity.mcc=='7221'?'selected':''} >[7221]摄影工作室 </option>
<option value="5921" ${entity.mcc=='5921'?'selected':''} >[5921]瓶装酒零售店</option>
<option value="5094" ${entity.mcc=='5094'?'selected':''} >[5094]贵重珠宝、首饰，钟表零售</option>
<option value="5722" ${entity.mcc=='5722'?'selected':''} >[5722]家用电器商店</option>
<option value="8641" ${entity.mcc=='8641'?'selected':''} >[8641]市民、社会及友爱组织 </option>
<option value="5681" ${entity.mcc=='5681'?'selected':''} >[5681]皮货店 </option>
<option value="8049" ${entity.mcc=='8049'?'selected':''} >[8049]手足病医生 </option>
<option value="7217" ${entity.mcc=='7217'?'selected':''} >[7217]室内清洁服务</option>
<option value="7216" ${entity.mcc=='7216'?'selected':''} >[7216]干洗店 </option>
<option value="8043" ${entity.mcc=='8043'?'selected':''} >[8043]光学产品、眼镜店 </option>
<option value="8042" ${entity.mcc=='8042'?'selected':''} >[8042]眼科医生 </option>
<option value="4457" ${entity.mcc=='4457'?'selected':''} >[4457]出租船只 </option>
<option value="8041" ${entity.mcc=='8041'?'selected':''} >[8041]按摩医生 </option>
<option value="7211" ${entity.mcc=='7211'?'selected':''} >[7211]洗熨服务（自助洗衣服务）</option>
<option value="5719" ${entity.mcc=='5719'?'selected':''} >[5719]各种家庭装饰专营店 </option>
<option value="5912" ${entity.mcc=='5912'?'selected':''} >[5912]药店、药房 </option>
<option value="7210" ${entity.mcc=='7210'?'selected':''} >[7210]洗衣店 </option>
<option value="7372" ${entity.mcc=='7372'?'selected':''} >[7372]计算机编程、数据处理和系统集成设计服务 </option>
<option value="5718" ${entity.mcc=='5718'?'selected':''} >[5718]壁炉、壁炉防护网及配件商店 </option>
<option value="5714" ${entity.mcc=='5714'?'selected':''} >[5714]帏帐、窗帘、室内装潢商店</option>
<option value="7012" ${entity.mcc=='7012'?'selected':''} >[7012]分时使用的别墅或度假用房</option>
<option value="5713" ${entity.mcc=='5713'?'selected':''} >[5713]地板商店 </option>
<option value="7011" ${entity.mcc=='7011'?'selected':''} >[7011]旅馆、酒店、度假村等</option>
<option value="5712" ${entity.mcc=='5712'?'selected':''} >[5712]家具、家庭摆品、家用设备零售商 </option>
<option value="5311" ${entity.mcc=='5311'?'selected':''} >[5311]百货商店 </option>
<option value="5310" ${entity.mcc=='5310'?'selected':''} >[5310]折扣商店 </option>
<option value="8031" ${entity.mcc=='8031'?'selected':''} >[8031]正骨医生 </option>
<option value="5111" ${entity.mcc=='5111'?'selected':''} >[5111]文具、办公用品、复印纸和书写纸（批发商） </option>
<option value="7361" ${entity.mcc=='7361'?'selected':''} >[7361]职业中介、临时工 </option>
<option value="5072" ${entity.mcc=='5072'?'selected':''} >[5072]五金器材及用品（批发商）</option>
<option value="5309" ${entity.mcc=='5309'?'selected':''} >[5309]免税商店 </option>
<option value="5661" ${entity.mcc=='5661'?'selected':''} >[5661]鞋店 </option>
<option value="5462" ${entity.mcc=='5462'?'selected':''} >[5462]面包房、糕点商店 </option>
<option value="8021" ${entity.mcc=='8021'?'selected':''} >[8021]牙科医生 </option>
<option value="5261" ${entity.mcc=='5261'?'selected':''} >[5261]草坪、花园用品商店 </option>
<option value="5065" ${entity.mcc=='5065'?'selected':''} >[5065]电器零件和设备（批发商）</option>
<option value="7941" ${entity.mcc=='7941'?'selected':''} >[7941]商业体育场馆、运动场和体育推广公司 </option>
<option value="5655" ${entity.mcc=='5655'?'selected':''} >[5655]运动服饰商店</option>
<option value="5651" ${entity.mcc=='5651'?'selected':''} >[5651]家庭服装商店</option>
<option value="7549" ${entity.mcc=='7549'?'selected':''} >[7549]拖车服务 </option>
<option value="4821" ${entity.mcc=='4821'?'selected':''} >[4821]电报服务 </option>
<option value="5451" ${entity.mcc=='5451'?'selected':''} >[5451]乳制品店、冷饮店 </option>
<option value="7349" ${entity.mcc=='7349'?'selected':''} >[7349]清洁、保养及门卫服务 </option>
<option value="7542" ${entity.mcc=='7542'?'selected':''} >[7542]洗车 </option>
<option value="8011" ${entity.mcc=='8011'?'selected':''} >[8011]其他医疗卫生活动 </option>
<option value="5251" ${entity.mcc=='5251'?'selected':''} >[5251]五金商店 </option>
<option value="7342" ${entity.mcc=='7342'?'selected':''} >[7342]灭虫及消毒服务 </option>
<option value="4582" ${entity.mcc=='4582'?'selected':''} >[4582]机场服务 </option>
<option value="4225" ${entity.mcc=='4225'?'selected':''} >[4225]公共仓储服务－农产品、冷冻品和家用产品 </option>
<option value="7933" ${entity.mcc=='7933'?'selected':''} >[7933]保龄球馆 </option>
<option value="7932" ${entity.mcc=='7932'?'selected':''} >[7932]台球、撞球场所 </option>
<option value="4816" ${entity.mcc=='4816'?'selected':''} >[4816]计算机网络/信息服务 </option>
<option value="5641" ${entity.mcc=='5641'?'selected':''} >[5641]婴儿、儿童服装店 </option>
<option value="4814" ${entity.mcc=='4814'?'selected':''} >[4814]电信服务， 包括本地和长途电话、信用卡电话、磁卡电话和传真 </option>
<option value="4812" ${entity.mcc=='4812'?'selected':''} >[4812]电信设备和销售（商） </option>
<option value="7538" ${entity.mcc=='7538'?'selected':''} >[7538]汽车服务商店（非经销商）</option>
<option value="7699" ${entity.mcc=='7699'?'selected':''} >[7699]各类维修店及相关服务 </option>
<option value="7535" ${entity.mcc=='7535'?'selected':''} >[7535]汽车喷漆店 </option>
<option value="7534" ${entity.mcc=='7534'?'selected':''} >[7534]轮胎翻新、维修店 </option>
<option value="5441" ${entity.mcc=='5441'?'selected':''} >[5441]糖果及坚果商店 </option>
<option value="7339" ${entity.mcc=='7339'?'selected':''} >[7339]速记、秘书服务（包括各类办公服务）</option>
<option value="7338" ${entity.mcc=='7338'?'selected':''} >[7338]复印及绘图服务 </option>
<option value="7531" ${entity.mcc=='7531'?'selected':''} >[7531]车体维修店 </option>
<option value="7692" ${entity.mcc=='7692'?'selected':''} >[7692]焊接维修服务</option>
<option value="7333" ${entity.mcc=='7333'?'selected':''} >[7333]商业摄影、工艺、绘图服务</option>
<option value="4411" ${entity.mcc=='4411'?'selected':''} >[4411]轮船及巡游航线服务 </option>
<option value="7298" ${entity.mcc=='7298'?'selected':''} >[7298]美容SPA </option>
<option value="7929" ${entity.mcc=='7929'?'selected':''} >[7929]未列入其他代码的乐队、文艺表演 </option>
<option value="5999" ${entity.mcc=='5999'?'selected':''} >[5999]其他专门零售店 </option>
<option value="7297" ${entity.mcc=='7297'?'selected':''} >[7297]按摩店、大保健你懂的</option>
<option value="5998" ${entity.mcc=='5998'?'selected':''} >[5998]其他批发商 </option>
<option value="4215" ${entity.mcc=='4215'?'selected':''} >[4215]快递服务</option>
<option value="7296" ${entity.mcc=='7296'?'selected':''} >[7296]出租衣物－服装、制服和正式场合服装</option>
<option value="5997" ${entity.mcc=='5997'?'selected':''} >[5997]电动剃刀商店－销售和服务</option>
<option value="5996" ${entity.mcc=='5996'?'selected':''} >[5996]游泳池－销售、供应和服务</option>
<option value="7295" ${entity.mcc=='7295'?'selected':''} >[7295]家政服务 </option>
<option value="4214" ${entity.mcc=='4214'?'selected':''} >[4214]货物搬运和托运</option>
<option value="5995" ${entity.mcc=='5995'?'selected':''} >[5995]宠物商店、宠物食品及用品</option>
<option value="5993" ${entity.mcc=='5993'?'selected':''} >[5993]香烟、雪茄专卖店 </option>
<option value="5992" ${entity.mcc=='5992'?'selected':''} >[5992]花店 </option>
<option value="7922" ${entity.mcc=='7922'?'selected':''} >[7922]戏剧制片（不含电影）、演出和票务 </option>
<option value="5631" ${entity.mcc=='5631'?'selected':''} >[5631]女性用品商店</option>
<option value="0780" ${entity.mcc=='0780'?'selected':''} >[0780]景观美化及园艺服务 </option>
<option value="8351" ${entity.mcc=='8351'?'selected':''} >[8351]儿童保育服务（含学前教育） </option>
<option value="5399" ${entity.mcc=='5399'?'selected':''} >[5399]其他综合零售</option>
<option value="5039" ${entity.mcc=='5039'?'selected':''} >[5039]未列入其他代码的建材批发（批发商）</option>
<option value="5231" ${entity.mcc=='5231'?'selected':''} >[5231]玻璃、油漆涂料、墙纸零售</option>
<option value="7911" ${entity.mcc=='7911'?'selected':''} >[7911]歌舞厅、夜总会、KTV，会所</option>
<option value="5621" ${entity.mcc=='5621'?'selected':''} >[5621]妇女成衣商店</option>
<option value="7519" ${entity.mcc=='7519'?'selected':''} >[7519]房车和娱乐车辆出租 </option>
<option value="5422" ${entity.mcc=='5422'?'selected':''} >[5422]各类肉类零售商 </option>
<option value="7513" ${entity.mcc=='7513'?'selected':''} >[7513]卡车及拖车出租 </option>
<option value="7512" ${entity.mcc=='7512'?'selected':''} >[7512]汽车出租 </option>
<option value="7311" ${entity.mcc=='7311'?'selected':''} >[7311]广告服务 </option>
<option value="8931" ${entity.mcc=='8931'?'selected':''} >[8931]会计、审计、务服务 </option>
<option value="7278" ${entity.mcc=='7278'?'selected':''} >[7278]购物服务及会所（贸易、经纪服务） </option>
<option value="5978" ${entity.mcc=='5978'?'selected':''} >[5978]打字机商店－销售、服务和出租 </option>
<option value="7277" ${entity.mcc=='7277'?'selected':''} >[7277]咨询服务－债务、婚姻和私人事务 </option>
<option value="5977" ${entity.mcc=='5977'?'selected':''} >[5977]化妆品商店 </option>
<option value="7276" ${entity.mcc=='7276'?'selected':''} >[7276]税收准备服务</option>
<option value="5976" ${entity.mcc=='5976'?'selected':''} >[5976]假肢店</option>
<option value="5814" ${entity.mcc=='5814'?'selected':''} >[5814]便民餐饮店 </option>
<option value="5021" ${entity.mcc=='5021'?'selected':''} >[5021]办公及商务家具（批发商）</option>
<option value="5975" ${entity.mcc=='5975'?'selected':''} >[5975]助听器－销售、服务和用品</option>
<option value="5813" ${entity.mcc=='5813'?'selected':''} >[5813]酒吧、酒馆 </option>
<option value="5812" ${entity.mcc=='5812'?'selected':''} >[5812]就餐场所和餐馆 </option>
<option value="7273" ${entity.mcc=='7273'?'selected':''} >[7273]婚姻介绍及陪同服务 </option>
<option value="5973" ${entity.mcc=='5973'?'selected':''} >[5973]宗教品商店 </option>
<option value="5972" ${entity.mcc=='5972'?'selected':''} >[5972]邮票和纪念币商店 </option>
<option value="5971" ${entity.mcc=='5971'?'selected':''} >[5971]艺术商和画廊</option>
<option value="5970" ${entity.mcc=='5970'?'selected':''} >[5970]工艺美术商店</option>
<option value="0763" ${entity.mcc=='0763'?'selected':''} >[0763]农业合作 </option>
<option value="5611" ${entity.mcc=='5611'?'selected':''} >[5611]男子和男童服装及用品商店</option>
<option value="5411" ${entity.mcc=='5411'?'selected':''} >[5411]超市</option>
<option value="5211" ${entity.mcc=='5211'?'selected':''} >[5211]建材和木材</option>
<option value="8099" ${entity.mcc=='8099'?'selected':''} >[8099]其他医疗保健服务 </option>
<option value="5969" ${entity.mcc=='5969'?'selected':''} >[5969]其他直销商户</option>
<option value="5013" ${entity.mcc=='5013'?'selected':''} >[5013]机动车供应及零配件（批发商） </option>
<option value="5962" ${entity.mcc=='5962'?'selected':''} >[5962]旅游相关服务直销 </option>
<option value="7261" ${entity.mcc=='7261'?'selected':''} >[7261]殡葬服务 </option>
		                </select>
      				</div>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label"><%=CcardJob.ALIAS_MONEY%></label>
                  <div class="col-sm-10">
                  
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
			                <option value="0" ${entity.status!=null && entity.status=='0'?'selected':''}>未刷卡</option>
			                <option value="1" ${entity.status!=null && entity.status=='1'?'selected':''}>已刷卡</option>
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