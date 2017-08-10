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

.w300{
	
	width:300px !important;
}

.w500{
	width:500px !important;
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
<body>
	<div style="margin-top: 20px;margin-left: 15px">
		<h4><a href="index.jsp">去 -> 单元测试</a></h4>
	</div>
	<div style="margin-top: 20px;margin-left: 15px">
		<h5>一切数据从 数据库关联 FSU id:59408e3615bf135433292485 ( TDYS_fsu 表) </h5>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="#getNodeAndPro" data-toggle="tab">联合测试</a></li>
		<li><a href="#login" data-toggle="tab">用户登陆</a></li>
		<li><a href="#loginOut" data-toggle="tab">用户登出</a></li>
		<li><a href="#synchronousProperty" data-toggle="tab">同步全局属性</a></li>
		<li><a href="#getAccessDate" data-toggle="tab">同步设备实时数据</a></li>
		<li><a href="#job" data-toggle="tab">轮询任务测试</a></li>
	</ul>
	<div id="myTabContent" class="tab-content">
		<div class="tab-pane fade  in active" id="getNodeAndPro">
			<h4>联合测试(如已连接 请先断开连接)<a href="#loginOut" data-toggle="tab">用户登出</a>：</h4>
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
					<input type="text" class="form-control  w500" id="getNodeAndPro_result" readOnly="true">
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
		<div class="tab-pane fade" id="login">
			<h4>用户登录测试(请先在 TDYS_fsu 中 修改 59408e3615bf135433292485 id的相关IP,端口,用户名,密码)</h4>
			<form class="form-inline first">
				<input class="btn btn-default" type="button" value="登录" onclick="login()"> |《》《》《》|
				<div class="form-group">
					<label for="login_value"></label>返回结果》用户权限: <input type="text"
						class="form-control w500" id="login_value" readOnly="true">
				</div>
			</form>
		</div>
		<div class="tab-pane fade divss" id="loginOut">
			<h4>用户登出</h4>
			<form class="form-inline first">
				<input class="btn btn-default" type="button" value="登出" onclick="loginOut()"> |《》《》《》|
				<div class="form-group">
					<label for="loginout_value"></label>返回结果》: <input type="text"
						class="form-control w500" id="loginout_value" readOnly="true">
				</div>
			</form>
		</div>
		<div class="tab-pane fade divssx" id="synchronousProperty">
			<h4>同步全局属性</h4>
			<form class="form-inline first">
				<input class="btn btn-default" type="button" value="同步" onclick="synchronousProperty()"> |《》《》《》|
				<div class="form-group">
					<label for="syn_value"></label>返回结果: <input type="text"
						class="form-control w500" id="syn_value" readOnly="true">
				</div>
			</form>
			<h4>修改属性(请先在 TDYS_signal 中 确定需要 修改的 ID。。c1_id 不能为空)</h4>
			<form class="form-inline first">
				<div class="form-group">
					<label for="pro_signalId">signalId:</label> <input type="text"
						class="form-control w300" id="pro_signalId">
				</div>
				<div class="form-group">
					<label for="pro_value">修改阀值:</label> <input type="text"
						class="form-control" id="pro_value">
				</div>
				<input class="btn btn-default" type="button" value="修改" onclick="setProperty()"> |《》《》《》|
				<div class="form-group">
					<label for="pro_result"></label>返回结果: <input type="text"
						class="form-control  w500" id="pro_result" readOnly="true">
				</div>
			</form>
		</div>
		<div class="tab-pane fade" id="getAccessDate">
			<h4>同步设备实时数据(请先在 TDYS_device 中 确定需要 修改的 ID。。c1_id 不能为空)</h4>
			<h4 style="color:red">(同步该设备下所有信号点的 实时数据 以设备为单位)</h4>
			<form class="form-inline first">
				<div class="form-group">
					<label for="access_deviceId">deviceId:</label> <input type="text"
						class="form-control w300" id="access_deviceId">
				</div>
				<input class="btn btn-default" type="button" value="实时数据请求"
					onclick="accessMode()"> |《》《》《》|</br>
				<div class="form-group">
					<label for="access_value"></label>返回结果: <input type="text"
						class="form-control w500" id="access_value" readOnly="true">
				</div>	
			</form>
			<h4>修改实时数据(请先在 TDYS_signal 中 确定需要 修改的 ID。。c1_id 不能为空)</h4>
			<form class="form-inline first">
				<div class="form-group">
					<label for="point_signalId">signalId:</label> <input type="text"
						class="form-control w300" id="point_signalId">
				</div>
				<div class="form-group">
					<label for="point_value">修改值:</label> <input type="text"
						class="form-control" id="point_value">
				</div>
				<input class="btn btn-default" type="button" value="修改" onclick="setPoint()"> |《》《》《》|
				<div class="form-group">
					<label for="point_result"></label>返回结果: <input type="text"
						class="form-control w500" id="point_result" readOnly="true">
				</div>
			</form>
		</div>
		
		<div class="tab-pane fade" id="job">
			<h4>历史数据 -> 任务测试</h4>
			<form class="form-inline first">
				<input class="btn btn-default" type="button" value="启动" onclick="historyStart(0)">
				<input class="btn btn-default" type="button" value="停止" onclick="historyStart(1)">
				<div class="form-group">
					<label class="first" for="alarmMode_result">返回结果》: </label><input type="text"
						class="form-control w500" id="histotyJob_result" readOnly="true">
				</div>
			</form>
			<h4>属性修改 -> 任务测试</h4>
			<form class="form-inline first">
				<input class="btn btn-default" type="button" value="启动" onclick="proStart(0)">
				<input class="btn btn-default" type="button" value="停止" onclick="proStart(1)">
				<label class="first" for="alarmMode_result">返回结果》: </label>
				<input type="text" class="form-control  w500" id="proJob_result" readOnly="true">

			</form>
		</div>

</body>

<!-- Javascript -->
<script src="jquery-easyui/jquery-1.11.1.min.js"></script>
<script src="bootstrap3/js/bootstrap.min.js"></script>
<script src="angular-1.2.17/angular.js"></script>
<script src="bootstrap3/js/bootstrap-table.min.js"></script>
<script src="jquery-easyui/jquery.backstretch.min.js"></script>
<script src="js/url.js"></script>
<script src="js/scripts2.js"></script>

</html>
