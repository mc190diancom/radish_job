$(function(){
	var hstate = function(state = 0){
	    var info = $(".interview_info");
	    if (state == 0) {
		info.children(".sub_title").css("display","none");
		$(".edit_btn").on("click",function(){
			history.go(-1);
		    });
	    } else{
			$(".edit_btn").text("准备");
			if (state == 2){
			    info.children(".title").text("离面试开始还有。。。");
				info.children(".title").css({"background":"#999","box-shadow":"none"});
			    $(".edit_btn").css("background","#999");
			    $(".edit_btn").on("click",function(){
					layer.open({
					    skin:'msg',
					    content:'面试还未开始',
					    time: 4
					})
			    })
				$(".edit_btn").text("已准备");
			}else if(state == 1){
			    $(".edit_btn").on("click",function(){
			    	$.ajax({
				    	type: "POST",
				        url: "http://www.luobosp.com/index.php/Home/Interview/SetInterviewState",
				        data: {'kname':'c_id'},
				        async: false,
				        dataType: 'jsonp',
				        jsonp : "callback",
				        jsonpCallback: "jsapi",
				        xhrFields: {withCredentials: true},
				        crossDomain: true,
				        success: function(msg) {
							layer.open({
							    skin:'msg',
							    content:'准备就绪',
							    time: 2
							});
							$(this).text("已准备");
							$(this).css("background","#e0387d");
				        }
				    });
			    });
			}else{

			}
	    }
	}
    
    $.ajax({
    	type: "POST",
        url: "http://www.luobosp.com/index.php/Home/Interview/InterviewGoto",
        data: {'kname':'c_id'},
        async: false,
        dataType: 'jsonp',
        jsonp : "callback",
        jsonpCallback: "jsapi",
        xhrFields: {withCredentials: true},
        crossDomain: true,
        success:function(msg) {
        	var l = '';
        	l += '<h4>面试公司 ： '+msg.data.q_name+'</h4>';
			l += '<h4>面试职位 : '+msg.data.post_name+'</h4>';
			l += '<h4>面试场编号 ： '+msg.data.number+'</h4>';
			l += '<h4>面试人 ： '+msg.data.name+'</h4>';
			l += '<h4>你的面试者编号 ： '+msg.data.interview_number+'</h4>';
			$(".content").html(l);
			if(msg.data.state == 3){
				hstate(0);
			}else if(msg.data.state == 1){
				if(msg.data.dnumber == null){
					$(".interview_info").children(".title").text("没有正在面试的人");
				}else{
					$(".interview_info").children(".title").text("当前面试者编号为"+msg.data.dnumber);
				}
				var l = '';
				l += '<h4>你前面还有'+msg.data.num+'个人</h4>';
				l += '<h4>预计你的面试开始时间为9:30</h4>';
				l += '<h4>请提前做好准备</h4>';
				$(".sub_title").html(l);
				hstate(1);
			}else if(msg.data.state == 2){
				var l = '';
				l += '<h4>你前面还有'+msg.data.num+'个人</h4>';
				l += '<h4>预计你的面试开始时间为9:30</h4>';
				l += '<h4>请提前做好准备</h4>';
				$(".sub_title").html(l);
				hstate(2);
			}
        }
    });
   
})
/*state 0 面试中
  state 1 面试开始，但还未轮到
  state 2 面试未开始*/