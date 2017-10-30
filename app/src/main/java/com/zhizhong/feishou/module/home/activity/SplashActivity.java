package com.zhizhong.feishou.module.home.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.zhizhong.feishou.base.BaseActivity;


/**
 * Created by Administrator on 2017/10/26.
 */

public class SplashActivity extends BaseActivity {
   /* @BindView(R.id.tv_splash)
    TextView tv_splash;*/
    @Override
    protected int getContentView() {
        return 0;
//        return R.layout.act_splash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                STActivity(MainActivity.class);
                finish();
            }
        },900);
        /*tv_splash.postDelayed(new Runnable() {
            @Override
            public void run() {
                STActivity(SelectUserActivity.class);
                finish();
            }
        },1900);*/
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onViewClick(View v) {

    }

    @Override
    public void again() {

    }
}
