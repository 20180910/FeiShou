package com.zhizhong.feishou.module.renwu.fragment;

import android.os.Bundle;
import android.view.View;

import com.zhizhong.feishou.base.BaseFragment;

/**
 * Created by administartor on 2017/8/1.
 */

public class RenWuFragment extends BaseFragment {
    @Override
    protected int getContentView() {
        return 0;
    }

    public static RenWuFragment newInstance() {

        Bundle args = new Bundle();

        RenWuFragment fragment = new RenWuFragment();
        fragment.setArguments(args);
        return fragment;
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
