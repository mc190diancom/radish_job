var appid = 'wd1603956872hteziy';
var videoAppid = 'wd8926362719hlnvhg';
var uid = '';

//初始化野狗
var usersref = null;
var currentConversation = null;
var usersref = null;
var localStream = null;

var onInviteCb = function(conversation) {
    incomingConversation = conversation;
    //监听被邀请者的事件
    conversation.on('response',function (callstatus) {
        switch (callstatus){
            case 'TIMEOUT':
                currentInvite = null;
                inviteEl.hidden = true;
                break;
            case 'REJECTED':
                currentInvite = null;
                inviteEl.hidden = true;
                break;

        }
    });
};

    
var localEl = document.getElementById('local');
var remoteEl = document.getElementById('remote');
var connectedRef;
var login = function(){
       var config = {
	   	   authDomain: videoAppid + '.wilddog.com'
	   };
       var videoApp = wilddog.initializeApp(config,'videoApp');
       var synConfig ={
	       syncURL: "https://" + appid + ".wilddogio.com" 
       }
       var syncApp = wilddog.initializeApp(synConfig,'syncApp');
       //定义quickstart使用的用户列表存储路径，userList 可以改为其他自定义路径
       usersref = syncApp.sync().ref();
       //获取video
       videoInstance = wilddogVideo.getInstance();
       //野狗登录
       videoApp.auth().signInAnonymously()
           .then(function(user) {
               //初始化WilddogVideo对象

               videoInstance.initialize(videoAppid,user.getToken());
               //获取本地媒体流，并绑定到页面
               videoInstance.createLocalStream({
                   captureVideo: true,
                   captureAudio: true,
                   dimension: '480p',
                   maxFPS: 15
               })
               .then(function(wdStream) {
                   localStream = wdStream;
                   localStream.attach(localEl);

               })
               .catch(function(err) {
                   console.error(err);
               });
           //loginCb(user);//登录回调 
           }).then(function(){  //野狗登录
               //监听收到的邀请
               videoInstance.on('called', onInviteCb);
               videoInstance.on('token_error',function () {
                   alert('token不合法或过期，请重新登录！');
               })
           })
           .catch(function(err) {
               console.log(err);
               alert('请检查appid是否正确并开启匿名登录功能和实时视频服务！');
           });
   };//login
   
   //接受当前收到的邀请
   var accept = function() {
       incomingConversation.accept(localStream).then(conversationStarted);
   };
   
   //拒绝当前收到的邀请
   var reject = function() {
       incomingConversation.reject();
       inviteEl.hidden = true;
   };
   
   var conversationStarted = function(conversation) {
       //监听新参与者加入conversation事件
       console.log(conversation);
       conversation.on('stream_received', function(stream) {
           remoteStream = stream;
           remoteStream.attach(remoteEl);
           //开始计时
           var timeStart = new Date();
           countTime();
       });
       currentConversation = conversation;
   };
   
   //邀请其他用户加入会话
   var invite = function(uid) {
       currentConversation = videoInstance.call(uid,localStream,'test');
       conversationStarted(currentConversation);
       //监听被邀请者的事件
       currentConversation.on('response',function (callstatus) {
           switch (callstatus){
               case 'REJECT':
                   currentConversation = null;
                   hangupEl.hidden = true;
                   userDiv.hidden = false;
                   break;
               case 'BUSY':
                   currentConversation = null;
                   hangupEl.hidden = true;
                   userDiv.hidden = false;
                   inviteBusyEl.hidden = false;
                   break;

           }
       });
   };

//进入即匿名登录
login(); 



//基本信息
$.ajax({
    type: "POST",
    url: "https://114.215.125.52/index.php/Home/Interview/InterviewStart",
    data: {},
    async: false,
    dataType: 'jsonp',
    jsonp : "callback",
    jsonpCallback: "jsapi",
    xhrFields: {withCredentials: true},
    crossDomain: true,
    success:function(msg) {
	console.log(msg);
    	var l = '';
    	    l += '<h4>面试公司 ： '+msg.data.e_name+'</h4>';
	    l += '<h4>面试职位 : '+msg.data.post_name+'</h4>';
	    l += '<h4>面试场编号 ： '+msg.data.number+'</h4>';
	    l += '<h4>面试人 ： '+msg.data.name+'</h4>';
	    l += '<h4>你的面试者编号 ： '+msg.data.interview_number+'</h4>';
	$(".content").html(l);
	uid = msg.data.uid;
    },
});


var timeStart = new Date();

//监听是否轮到自己面试
function monitor(){
    $.ajax({
    	type: "POST",
        url: "https://114.215.125.52/index.php/Home/Interview/InterviewStart",
        data: {},
        async: false,
        dataType: 'jsonp',
        jsonp : "callback",
        jsonpCallback: "jsapi",
        xhrFields: {withCredentials: true},
        crossDomain: true,
        success:function(msg) {
		if(msg.data.state == 3){
		    //邀请面试并清除计时器
		    //
		    startState(uid);
		}else if(msg.data.state == 1 || msg.data.state == 2){
		    if(msg.data.dnumber == null){
			var timeStart = new Date();
		        $(".interview_info").children(".title").text("没有正在面试的人");
		    }else{
	                $(".interview_info").children(".title").text("当前面试者编号为"+msg.data.dnumber);
		    }
		    /*计算时间*/
		    var now = new Date().getTime();
		    var estimate = new Date(now + 60000 * (msg.data.num+1));
		    var hour = estimate.getHours();
		    var minute = estimate.getMinutes();
		    minute = minute>=10?minute: '0'+ minute;
		    hour = hour>=10?hour: '0'+ hour;
		    var estimateTime = String(hour)+":"+String(minute);
		    var l = '';
		        l += '<h4>你前面还有'+msg.data.num+'个人</h4>';
		        l += '<h4>预计你的面试开始时间为'+ estimateTime+'</h4>';
		        l += '<h4>请提前做好准备</h4>';
		    $(".sub_title").html(l);
		}else{
		    layer.open({
			skin:'msg',
		    	content:'面试已结束,请等待公司电话通知',
		    	time:2
		    })
		    clearInterval(timer);
		    setTimeout("window.location.href='msg_interview.html';",3000);
		}
        }
    });
}//monitar clearInterval(timer);
var timer = setInterval("monitor()",1000);



//开始面试

var starti = 1;
function startState(uid){
    if (starti == 1){
	invite(uid);
	$(".interview_info").children(".title").text('等待面试官连入');
	$(".sub_title").html('');
    }
    starti = 0;
}

//计时
function countTime(){
    Date.prototype.diff = function(date){
        return (this.getTime() - date.getTime())/1000;
    };
    // 构造两个日期

    var now = new Date();
    var date = timeStart;
    
    // 打印日期差
       var diff = now.diff(date);
       var hour = parseInt(diff/(60*60));//计算整数小时数
       var afterHour = diff - hour*60*60;//取得算出小时数后剩余的秒数
       var min = parseInt(afterHour/60);//计算整数分
       var sec = parseInt(diff  - hour*60*60 - min*60);//取得算出分后剩余的秒数
       hour = hour>=10?hour:('0'+hour);
       min = min>=10?min:('0'+min);
       sec = sec>=10?sec:('0'+sec);
       var diffTime = hour+':'+min+':'+sec;
       var text = "面试已开始"+diffTime;
       $(".interview_info").children(".title").text(text);
       setTimeout(countTime,1000);
}