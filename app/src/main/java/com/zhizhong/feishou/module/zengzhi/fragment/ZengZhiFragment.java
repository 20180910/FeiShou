package com.zhizhong.feishou.module.zengzhi.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseFragment;
import com.zhizhong.feishou.module.zengzhi.adapter.ZengZhiAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by administartor on 2017/8/1.
 */

public class ZengZhiFragment extends BaseFragment {

    @BindView(R.id.tl_my_order)
    TabLayout tl_my_order;
    @BindView(R.id.vp_zz)
    ViewPager vp_zz;
    ZengZhiAdapter adapter;
    List<Fragment> list;

    SheBeiGouMaiFragment sheBeiFragment;
    ZhuanYePeiXunFragment peiXunFragment;
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
        sheBeiFragment= SheBeiGouMaiFragment.newInstance();
        peiXunFragment= ZhuanYePeiXunFragment.newInstance();

        adapter=new ZengZhiAdapter(getChildFragmentManager());

        list = new ArrayList<>();
        list.add(sheBeiFragment);
        list.add(peiXunFragment);

        adapter.setList(list);

        vp_zz.setAdapter(adapter);
        vp_zz.setOffscreenPageLimit(list.size()-1);

        tl_my_order.setupWithViewPager(vp_zz);


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
