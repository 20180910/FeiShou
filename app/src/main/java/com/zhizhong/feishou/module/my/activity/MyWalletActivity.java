package com.zhizhong.feishou.module.my.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/8/2.
 */

public class MyWalletActivity extends BaseActivity {
    LoadMoreAdapter adapter;
    @BindView(R.id.rv_my_wallet)
    RecyclerView rv_my_wallet;

    @Override
    public void again() {

    }

    @Override
    protected int getContentView() {
        setAppTitle("我的钱包");
        return R.layout.act_my_wallet;
    }

    @Override
    protected void initView() {
        adapter=new LoadMoreAdapter(mContext,R.layout.item_wallet_shouyi,0) {
            @Override
            public void bindData(LoadMoreViewHolder loadMoreViewHolder, int i, Object o) {

            }
        };
        adapter.setTestListSize(10);

        rv_my_wallet.setLayoutManager(new LinearLayoutManager(mContext));
        rv_my_wallet.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.ll_wallet_szmx,R.id.ll_my_tixian})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.ll_wallet_szmx:
                STActivity(ShouZhiMingXiActivity.class);
                break;
            case R.id.ll_my_tixian:
                STActivity(TiXianActivity.class);
                break;
        }
    }
}
