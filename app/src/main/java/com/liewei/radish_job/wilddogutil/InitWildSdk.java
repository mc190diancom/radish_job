package com.liewei.radish_job.wilddogutil;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.liewei.radish_job.App;
import com.liewei.radish_job.activity.VideoActivity;
import com.liewei.radish_job.async.AsyncUtil;
import com.liewei.radish_job.async.Callback;
import com.liewei.radish_job.async.Result;
import com.liewei.radish_job.util.Config;
import com.liewei.radish_job.util.UserData;
import com.wilddog.video.Conversation;
import com.wilddog.video.LocalStream;
import com.wilddog.video.WilddogVideo;
import com.wilddog.wilddogauth.WilddogAuth;
import com.wilddog.wilddogauth.core.Task;
import com.wilddog.wilddogauth.core.listener.OnCompleteListener;
import com.wilddog.wilddogauth.core.result.AuthResult;
import com.wilddog.wilddogauth.model.WilddogUser;
import com.wilddog.wilddogcore.WilddogApp;
import com.wilddog.wilddogcore.WilddogOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by murphy on 2017/9/11.
 */

public class InitWildSdk {
    private WilddogAuth mAuth;
    private WilddogUser user;//通过登录或者创建用户获取到的WilddogUser
    private WilddogVideo video;//通过用户信息初始化的video
    private Conversation mConversation;//用于发起通话请求的实例
    private Context mContext;

    private static String TAG = "InitWildSdk";

    public InitWildSdk(Context mContext){
        this.mContext = mContext;
    }

    /**
     * TODO 初始化视频通话的用户
     */
    public void initWildDogUser(String interview_id,String interview_num_id) {
        mAuth = WilddogAuth.getInstance();
        sign(interview_id,interview_num_id);
    }

    /**
     * TODO 自定义登录方式
     */
    private void sign(final String interview_id,final String interview_num_id){
        mAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> var1) {
                if(var1.isSuccessful()){
                    Log.e("user","user:"+var1.getResult().getWilddogUser().toString()+"==="+var1.getResult().getWilddogUser().getUid());
                    Log.d("success","Login success!");
                    Log.d("Anonymous",String.valueOf(var1.getResult().getWilddogUser().isAnonymous()));
                    user = var1.getResult().getWilddogUser();
                    quest(user.getUid(),interview_id,interview_num_id);
                }else {
                    Log.d("failure","reason:"+var1.getException());
                }
            }
        });
    }

    /**
     * TODO 当创建好uid后，就通知服务器准备开启视频
     * @param uid
     * @param interview_id
     * @param interview_num_id
     */
    private void quest(final String uid,final String interview_id,final String interview_num_id){
        AsyncUtil.goAsync(new Callable<Result<String>>() {

            @Override
            public Result<String> call() throws Exception {
                return UserData.getStatus(uid,interview_id,interview_num_id);
            }
        }, new Callback<Result<String>>() {

            @Override
            public void onHandle(Result<String> result) {
                if (result.ok()) {
                    Intent intent = new Intent("success");
                    intent.putExtra("isSuccess",true);
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                } else {
                }
            }
        });
    }


    /**
     * TODO 初始化videoSDK
     */
    public WilddogVideo initVideoSDK(){
        String token = user.getToken(false).getResult().getToken();
        //初始化 WilddogVideo SDK
        WilddogVideo.initialize(App.self, Config.VIDEO_ID,token);//initializeWilddogVideo(App.self, Config.APP_ID,token);
        //获取 WilddogVideo对象
        video = WilddogVideo.getInstance();
        return video;
    }

    public void startConversation(WilddogVideo video,LocalStream localStream){
        Log.e("log","log:"+user.getUid());
        mConversation = video.call("c4341fb913a3c8d7ec33f01c7629",localStream,"发起啦");//user.getUid()
    }

}
