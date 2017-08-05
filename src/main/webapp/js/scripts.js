	$("#connect_host").val("10.0.6.135");
	$("#connect_port").val("1234");
	$("#setPoint_nodeId").val("848562389");
	$("#setPoint_value").val("5");
	
	
	/*
	 * Fullscreen background
	 */
	function connect() {
		$("#connect_key").val("");
		var host = $("#connect_host").val();
		var port = $("#connect_port").val();
		$.ajax({
			type : 'POST',
			url : Urlhead + "inter/test/connect.do",
			data : {
				"host" : host,
				"port" : port
			},
			success : function(result) {
				var res = result.success;
				if(res){
					$("#connect_key").val(result.data);
				}else{
					alert(result.info);
				}
			},
			dataType : "json"
		});
	}
	
	function disconnect() {
		$("#connect_key").val("");
		var host = $("#connect_host").val();
		var port = $("#connect_port").val();
		$.ajax({
			type : 'POST',
			url : Urlhead + "inter/test/disconnect.do",
			data : {
				"host" : host,
				"port" : port
			},
			success : function(result) {
				var res = result.success;
				if(res){
					alert(result.data);
				}else{
					alert(result.info);
				}
			},
			dataType : "json"
		});
	}

	function login(){
		var username = $("#login_username").val();
		var password = $("#login_password").val();
		var key = $("#connect_key").val();
		$.ajax({
			type : 'POST',
			url : Urlhead + "inter/test/login.do",
			data : {
				"username" : username,
				"password" : password,
				"key" : key
			},
			success : function(result) {
				var res = result.success;
				if(res){
					$("#login_value").val(result.data.rigthMode);
				}else{
					alert(result.info);
				}
			},
			dataType : "json"
		});
	}

	function setPoint(){
		var id = $("#setPoint_nodeId").val();
		var value = $("#setPoint_value").val();
		var key = $("#connect_key").val();
		$.ajax({
			type : 'POST',
			url : Urlhead + "inter/test/setPoint.do",
			data : {
				"id" : id,
				"value" : value,
				"key" : key
			},
			success : function(result) {
				var res = result.success;
				if(res){
					$("#setPoint_result").val(result.data);
				}else{
					alert(result.info);
				}
			},
			dataType : "json"
		});
	}
	
	function getNodeAndPro(){
		$.ajax({
			type : 'POST',
			url : Urlhead + "inter/test/getNodeAndPro.do",
			success : function(result) {
				var res = result.success;
				if(res){
					$("#getNodeAndPro_result").val(result.data);
				}else{
					alert(result.info);
				}
			},
			dataType : "json"
		});
	}
	var app = angular.module('app', []);
	app.controller('ctrl', function($scope, $http) {
	    $scope.getNode = function(){
	    	var key = $("#connect_key").val();
			$.ajax({
				type : 'POST',
				url : Urlhead + "inter/test/getNode.do",
				data : {
					"key" : key
				},
				success : function(result) {
					var res = result.success;
					if(res){
						$scope.list = result.data;
						$scope.$apply();
					}else{
						alert(result.info);
					}
				},
				dataType : "json"
			});
	    },
	    $scope.getProperty = function(){
	    	var key = $("#connect_key").val();
	    	var nodeId = $("#pro_nodeId").val();
			$.ajax({
				type : 'POST',
				url : Urlhead + "inter/test/getProperty.do",
				data : {
					"key" : key,
					"nodeId":nodeId
				},
				success : function(result) {
					var res = result.success;
					if(res){
						$scope.Propertylist = result.data;
						$scope.$apply();
					}else{
						alert(result.info);
					}
				},
				dataType : "json"
			});
	    },
	    $scope.accessMode = function(){
	    	var key = $("#connect_key").val();
	    	var nodeId = $("#accessMode_nodeId").val();
			$.ajax({
				type : 'POST',
				url : Urlhead + "inter/test/accessMode.do",
				data : {
					"key" : key,
					"nodeId":nodeId
				},
				success : function(result) {
					var res = result.success;
					if(res){
						$scope.accessModelist = result.data;
						$scope.$apply();
					}else{
						alert(result.info);
					}
				},
				dataType : "json"
			});
	    },
	    $scope.alarmMode = function(){
	    	var key = $("#connect_key").val();
			$.ajax({
				type : 'POST',
				url : Urlhead + "inter/test/alarmMode.do",
				data : {
					"key" : key
				},
				success : function(result) {
					var res = result.success;
					if(res){
						$("#alarmMode_result").val(result.data);
					}else{
						alert(result.info);
					}
				},
				dataType : "json"
			});
	    }
	});
