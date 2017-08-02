package com.zhizhong.feishou.module.my.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseFragment;
import com.zhizhong.feishou.module.my.adapter.DaiZhiXingOrderAdapter;

import butterknife.BindView;

/**
 * Created by administartor on 2017/8/2.
 */

public class DaiZhiXingOrderFragment extends BaseFragment{
    @BindView(R.id.rv_dzx_order)
    RecyclerView rv_dzx_order;

    DaiZhiXingOrderAdapter adapter;
    @Override
    public void again() {

    }

    @Override
    protected int getContentView() {
        return R.layout.frag_daizhixing_order;
    }

    @Override
    protected void initView() {
        adapter=new DaiZhiXingOrderAdapter(mContext,R.layout.item_daizhixing_order,0);
        adapter.setTestListSize(10);

        rv_dzx_order.setLayoutManager(new LinearLayoutManager(mContext));
        rv_dzx_order.setAdapter(adapter);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}
