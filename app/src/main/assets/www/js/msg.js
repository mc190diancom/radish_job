$(document).ready(function(){
	$.ajax({
		type: "POST",
		url: "https://114.215.125.52/index.php/Home/Interview/MsgIndex",
		data: '',
		async: false,
		dataType: 'jsonp',
		jsonp : "callback",
		jsonpCallback: "jsapi",
		xhrFields: {withCredentials: true},
		crossDomain: true,
		success:function(msg){
			if(msg.status == 1){
				$("#sysNum").html(msg.data.sys_num);
				$("#viNum").html(msg.data.vi_num);
			}else{
	   			layer.open({
	                content: msg.info,
	                skin: 'msg',
	                time: 1 //2秒后自动关闭
	            });
			}
		},
	});
});