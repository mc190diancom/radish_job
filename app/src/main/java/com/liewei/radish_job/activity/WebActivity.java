package com.liewei.radish_job.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import com.liewei.radish_job.R;
import com.liewei.radish_job.util.WebUtil;
import com.liewei.radish_job.views.WebViewController;
import com.wilddog.video.Conversation;
import com.wilddog.video.WilddogVideo;
import com.wilddog.video.WilddogVideoError;

public class WebActivity extends BaseActivity {

    private static final String TAG = "WebActivity";
    private WebView webView;
    private String title;
    private String url;
    public static String type;
    private static final int REQUEST_CODE = 0; // 请求码
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA
    };
    //private HeaderHolder head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        type = null;
        title = getIntent().getStringExtra("title");
        //url = getIntent().getStringExtra("url");
        url = "file:///android_asset/www/index.html";//这里是从assets文件取得，但是实际上应该是从登录界面传过来
        if (!url.isEmpty()) {
            initView();
            initData();
        }
        IntentFilter filter = new IntentFilter("success");
        LocalBroadcastManager.getInstance(WebActivity.this).registerReceiver(bookReceiver, filter);
    }


    private BroadcastReceiver bookReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Intent tagert = new Intent(self,VideoActivity.class);
            startActivity(tagert);
        }
    };
   /* *//**
     * TODO 当获取到用户信息后，开启通话监听；有通话请求将执行
     *//*
    private BroadcastReceiver bookReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final WilddogVideo video = wildSdk.initVideoSDK();//当知道用户加载完毕后就初始化sdk
            if(1==type){
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
            }else{

            }
        }
    };*/

    private void initData() {
        webView.loadUrl(url);
    }

    protected void initView() {
        webView = (WebView) findViewById(R.id.more_webview);
        WebViewController ctrl = (WebViewController) findViewById(R.id.more_controller);
        ctrl.setVisibility(getIntent().getBooleanExtra("controller", false) ? View.VISIBLE : View.GONE);
        WebUtil.wrapWebView(webView, ctrl,this);
        ctrl.setWebView(webView, url);
        int sdk=android.os.Build.VERSION.SDK_INT;
        if (sdk>=23){
            Intent intent=new Intent(this,PermissionActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle bundle=new Bundle();
            bundle.putStringArray("permission",PERMISSIONS);
            PermissionActivity.startActivityForResult(this,REQUEST_CODE,PERMISSIONS);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        webView.onResume();
    }

   private long firstTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
            if (System.currentTimeMillis()-firstTime>2000){
                Toast.makeText(WebActivity.this,"再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime=System.currentTimeMillis();
            }else{
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((ViewGroup) webView.getParent()).removeView(webView);
        webView.destroy();
    }


}
