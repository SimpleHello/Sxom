
	var outUrl = Urlhead + "inter/outer/";
	
	function login(){
		$("#login_value").val("");
		$.ajax({
			type : 'POST',
			url : outUrl+"login.do",
			success : function(result) {
				var res = result.success;
				if(res){
					$("#login_value").val(result.data.rigthMode);
				}else{
					alert(result.info);
				}
			}
		});
	}
	
	function loginOut(){
		$("#loginout_value").val("");
		$.ajax({
			type : 'POST',
			url : outUrl+"loginOut.do",
			success : function(result) {
				var res = result.success;
				if(res){
					$("#loginout_value").val(result.data);
				}else{
					alert(result.info);
				}
			}
		});
	}

	function synchronousProperty(){
		$("#syn_value").val("");
		$.ajax({
			type : 'POST',
			url : outUrl+"synchronousProperty.do",
			success : function(result) {
				var res = result.success;
				if(res){
					$("#syn_value").val(result.data);
				}else{
					alert(result.info);
				}
			}
		});
	}
	
	function setProperty() {
		$("#pro_result").val("");
		var id = $("#pro_signalId").val();
		var value = $("#pro_value").val();
		$.ajax({
			type : 'POST',
			url : outUrl+"setProperty.do",
			data : {
				"id" : id,
				"value" : value
			},
			success : function(result) {
				var res = result.success;
				if(res){
					$("#pro_result").val(result.data);
				}else{
					alert(result.info);
				}
			},
			dataType : "json"
		});
	}
	
	function accessMode() {
		$("#access_value").val("");
		var id = $("#access_deviceId").val();
		$.ajax({
			type : 'POST',
			url : outUrl+"getAccessDate.do",
			data : {
				"id" : id
			},
			success : function(result) {
				var res = result.success;
				if(res){
					$("#access_value").val(result.data);
				}else{
					alert(result.info);
				}
			},
			dataType : "json"
		});
	}
	
	function setPoint() {
		$("#point_result").val("");
		var id = $("#point_signalId").val();
		var value =  $("#point_value").val();
		$.ajax({
			type : 'POST',
			url : outUrl+"setPoint.do",
			data : {
				"id" : id,
				"value":value
			},
			success : function(result) {
				var res = result.success;
				if(res){
					$("#point_result").val(result.data);
				}else{
					alert(result.info);
				}
			},
			dataType : "json"
		});
	}
	
	function historyStart(type){
		var urljob = outUrl+"startHistory.do";
		if(type==1){
			urljob = outUrl+"stopHistory.do";
		}
		$.ajax({
			type : 'POST',
			url : urljob,
			success : function(result) {
				var res = result.success;
				if(res){
					$("#histotyJob_result").val(result.data);
				}else{
					alert(result.info);
				}
			},
			dataType : "json"
		});
	}
	
	function proStart(type){
		var urljob = outUrl+"startProperty.do";
		if(type==1){
			urljob = outUrl+"stopProperty.do";
		}
		$.ajax({
			type : 'POST',
			url : urljob,
			success : function(result) {
				var res = result.success;
				if(res){
					$("#proJob_result").val(result.data);
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
			url : outUrl+"getNodeAndPro.do",
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
	