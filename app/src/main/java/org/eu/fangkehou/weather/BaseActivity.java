package org.eu.fangkehou.weather;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.eu.fangkehou.weather.util.ActivityUtil;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 添加Activity到堆栈
        ActivityUtil.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 结束Activity&从栈中移除该Activity
        ActivityUtil.getInstance().removeActivity(this);
    }

}