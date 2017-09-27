$(document).ready(function(){
	$.ajax({
		type: "POST",
		url: "https://114.215.125.52/index.php/Home/Person/Index",
		data: '',
		async: false,
		dataType: 'jsonp',
		jsonp : "callback",
		jsonpCallback: "jsapi",
		xhrFields: {withCredentials: true},
		crossDomain: true,
		success: function(msg){
			$("#header_img").attr('src','https://114.215.125.52/'+msg.data.header_img);
			if(msg.data.name){
				var l = '<h3>'+msg.data.name+'</h3>';
				$('.m_body').html(l);
			}
			$("#cnum").text(msg.data.CollectNum);
			console.log(msg);
		},
		error: function(msg) {
			layer.open({
                content: '网络错误',
                skin: 'msg',
                time: 1 //2秒后自动关闭
            });
		}
	});

	//用户退出操作
	$(document).on('click','.exit',function(){
		$.ajax({
			type: "POST",
			url: "https://114.215.125.52/index.php/Home/Login/LogOut",
			data: '',
			async: false,
			dataType: 'jsonp',
			jsonp : "callback",
			jsonpCallback: "jsapi",
			xhrFields: {withCredentials: true},
			crossDomain: true,
			success: function(msg){
				if(msg.status == 1){
					layer.open({
						content: msg.info,
						skin: 'msg',
						time: 1
					});
					window.location.href = 'login.html';
				}else{
					layer.open({
						content: '网络错误',
						skin: 'msg',
						time: 1
					});
				}
			},
			error: function(msg) {
				layer.open({
	                content: '网络错误',
	                skin: 'msg',
	                time: 1
	            });
			}
		});
	});
})