package com.liewei.radish_job.util;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.liewei.radish_job.R;


public class Windows {

    public static MyProgressDialog waiting(final Context context) {
        return waiting(context, null);
    }

    public static MyProgressDialog waiting(final Context context, final String text) {
        MyProgressDialog commonDialog = new MyProgressDialog(context, R.layout.common_waiting, R.style.clean_dialog) {
            private ImageView imageView;

            public void initListener() {
                imageView = (ImageView) findViewById(R.id.waiting);
                if (!TextUtils.isEmpty(text)) {
                    TextView v = (TextView) findViewById(R.id.text);
                    v.setText(text);
                }
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.wait);
                animation.setInterpolator(new LinearInterpolator());
                imageView.startAnimation(animation);
            }

            @Override
            public void closeListener() {
                imageView.clearAnimation();
            }
        };
        commonDialog.show();
        return commonDialog;
    }

    public static CommonDialog confirm(Context ctx, int icon, String title, String msg, String okText,
                                       View.OnClickListener okListener, String cancelText, final View.OnClickListener cancelListener, int timeCount,
                                       UiTool.OnCompleteListener onCompleteListener) {
        CommonDialog dialog = new CommonDialog(ctx, icon, title, okText, okListener, cancelText, cancelListener,
                timeCount, onCompleteListener, true);
        TextView msgView = (TextView) LayoutInflater.from(ctx).inflate(R.layout.dialog_content_msg, null);
        dialog.setCustom(msgView);
        msgView.setText(msg);
        dialog.show();
        return dialog;
    }

    public static CommonDialog confirm(Context ctx, String title, String msg, String okText, View.OnClickListener okListener,
                                       String cancelText, final View.OnClickListener cancelListener, int timeCount,
                                       UiTool.OnCompleteListener onCompleteListener) {
        return confirm(ctx, 0, title, msg, okText, okListener, cancelText, cancelListener, timeCount,
                onCompleteListener);
    }

    public static AlertDialog confirmUpDialog(Context ctx, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

        AlertDialog dialog = builder.create();
        // 用dialog添加自定义的view
        dialog.setView(view);
        dialog.setCancelable(false);
        return dialog;

    }
}
