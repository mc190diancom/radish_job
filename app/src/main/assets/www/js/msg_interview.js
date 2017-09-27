$(function() {
    var pd = 0;
    var page = 1;
    var terid = 0;
    
    //查看操作接受和拒绝
    function viewedit() {
        $(".r_part").children(".item").on("click",
        function() {

            erid = $(this).attr("data-erid");
            var t = $(this);
            //获取面试官ID
            var em_id = t.attr("data-emid");
            //获取面试公司
            var e_name = t.parent().prev().children('h3').text();
            //获取面试职位
            var p_name = t.parent().prev().children('p').text();
            //获取面试时间
            var i_time = t.parent().parent().prev().children().children('span').text();
            if (t.hasClass("fbd")) {
                return false;
            } else if (t.hasClass("c1")) {
                layer.open({
                    title: ['你收到一份同步面试邀请', 'background:#337ab7;color:#fff;'],
                    content: '面试公司：' + e_name + '<br />面试职位是：' + p_name + '<br/>面试时间：' + i_time,
                    btn: ['接受邀请', '拒绝邀请'],
                    yes: function(index) {
                	var state = 4;
                	accept(state,erid,i_time,e_name,em_id);
                        layer.open({
                            title: ['你已接受同步面试邀请', 'background:#337ab7;color:#fff;'],
                            content: '具体面试信息稍后将以信息方式通知，请注意查收',
                            btn: '我知道了'
                        })
                    },
                    no: function(index) {
                	var state = 6;
                	accept(state);
                        layer.open({
                            content: '你拒绝邀请',
                            skin: 'msg',
                            time: 1
                        })
                    }
                });
            } else if (t.hasClass("c3")) {
                layer.open({
                    title: ['同步面试', 'background:#337ab7;color:#fff;'],
                    content: '具体面试信息请在通知中查看',
                    btn: '关闭'
                });
            } else {
                return false;
            }
        });
    }
    
    //获取面试信息
    function interviewInfo() {
        $.ajax({
            type: "POST",
            url: 'https://114.215.125.52/index.php/Home/Interview/Invitation',
            data: {
                'sid': pd,
                'page': page
            },
            async: false,
            dataType: 'jsonp',
            jsonp: "callback",
            jsonpCallback: "jsapi",
            xhrFields: {
                withCredentials: true
            },
            crossDomain: true,
            success: function(msg) {
                var l = '';
                console.log(msg);
                if (msg.data.length > 0) {
                    for (var i = 0; i < msg.data.length; i++) {
                        if (msg.data[i].state == 3) {
                            var state = 'c1';
                        } else {
                            var state = 'c3';
                        }
                        l += '<div class="msg_panel_i" data-er-id="'+msg.data[i].id+'">' + '<div class="title">' + '<p>面试邀请 -  <span>' + msg.data[i].invitation_time + '</span></p>' + '</div>' + '<div class="body">' + '<div class="l_part">' + '<h3>' + msg.data[i].e_name + '</h3>' + '<p>' + msg.data[i].post_name + '</p>' + '</div>' + '<div class="r_part">' + '<div class="item ' + state + '" data-erid="'+msg.data[i].id+'" data-emid="'+msg.data[i].em_id+'">查看</div>' + '</div>' + '</div>' + '</div>';
                        
                    }
                }
                $('.m_list').append(l);
                viewedit();
                click_more_hide();
                isNow();//判定是否有面试场
            }
        })
    }
    interviewInfo();
    
    //接受和拒绝邀请
    function accept(state,erid,time,enname,emid){
	$.ajax({
            url: 'https://114.215.125.52/index.php/Home/Interview/Accept',
            data: {
                'erid': erid,
                'state': state,
                'time': time,
                'enname': enname,
                'emid': emid
            },
            async: false,
            dataType: 'jsonp',
            jsonp: "callback",
            jsonpCallback: "jsapi",
            xhrFields: {
                withCredentials: true
            },
            crossDomain: true,
            success: function(){
        	alert('接受');
            },
            error:function(){
        	alert('拒绝');
            }
	})
    }
    
    //是否有开始面试的面试场
    function isNow(){
	$.ajax({
            url: 'https://114.215.125.52/index.php/Home/Interview/InterviewIsNow',
            data: {
            },
            async: false,
            dataType: 'jsonp',
            jsonp: "callback",
            jsonpCallback: "jsapi",
            xhrFields: {
                withCredentials: true
            },
            crossDomain: true,
            success: function(msg){
        	    $(".interview_now").remove();
        	    console.log("data"+msg)
        	    for(var i = 0;i<msg.data.length;i++){
                	    var result = '<div class="interview_now" data-erid="'+msg.data[i].e_r_id+'">'
                			+'<div class="title">'
                				+'<div>准备面试</div>'
                				+'<div>'
                					+msg.data[i].interview_time
                				+'</div>'
                			+'</div>'
                			+'<div class="in">'
                				+'<div class="info">'+msg.data[i].e_name+' - <span>面试场：'+msg.data[i].number+'</span></div>'
                				+'<a href="javascript:;" class="btn enter">进入</a>'
                			+'</div>'
                		'</div>';
                	    $(".drop_content").prepend(result);
        	    } 
        	    enterInterviewScreen();
            },
            error:function(){
        	return;
            }
	});
    }
   
    
    //点击进入面试场
    function enterInterviewScreen(){
	$(".enter").on("click",function(){
	    var erid = $(this).parents(".interview_now").attr("data-erid");
	    $.ajax({
		 url: 'https://114.215.125.52/index.php/Home/Interview/enterInterviewScreen',
	         data: {'erid': erid},
	         async: false,
	         dataType: 'jsonp',
	         jsonp: "callback",
	         jsonpCallback: "jsapi",
	         xhrFields: {
	            withCredentials: true
	         },
	         crossDomain: true,
	         success:function(msg){
	             if(msg.status == 1) {
	        	 alert(msg.data);
	        	 window.location.href = "msg_interview_start.html";
	             }else{
	        	 layer.open({
	        	     content:'网络堵塞,请稍后',
	        	     skin:'msg',
	        	     time:2
	        	 })
	             }
	         }
	    })
            alert(erid);
	});
    }
    
    //隐藏显示点击更多
    function click_more_hide(){
	var c = $(".m_list").children().length;
        if (c < 10){
            $(".click_more").hide();
        }else{
            $(".click_more").show();
        }
    }
    
    //加载更多
    $(".click_more").on("click",
    function() {
        page++;
        interviewInfo();
    })

    //点击tab切换
    $(".tab_status").children(".item").on("click",
    function() {
        $(this).addClass("active").siblings().removeClass("active");
        pd = $(".tab_status > .active").index();
        $('.m_list').html('');
        page = 1;
        interviewInfo();
    })
   
    //开始面试
    function enter_interview(){
	$.ajax({
	    url: 'https://114.215.125.52/index.php/Home/Interview/EnterInterview',
            data: {
                'terid': terid,
                'state': state
            },
            async: false,
            dataType: 'jsonp',
            jsonp: "callback",
            jsonpCallback: "jsapi",
            xhrFields: {
                withCredentials: true
            },
            crossDomain: true,
            success: function(){
        	alert('接受');
            },
	})
    }
});