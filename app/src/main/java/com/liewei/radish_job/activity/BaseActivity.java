package com.liewei.radish_job.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.liewei.radish_job.R;
import com.loror.lororUtil.view.ViewUtil;

public class BaseActivity extends FragmentActivity {
    private boolean created;
    protected SharedPreferences sharedPreferences;
    protected Context context;
    protected Activity self;
    private Boolean start = true;

    public boolean getStart() {
        return start;
    }
    /**
     * 注销账号监听
     */
    private BroadcastReceiver finishReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            start = intent.getBooleanExtra("start", true);
            if (!context.getClass().getName().equals(intent.getStringExtra("ignore"))) {
                finish();
            }
        }
    };

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        created = true;
        sharedPreferences = getSharedPreferences("info", Context.MODE_PRIVATE);
        context = self = this;
        super.onCreate(savedInstanceState);
        // 去除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);

		/*if (android.os.Build.VERSION.SDK_INT >= 19) {
            // 透明状态栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			if (android.os.Build.VERSION.SDK_INT >= 21) {
				Window window = getWindow();
				window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
						| WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
				window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
						| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
				window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
				window.setStatusBarColor(Color.TRANSPARENT);
				window.setNavigationBarColor(Color.TRANSPARENT);
			}
			try {
				setNavigationColor();
			} catch (Throwable e) {
				System.out.println("cannot set navigtion bar color");
			} // 设置虚拟按键背景色
		}*/
        // 透明导航栏
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    public interface OnTitleChosed {
        void chosed(int position);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(finishReceiver);
        super.onDestroy();
    }


    /**
     * 初始化控件
     */
    protected void initView() {
        ViewUtil.find(this);
    }


    /**
     * 设置标题
     */
    public void setContentTitle(CharSequence title) {
        TextView titleView = (TextView) findViewById(R.id.title);
        if (titleView == null) {
            System.err.println("title view cannot be found");
            return;
        }
        titleView.setText(title);
    }

    private TextView right;

    /**
     * 设置左侧图像
     */
    public void setLeftImage(int resource) {
        ImageView back_btn = (ImageView) findViewById(R.id.back_btn);
        if (back_btn == null) {
            System.err.println("left image cannot be found");
            return;
        }
        if (resource == -1) {
            back_btn.setVisibility(View.GONE);
            Log.e("back_btn", "back_btn");
        } else if (resource == 1) {
            back_btn.setVisibility(View.VISIBLE);
        } else {
            back_btn.setImageResource(resource);
        }

    }

    /**
     * 设置右侧图像
     */
    public void setRightImage(int resource, OnClickListener listener) {
        ImageView right = (ImageView) findViewById(R.id.right_img);
        if (right == null) {
            System.err.println("right image cannot be found");
            return;
        }
        if (resource == -1) {
            right.setVisibility(View.GONE);
        } else {
            right.setVisibility(View.VISIBLE);
            right.setImageResource(resource);
        }
        if (listener != null)
            right.setOnClickListener(listener);
    }

    /**
     * 设置标题栏右侧文字与点击事件
     */
    public void setRight(CharSequence text, OnClickListener listener) {
        if (right == null)
            right = (TextView) findViewById(R.id.right_button);
        if (right == null) {
            System.err.println("right textview cannot be found");
            return;
        }
        right.setVisibility(View.VISIBLE);
        right.setText(text);
        if (listener != null)
            right.setOnClickListener(listener);
    }

    /**
     * 设置标题栏右侧文字、文字颜色与点击事件
     */
    public void setRight(CharSequence text, int textColorRes, OnClickListener listener) {
        if (right == null)
            right = (TextView) findViewById(R.id.right_button);
        if (right == null) {
            System.err.println("right textview cannot be found");
            return;
        }
        right.setVisibility(View.VISIBLE);
        right.setText(text);
        right.setTextColor(getResources().getColor(textColorRes));
        if (listener != null)
            right.setOnClickListener(listener);
    }

    /**
     * 设置标题栏右侧文字、文字颜色
     */
    public void setRight(CharSequence text, int textColorRes) {
        if (right == null)
            right = (TextView) findViewById(R.id.right_button);
        if (right == null) {
            System.err.println("right textview cannot be found");
            return;
        }
        right.setVisibility(View.VISIBLE);
        right.setText(text);
        right.setTextColor(getResources().getColor(textColorRes));
    }

    /**
     * 隐藏标题栏右侧文字
     */
    public void hideRight() {
        if (right == null)
            right = (TextView) findViewById(R.id.right_button);
        if (right == null) {
            System.err.println("right textview cannot be found");
            return;
        }
        right.setVisibility(View.GONE);
    }

    /**
     * 设置状态栏颜色
     */
    @SuppressLint("NewApi")
    public void setNavigationColor() throws Throwable {
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.foot));
        }
    }

    private ProgressDialog mypDialog;

    /**
     * 开启等待弹窗
     */
    public void openWaiteDialog(String message) {
        if (mypDialog == null) {
            mypDialog = new ProgressDialog(this);
            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mypDialog.setIndeterminate(false);// 进度条是否不明确
            mypDialog.setCancelable(false);// 是否可以按退回按键取消
        }
        mypDialog.setMessage(message);
        mypDialog.show();
    }

    /**
     * 关闭等待弹窗
     */
    public void closeWaiteDialog() {
        if (mypDialog != null) {
            mypDialog.cancel();
        }
    }

    /**
     * 显示提醒
     */
    protected void showNotice(String title, String message) {
        AlertDialog dialog = new AlertDialog.Builder(context).setTitle(title).setMessage(message)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create();
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * 打印toast
     */
    protected void toast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private long time;
    private String lastMsg;

    /**
     * 打印toast，此方法等上一个toast完毕再打印否则不打印
     */
    protected void toastInOrder(String message) {
        long now = System.currentTimeMillis();
        if (now - time > 2000 || !message.equals(lastMsg)) {
            time = now;
            lastMsg = message;
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        if (!created)
            throw new IllegalArgumentException("you didinot call super onCreate");
        super.onStart();
    }



    private static final String TAG = "BaseActivity";


    @Override
    protected void onStop() {
        super.onStop();
    }


    /**
     * 左上角返回按钮事件
     */
    public void onBack(View view) {
        finish();
    }

    /**
     * 空白事件，吸收事件
     */
    public void onNull(View view) {
    }
}
