$(document).ready(function() {
    //Search
    function SearchJob(data = '',page = 0) {
	$.ajax({
	    type: "POST",
	    url: "https://114.215.125.52/index.php/Home/Index/JobSearch",
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
                if (msg.status == 1) {
                    var l = '';
                    for (var i = 0; i < msg.data.length; i++) {
                        l += '<div class="job_item job_into" value="' + msg.data[i].id + '">';
                        l += '<div class="main_body clearfix">';
                        l += '<div class="m_left">';
                        l += '<h3>' + msg.data[i].post_name + '</h3>';
                        l += '<p>';
                        l += '<span class="icon-location"></span><span>' + msg.data[i].work_address + '</span>';
                        l += '<span class="fa fa-mortar-board"></span><span>' + msg.data[i].post_education + '</span>';
                        l += '<span class="icon-clock"></span><span>' + msg.data[i].post_exp + '</span>';
                        l += '</p>';
                        l += '</div>';
                        l += '<div class="m_right">';
                        l += '<h2>' + msg.data[i].post_salary + '</h2>';
                        l += '</div>';
                        l += '</div>';
                        l += '<div class="addition clearfix">';
                        l += '<div class="a_left">' + msg.data[i].name + '</div>';
                        l += '<div class="a_right">';
                        l += '<span>' + msg.data[i].kind_id + '</span>';
                        l += '<span>' + msg.data[i].scale + '</span>';
                        l += '<span id="industry">' + msg.data[i].industry_id + '</span>';
                        l += '</div>';
                        l += '</div>';
                        l += '</div>';
                    }
                    if (page == 0) {
                        $(".more").attr('value', 1);
                        $(".m_list").html(l);
                        $(".click_more").attr('value', 1);
                        click_more_hide();
                    } else {
                        $(".m_list").append(l);
                        click_more_hide();
                    }
                    ClickJob();
                } else {
                    layer.open({
                        content: msg.info,
                        skin: 'msg',
                        time: 1 //2秒后自动关闭
                    });
                    var l = '';
                    if (page == 0) {
                        $(".m_list").html(l);
                        click_more_hide();
                    }
                }
            }
	});
    }
    
    //City城市条件 Claim要求条件 Industry行业条件
    function JobList(City = '', Claim = '', Industry = '', page = 0) {
        $(".sort_panel_out").hide();
        $.ajax({
            type: "POST",
            url: "https://114.215.125.52/index.php/Home/Index/JobList",
            data: {
                City,
                Claim,
                Industry,
                page
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
                if (msg.status == 1) {
                    var l = '';
                    for (var i = 0; i < msg.data.length; i++) {
                        l += '<div class="job_item job_into" value="' + msg.data[i].id + '">';
                        l += '<div class="main_body clearfix">';
                        l += '<div class="m_left">';
                        l += '<h3>' + msg.data[i].post_name + '</h3>';
                        l += '<p>';
                        l += '<span class="icon-location"></span><span>' + msg.data[i].work_address + '</span>';
                        l += '<span class="fa fa-mortar-board"></span><span>' + msg.data[i].post_education + '</span>';
                        l += '<span class="icon-clock"></span><span>' + msg.data[i].post_exp + '</span>';
                        l += '</p>';
                        l += '</div>';
                        l += '<div class="m_right">';
                        l += '<h2>' + msg.data[i].post_salary + '</h2>';
                        l += '</div>';
                        l += '</div>';
                        l += '<div class="addition clearfix">';
                        l += '<div class="a_left">' + msg.data[i].name + '</div>';
                        l += '<div class="a_right">';
                        l += '<span>' + msg.data[i].kind_id + '</span>';
                        l += '<span>' + msg.data[i].scale + '</span>';
                        l += '<span id="industry">' + msg.data[i].industry_id + '</span>';
                        l += '</div>';
                        l += '</div>';
                        l += '</div>';
                    }
                    if (page == 0) {
                        $(".more").attr('value', 1);
                        $(".m_list").html(l);
                        $(".click_more").attr('value', 1);
                        click_more_hide();
                    } else {
                        $(".m_list").append(l);
                        click_more_hide();
                    }
                    ClickJob();
                } else {
                    layer.open({
                        content: msg.info,
                        skin: 'msg',
                        time: 1 //2秒后自动关闭
                    });
                    var l = '';
                    if (page == 0) {
                        $(".m_list").html(l);
                        click_more_hide();
                    }
                }
            }
        });
    }
    JobList();


    //点击显示条件
    $(".sort_bar_item").on("click",
    function() {
        var IS = $(this).index();
        if (IS == 0) {
            City();
            $(".sort_bar_item").removeAttr('value');
            $(this).attr('value', IS);
            $(".sort_bar_item").removeClass('sd');
            $(this).addClass('sd');
        } else if (IS == 1) {
            Claim();
            $(".sort_bar_item").removeAttr('value');
            $(this).attr('value', IS);
            $(".sort_bar_item").removeClass('sd');
            $(this).addClass('sd');
        } else {
            Industry();
            $(".sort_bar_item").removeAttr('value');
            $(this).attr('value', IS);
            $(".sort_bar_item").removeClass('sd');
            $(this).addClass('sd');
        }
        $(".sort_panel_out").show();
    });

    //获取城市信息
    function City() {
        $.ajax({
            type: "POST",
            url: "https://114.215.125.52/index.php/Home/Index/City",
            data: '',
            async: false,
            dataType: 'jsonp',
            jsonp: "callback",
            jsonpCallback: "jsapi",
            xhrFields: {
                withCredentials: true
            },
            crossDomain: true,
            success: function(msg) {
                if (msg.status == 1) {
                    console.log(msg);var l = '';
                    for (var i = 0; i < msg.data.length; i++) {
                                l += '<div class="s_badge" value="' + msg.data[i].id + '">' + msg.data[i].name + '</div>';
                    }
                    $(".sort_panel").html(l);
                    WhereClick();
                } else {
                    layer.open({
                        content: '数据错误!',
                        skin: 'msg',
                        time: 1
                    });
                }
            },
            error: function(msg) {
                layer.open({
                    content: '网络错误!请刷新',
                    skin: 'msg',
                    time: 1
                });
            }
        });
    }

    //获取要求信息
    function Claim() {
        $.ajax({
            type: "POST",
            url: "https://114.215.125.52/index.php/Home/Index/Requirement",
            data: '',
            async: false,
            dataType: 'jsonp',
            jsonp: "callback",
            jsonpCallback: "jsapi",
            xhrFields: {
                withCredentials: true
            },
            crossDomain: true,
            success: function(msg) {
                if (msg.status == 1) {
                    var l = '';
                    //for (var i = 0; i < msg.data.work.length; i++) {
                    //	l += '<div class="s_badge" value="'+msg.data.work[i].id+'">'+msg.data.work[i].work_exp_name+'</div>';
                    //}
                    for (var i = 0; i < msg.data.salary.length; i++) {
                        l += '<div class="s_badge" value="' + msg.data.salary[i].id + '">' + msg.data.salary[i].salary_name + '</div>';
                    }
                    //for (var i = 0; i < msg.data.edu.length; i++) {
                    //	l += '<div class="s_badge" value="'+msg.data.edu[i].id+'">'+msg.data.edu[i].edu_name+'</div>';
                    //}
                    $(".sort_panel").html(l);
                    WhereClick();
                } else {
                    layer.open({
                        content: '数据错误!',
                        skin: 'msg',
                        time: 1
                    });
                }
            },
            error: function(msg) {
                layer.open({
                    content: '网络错误!请刷新',
                    skin: 'msg',
                    time: 1
                });
            }
        });
    }

    //获取行业信息
    function Industry() {
        $.ajax({
            type: "POST",
            url: "https://114.215.125.52/index.php/Home/Index/Industry",
            data: '',
            async: false,
            dataType: 'jsonp',
            jsonp: "callback",
            jsonpCallback: "jsapi",
            xhrFields: {
                withCredentials: true
            },
            crossDomain: true,
            success: function(msg) {
                if (msg.status == 1) {
                    var l = '';
                    for (var i = 0; i < msg.data.length; i++) {
                        l += '<div class="s_badge" value="' + msg.data[i].id + '">' + msg.data[i].name + '</div>';
                    }
                    $(".sort_panel").html(l);
                    WhereClick();
                } else {
                    layer.open({
                        content: '数据错误!',
                        skin: 'msg',
                        time: 1
                    });
                }
            },
            error: function(msg) {
                layer.open({
                    content: '网络错误!请刷新',
                    skin: 'msg',
                    time: 1
                });
            }
        });
    }

    //条件查询数据
    function WhereClick() {
        $(".s_badge").unbind("click");
        $(".s_btn").unbind("click");
        $(".s_badge").on("click",
        function() {
            $(".s_badge").removeClass("active");
            $(".s_badge").removeClass("default");
            $(this).toggleClass("active");
            $(this).toggleClass("default");
        });
        $(".s_btn").eq(0).on("click",
        function() {
            $(".s_badge").removeClass("active");
            $(".s_badge").removeClass("default");
        });
        $(".s_btn").eq(1).on("click",
        function() {
            var str = $(".default").attr('value');
            var IS = $(".sd").attr('value');
            $(".click_more").attr('value', 1);
            if (IS == 0) {
                JobList(str, '', '');
            } else if (IS == 1) {
                JobList('', str, '');
            } else {
                JobList('', '', str);
            }
        });
    }
    
    //搜索查询
    $(".search-icon").on("click",function(){
	var data = $("#search").val();
	SearchJob(data);
    })
    
    //更多内容 分页显示
    function More() {
        $(".click_more").unbind("click");
        $(".click_more").on("click",
        function() {
            var p = $(this).attr('value');
            var v = p * 1 + 1;
            $(this).attr('value', v);
            var str = $(".default").attr('value');
            var IS = $(".sd").attr('value');
            if (IS == 0) {
                JobList(str, '', '', p);
            } else if (IS == 1) {
                JobList('', str, '', p);
            } else {
                JobList('', '', str, p);
            }
        });
    }
    More();
});

