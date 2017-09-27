$(document).ready(function() {
    /*layer*/
    $(".resume_edit").on("click",function(){
	    layer.open({
		content: "请使用电脑访问<br />http://www.resume.com编辑简历",
		btn: "我知道了"
	    });
	}) //编辑简历
	$(".exit").on("click",function(){
	    layer.open({
		content: "是否退出登录",
		btn : ['是','否'],
		yes : function(index){
		    window.location.href='home.html';
		    layer.close(index);
		}
	    });
	}); //退出登录
	function clear_ca(){
	    layer.open({
		    content: '缓存清理完成'
		    ,skin: 'msg'
		    ,time: 200 //2秒后自动关闭
		  });
	}
	$(".clear_cache").on("click",function(){
	    layer.open({
		content: "是否清除缓存",
		btn : ['是','否'],
		yes : function(index){
		    clear_ca();
		    layer.close(index);
		}
	    });
	}); //清除缓存	
	
	
	$.ajax({
		url: "https://114.215.125.52/index.php/Home/Resume/Index",
		type: "POST",
		data: '',
		async: false,
		dataType: 'jsonp',
		jsonp : "callback",
		jsonpCallback: "jsapi",
		xhrFields: {withCredentials: true},
		crossDomain: true,
		success:function(msg){
			if(msg.status == 1){
				for (var i = 0; i < msg.data.length; i++) {
					if(i == 0){
						$(".panel-group").css('display','block');
					}else{
						$(".panel-group").css('display','none');
					}
					var l = '';
					l += '<div class="panel">';
						l += '<div class="panel_title">';
							l += '<i class="fa fa-user"></i>个人信息';
						l += '</div>';
						l += '<div class="panel_body">';
							l += '<div class="user_info main">';
								l += '<div class="title">';
								l += '<div class="img_outline">';
									if(msg.data[i].header_img){
										l += '<img class="headimg" src="https://114.215.125.52'+msg.data[i].header_img+'" alt="" />';
									}else{
										l += '<img src="img/user_logo/default.jpg" alt="默认头像" />';
									}
								l += '</div>';
								l += '<h3>'+msg.data[i].name+'</h3></div>';
								l += '<p><i class="fa fa-intersex fa-fw"></i>性别：'+msg.data[i].sex+'</p>';
								l += '<p><i class="fa fa-leaf fa-fw"></i>年龄：'+msg.data[i].birth+'</p>';
								l += '<p><i class="fa fa-bank fa-fw"></i>毕业院校：'+msg.data[i].edu_name+'</p>';
								l += '<p><i class="fa fa-graduation-cap fa-fw"></i>学历：'+msg.data[i].education+'</p>';
								l += '<p><i class="fa fa-map-marker fa-fw"></i>所在城市：'+msg.data[i].address+'</p>';
								l += '<p><i class="fa fa-mobile-phone fa-fw"></i>联系电话：'+msg.data[i].phone+'</p>';
								l += '<p><i class="fa fa-envelope-o fa-fw"></i>电子邮箱：'+msg.data[i].email+'</p>';
							l += '</div>';
						l += '</div>';
					l += '</div>';
					l += '<div class="panel">';
						l += '<div class="panel_title">';
							l += '<i class="fa fa-file-video-o"></i>视频简历';
						l += '</div>';
						l += '<div class="panel_body">';
							l += '<div class="panel_video">';
								if(msg.data[i].video != null && msg.data[i].video != ''){
									l += '<video src="https://114.215.125.52'+msg.data[i].video+'" controls poster="img/video_poster.jpg"></video>';
								}else{
									l += '<p>无视频简历</p>';
								}
							l += '</div>';
						l += '</div>';
					l += '</div>';
					l += '<div class="panel">';
						l += '<div class="panel_title">';
							l += '<i class="fa fa-handshake-o"></i>求职意愿';
						l += '</div>';
						l += '<div class="panel_body">';
							l += '<div class="list">';
								l += '<div class="list_item">';
									l += '<h3 class="sp"><span>'+msg.data[i].hope_work_vocation+'</span></h4>';
								l += '</div>';
							l += '</div>';
						l += '</div>';
					l += '</div>';
					l += '<div class="panel">';
						l += '<div class="panel_title">';
							l += '<i class="fa fa-graduation-cap"></i>教育经历';
						l += '</div>';
						l += '<div class="panel_body">';
							l += '<div class="list">';
								for (var x = 0; x < msg.data[i].edu_exp.length; x++) {
									l += '<div class="list_item">';
										l += '<h3>'+msg.data[i].edu_exp[x].edu_name+'</h3>';
										l += ''+msg.data[i].edu_exp[x].edu_start_time+' - '+msg.data[i].edu_exp[x].edu_end_time+'';
									l += '</div>';
								}
							l += '</div>';
						l += '</div>';
					l += '</div>';
					l += '<div class="panel">';
						l += '<div class="panel_title">';
							l += '<i class="fa fa-sun-o"></i>获奖情况';
						l += '</div>';
						l += '<div class="panel_body">';
							l += '<div class="list">';
								for (var a = 0; a < msg.data[i].schoolsituation.length; a++) {
									l += '<div class="list_item">';
										l += '<h4 class="sp">'+msg.data[i].schoolsituation[a].awards+'</h>';
										l += '<h4>'+msg.data[i].schoolsituation[a].awards_time+'</h4>';
									l += '</div>';
								}
							l += '</div>';
						l += '</div>';
					l += '</div>';
					l += '<div class="panel">';
						l += '<div class="panel_title">';
							l += '<i class="fa fa-telegram"></i>资格证书';
						l += '</div>';
						l += '<div class="panel_body">';
							l += '<div class="list">';
								for (var y = 0; y < msg.data[i].certificate.length; y++) {
									l += '<div class="list_item">';
										l += '<h4 class="sp">'+msg.data[i].certificate[y].certificate_name+'</h>';
										l += '<h4>'+msg.data[i].certificate[y].certificate_time+'</h4>';
									l += '</div>';
								}
							l += '</div>';
						l += '</div>';
					l += '</div>';
					l += '<div class="panel">';
						l += '<div class="panel_title">';
							l += '<i class="fa fa-file-pdf-o"></i>其他附件';
						l += '</div>';
					l += '</div>';
					
				}
				$(".panel-group").html(l);
			    backClick();
			    resumeEditClick();
			}
		}
	});
});