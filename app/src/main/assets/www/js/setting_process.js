$(document).ready(function(){
	//0全部  1面试  2被查看  3待沟通  4不合适
	//获取更多数据
	$('.click_more').unbind('click');
	$('.click_more').on('click',function() {
		var condition = $('.active').attr("value");
		var page = $(this).attr('value');
		var p = page*1+1;
		$(this).attr('value',p);
		Operation(condition,page);
	});
	//点击查询数据
	$(".condition").unbind('click');
	$(".condition").on('click',function(){
		$('.condition').removeClass('active');
		$(this).addClass('active');
		var condition = $(this).attr("value");
		Operation(condition);
	})
	function Operation(condition,page = 0) {
		$.ajax({
	        type: "POST",
	        url: "https://114.215.125.52/index.php/Home/Person/ResumeState",
	        data: {'k':condition,'p':page},
	        async: false,
		dataType: 'jsonp',
		jsonp : "callback",
		jsonpCallback: "jsapi",
	        xhrFields: {withCredentials: true},
	        crossDomain: true,
	        success:function(msg){
	        	if(msg.status == 1){
	        		var l = '';
	        		for (var i = 0; i < msg.data.length; i++) {
	        			l += '<div class="msg_panel job_into">';
							l += '<div class="l_part check" value="'+msg.data[i].post_id+'">';
								l += '<h3>'+msg.data[i].post_name+'<span>'+msg.data[i].post_salary+'</span></h3>';
								l += '<h4>'+msg.data[i].e_name+'</h4>';
							l += '</div>';
							l += '<div class="r_part handle">';
								if(msg.data[i].state == '0'){
									l += '<div class="item">待查看</div>';
								}else if(msg.data[i].state == '1'){
									l += '<div class="item c2">已查看</div>';
								}else if(msg.data[i].state == '2'){
									l += '<div class="item c3">待沟通</div>';
								}else if(msg.data[i].state == '5'){
									l += '<div class="item c4">不合适</div>';
								}else if(msg.data[i].state == '3'){
									l += '<div class="item c1 interview-invite">面试邀请</div>';
								}else if(msg.data[i].state == '4'){
									l += '<div class="item c1 interview" f_time="'+msg.data[i].invitation_time+'">面试</div>';
								}else{
									l += '<div class="item">已拒绝</div>';
								}
							l += '</div>';
						l += '</div>';
	        		}
	        		if(page == 0){
	        			$('.click_more').attr('value',1);
	        			$(".m_list").html(l);
	        			click_more_hide();
	        		}else{
	        			$(".m_list").append(l);
	        			click_more_hide();
	        		}
	        		CheckJob();
	        		AcceptClick();
	        		InterviewClick();
	        		interviewInvite();
	        	}else{
		        	layer.open({
			          content: msg.info,
			          skin: 'msg',
			          time: 1 //2秒后自动关闭
			        });
			        var l = '';
			        if(page == 0){
			        	$(".m_list").html(l);
			        	click_more_hide();
			        }
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
	}
	Operation(0);
	function interviewInvite(){
	    $(".interview-invite").on("click",function(){
		window.location.href = "msg.html";
	    })	
	}
})