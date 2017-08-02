package com.zhizhong.feishou.module.home.activity;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.zhizhong.feishou.R;
import com.github.androidtools.StatusBarUtils;
import com.github.customview.MyRadioButton;
import com.zhizhong.feishou.base.BaseActivity;
import com.zhizhong.feishou.module.my.fragment.MyFragment;
import com.zhizhong.feishou.module.renwu.fragment.RenWuFragment;
import com.zhizhong.feishou.module.zengzhi.fragment.ZengZhiFragment;

import butterknife.BindView;
import butterknife.OnClick;

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

    @Override
    protected int getContentView() {
        return R.layout.act_home;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null && "login".equals(intent.getAction())) {
//            selectPerson();
//            RxBus.getInstance().postSticky(new RefreshInfoEvent());
//            selectButton.setChecked(true);
        }
    }

    @Override
    protected void initView() {
        int statusBarHeight = StatusBarUtils.getStatusBarHeight(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.height = statusBarHeight;
        status_bar.setLayoutParams(layoutParams);
        status_bar.setBackgroundColor(getResources().getColor(R.color.white));

        selectButton = rb_home_rwdt;
        renWuFragment = new RenWuFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.layout_main_content, renWuFragment).commit();

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
                selectButton = rb_home_rwdt;
                if (renWuFragment == null) {
                    renWuFragment = new RenWuFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.layout_main_content, renWuFragment).commit();
                } else {
                    showFragment(renWuFragment);
                }
                hideFragment(zengZhiFragment);
                hideFragment(myFragment);
                break;
            case R.id.rb_home_zzfw:
                selectButton = rb_home_zzfw;
                if (zengZhiFragment == null) {
                    zengZhiFragment = new ZengZhiFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.layout_main_content, zengZhiFragment).commit();
                } else {
                    showFragment(zengZhiFragment);
                }
                hideFragment(renWuFragment);
                hideFragment(myFragment);
                break;
            case R.id.rb_home_my:
                selectButton = rb_home_my;
                if (myFragment == null) {
                    myFragment = new MyFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.layout_main_content, myFragment).commit();
                } else {
                    showFragment(myFragment);
                }
                hideFragment(zengZhiFragment);
                hideFragment(renWuFragment);
                break;
        }
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
