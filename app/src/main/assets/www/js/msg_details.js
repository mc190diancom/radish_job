$(document).ready(function(){
	function NList(page = 0){
		$.ajax({
			type: "POST",
			url: "https://114.215.125.52/index.php/Home/Interview/NotifyLists",
			data: {'p':page},
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
						l += '<div class="msg_panel msg_detail" value="'+msg.data[i].id+'">';
							l += '<div class="l_part">';
								l += '<h3>'+msg.data[i].title+'</h3>';
								l += '<p>'+msg.data[i].content+'</p>';
							l += '</div>';
							l += '<div class="r_part">';
								if(msg.data[i].state == 0){
									l += '<div class="item">待查看</div>';
								}else{
									l += '<div class="item c2">已查看</div>';
								}
							l += '</div>';
						l += '</div>';
					}
					MoreClick();
					if(page == 0){
						$('.more').attr('value',1);
						$('.m_list').html(l);
					}else{
						$('.m_list').append(l);
					}
					SysClick();
				}else{
		   			layer.open({
		                content: msg.info,
		                skin: 'msg',
		                time: 1 //2秒后自动关闭
		            });
				}
			},
		});
	}
	NList();
	//点击查看信息
	function SysClick() {
		$(".msg_detail").unbind("click");
		$(".msg_detail").on("click",function(){
			var sid = $(this).attr('value');
			$.post("https://114.215.125.52/index.php/Home/Interview/SetSysState",{'sid':sid},function(){});
			var title = $(this).children(".l_part").children("h3").text();
			var content = $(this).children(".l_part").children("p").text();
			layer.open({
			    style:'',
			    title: [
				title,
				'border-bottom:1px solid #eee;'
				],
			    content: content,
			    btn: '我知道了'
			})
			var status = $(this).children(".r_part").children(".item");
			if(!status.hasClass("c2")){
			    status.addClass("c2");
			    status.text("已查看");
			}
	    });
	}
	//点击加载更多内容
	function MoreClick() {
		$(".click_more").unbind('click');
		$(".click_more").on('click',function() {
			var p = $(this).attr('value');
			var v = p*1+1;
			$(this).attr('value',v);
			NList(p);
		});
	}
});

//10条以上信息显示
var num=$(".m_list").children().length;
			if(num>10){
				$(".click_more").css("display","block");
			}
			else{
				$(".click_more").css("display","none");
			}