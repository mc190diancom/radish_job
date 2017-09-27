function ClickJob() {
    $(".job_into").unbind("click");
    $(".job_into").on("click",
    function() {
        var job_id = $(this).attr('value');
        $.ajax({
            type: "POST",
            url: "https://114.215.125.52/index.php/Home/Index/setSession",
            data: {
                'kname': 'job_id',
                'value': job_id
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
                if (msg.status) {
                    window.location.href = "home_jobDetail.html";
                } else {
                    layer.open({
                        content: msg.info,
                        skin: 'msg',
                        time: 2 //2秒后自动关闭
                    });
                }
            },
            error: function(msg) {
                layer.open({
                    content: '网络错误',
                    skin: 'msg',
                    time: 2 //2秒后自动关闭
                });
            }
        });
    })
}
function CheckJob() {
    $(".check").unbind("click");
    $(".check").on("click",
    function() {
        var job_id = $(this).attr('value');
        $.ajax({
            type: "POST",
            url: "https://114.215.125.52/index.php/Home/Index/setSession",
            data: {
                'kname': 'job_id',
                'value': job_id
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
                if (msg.status) {
                    window.location.href = "home_jobDetail.html";
                } else {
                    layer.open({
                        content: msg.info,
                        skin: 'msg',
                        time: 2 //2秒后自动关闭
                    });
                }
            },
            error: function(msg) {
                layer.open({
                    content: '网络错误',
                    skin: 'msg',
                    time: 2 //2秒后自动关闭
                });
            }
        });
    })
}
//点击进入企业详细信息
function CompanyDetailClick() {
    $('.company_into').unbind('click');
    $('.company_into').on('click',
    function() {
        var c_id = $(this).attr('value');
        $.ajax({
            type: "POST",
            url: "https://114.215.125.52/index.php/Home/Index/setSession",
            data: {
                'kname': 'company_id',
                'value': c_id
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
                if (msg.status) {
                    window.location.href = "home_companyDetail.html";
                } else {
                    layer.open({
                        content: msg.info,
                        skin: 'msg',
                        time: 2 //2秒后自动关闭
                    });
                }
            },
            error: function(msg) {
                layer.open({
                    content: '网络错误',
                    skin: 'msg',
                    time: 2 //2秒后自动关闭
                });
            }
        });
    });
}
//返回上一级页面
function backClick() {
    $(".back").on("click",
    function() {
        history.go( - 1);
    })
}
//简历修改按钮
function resumeEditClick() {
    $(".resume_edit").unbind("click");
    $(".resume_edit").on("click",
    function() {
        layer.open({
            content: "请使用电脑访问<br />http://www.resume.com编辑简历",
            btn: "我知道了"
        });
    }) //编辑简历
}
//接受面试邀请操作
function AcceptClick() {
    $(".accept").unbind('click');
    $(".accept").on('click',
    function() {
        var job_id = $(this).attr("value");
        var time = $(this).attr("f_time");
        layer.open({
            content: '面试时间:' + time,
            btn: ['拒绝', '接受'],
            yes: function(index) {
                HandleFun(job_id, 2);
                location.reload();
            },
            no: function(index) {
                HandleFun(job_id, 1);
                location.reload();
            },
        });
    });
}
//接受面试邀请操作方法
function HandleFun(job_id, index) {
    if (index == 1) {
        var Url = "https://114.215.125.52/index.php/Home/Person/Accept";
    } else {
        var Url = "https://114.215.125.52/index.php/Home/Person/Refuse";
    }
    $.ajax({
        type: "POST",
        url: Url,
        data: {
            'e_r_id': job_id
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
            if (msg.status) {
                layer.open({
                    content: msg.info,
                    skin: 'msg',
                    time: 2 //2秒后自动关闭
                });
            } else {
                layer.open({
                    content: msg.info,
                    skin: 'msg',
                    time: 2 //2秒后自动关闭
                });
            }
        },
        error: function(msg) {
            layer.open({
                content: '网络错误',
                skin: 'msg',
                time: 2 //2秒后自动关闭
            });
        }
    });
}
//查看面试时间
function InterviewClick() {
    $(".interview").unbind('click');
    $(".interview").on('click',
    function() {
        var time = $(this).attr("f_time");
        layer.open({
            content: '面试时间:' + time,
            btn: '知道了',
            yes: function(index) {
                layer.close(index);
            }
        });
    });
}


//隐藏显示点击更多
function click_more_hide() {
    var c = $(".m_list").children().length;
    if (c < 8) {
        $(".click_more").hide();
    } else {
        $(".click_more").show();
    }
}