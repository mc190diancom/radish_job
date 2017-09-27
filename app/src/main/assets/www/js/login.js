$(document).ready(function(){
	$(document).on('click','.BtnLogin',function() {
		var data = $('#pasd').serialize();
		var Url = 'https://114.215.125.52/index.php/Home/Login/Login2';
		$.ajax({
		    type: "POST",
		    url: Url,
		    data: data,
		    async: false,
		    dataType: 'jsonp',
		    jsonp : "callback",
		    jsonpCallback: "jsapi",
		    xhrFields: {withCredentials: true},
		    crossDomain: true,
		    success:function(msg){
			console.log(msg);
			    if(msg.status){
			        layer.open({
			          	content: msg.info,
			          	skin: 'msg',
			          	time: 2 //2秒后自动关闭
			        });
			        window.location.href = 'index.html';
			    }else{
			        layer.open({
			          	content: msg.info,
			          	skin: 'msg',
			          	time: 2 //2秒后自动关闭
			        });
			    }
		    },
		    error:function(msg){
		      	layer.open({
		        	content: '网络错误',
		        	skin: 'msg',
		        	time: 2 //2秒后自动关闭
		      	});
		    }
	  	});
	});
});