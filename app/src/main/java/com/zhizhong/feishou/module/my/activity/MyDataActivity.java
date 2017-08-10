package com.zhizhong.feishou.module.my.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.TextView;

import com.github.androidtools.SPUtils;
import com.github.baseclass.view.MyDialog;
import com.zhizhong.feishou.Config;
import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/8/3.
 */

public class MyDataActivity extends BaseActivity {
    @BindView(R.id.tv_info_exit)
    TextView tvInfoExit;

    @Override
    public void again() {

    }

    @Override
    protected int getContentView() {
        setAppTitle("我的资料");
        return R.layout.act_my_data;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.tv_info_exit)
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_info_exit:
                mDialog=new MyDialog.Builder(mContext);
                mDialog.setMessage("是否确认退出登录?")
                        .setNegativeButton(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                exitLogin();
                            }
                        });
                mDialog.create().show();
            break;
        }

    }

    private void exitLogin() {
        SPUtils.removeKey(mContext, Config.user_id);
        Intent intent = new Intent(Config.Bro.login);
        intent.putExtra(Config.Bro.flag,Config.Bro.exit_login);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);

        STActivity(LoginActivity.class);

        finish();
    }

}
