package com.zhizhong.feishou.module.zengzhi.fragment;

import android.os.Bundle;
import android.view.View;

import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseFragment;

/**
 * Created by administartor on 2017/8/1.
 */

public class ZengZhiFragment extends BaseFragment {
    @Override
    protected int getContentView() {
        return R.layout.frag_zeng_zhi;
    }

    public static ZengZhiFragment newInstance() {


        Bundle args = new Bundle();

        ZengZhiFragment fragment = new ZengZhiFragment();
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

    @Override
    public void again() {

    }
}
