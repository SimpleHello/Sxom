<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="icon" type="image/x-icon" href="image/favicon1.ico">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- CSS 
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
-->
<link rel="stylesheet" href="bootstrap3/css/bootstrap.min.css">
<link rel="stylesheet" href="bootstrap3/css/bootstrap-table.min.css">
<link rel="stylesheet" href="css/font-awesome.min.css">
<link rel="stylesheet" href="css/form-elements.css">
<link rel="stylesheet" href="css/style.css">
<style>
.first {
	margin-left: 10px;
	margin-top: 20px;
}


.divss {
	margin-top: 20px;
	overflow: auto;
	height: 300px;
}

.divssx {
	margin-top: 20px;
	overflow: auto;
	height: 100%;
}

.divssa {
	width:100px
}
.divssa2 {
	width:300px
}
.divssh {
	height: 200px;
}
.div-inline{ 
	margin-top: 15px;
	margin-left: 15px;
	width:400px;
	float:left;
} 
</style>
</head>
<body ng-app="app" ng-controller="ctrl">
	<div style="margin-top: 20px">
		<form  class="form-horizontal" role="form">
			<div  class="form-group">
				<label for="connect_host" class="col-sm-2 control-label divssa">IP</label> 
			    <div class="col-lg-2">
			    	<input type="text" class="form-control" id="connect_host">
			    </div>
			    <label for="connect_port" class="col-sm-2 control-label divssa">端口</label> 
			    <div class="col-lg-2">
			      	<input type="text" class="form-control" id="connect_port">
			    </div>
			    <div class="col-lg-2">
			    	 <input class="btn btn-default" type="button" value="连接" onclick="connect()">
					 <input class="btn btn-default" type="button" value="取消连接" onclick="disconnect()"> 
			    </div>
			     <label for="connect_port" class="col-sm-2 control-label divssa"> key值:</label> 
			    <div class="col-lg-2">
			    	 <input type="text" class="connect_key" id="connect_key" readOnly="true">
			    </div>
			  </div>
		</form>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="#info" data-toggle="tab">基本说明</a></li>
		<li><a href="#home" data-toggle="tab"> 用户登录测试</a></li>
		<li><a href="#node" data-toggle="tab">Node 测试</a></li>
		<li><a href="#property" data-toggle="tab">Property 测试</a></li>
		<li><a href="#accessMode" data-toggle="tab">set_access_mode 一问一答测试</a></li>
		<li><a href="#alarmMode" data-toggle="tab">send_alarm 告警模式设置测试</a></li>
		<li><a href="#setPoint" data-toggle="tab">写数据 测试</a></li>
		<li><a href="#getNodeAndPro" data-toggle="tab">联合测试</a></li>
	</ul>
	<div id="myTabContent" class="tab-content">
	<div class="tab-pane fade in active" id="info">
			<div class="list-group div-inline ">
			    <a href="#" class="list-group-item active">
			        <h4 class="list-group-item-heading">
			           	数据库配置
			        </h4>
			    </a>
			    <a href="#" class="list-group-item divssh">
			        <h4 class="list-group-item-text"> host：10.0.6.36</h4>
			        <h4 class="list-group-item-text"> port：27017</h4>
			        <h4 class="list-group-item-text"> 用户名：develop</h4>
			        <h4 class="list-group-item-text"> 密码：YYtd_develop</h4>	   
					<h4 class="list-group-item-text"> 数据库：scloud-develop</h4>
					<hr/>
			    </a>
			</div>
			<div class="list-group div-inline">
			    <a href="#" class="list-group-item active">
			        <h4 class="list-group-item-heading">
			           	FSU配置
			        </h4>
			    </a>
			    <a href="#" class="list-group-item divssh">
			        <h4 class="list-group-item-text">企业编码: TDYS</h4>
			        <h4 class="list-group-item-text">FSU id: 59408e3615bf135433292485</h4>
			        <h4 class="list-group-item-text">(相关的FSU IP,port配置在该FSU中)</h4>
			        <h4 class="list-group-item-text">(表：TDYS_fsu)</h4>
			    </a>
			</div>
			<div class="list-group div-inline">
			    <a href="#" class="list-group-item active">
			        <h4 class="list-group-item-heading">
			           	表说明1(接口接收到数据会保存到响应表中)
			        </h4>
			    </a>
			    <a href="#" class="list-group-item">
			        <h5 class="list-group-item-text">节点Node表：TDYS_c1_nodes</h5>
			        <h5 class="list-group-item-text">属性表 TSTATION: TDYS_c1_tstation</h5>
			        <h5 class="list-group-item-text">属性表 TDEVICE: TDYS_c1_tdevice</h5>
			        <h5 class="list-group-item-text">属性表 TAIC: TDYS_c1_taic</h5>
			        <h5 class="list-group-item-text">属性表 TAOC: TDYS_c1_taoc</h5>
			        <h5 class="list-group-item-text">属性表 TDIC: TDYS_c1_tdic</h5>
			        <h5 class="list-group-item-text">属性表 TDOC: TDYS_c1_tdoc</h5>
			        <h5 class="list-group-item-text">属性表 转换表: TDYS_c1_tstation 对应 TDYS_site</h5>
			        <h5 class="list-group-item-text">属性表 转换表: TDYS_c1_tdevice 对应 TDYS_device</h5>
			        <hr />
			        <h5 class="list-group-item-text">属性表 转换表:  TDYS_c1_taic/taoc/tdic/tdoc</h5>
			        <h5 class="list-group-item-text">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;对应 TDYS_signal</h5>
			        
			    </a>
			</div>
			<div class="list-group div-inline">
			    <a href="#" class="list-group-item active">
			        <h4 class="list-group-item-heading">
			           	表说明2(接口接收到数据会保存到响应表中)
			        </h4>
			    </a>
			    <a href="#" class="list-group-item">
			        <h5 class="list-group-item-text">实时数据表：TDYS_c1_data</h5>
			        <h5 class="list-group-item-text">转换表 TDYS_signal中的value值;一问一答</h5>
			        <h5 class="list-group-item-text">转换表 TDYS_history中的value值;客户端轮询一问一答接口</h5>
			        <hr />
			        <h5 class="list-group-item-text">告警表：TDYS_c1_talarm</h5>
			        <h5 class="list-group-item-text">转换表 ：TDYS_alarm</h5>
			        
			    </a>
			</div>
		</div>
		<div class="tab-pane fade" id="home">
			<h4>用户登录测试</h4>
			<form class="form-inline first">
				<div class="form-group">
					<label for="login_username">用户名</label> <input type="text"
						class="form-control" id="login_username">
				</div>
				<div class="form-group">
					<label for="login_password">密码</label> <input type="text"
						class="form-control" id="login_password">
				</div>
				<input class="btn btn-default" type="button" value="登录"
					onclick="login()"> |《》《》《》|
				<div class="form-group">
					<label for="login_value"></label>返回结果》用户权限: <input type="text"
						class="form-control" id="login_value" readOnly="true">
				</div>
			</form>
		</div>
		<div class="tab-pane fade divss" id="node">
			<h4>Node 测试=>获取所有NodeId</h4>
			<form class="form-inline first">
				<input class="btn btn-default" type="button" value="getNode"
					ng-click="getNode()">
			</form>
			<table id="cusTable" class="table table-bordered"
				style="height: 200px">
				<thead>
					<tr>
						<th>nodeId</th>
						<th>parentId</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="item in list">
						<td ng-bind="item.nodeId"></td>
						<td ng-bind="item.parentId"></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="tab-pane fade divssx" id="property">
			<h4>Property 测试=>获取 Property</h4>
			<form>
				<div class="form-group">
					<label for="pro_nodeId">nodeId:</label> <input type="text"
						class="form-control" id="pro_nodeId">
				</div>
				<input class="btn btn-default" type="button" value="getProperty"
					ng-click="getProperty()"> |《》《》《》|
			</form>
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>item.id</th>
						<th>具体内容</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="item in Propertylist">
						<td ng-bind="item.id"></td>
						<td ng-bind="item.str"></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="tab-pane fade" id="accessMode">
			<h4>实时数据测试=>一问一答</h4>
			<form class="form-inline first">
				<div class="form-group">
					<label for="accessMode_nodeId">nodeId:</label> <input type="text"
						class="form-control" id="accessMode_nodeId">
				</div>
				<input class="btn btn-default" type="button" value="请求"
					ng-click="accessMode()"> |《》《》《》|</br>
			</form>
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>item.id</th>
						<th>具体内容</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="item in accessModelist">
						<td ng-bind="item.id"></td>
						<td ng-bind="item.str"></td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<div class="tab-pane fade" id="alarmMode">
			<h4>告警模式设置测试=></h4>
			<form>
				<input class="btn btn-default" type="button" value="请求"
					ng-click="alarmMode()"> |《》《》《》|
				<div class="form-group">
					<label class="first" for="alarmMode_result">返回结果》: </label><input type="text"
						class="form-control" id="alarmMode_result" readOnly="true">
				</div>
			</form>
			<h4>查看数据库  告警  相关数据：</h4>
		</div>
		<div class="tab-pane fade" id="setPoint">
			<h4>写数据请求=></h4>
			<form class="form-inline first">
				<div class="form-group">
					<label for="setPoint_nodeId">nodeId:</label> <input type="text"
						class="form-control" id="setPoint_nodeId">
				</div>
				<div class="form-group">
					<label for="setPoint_value">value:</label> <input type="text"
						class="form-control" id="setPoint_value">
				</div>
				<input class="btn btn-default" type="button" value="请求"
					onclick="setPoint()"> |《》《》《》|
			</form>
			<div class="form-group">
					<label for="alarmMode_result">返回结果》: </label><input type="text"
						class="form-control divssa2" id="setPoint_result" readOnly="true">
			</div>
		</div>
		<div class="tab-pane fade" id="getNodeAndPro">
			<h4>联合测试(如已连接 请先断开连接)：</h4>
			<h5 style="color:red">(请先在 TDYS_fsu 中 修改 59408e3615bf135433292485 id的相关IP,端口,用户名,密码)</h5>
			<h5>1：FSU连接  = 用户登录</h5>
			<h5>2：通过getNode接口 取得 id列表</h5>
			<h5>3：根据不同的ID级别 获取响应的属性列表</h5>
			<h5>4：发送告警模式</h5>
			<form>
				<input class="btn btn-default" type="button" value="请求"
					onclick="getNodeAndPro()"> |《》《》《》|
				<div class="form-group">
					<label class="first" for="alarmMode_result">该接口sleep较多 请耐心等待(具体业务时将异步执行)返回结果》: </label>
					<input type="text" class="form-control" id="getNodeAndPro_result" readOnly="true">
				</div>
			</form>
			<h4>查看数据库相关数据：</h4>
			<h5>基本说明 >> 表说明 1,2;常用查询：</h5>
			<h5>db.TDYS_alarm.find({"c1_id":{$gt:0}})  //查询告警表</h5>
			<h5>db.TDYS_device.find({"c1_id":{$gt:0}}) //查询设备表</h5>	   
			<h5>db.TDYS_signal.find({"c1_id":{$gt:0}}) //查询信号表</h5>
			<h5>db.TDYS_c1_data.find({});</h5>
			<h5>db.TDYS_c1_nodes.find({});</h5>
			<h5>db.TDYS_c1_taic.find({});</h5>
			<h5>db.TDYS_c1_talarm..find({});</h5>
			<h5>db.TDYS_c1_taoc.find({});</h5>
			<h5>db.TDYS_c1_tdevice.find({});</h5>
			<h5>db.TDYS_c1_tdic.find({});</h5>
			<h5>db.TDYS_c1_tdoc.find({});</h5>
			<h5>db.TDYS_c1_tstation.find({});</h5>
		</div>
	</div>



</body>

<!-- Javascript -->
<script src="jquery-easyui/jquery-1.11.1.min.js"></script>
<script src="bootstrap3/js/bootstrap.min.js"></script>
<script src="angular-1.2.17/angular.js"></script>
<script src="bootstrap3/js/bootstrap-table.min.js"></script>
<script src="jquery-easyui/jquery.backstretch.min.js"></script>
<script src="js/url.js"></script>
<script src="js/scripts.js"></script>

</html>
