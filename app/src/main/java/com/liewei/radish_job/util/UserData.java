package com.liewei.radish_job.util;

import android.util.Log;

import com.liewei.radish_job.App;
import com.liewei.radish_job.async.ExceptionHandler;
import com.liewei.radish_job.async.Result;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;


/**
 * Created by murphy on 2017/9/8.
 */

public class UserData {
    /**
     * 发送验证码
     *
     * @return
     */
    public static Result<Void> sendMsg(String mobile) {
        Result<Void> result = new Result<Void>();
        try {
            Log.e("res1",Config.SERVER_URL + "Login/SmsApi/mobile/"+mobile);
            String res = HttpUtil.post(Config.SERVER_URL + "Login/SmsApi/mobile/"+mobile, null);//Login/SmsApi/mobile/15196666130
            Log.e("res1",res);
            System.out.println("CommonData:" + res);
        } catch (Throwable e) {
            e.printStackTrace();
            result.setError(-1);
            result.setErrorMsg("");
            result.setThrowable(e);
            ExceptionHandler.handleException(App.self, result);
        }
        return result;
    }

    /**
     * 登录验证
     *
     * @return
     */
    public static Result<String> validate(String mobile,String code) {
        Result<String> result = new Result<String>();
        try {
            Log.e("res2",Config.SERVER_URL + "Login/loginphone/"+mobile+"/code/"+code);
            String res = HttpUtil.post(Config.SERVER_URL + "Login/Login/loginphone/"+mobile+"/code/"+code, null);//Yc/sendMsg
            Log.e("res2",res);
            System.out.println("CommonData:" + res);
            result.setData(res);
        } catch (Throwable e) {
            e.printStackTrace();
            result.setError(-1);
            result.setErrorMsg("");
            result.setThrowable(e);
            ExceptionHandler.handleException(App.self, result);
        }

        return result;
    }

    /**
     * 登录验证
     * @return
     */
    public static Result<String> getStatus(String uid,String interview_id,String interview_num_id) {
        Result<String> result = new Result<String>();
        try {
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid", uid));
            params.add(new BasicNameValuePair("interview_num", interview_id));
            params.add(new BasicNameValuePair("interview_num_id", interview_num_id));
            Log.e("res2",params.toString());
            String res = HttpUtil.post(Config.SERVER_URL + "Interview/intervieweeInfo ", params);//Yc/sendMsg
            Log.e("res2",res);
            System.out.println("CommonData:" + res);
            result.setData(res);
        } catch (Throwable e) {
            e.printStackTrace();
            result.setError(-1);
            result.setErrorMsg("");
            result.setThrowable(e);
            ExceptionHandler.handleException(App.self, result);
        }

        return result;
    }

}
