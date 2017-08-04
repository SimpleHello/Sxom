var init = function() {
	var url = "ws://10.0.6.135:61614/stomp";
	var login = "admin";
	var passcode = "admin";
	// 这个就是监听的队列
	destination = "quartzReceiveQuene";
	sendQueue = "";
	client = Stomp.client(url);
	userName = "mag";
	// this allows to display debug logs directly on the web page
	client.debug = function(str) {
		$("#debug").append(document.createTextNode(str + "\n"));
	};
	// the client is notified when it is connected to the server.
	var onconnect = function(frame) {
		client.debug("connected to Stomp");
		$('#connect').fadeOut({
			duration : 'fast'
		});
		$('#disconnect').fadeIn();
		$('#send_form_input').removeAttr('disabled');

		client.subscribe(destination, function(message) {
			var headers = message.headers;
			// 这个from就是我新设置的参数名称，放在message里面的
			var from = headers.from;
			// 这个方法是用来做提醒的，后面会讲到
			showMessage(message, from);
		});
	};
	client.connect(login, passcode, onconnect);

	return false;
}