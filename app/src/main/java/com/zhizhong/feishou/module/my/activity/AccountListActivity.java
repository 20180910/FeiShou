package com.zhizhong.feishou.module.my.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseActivity;
import com.zhizhong.feishou.module.my.adapter.AccountAdapter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/8/2.
 */

public class AccountListActivity extends BaseActivity{
    @BindView(R.id.rv_account)
    RecyclerView rv_account;

    AccountAdapter adapter;
    @Override
    public void again() {

    }

    @Override
    protected int getContentView() {
        setAppTitle("账户列表");
        setAppRightImg(R.drawable.img34);
        return R.layout.act_account_list;
    }

    @Override
    protected void initView() {
        adapter=new AccountAdapter(mContext,R.layout.item_account,0);
        adapter.setTestListSize(10);

        rv_account.setLayoutManager(new LinearLayoutManager(mContext));
        rv_account.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.app_right_iv})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.app_right_iv:
                STActivity(AddBankCardActivity.class);
            break;
        }
    }
}
