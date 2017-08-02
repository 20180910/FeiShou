package com.zhizhong.feishou.module.my.activity;

import android.view.View;
import android.widget.LinearLayout;

import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/8/2.
 */

public class TiXianActivity extends BaseActivity {
    @BindView(R.id.ll_tixian)
    LinearLayout ll_tixian;

    @Override
    public void again() {

    }

    @Override
    protected int getContentView() {
        setAppTitle("提现");
        return R.layout.act_ti_xian;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.ll_tixian})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.ll_tixian:
                STActivity(AccountListActivity.class);
            break;
        }
    }
}
