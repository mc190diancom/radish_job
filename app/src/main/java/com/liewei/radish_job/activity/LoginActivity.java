package com.liewei.radish_job.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.liewei.radish_job.R;
import com.liewei.radish_job.async.AsyncUtil;
import com.liewei.radish_job.async.Callback;
import com.liewei.radish_job.async.Result;
import com.liewei.radish_job.util.Config;
import com.liewei.radish_job.util.OnUpdateListner;
import com.liewei.radish_job.util.UpdateUtils;
import com.liewei.radish_job.util.UserData;
import com.liewei.radish_job.util.UserPreference;
import com.liewei.radish_job.util.Windows;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;


public class LoginActivity extends Activity implements View.OnClickListener,OnUpdateListner {
    @ViewInject(R.id.log_id)
    private EditText log_id;
    @ViewInject(R.id.log_pwd)
    private EditText log_pwd;
    @ViewInject(R.id.log_login)
    private Button log_login;
    @ViewInject(R.id.vcode)
    private TextView vcode;
    String path;//放置HTML文件的路径

    private AlertDialog dialog;
    private TextView tv_Log;
    private ProgressBar update_Progress;// 进度条
    private TextView update_Description;// 显示当前更新进度
    private View viewWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        ViewUtils.inject(LoginActivity.this);
        log_login.setOnClickListener(this);
        vcode.setOnClickListener(this);
        //isFirst();
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    UnZip.unZip(Config.DIR_PATH+".zip",Config.DIR_PATH2 ,false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();*/
    }

