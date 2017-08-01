package com.zhizhong.feishou.module.home.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.github.androidtools.SPUtils;
import com.github.androidtools.StatusBarUtils;
import com.github.baseclass.rx.MySubscriber;
import com.github.baseclass.rx.RxBus;
import com.github.customview.MyRadioButton;
import com.google.gson.Gson;
import com.haitaoit.yinya.Config;
import com.haitaoit.yinya.R;
import com.haitaoit.yinya.base.BaseActivity;
import com.haitaoit.yinya.base.MySub;
import com.haitaoit.yinya.event.RefreshInfoEvent;
import com.haitaoit.yinya.event.ShowKeyBoardEvent;
import com.haitaoit.yinya.module.credit.fragment.CreditFragment;
import com.haitaoit.yinya.module.credit.network.response.BankActiveSearchObj;
import com.haitaoit.yinya.module.daikuan.fragment.DaiKuanFragment;
import com.haitaoit.yinya.module.daikuan.network.ApiRequest;
import com.haitaoit.yinya.module.daikuan.network.response.DaiKuanOrderObj;
import com.haitaoit.yinya.module.help.fragment.HelpLoanSetFragment;
import com.haitaoit.yinya.module.home.event.SelectViewEvent;
import com.haitaoit.yinya.module.home.fragment.HomePageFragment;
import com.haitaoit.yinya.module.person.activity.LoginActivity;
import com.haitaoit.yinya.module.person.event.ExitEvent;
import com.haitaoit.yinya.module.person.fragment.PersonalCenterFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.status_bar)
    View status_bar;
    @BindView(R.id.rb_home_page)
    MyRadioButton rbHomePage;
    @BindView(R.id.rb_loan_zone)
    MyRadioButton rbLoanZone;
    @BindView(R.id.rb_credit_pref)
    MyRadioButton rbCreditPref;
    @BindView(R.id.rb_help_loan)
    MyRadioButton rbHelpLoan;
    @BindView(R.id.rb_personal)
    MyRadioButton rbPersonal;
    @BindView(R.id.rg_main)
    RadioGroup rgMain;

    private MyRadioButton selectButton;

    private HomePageFragment homePageFragment;
    private DaiKuanFragment daiKuanFragment;
    private CreditFragment creditFragment;
    private HelpLoanSetFragment helpLoanFragment;
    private PersonalCenterFragment personalCenterFragment;

    @Override
    protected int getContentView() {
        return R.layout.act_main;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent!=null&&"login".equals(intent.getAction())){
            selectPerson();
//            personalCenterFragment.refreshInfo();
            RxBus.getInstance().postSticky(new RefreshInfoEvent());
            selectButton.setChecked(true);
        }
    }

    @Override
    protected void initView() {
        int statusBarHeight = StatusBarUtils.getStatusBarHeight(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.height=statusBarHeight;
        status_bar.setLayoutParams(layoutParams);
        status_bar.setBackgroundColor(getResources().getColor(R.color.home_title));

        selectButton = rbHomePage;
        homePageFragment = new HomePageFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.layout_main_content, homePageFragment).commit();

        getDaiKuanOrder();//提前把贷款专区排序和搜索条件数据加载好提高用户体验
        getXinYongYouHuiSearch();//提前把信用优惠搜索条件数据加载好提高用户体验
    }

    private void getXinYongYouHuiSearch() {
        addSubscription(com.haitaoit.yinya.module.credit.network.ApiRequest.getBankAndType().subscribe(new MySub<BankActiveSearchObj>(mContext) {
            @Override
            public void onMyNext(BankActiveSearchObj obj) {
                List<BankActiveSearchObj.DybanklistBean> bankList = obj.getDybanklist();
                List<BankActiveSearchObj.DytypelistBean> typeList = obj.getDytypelist();

                BankActiveSearchObj.DybanklistBean bankBean=new BankActiveSearchObj.DybanklistBean();
                bankBean.setBrand_id(0);
                bankBean.setTitle("全部银行");
                bankList.add(0,bankBean);

                BankActiveSearchObj.DytypelistBean typeBean=new BankActiveSearchObj.DytypelistBean();
                typeBean.setCategory_id(0);
                typeBean.setTitle("全部类型");
                typeList.add(0,typeBean);

                SPUtils.setPrefString(mContext, Config.SP.xinyong_youhui_bank,new Gson().toJson(bankList));
                SPUtils.setPrefString(mContext, Config.SP.xinyong_youhui_type,new Gson().toJson(typeList));

            }
        }));
    }

    private void getDaiKuanOrder() {
        addSubscription(ApiRequest.getDaiKuanOrder().subscribe(new MySub<DaiKuanOrderObj>(mContext) {
            @Override
            public void onMyNext(DaiKuanOrderObj obj) {
                List<DaiKuanOrderObj.DyshowlistBean> eDuList = obj.getDyshowlist();
                List<DaiKuanOrderObj.DyamountlistBean> moneyList = obj.getDyamountlist();
                List<DaiKuanOrderObj.DytimelistBean> qiXianList = obj.getDytimelist();

                DaiKuanOrderObj.DyshowlistBean eDuBean=new DaiKuanOrderObj.DyshowlistBean();
                eDuBean.setCategory_id(0);
                eDuBean.setTitle("默认排序");
                eDuList.add(0,eDuBean);

                DaiKuanOrderObj.DyamountlistBean moneyBean=new DaiKuanOrderObj.DyamountlistBean();
                moneyBean.setBrand_id(0);
                moneyBean.setTitle("金额不限");
                moneyList.add(0,moneyBean);

                DaiKuanOrderObj.DytimelistBean qiXianBean=new DaiKuanOrderObj.DytimelistBean();
                qiXianBean.setCategory_id(0);
                qiXianBean.setTitle("全部期限");
                qiXianList.add(0,qiXianBean);

                SPUtils.setPrefString(mContext, Config.SP.dai_kuan_edu_order,new Gson().toJson(eDuList));
                SPUtils.setPrefString(mContext, Config.SP.dai_kuan_money_order,new Gson().toJson(moneyList));
                SPUtils.setPrefString(mContext, Config.SP.dai_kuan_qixian_order,new Gson().toJson(qiXianList));
            }
        }));
    }

    @Override
    protected void initRxBus() {
        super.initRxBus();
        getRxBusEvent(SelectViewEvent.class, new MySubscriber<SelectViewEvent>() {
            @Override
            public void onMyNext(SelectViewEvent event) {
                selectDaiKuan();
                selectButton.setChecked(true);
                RxBus.getInstance().postSticky(new ShowKeyBoardEvent(event.isSearch));
            }
        });
        getRxBusEvent(ExitEvent.class, new MySubscriber() {
            @Override
            public void onMyNext(Object o) {
                selectHome();
                selectButton.setChecked(true);
            }
        });

    }

    @Override
    protected void initData() {
    }

    @OnClick({R.id.rb_home_page, R.id.rb_loan_zone, R.id.rb_credit_pref, R.id.rb_help_loan, R.id.rb_personal})
    protected void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.rb_home_page:
                selectHome();
                break;
            case R.id.rb_loan_zone:
                selectDaiKuan();
                break;
            case R.id.rb_credit_pref:
                selectButton = rbCreditPref;
                if (creditFragment == null) {
                    creditFragment = new CreditFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.layout_main_content, creditFragment).commit();
                } else {
                    showFragment(creditFragment);
                }
                status_bar.setBackgroundColor(getResources().getColor(R.color.white));
                hideFragment(daiKuanFragment);
                hideFragment(homePageFragment);
                hideFragment(helpLoanFragment);
                hideFragment(personalCenterFragment);
                break;
            case R.id.rb_help_loan:
                selectButton = rbHelpLoan;
                status_bar.setBackgroundColor(getResources().getColor(R.color.white));
                if (helpLoanFragment == null) {
                    helpLoanFragment = new HelpLoanSetFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.layout_main_content, helpLoanFragment).commit();
                } else {
                    showFragment(helpLoanFragment);
                }
                hideFragment(daiKuanFragment);
                hideFragment(creditFragment);
                hideFragment(homePageFragment);
                hideFragment(personalCenterFragment);
                break;
            case R.id.rb_personal:
                selectPerson();
                break;
        }
    }

    private void selectPerson() {
        if (TextUtils.isEmpty(SPUtils.getPrefString(mContext, Config.user_id,null))) {
            STActivity(LoginActivity.class);
            selectButton.setChecked(true);
        } else {
            selectButton = rbPersonal;
            status_bar.setBackgroundColor(getResources().getColor(R.color.orange_ff));
            if (personalCenterFragment == null) {
                personalCenterFragment = new PersonalCenterFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.layout_main_content, personalCenterFragment).commit();
            } else {
                showFragment(personalCenterFragment);
            }
            hideFragment(homePageFragment);
            hideFragment(daiKuanFragment);
            hideFragment(creditFragment);
            hideFragment(helpLoanFragment);
        }
    }

    private void selectHome() {
        selectButton = rbHomePage;
        status_bar.setBackgroundColor(getResources().getColor(R.color.home_title));
        if (homePageFragment == null) {
            homePageFragment = new HomePageFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.layout_main_content, homePageFragment).commit();
        } else {
            showFragment(homePageFragment);
        }
        hideFragment(daiKuanFragment);
        hideFragment(creditFragment);
        hideFragment(helpLoanFragment);
        hideFragment(personalCenterFragment);
    }

    private void selectDaiKuan() {
        selectButton = rbLoanZone;
        if (daiKuanFragment == null) {
            daiKuanFragment = new DaiKuanFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.layout_main_content, daiKuanFragment).commit();
        } else {
            showFragment(daiKuanFragment);
        }
        status_bar.setBackgroundColor(getResources().getColor(R.color.orange_ff));
        hideFragment(homePageFragment);
        hideFragment(creditFragment);
        hideFragment(helpLoanFragment);
        hideFragment(personalCenterFragment);
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
}
