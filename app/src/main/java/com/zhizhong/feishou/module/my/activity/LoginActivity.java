package com.zhizhong.feishou.module.my.activity;

import android.view.View;

import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseActivity;

import butterknife.OnClick;

/**
 * Created by administartor on 2017/8/1.
 */

public class LoginActivity extends BaseActivity {

    @Override
    protected int getContentView() {
        setAppTitle("登录");
        return R.layout.act_login;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_my_login})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_my_login:

            break;
        }
    }

    @Override
    public void again() {

    }
}
