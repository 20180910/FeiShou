package com.zhizhong.feishou.module.home.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.github.androidtools.SPUtils;
import com.github.androidtools.StatusBarUtils;
import com.github.customview.MyRadioButton;
import com.zhizhong.feishou.Config;
import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseActivity;
import com.zhizhong.feishou.broadcast.MyOperationBro;
import com.zhizhong.feishou.module.my.activity.LoginActivity;
import com.zhizhong.feishou.module.my.fragment.MyFragment;
import com.zhizhong.feishou.module.renwu.Constant;
import com.zhizhong.feishou.module.renwu.fragment.RenWuFragment;
import com.zhizhong.feishou.module.zengzhi.fragment.ZengZhiFragment;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

public class MainActivity extends BaseActivity {

    @BindView(R.id.status_bar)
    View status_bar;


    RenWuFragment renWuFragment;
    ZengZhiFragment zengZhiFragment;
    MyFragment myFragment;

    @BindView(R.id.rb_home_rwdt)
    MyRadioButton rb_home_rwdt;

    @BindView(R.id.rb_home_zzfw)
    MyRadioButton rb_home_zzfw;

    @BindView(R.id.rb_home_my)
    MyRadioButton rb_home_my;

    private MyRadioButton selectButton;
    private LocalBroadcastManager localBroadcastManager;
    private MyOperationBro myOperationBro;
    @Override
    protected int getContentView() {
        return R.layout.act_home;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null && "operation".equals(intent.getAction())) {
//            selectPerson();
//            selectButton.setChecked(true);
        }
    }

    @Override
    protected void initView() {
        String registrationID = JPushInterface.getRegistrationID(mContext);
        Log.i("registrationID","registrationID====="+registrationID);
        SPUtils.setPrefString(mContext,Config.jiguangRegistrationId,registrationID);

        setBroadcast();

        int statusBarHeight = StatusBarUtils.getStatusBarHeight(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.height = statusBarHeight;
        status_bar.setLayoutParams(layoutParams);
        status_bar.setBackgroundColor(getResources().getColor(R.color.blue));

        selectButton = rb_home_rwdt;
        renWuFragment = new RenWuFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.layout_main_content, renWuFragment).commitAllowingStateLoss();

    }

    private void setBroadcast() {
        localBroadcastManager = LocalBroadcastManager.getInstance( this );
        myOperationBro =new MyOperationBro(new MyOperationBro.LoginBroInter() {
            @Override
            public void loginSuccess() {
                selectMy();
                selectButton.setChecked(true);
            }

            @Override
            public void exitLogin() {
                selectRWDT();
                selectButton.setChecked(true);
                myFragment=null;
            }
            @Override
            public void addHomeworkSuccess() {
                renWuFragment.getHomework();
            }
        });
        localBroadcastManager.registerReceiver(myOperationBro,new IntentFilter(Config.Bro.operation));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (localBroadcastManager != null) {
            localBroadcastManager.unregisterReceiver(myOperationBro);
        }
    }

    @Override
    protected void initRxBus() {
        super.initRxBus();

    }

    @Override
    protected void initData() {
    }
    @OnClick({R.id.rb_home_rwdt, R.id.rb_home_zzfw, R.id.rb_home_my})
    protected void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.rb_home_rwdt:
                selectRWDT();
                break;
            case R.id.rb_home_zzfw:
                selectButton = rb_home_zzfw;
                if (zengZhiFragment == null) {
                    zengZhiFragment = new ZengZhiFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.layout_main_content, zengZhiFragment).commitAllowingStateLoss();
                } else {
                    showFragment(zengZhiFragment);
                }
                hideFragment(renWuFragment);
                hideFragment(myFragment);
                break;
            case R.id.rb_home_my:
                if(TextUtils.isEmpty(getUserId())){
                    selectButton.setChecked(true);
                    STActivity(LoginActivity.class);
                    return;
                }
                selectMy();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case Constant.RCode.getOrder:
                    renWuFragment.again();
                break;
            }
        }
    }

    private void selectRWDT() {
        selectButton = rb_home_rwdt;
        if (renWuFragment == null) {
            renWuFragment = new RenWuFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.layout_main_content, renWuFragment).commitAllowingStateLoss();
        } else {
            showFragment(renWuFragment);
        }
        hideFragment(zengZhiFragment);
        hideFragment(myFragment);
    }

    private void selectMy() {
        selectButton = rb_home_my;
        if (myFragment == null) {
            myFragment = new MyFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.layout_main_content, myFragment).commitAllowingStateLoss();
        } else {
            showFragment(myFragment);
        }
        hideFragment(zengZhiFragment);
        hideFragment(renWuFragment);
    }

    private long mExitTime;

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 1500) {
            showToastS("再按一次退出程序");
            mExitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void again() {

    }
}
