package com.zhizhong.feishou.module.my.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseFragment;
import com.zhizhong.feishou.module.my.adapter.DaiJieSuanOrderAdapter;

import butterknife.BindView;

/**
 * Created by administartor on 2017/8/2.
 */

public class DaiJieSuanOrderFragment extends BaseFragment{
    @BindView(R.id.rv_djs_order)
    RecyclerView rv_djs_order;

    DaiJieSuanOrderAdapter adapter;
    @Override
    public void again() {

    }

    @Override
    protected int getContentView() {
        return R.layout.frag_daijiesuan_order;
    }

    @Override
    protected void initView() {
        adapter=new DaiJieSuanOrderAdapter(mContext,R.layout.item_daijiesuan_order,0);
        adapter.setTestListSize(10);

        rv_djs_order.setLayoutManager(new LinearLayoutManager(mContext));
        rv_djs_order.setAdapter(adapter);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}
