package com.zhizhong.feishou.module.my.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseFragment;
import com.zhizhong.feishou.module.my.adapter.AllOrderAdapter;

import butterknife.BindView;

/**
 * Created by administartor on 2017/8/2.
 */

public class AllOrderFragment extends BaseFragment{
    @BindView(R.id.rv_all_order)
    RecyclerView rv_all_order;

    AllOrderAdapter allOrderAdapter;
    @Override
    public void again() {

    }

    @Override
    protected int getContentView() {
        return R.layout.frag_all_order;
    }

    @Override
    protected void initView() {
        allOrderAdapter=new AllOrderAdapter(mContext,R.layout.item_all_order,0);
        allOrderAdapter.setTestListSize(10);

        rv_all_order.setLayoutManager(new LinearLayoutManager(mContext));
        rv_all_order.setAdapter(allOrderAdapter);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}
