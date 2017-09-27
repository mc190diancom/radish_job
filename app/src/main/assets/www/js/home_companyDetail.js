$(document).ready(function(){
	$.ajax({
		url: "https://114.215.125.52/index.php/Home/Index/EntDetail",
		type: "POST",
		data: {'kname':'company_id'},
		async: false,
		dataType: 'jsonp',
		jsonp : "callback",
		jsonpCallback: "jsapi",
		xhrFields: {withCredentials: true},
		crossDomain: true,
		success:function(msg){
			if(msg.status == 1){
				if(msg.data.logo){
					var l = '';
					l += '<img src="https://114.215.125.52'+msg.data.logo+'" alt="企业LOGO" />';
					$("#imgLogo").html(l);
				}
				$("#cname").html(msg.data.name);
				$("#address").html(msg.data.address);
				$("#industry").html(msg.data.industry_id);
				$("#kind").html(msg.data.kind_id);
				$("#scale").html(msg.data.scale);
				$("#num").html(msg.data.num);
				$("#introdution").html(msg.data.introdution);
				if(msg.data.kiro) {
					var l = '';
					l += '<video src="https://114.215.125.52' + msg.data.kiro + '" controls></video>';
					$("#move").html(l);

				}else{
						l += '<video src="video/00001.mp4" controls></video>';

				}
				$("#addresslist").html(msg.data.address);
				$("#contacts").html(msg.data.contacts);
				$("#mobile").html(msg.data.mobile);
				$("#email").html(msg.data.email);
				$("#zip_code").html(msg.data.zip_code);
				var l = '';
				for (var i = 0; i < msg.data.list.length; i++) {
					l+= '<div class="job_item job_into" value="'+msg.data.list[i].id+'">';
					l+= '<div class="main_body clearfix">';
					l+= '<div class="m_left">';
					l+= '<h3>'+msg.data.list[i].post_name+'</h3>';
					l+= '<p>';
					l+= '<span class="icon-location"></span><span>'+msg.data.list[i].work_address+'</span>';
					l += '<span class="fa fa-mortar-board"></span><span>'+msg.data.list[i].post_education+'</span>';
					l += '<span class="icon-clock"></span><span>'+msg.data.list[i].post_exp+'</span>';
					l+= '</p>';
					l+= '</div>';
					l+= '<div class="m_right">';
					l+= '<h2>'+msg.data.list[i].post_salary+'</h2>';
					l+= '</div>';
					l+= '</div>';
					l+= '</div>';
				}
				EnterpriseCollection()
				$(".clsit").html(l);
				ClickJob();
			}else{
				layer.open({
					content: msg.info,
					skin: 'msg',
					time: 1 //2秒后自动关闭
				});
			}
			if(msg.data.enterprise_collect == 1){
				$(".m_part3>span").text("已收藏")
			}
		},
		error:function(msg){
			layer.open({
				content: '网络错误',
				skin: 'msg',
				time: 1 //2秒后自动关闭
			});
		}
	});


	//企业收藏
	$(".m_part3").on("click",function(){
		$.ajax({
			url:"https://114.215.125.52/index.php/Home/Person/EnterpriseCollect",
			type:"post",
			data:{'kname':'company_id'},
			dataType:"jsonp",
			async: false,
			jsonp : "callback",
			jsonpCallback: "jsapi",
			xhrFields: {withCredentials: true},
			crossDomain: true,
			success:function(msg){
				layer.open({
					content: msg.info,
					skin: 'msg',
					time: 1 //2秒后自动关闭
				});
				$(".m_part3>span").text("已收藏")
			},
			error:function(){
				layer.open({
					content: '网络错误',
					skin: 'msg',
					time: 1 //2秒后自动关闭
				});
			}
		})
	})
	//职位收藏
	function EnterpriseCollection(){
		$(".m_right").on("click",function(){
			$.ajax({
				url:"https://114.215.125.52/index.php/Home/Person/EnterpriseCollect",
				type:"post",
				data:{'kname':'en_id'},
				dataType:"jsonp",
				async: true,
				jsonp : "callback",
				jsonpCallback: "jsapi",
				xhrFields: {withCredentials: true},
				crossDomain: true,
				success:function(msg){
					layer.open({
						content: msg.info,
						skin: 'msg',
						time: 1 //2秒后自动关闭
					});
					$(".m_right>span").text("已收藏");
				},
				error:function(){
					layer.open({
						content: '网络错误',
						skin: 'msg',
						time: 1 //2秒后自动关闭
					});
				}
			})
		})
	}

})