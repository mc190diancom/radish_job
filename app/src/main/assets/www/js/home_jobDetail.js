$(document).ready(function(){
	$.ajax({
		url: "https://114.215.125.52/index.php/Home/Index/Position",
		type: "POST",
		data: {'kname':'job_id'},
		async: true,
		dataType: 'jsonp',
		jsonp : "callback",
		jsonpCallback: "jsapi",
		xhrFields: {withCredentials: true},
		crossDomain: true,
		success:function(msg){
			if(msg.status){
				$(".delivery").attr('value',msg.data.id);
				var l = '';
				l += '<h3>'+msg.data.post_name+'</h3>';
				l += '<h4><i class="fa fa-bookmark"></i>'+msg.data.post_salary+'</h4>';
				l += '<p>';
				l += '<span class="icon-location"></span><span>'+msg.data.work_address+'</span>';
				l += '<span class="fa fa-mortar-board"></span><span>'+msg.data.post_education+'</span>';
				l += '<span class="icon-clock"></span><span>'+msg.data.post_exp+'</span>';
				l += '</p>';
				$(".m_left").html(l);
				//企业信息
				var l = '';
				l += '<div class="m_part1">';
				l += '<div class="img_outline">';
				if(msg.data.logo){
					l += '<img src="https://114.215.125.52/'+msg.data.logo+'" alt="" />';
				}else{
					l += '<img src="img/company_logo/00001.png" alt="" />';
				}
				l += '</div>';
				l += '</div>';
				l += '<div class="m_part2 company_into">';
				l += '<h3>'+msg.data.name+'</h3>';
				l += '<div class="info_detail clearfix">';
				l += '<p>';
				l += '<span>'+msg.data.address+'</span>';
				l += '</p>';
				l += '<p>';
				l += '<span>'+msg.data.industry_id+'</span>';
				l += '<span>'+msg.data.kind_id+'</span>';
				l += '<span>'+msg.data.scale+'</span>';
				l += '</p>';
				l += '</div>';
				l += '</div>';
				$(".company").html(l);
				//职位描述与要求
				var l = '';
				l +='<h4>岗位描述:</h4>';
				l += msg.data.post_describe;
				l +='<h4>岗位要求:</h4>';
				l += msg.data.post_job_requirements;
				DeliveryClick();
				jopCollection();
				$('.worklist').html(l);
			}else{
				layer.open({
					content: msg.info,
					skin: 'msg',
					time: 1 //2秒后自动关闭
				});
			}
			if(msg.data.job_collect == 1){
				$(".m_right>span").text("已收藏")
			}
		},
		error:function(){
			layer.open({
				content: '网络错误',
				skin: 'msg',
				time: 1 //2秒后自动关闭
			});
		}
	});

	//用户投递简历
	function DeliveryClick() {
		$(".delivery").unbind('click');
		$(".delivery").on('click',function(){
			var p_id = $(this).attr('value');
			$.ajax({
				url: "https://114.215.125.52/index.php/Home/Index/ResumeOperation",
				type: "POST",
				data: {'p_id':p_id},
				async: false,
				dataType: 'jsonp',
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
				},
				error:function(){
		   			layer.open({
		                content: '网络错误',
		                skin: 'msg',
		                time: 1 //2秒后自动关闭
		            });
				}
			});
		});
	}
	//职位收藏
	function jopCollection(){
		$(".m_right").on("click",function(){
			$.ajax({
				url:"https://114.215.125.52/index.php/Home/Person/JobCollect",
				type:"post",
				data:{'kname':'job_id'},
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

});