package com.liewei.radish_job.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.liewei.radish_job.R;
import com.liewei.radish_job.async.AsyncUtil;
import com.liewei.radish_job.async.Callback;
import com.liewei.radish_job.async.Result;
import com.liewei.radish_job.util.Config;
import com.liewei.radish_job.util.UserData;
import com.liewei.radish_job.util.Windows;
import com.liewei.radish_job.wilddogutil.InitWildSdk;
import com.liewei.radish_job.wilddogutil.wilddogStream;
import com.wilddog.video.CallStatus;
import com.wilddog.video.Conversation;
import com.wilddog.video.LocalStream;
import com.wilddog.video.RemoteStream;
import com.wilddog.video.WilddogVideo;
import com.wilddog.video.WilddogVideoError;
import com.wilddog.video.WilddogVideoView;
import com.wilddog.wilddogauth.WilddogAuth;
import com.wilddog.wilddogauth.core.Task;
import com.wilddog.wilddogauth.core.listener.OnCompleteListener;
import com.wilddog.wilddogauth.core.result.AuthResult;
import com.wilddog.wilddogcore.WilddogApp;
import com.wilddog.wilddogcore.WilddogOptions;

import java.util.concurrent.Callable;

public class VideoActivity extends Activity {
    @ViewInject(R.id.local_video_view)
    private WilddogVideoView localView;
    @ViewInject(R.id.back_btn)
    private ImageView back_btn;
    @ViewInject(R.id.title)
    private TextView title;

    private InitWildSdk wildSdk;
    private Conversation mConversation;
    int type =1;

    private static String TAG = "VideoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        initView();
    }

    private void initView() {
        ViewUtils.inject(VideoActivity.this);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        title.setText("面试中");
        initVideo();
    }

    private void initVideo(){
        final WilddogVideo video = wildSdk.initVideoSDK();//当知道用户加载完毕后就初始化sdk
        video.setListener(new WilddogVideo.Listener() {
            @Override
            public void onCalled(Conversation conversation, String s) {
                Log.e("log","log:"+s);
                mConversation = conversation;
                startConversation(video);
            }

            @Override
            public void onTokenError(WilddogVideoError wilddogVideoError) {
            }
        });
    }

    /**
     * TODO 判断是否开启通话
     */
    private void startConversation(final WilddogVideo video) {
        LocalStream localStream = new wilddogStream(video).init();//获取视频流
        mConversation.accept(localStream);
        setConversation(mConversation,localStream);
    }

    /**
     * TODO 发送通话请求
     */
    private void sendConversation(final WilddogVideo video) {
        LocalStream localStream = new wilddogStream(video).init();//获取视频流
        // wildSdk.startConversation(video,localStream);
        //localStream.attach(localView);
        Conversation conversation= video.call("1971d1be24daafd021530adc18c3",localStream,"发起啦");
        conversation.setConversationListener(new Conversation.Listener() {
            @Override
            public void onCallResponse(CallStatus callStatus) {
                switch (callStatus){
                    case ACCEPTED:
                        Log.d("log","log:通话被接受1");
                        break;
                    case REJECTED:
                        Log.d("log","log:通话被拒绝1");
                        break;
                    case BUSY:
                        Log.d("log","log:正忙1");
                        break;
                    case TIMEOUT:
                        Log.d("log","log:超时1");
                        break;
                    default:
                        Log.d("log","log:状态未识别1");
                        break;
                }
            }

            @Override
            public void onStreamReceived(RemoteStream remoteStream) {
                Log.d("log","log:接收1");
                remoteStream.attach(localView);
            }

            @Override
            public void onClosed() {
                Log.d("log","log:关闭1"+"===");
            }

            @Override
            public void onError(WilddogVideoError wilddogVideoError) {
                Log.d("log","log:错误1"+"==="+wilddogVideoError.toString());
            }
        });

    }

    /**
     * TODO 当确认接收通话请求时设置通话代理
     * @param conversation
     */
    private void setConversation(Conversation conversation,final LocalStream localStream) {
        conversation.setConversationListener(new Conversation.Listener() {
            @Override
            public void onCallResponse(CallStatus callStatus) {
                switch (callStatus){
                    case ACCEPTED:
                        Log.d("log","log:通话被接受");
                        break;
                    case REJECTED:
                        Log.d("log","log:通话被拒绝");
                        break;
                    case BUSY:
                        Log.d("log","log:正忙");
                        break;
                    case TIMEOUT:
                        Log.d("log","log:超时");
                        break;
                    default:
                        Log.d("log","log:状态未识别");
                        break;
                }
            }

            @Override
            public void onStreamReceived(RemoteStream remoteStream) {
                Log.d("log","log:接收");
                remoteStream.attach(localView);
            }

            @Override
            public void onClosed() {
                Log.d("log","log:关闭");
            }

            @Override
            public void onError(WilddogVideoError wilddogVideoError) {
                Log.d("log","log:错误"+"==="+wilddogVideoError.toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        Windows.confirm(VideoActivity.this, "提示", "是否退出通话？", "是", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("spot","回来了");
                setResult(RESULT_OK,intent);
                finish();
            }
        }, "否", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            }
        }, -1, null);
    }

    ///***************************************************这些方法暂时未用到**************************************************************************///

}
