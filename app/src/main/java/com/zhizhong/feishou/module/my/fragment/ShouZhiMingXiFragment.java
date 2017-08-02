package com.zhizhong.feishou.module.my.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by administartor on 2017/8/2.
 */

public class ShouZhiMingXiFragment extends BaseFragment {
    @BindView(R.id.rv_szmx)
    RecyclerView rv_szmx;

    LoadMoreAdapter adapter;
    @Override
    public void again() {

    }

    public static ShouZhiMingXiFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt("type",type);
        ShouZhiMingXiFragment fragment = new ShouZhiMingXiFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getContentView() {
        return R.layout.frag_shou_zhi_ming_xi;
    }

    @Override
    protected void initView() {
        adapter=new LoadMoreAdapter(mContext,R.layout.item_wallet_shouyi,0) {
            @Override
            public void bindData(LoadMoreViewHolder loadMoreViewHolder, int i, Object o) {

            }
        };
        adapter.setTestListSize(7);
        rv_szmx.setLayoutManager(new LinearLayoutManager(mContext));
        rv_szmx.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}
