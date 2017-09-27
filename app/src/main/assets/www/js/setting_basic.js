$(document).ready(function(){
	$(document).on('click','.Edit',function() {
		var data = $('form').serialize();
		var options = {
			url: "https://114.215.125.52/index.php/Home/Person/EditResume",
			dataType: 'json',
			xhrFields: {withCredentials: true},
			crossDomain: true,
			success: function(msg) {
				console.log(msg);
				if(msg.status == 1){
					layer.open({
		              	content: msg.info,
		              	skin: 'msg',
		             	time: 2 //2秒后自动关闭
		            });
				}else{
					layer.open({
		              	content: msg.info,
		              	skin: 'msg',
		             	time: 2 //2秒后自动关闭
		            });
				}
			}
		}
		$('#information').ajaxForm(options);
	});
	//获取数据
	$.ajax({
        url:'https://114.215.125.52/index.php/Home/Person/GetResume',
        type:'post',
        dataType:'jsonp',
        async:true,
        jsonp:'callback',
        jsonpCallback:'jsapi',
        xhrFields: {withCredentials: true},
        crossDomain: true,
        success:function(msg){
        	if(msg.data.header_img != ''){
        		$(".user-image > img").attr('src','https://114.215.125.52'+msg.data.header_img);
        	}
        	$("input[name='name']").val(msg.data.name);
        	$("input[name='sex']").eq(msg.data.sex).attr("checked",true);
        	$("input[name='birth']").val(msg.data.birth);
        	$("input[name='phone']").val(msg.data.phone);
        	$("input[name='email']").val(msg.data.email);
        	$("input[name='hope_work_vocation']").val(msg.data.hope_work_vocation);
        }
    });
})