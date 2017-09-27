package com.liewei.radish_job;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.liewei.radish_job.activity.BaseActivity;
import com.liewei.radish_job.activity.WebActivity;
import com.loror.lororUtil.view.Find;


public class MainActivity extends BaseActivity {
    @Find(R.id.btn_start)
    private Button btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("title", "测试");
                intent.putExtra("url", "file:///android_asset/www/index.html");
                startActivity(intent);
            }
        });
    }
}
