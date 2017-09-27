$(document).ready(function(){
/*responsive*/
    var $html = $('html');
    var $window = $('window');
    var $body = $('body');
    var psdsize = 360;//这里表示我设计图的宽度
    var htmlfont = $body.width()/psdsize*14 + 'px';//计算字体大小
    $html.css('font-size', htmlfont);//设置字体大小

    $body.css('opacity', 1);//针对一些手机浏览器的默认样式
    //屏幕尺寸修改时，改变其大小
    $(window).resize(function(event) {
        htmlfont = $body.width()/psdsize*100+'px';
        $html.css('font-size', 'htmlfont');
    });
	
	
/*back*/
    $(".back").on("click",function(){
	history.go(-1);
    })

/*sort_toggle*/
	$(".sort_toggle").children().on("click",function(){
		if (!$(this).hasClass("active")){
			$(this).addClass("active");
			$(this).siblings().removeClass("active");
			if($(".sort_bar").css("display") == "none"){
			    $(".home_header").animate({marginTop:"5.25rem"});
			}else{
			    $(".home_header").animate({marginTop:"3rem"});
			}
			$(".sort_bar").slideToggle();
		}else{
			return false;
		}
	});

    
/*msg detail*/
    $(".msg_detail").on("click",function(){
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
    
/*card_toggle*/
	$(".card_toggle").on("click",function(){
	    if($(this).hasClass("active")){
		$(this).removeClass("active");
	    }else{
		$(this).addClass("active");
	    }
	});

/*tab_status*/
	$(".tab_status").children().on("click",function(){
	    if(!$(this).hasClass("active")){
		$(this).siblings().removeClass("active");
		$(this).addClass("active");
	    }else{
		return false;
	    }
	});
}) //all end