$(document).ready(function(){
    //Search
    function SearchCompany(data = '',page = 0) {
	$.ajax({
	    type: "POST",
	    url: "https://114.215.125.52/index.php/Home/Index/searchCompany",
	    data: {'data':data},
	    async: false,
            dataType: 'jsonp',
            jsonp: "callback",
            jsonpCallback: "jsapi",
            xhrFields: {
                withCredentials: true
            },
            crossDomain: true,
            success: function(msg) {
        	console.log(msg);
        	if (msg.status == 1) {
			var db = msg.data;
			var l = '';
			for (var i = 0; i < db.length; i++) {
				l += '<div class="company_item company_into" value="' + db[i].id + '">';
					l += '<div class="main_body clearfix">';
						l += '<div class="m_part1">';
							l += '<div class="img_outline">';
								if (db[i].logo) {
									l += '<img src="https://114.215.125.52' + db[i].logo + '" alt="' + db[i].name + '" />';
								} else {
									l += '<img src="img/company_logo/00001.png" alt="' + db[i].name + '" />';
								}
							l += '</div>';
						l += '</div>';
						l += '<div class="m_part2">';
							l += '<h3>' + db[i].name + '</h3>';
							l += '<p>';
								l += '<span>' + db[i].provincial + '</span>';
								l += '<span>' + db[i].address + '</span>';
							l += '</p>';
							l += '<p>';
								l += '<span>' + db[i].industry_id + '</span>';
								l += '<span>' + db[i].kind_id + '</span>';
								l += '<span>' + db[i].scale + '</span>';
							l += '</p>';
						l += '</div>';
						l += '<div class="m_part3">';
							l += '<div>' + db[i].num + '个职位</div>';
						l += '</div>';
					l += '</div>';
				l += '</div>';
			}
			if(page == 0){
				$('.more').attr('value',1);
				$(".m_list").html(l);
			}else{
				$(".m_list").append(l);
			}
			companyClick()
			CompanyDetailClick();
		} else {
			layer.open({
            content: msg.info,
            skin: 'msg',
            time: 1 //2秒后自动关闭
        });
        var l = '';
        if(page == 0){
				$(".m_list").html(l);
			}
		}
            }
	});
    }
    
  //搜索查询
    $(".search-icon").on("click",function(){
	var data = $("#search").val();
	alert(123);
	SearchCompany(data);
    })
    
	function Company(Industry = '',Kind = '',Scale = '',page = 0) {
		$(".sort_panel_out").hide();
		$.ajax({
			type: "POST",
			url: "https://114.215.125.52/index.php/Home/Index/Company",
			data:{Industry,Kind,Scale,page},
			async: false,
			dataType: 'jsonp',
			jsonp : "callback",
			jsonpCallback: "jsapi",
			xhrFields: {withCredentials: true},
			crossDomain: true,
			success: function (msg) {
				if (msg.status == 1) {
					var db = msg.data;
					var l = '';
					for (var i = 0; i < db.length; i++) {
						l += '<div class="company_item company_into" value="' + db[i].id + '">';
							l += '<div class="main_body clearfix">';
								l += '<div class="m_part1">';
									l += '<div class="img_outline">';
										if (db[i].logo) {
											l += '<img src="https://114.215.125.52' + db[i].logo + '" alt="' + db[i].name + '" />';
										} else {
											l += '<img src="img/company_logo/00001.png" alt="' + db[i].name + '" />';
										}
									l += '</div>';
								l += '</div>';
								l += '<div class="m_part2">';
									l += '<h3>' + db[i].name + '</h3>';
									l += '<p>';
										l += '<span>' + db[i].provincial + '</span>';
										l += '<span>' + db[i].address + '</span>';
									l += '</p>';
									l += '<p>';
										l += '<span>' + db[i].industry_id + '</span>';
										l += '<span>' + db[i].kind_id + '</span>';
										l += '<span>' + db[i].scale + '</span>';
									l += '</p>';
								l += '</div>';
								l += '<div class="m_part3">';
									l += '<div>' + db[i].num + '个职位</div>';
								l += '</div>';
							l += '</div>';
						l += '</div>';
					}
					if(page == 0){
						$('.more').attr('value',1);
						$(".m_list").html(l);
					}else{
						$(".m_list").append(l);
					}
					companyClick()
					CompanyDetailClick();
				} else {
		   			layer.open({
		                content: msg.info,
		                skin: 'msg',
		                time: 1 //2秒后自动关闭
		            });
		            var l = '';
		            if(page == 0){
						$(".m_list").html(l);
					}
				}
			}
		});
	}
	Company();

	//点击显示条件

	$(".sort_bar_item").on("click",function(){
		var IS = $(this).index();
		if(IS == 0){
			Industry();
			$(".sort_bar_item").removeAttr('value');
			$(this).attr('value',IS);
			$(".sort_bar_item").removeClass('sd');
			$(this).addClass('sd');
		}else if(IS == 1){
		        Kind();
			$(".sort_bar_item").removeAttr('value');
			$(this).attr('value',IS);
			$(".sort_bar_item").removeClass('sd');
			$(this).addClass('sd');
		}else{
			ScaleFun();
			$(".sort_bar_item").removeAttr('value');
			$(this).attr('value',IS);
			$(".sort_bar_item").removeClass('sd');
			$(this).addClass('sd');
		}
		$(".sort_panel_out").show();
	});
	function Industry() {
		$.ajax({
			type: "POST",
			url: "https://114.215.125.52/index.php/Home/Index/Industry",
			data: '',
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
						l += '<div class="s_badge" value="'+msg.data[i].id+'">'+msg.data[i].name+'</div>';
					}
					$(".sort_panel").html(l);
					companyClick();
				}else{
					layer.open({
						content: '数据错误!',
						skin: 'msg',
						time: 1
					});
				}
			},
			error:function(msg){
				layer.open({
					content: '网络错误!请刷新',
					skin: 'msg',
					time: 1
				});
			}
		});
	}
	function ScaleFun() {
		$.ajax({
			type: "POST",
			url: "https://114.215.125.52/index.php/Home/Index/Scale",
			data: '',
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
						l += '<div class="s_badge" value="'+msg.data[i].id+'">'+msg.data[i].scale_name+'</div>';
					}
					$(".sort_panel").html(l);
					companyClick();
				}else{
					layer.open({
						content: '数据错误!',
						skin: 'msg',
						time: 1
					});
				}
			},
			error:function(msg){
				layer.open({
					content: '网络错误!请刷新',
					skin: 'msg',
					time: 1
				});
			}
		});
	}
	function Kind() {
		$.ajax({
			type: "POST",
			url: "https://114.215.125.52/index.php/Home/Index/Kind",
			data: '',
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
						l += '<div class="s_badge" value="'+msg.data[i].id+'">'+msg.data[i].name+'</div>';
					}
					$(".sort_panel").html(l);
					companyClick();
				}else{
					layer.open({
						content: '数据错误!',
						skin: 'msg',
						time: 1
					});
				}
			},
			error:function(msg){
				layer.open({
					content: '网络错误!请刷新',
					skin: 'msg',
					time: 1
				});
			}
		});
	}
	//条件查询数据
	function companyClick() {
		$(".s_badge").unbind('click');
		$(".s_btn").unbind('click');
		$(".s_badge").on("click",function(){
			$(".s_badge").removeClass("active");
			$(".s_badge").removeClass("default");
			$(this).toggleClass("active");
			$(this).toggleClass("default");
		});
		$(".s_btn").eq(0).on("click",function(){
			$(".s_badge").removeClass("active");
			$(".s_badge").removeClass("default");
		});
		$(".s_btn").eq(1).on("click",function(){
			var str = $(".default").attr('value');
			var IS = $(".sd").attr('value');
			if(IS == 0){
				Company(str,'','');
			}else if(IS == 1){
				Company('',str,'');
			}else{
				Company('','',str);
			}
			
		})
	}
	//更多内容 分页显示
	function More() {
		$(".click_more").unbind("click");
		$(".click_more").on("click",function() {
			var p = $(this).attr('value');
			var v = p*1+1;
			$(this).attr('value',v);
			var str = $(".default").attr('value');
	  	    var IS = $(".sd").attr('value');
	  	    if(IS == 0){
	  	    	Company(str,'','',p);
	  	    }else if(IS == 1){
	  	    	Company('',str,'',p);
	  	    }else{
	  	    	Company('','',str,p);
	  	    }
		});
	}
	More();
});

//10条以上信息显示		
	var num=$(".m_list").children().length;
	if(num>10){
		$(".click_more").css("display","block");
	}
	else{
		$(".click_more").css("display","none");
	}