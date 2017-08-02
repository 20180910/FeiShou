package com.zhizhong.feishou.module.my.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseActivity;
import com.zhizhong.feishou.module.my.adapter.ShouZhiMingXiFragmentAdapter;
import com.zhizhong.feishou.module.my.fragment.ShouZhiMingXiFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by administartor on 2017/8/2.
 */

public class ShouZhiMingXiActivity extends BaseActivity {
    @BindView(R.id.tl_my_szmx)
    TabLayout tl_my_szmx;

    @BindView(R.id.vp_my_szmx)
    ViewPager vp_my_szmx;

    List<Fragment> list=new ArrayList<>();
    ShouZhiMingXiFragmentAdapter adapter;

    ShouZhiMingXiFragment shouRuFragment;
    ShouZhiMingXiFragment zhiChuFragment;

    @Override
    public void again() {

    }

    @Override
    protected int getContentView() {
        setAppTitle("收支明细");
        return R.layout.act_shou_zhi_ming_xi;
    }

    @Override
    protected void initView() {
        adapter=new ShouZhiMingXiFragmentAdapter(getSupportFragmentManager());
        shouRuFragment=ShouZhiMingXiFragment.newInstance(1);
        zhiChuFragment=ShouZhiMingXiFragment.newInstance(0);
        list.add(shouRuFragment);
        list.add(zhiChuFragment);
        adapter.setList(list);
        vp_my_szmx.setOffscreenPageLimit(list.size()-1);
        vp_my_szmx.setAdapter(adapter);

        tl_my_szmx.setupWithViewPager(vp_my_szmx);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}
