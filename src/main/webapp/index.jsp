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

</style>
</head>
<body ng-app="app" ng-controller="ctrl">
	<div>
		<form class="form-inline first">
			<div class="form-group">
				<label for="connect_host">IP</label> <input type="text"
					class="form-control" id="connect_host">
			</div>
			<div class="form-group">
				<label for="connect_port">端口</label> <input type="text"
					class="form-control" id="connect_port">
			</div>
			<input class="btn btn-default" type="button" value="连接"
				onclick="connect()">
				<input class="btn btn-default" type="button" value="取消连接"
				onclick="disconnect()"> |《》《》《》|
			<div class="form-group">
				<label for="connect_port"> key值:</label> <input type="text"
					class="connect_key" id="connect_key" readOnly="true">
			</div>
		</form>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="#home" data-toggle="tab"> 用户登录测试</a></li>
		<li><a href="#node" data-toggle="tab">Node 测试</a></li>
		<li><a href="#property" data-toggle="tab">Property 测试</a></li>
		<li><a href="#accessMode" data-toggle="tab">set_access_mode 一问一答测试</a></li>
		<li><a href="#alarmMode" data-toggle="tab">send_alarm 告警模式设置测试</a></li>
	</ul>
	<div id="myTabContent" class="tab-content">
		<div class="tab-pane fade in active" id="home">
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
