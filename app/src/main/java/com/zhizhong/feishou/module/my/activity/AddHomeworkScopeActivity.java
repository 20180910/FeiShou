package com.zhizhong.feishou.module.my.activity;

import android.view.View;

import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseActivity;

/**
 * Created by administartor on 2017/8/3.
 */

public class AddHomeworkScopeActivity extends BaseActivity {
    @Override
    public void again() {

    }

    @Override
    protected int getContentView() {
        setAppTitle("新增作业范围");
        setAppRightTitle("确定");
        setAppRightTitleColor(getResources().getColor(R.color.blue));
        return R.layout.act_add_homework_scope;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}