    @Override
    public void onClick(View v) {
        if (v == log_login) {
            //login();
            goNext();
        } else if (v == vcode) {
            onVcodeBtnClick();//手机验证
            //Toast.makeText(LoginActivity.this, "验证码发送成功，请注意接收短信", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 登录验证
     */
    private void login() {
        //pfr.updateUser("15196666130",false);//获取用户信息并更新
       Log.e("Logacti","sss");
        final String tel = log_id.getText().toString();
        final String code = log_pwd.getText().toString();
        Log.e("Logacti",tel);
        if (!Patterns.MOBILE.matcher(tel).matches()) {
            Toast.makeText(LoginActivity.this, "手机号不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(LoginActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        AsyncUtil.goAsync(new Callable<Result<String>>() {

            @Override
            public Result<String> call() throws Exception {
                return UserData.validate(tel, code);
                //return UserData.login(tel, code);
            }
        }, new Callback<Result<String>>() {

            @Override
            public void onHandle(Result<String> result) {
                if (result.ok()) {
                    try {
                        JSONObject jsonObject = new JSONObject(result.getData());
                        int status = jsonObject.optInt("status");
                        String info = jsonObject.optString("info");
                        JSONObject data = jsonObject.optJSONObject("data");
                        String id = data.optString("id");
                        String login_user = data.optString("login_user");
                        String login_mobile = data.optString("login_mobile");
                        String login_pwd = data.optString("login_pwd");
                        new UserPreference(LoginActivity.this).updateUser(id,login_user,login_mobile,login_pwd);
                        if(1==status){
                            //Toast.makeText(LoginActivity.this,id+login_user+login_mobile+login_pwd,Toast.LENGTH_SHORT).show();
                           goNext();
                        }else{
                            Toast.makeText(LoginActivity.this,info,Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                }
            }
        });
    }


    private CodeCounterHandler vCodeCounterHandler;
    /**
     * 获取手机验证码
     */
    private void onVcodeBtnClick() {
        final String tel = log_id.getText().toString();
        if (!Patterns.MOBILE.matcher(tel).matches()) {
            Toast.makeText(LoginActivity.this, "手机号不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        vcode.setEnabled(false);
        int max = 60;
        vCodeCounterHandler = new CodeCounterHandler(LoginActivity.this);
        vCodeCounterHandler.sendMessage(Message.obtain(vCodeCounterHandler, 0, max, 0));
        AsyncUtil.goAsync(new Callable<Result<Void>>() {

            @Override
            public Result<Void> call() throws Exception {
                return UserData.sendMsg(tel);
            }
        }, new Callback<Result<Void>>() {

            @Override
            public void onHandle(Result<Void> result) {
                if (result.ok()) {
                    Toast.makeText(LoginActivity.this, "验证码发送成功，请注意接收短信", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, result.getErrorMsg(), Toast.LENGTH_LONG).show();
                    vCodeCounterHandler.removeMessages(0);
                    vCodeCounterHandler.sendMessage(Message.obtain(vCodeCounterHandler, 0, 0, 0));
                }
            }
        });
    }

    @Override
    public void onUpdate(boolean isUpdate, boolean isqueryOk, String LOG, int length, String VSCODE) {
        if(length>0){
            View view = LayoutInflater.from(LoginActivity.this).inflate(R.layout.item_update_layout, null);
            dialog = Windows.confirmUpDialog(LoginActivity.this, view);
            dialog.show();
            viewWindow = dialog.getWindow().getDecorView();
            tv_Log = (TextView) viewWindow.findViewById(R.id.tv_Log);
            update_Progress = (ProgressBar) viewWindow.findViewById(R.id.update_Progress);
            update_Description = (TextView) viewWindow.findViewById(R.id.update_Description);
            tv_Log.setText(LOG);
            new UpdateUtils(this).DownLoadApk("http://47.92.1.11/download/radish.zip",update_Progress,dialog);
        }
    }

    public static class Patterns {
        public static Pattern NUMBER = Pattern.compile("[0-9]+");
        public static Pattern MOBILE = Pattern.compile("^1(3|5|7|8)[0-9]{9}$");
        public static Pattern CAR_NUM = Pattern.compile("^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_a-z_0-9]{5}$");
    }

    private static class CodeCounterHandler extends Handler {
        WeakReference<LoginActivity> ref;

        public CodeCounterHandler(LoginActivity loginActivity) {
            ref = new WeakReference<LoginActivity>(loginActivity);
        }

        public void handleMessage(Message msg) {
            LoginActivity act = ref.get();
            if (act == null) {
                return;
            }
            act.vcode.setText("重新发送(" + msg.arg1 + ")");
            if (msg.arg1 > 0) {
                sendMessageDelayed(Message.obtain(this, 0, --msg.arg1, 0), 1000);
            } else {
                act.vcode.setEnabled(true);
                act.vcode.setText("获取验证码");
            }
        }
        ;
    }

    /**
     * 跳转下一页
     */
    protected void goNext() {
        Intent intent = new Intent(LoginActivity.this, WebActivity.class);
        intent.putExtra("title", "测试");
        intent.putExtra("url", "file://"+ Config.DIR_PATH+"/index.html");//"file:///android_asset/www/index.html"
        startActivity(intent);
        finish();
    }

    /**
     * TODO 判断是否是第一次安装
     */
    private void isFirst() {
        String pathRoot = Environment.getExternalStorageDirectory()
                .getAbsolutePath();
        final File file = new File(pathRoot + "/Android/data/"
                + getPackageName() + "/files/radish");
        path = file.getAbsolutePath();
        if(!file.exists()){
            long t = System.currentTimeMillis();
            copyFilesFassets("www",path);
            Log.e("file","file:time"+(System.currentTimeMillis()-t));
        }else{
            Log.e("file","file:"+path);
        }
        checkUpdata();
    }

    /**
     *  从assets目录中复制整个文件夹内容
     *  @param  oldPath  String  原文件路径  如：/aa
     *  @param  newPath  String  复制后路径  如：xx:/bb/cc
     */
    public void copyFilesFassets(String oldPath, String newPath) {
        try {
            String fileNames[] = LoginActivity.this.getAssets().list(oldPath);//获取assets目录下的所有文件及目录名
            if (fileNames.length > 0) {//如果是目录
                File file = new File(newPath);
                file.mkdirs();//如果文件夹不存在，则递归
                for (String fileName : fileNames) {
                    copyFilesFassets(oldPath + "/" + fileName,newPath+"/"+fileName);
                }
            } else {//如果是文件
                InputStream is = LoginActivity.this.getAssets().open(oldPath);
                FileOutputStream fos = new FileOutputStream(new File(newPath));
                byte[] buffer = new byte[1024];
                int byteCount=0;
                while((byteCount=is.read(buffer))!=-1) {//循环从输入流读取 buffer字节
                    fos.write(buffer, 0, byteCount);//将读取的输入流写入到输出流
                }
                fos.flush();//刷新缓冲区
                is.close();
                fos.close();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //如果捕捉到错误则通知UI线程
            //MainActivity.handler.sendEmptyMessage(COPY_FALSE);
        }
    }

    /**
     * TODO　去服务器检查更新
     */
    private void checkUpdata() {
        UpdateUtils updateUtils = new UpdateUtils(this);
        updateUtils.queryVersion(this);
    }
}
