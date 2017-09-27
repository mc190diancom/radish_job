package com.liewei.radish_job.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Message;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.liewei.radish_job.App;
import com.liewei.radish_job.activity.LoginActivity;
import com.liewei.radish_job.activity.VideoActivity;
import com.liewei.radish_job.async.AsyncUtil;
import com.liewei.radish_job.async.Callback;
import com.liewei.radish_job.async.Result;
import com.liewei.radish_job.wilddogutil.InitWildSdk;

import java.util.concurrent.Callable;

/**
 * Created by murphy on 2017/9/14.
 */

public class JavaScriptinterface {

    Activity mActivity;

    public JavaScriptinterface(Activity mActivity) {
        this.mActivity = mActivity;
    }

    /** 与js交互时用到的方法，在js里直接调用的 */
    @JavascriptInterface
    public void startActivity() {
        Intent intent = new Intent();
        intent.setClass(mActivity, VideoActivity.class);
        mActivity.startActivity(intent);
    }

    /** 与js交互时用到的方法，在js里直接调用的 */
    @JavascriptInterface
    public String getValue() {
        UserPreference user = new UserPreference(App.self);
        String content = "{\"id\":\""+user.getId()+"\","+"\"login_user\":\""+user.getUser()+"\","+"\"login_mobile\":\""+user.getMobile()+"\","+"\"login_pwd\":\""+user.getPwd()+"\"}";
        return content;
    }

    @JavascriptInterface
    public void doit(String interview_id,String interview_num_id) {
        InitWildSdk wildSdk;
        wildSdk = new InitWildSdk(App.self);
        wildSdk.initWildDogUser(interview_id,interview_num_id);//初始化获取到用户
    }

}
