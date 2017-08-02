package com.zhizhong.feishou.module.my.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseActivity;
import com.zhizhong.feishou.module.my.adapter.OrderFragmentAdapter;
import com.zhizhong.feishou.module.my.fragment.AllOrderFragment;
import com.zhizhong.feishou.module.my.fragment.CompleteOrderFragment;
import com.zhizhong.feishou.module.my.fragment.DaiJieDanOrderFragment;
import com.zhizhong.feishou.module.my.fragment.DaiJieSuanOrderFragment;
import com.zhizhong.feishou.module.my.fragment.DaiZhiXingOrderFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by administartor on 2017/8/2.
 */

public class MyOrderActivity extends BaseActivity {

    @BindView(R.id.tl_my_order)
    TabLayout tl_all_order;
    @BindView(R.id.vp_my_order)
    ViewPager vp_my_order;

    OrderFragmentAdapter adapter;
    List<Fragment> list;

    AllOrderFragment allOrderFragment;
    DaiJieDanOrderFragment daiJieDanOrderFragment;
    DaiZhiXingOrderFragment daiZhiXingOrderFragment;
    DaiJieSuanOrderFragment daiJieSuanOrderFragment;
    CompleteOrderFragment completeOrderFragment;

    @Override
    public void again() {

    }

    @Override
    protected int getContentView() {
        setAppTitle("我的订单");
        return R.layout.act_my_order;
    }

    @Override
    protected void initView() {
        adapter = new OrderFragmentAdapter(getSupportFragmentManager());

        allOrderFragment = new AllOrderFragment();
        daiJieDanOrderFragment = new DaiJieDanOrderFragment();
        daiZhiXingOrderFragment = new DaiZhiXingOrderFragment();
        daiJieSuanOrderFragment = new DaiJieSuanOrderFragment();
        completeOrderFragment = new CompleteOrderFragment();

        list = new ArrayList<>();
        list.add(allOrderFragment);
        list.add(daiJieDanOrderFragment);
        list.add(daiZhiXingOrderFragment);
        list.add(daiJieSuanOrderFragment);
        list.add(completeOrderFragment);

        adapter.setList(list);
        vp_my_order.setAdapter(adapter);
        vp_my_order.setOffscreenPageLimit(list.size()-1);

        tl_all_order.setupWithViewPager(vp_my_order);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }


}
