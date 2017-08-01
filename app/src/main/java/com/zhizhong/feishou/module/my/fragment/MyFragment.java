package com.zhizhong.feishou.module.my.fragment;

import android.os.Bundle;
import android.view.View;

import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseFragment;


/**
 * Created by administartor on 2017/8/1.
 */

public class MyFragment extends BaseFragment {
    @Override
    protected int getContentView() {
        return R.layout.frag_my;
    }

    public static MyFragment newInstance() {

        Bundle args = new Bundle();

        MyFragment fragment = new MyFragment();
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
