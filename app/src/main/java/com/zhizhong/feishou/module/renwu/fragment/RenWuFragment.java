package com.zhizhong.feishou.module.renwu.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseFragment;
import com.zhizhong.feishou.module.renwu.adapter.RenWuAdapter;

import butterknife.BindView;

/**
 * Created by administartor on 2017/8/1.
 */

public class RenWuFragment extends BaseFragment {
    RenWuAdapter renWuAdapter;

    @BindView(R.id.rv_renwu)
    RecyclerView rv_renwu;

    @Override
    protected int getContentView() {
        return R.layout.frag_ren_wu;
    }

    public static RenWuFragment newInstance() {

        Bundle args = new Bundle();

        RenWuFragment fragment = new RenWuFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void initView() {
        renWuAdapter=new RenWuAdapter(mContext,R.layout.item_ren_wu,0);
        renWuAdapter.setTestListSize(10);

        rv_renwu.setLayoutManager(new LinearLayoutManager(mContext));
        rv_renwu.setAdapter(renWuAdapter);

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
