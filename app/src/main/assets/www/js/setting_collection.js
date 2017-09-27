/**
 * Created by LIEV_OB_05 on 2017/8/31.
 */
$(function(){
    getJobData();
    //获取职业收藏数据
    function getJobData(){
        $.ajax({
            url:'https://114.215.125.52/index.php/Home/Person/JobClist',
            type:'post',
            dataType:'jsonp',
            async:true,
            jsonp:'callback',
            jsonpCallback:'jsapi',
            xhrFields: {withCredentials: true},
            crossDomain: true,
            success:function(msg){
                if(msg.status == 1){
                    var l = '';
                    for (var i=0;i<msg.data.length;i++){
                        l += '<div class="msg_panel job_into">';
                        l += '<div class="l_part">';
                        l += '<h3>'+msg.data[i].post_name;
                        l += '<span>'+msg.data[i].post_salary+'</span>';
                        l += '</h3>';
                        l += '<h4>'+msg.data[i].name+'</h4>';
                        l += '</div>';
                        l += '<div class="r_part">';
                        l += '<div class="item" value="'+msg.data[i].id+'">取消收藏</div>';
                        l += '</div>';
                        l += '</div>';
                    }
                    $(".m_list").html(l)
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
    }
    //获取公司收藏数据
    function getEnterpriseData(){
        $.ajax({
            url:'https://114.215.125.52/index.php/Home/Person/EnterpriseClist',
            type:'post',
            dataType:'jsonp',
            async:true,
            jsonp:'callback',
            jsonpCallback:'jsapi',
            xhrFields: {withCredentials: true},
            crossDomain: true,
            success:function(msg){
                if(msg.status == 1){
                    var l = '';
                    for (var i=0;i<msg.data.length;i++){
                        l += '<div class="msg_panel job_into">';
                        l += '<div class="l_part">';
                        l += '<h3>'+msg.data[i].name;
                        l += '<span>'+msg.data[i].address+'</span>';
                        l += '</h3>';
                        l += '<p>' +
                            '<span id="industry">'+msg.data[i].scale_id+'</span> | ' +
                            '<span id="kind">'+msg.data[i].industry_id+'</span> | ' +
                            '<span id="scale">'+msg.data[i].kind_id+'</span>' +
                            '</p>';
                        l += '</div>';
                        l += '<div class="r_part">';
                        l += '<div class="item EnterpriseBtn" value="'+msg.data[i].id+'">取消收藏</div>';
                        l += '</div>';
                        l += '</div>';
                    }
                    $(".m_list").html(l)
                }
            },
            error:function(){
                layer.open({
                    content: '网络错误',
                    skin: 'msg',
                    time: 1 //2秒后自动关闭
                });
            }
        })
    }

    //职位公司数据切换
    $(".tab_status>.item").click(function(){
        if ($(this).text()==="公司收藏"){
            $(".m_list").children().remove();
            getEnterpriseData();
        }else if ($(this).text()==="职位收藏"){
            $(".m_list").children().remove();
            getJobData();
        }
    });
    //取消职业收藏
    $(document).on("click",".r_part>.item",function(){
        var id = $(this).attr('value');
        var $this = $(this);
        layer.open({
            content: "是否取消收藏",
            btn : ['是','否'],
            yes : function(index){
                $.ajax({
                    url:'https://114.215.125.52/index.php/Home/Person/DelJobCollect',
                    type:'post',
                    data:{'kname':id},
                    dataType:'jsonp',
                    async:true,
                    jsonp:'callback',
                    jsonpCallback:'jsapi',
                    xhrFields: {withCredentials: true},
                    crossDomain: true,
                    success:function(msg){
                        layer.open({
                            content: msg.info,
                            skin: 'msg',
                            time: 1 //2秒后自动关闭
                        });
                        $this.parent().parent().remove();
                    },
                    error:function(){
                        layer.open({
                            content: '网络错误',
                            skin: 'msg',
                            time: 1 //2秒后自动关闭
                        });
                    }
                });
                layer.close(index);
            }
        });

    });
    //取消企业收藏
    $(document).on("click",".r_part>.EnterpriseBtn",function(){
        var id = $(this).attr('value');
        var $this = $(this);
        layer.open({
            content: "是否取消收藏",
            btn : ['是','否'],
            yes : function(index){
                $.ajax({
                    url:'https://114.215.125.52/index.php/Home/Person/DelEnterpriseCollect',
                    type:'post',
                    data:{'kname':id},
                    dataType:'jsonp',
                    async:true,
                    jsonp:'callback',
                    jsonpCallback:'jsapi',
                    xhrFields: {withCredentials: true},
                    crossDomain: true,
                    success:function(msg){
                        layer.open({
                            content: msg.info,
                            skin: 'msg',
                            time: 1 //2秒后自动关闭
                        });
                        $this.parent().parent().remove();
                    },
                    error:function(){
                        layer.open({
                            content: '网络错误',
                            skin: 'msg',
                            time: 1 //2秒后自动关闭
                        });
                    }
                });
                layer.close(index);
            }
        });

    });

});
