package com.zhizhong.feishou.module.my.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.SPUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.zhizhong.feishou.Config;
import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseFragment;
import com.zhizhong.feishou.base.MySub;
import com.zhizhong.feishou.module.my.Constant;
import com.zhizhong.feishou.module.my.activity.CeMuToolActivity;
import com.zhizhong.feishou.module.my.activity.HomeworkScopeActivity;
import com.zhizhong.feishou.module.my.activity.MyDataActivity;
import com.zhizhong.feishou.module.my.activity.MyOrderActivity;
import com.zhizhong.feishou.module.my.activity.MyToolListActivity;
import com.zhizhong.feishou.module.my.activity.MyWalletActivity;
import com.zhizhong.feishou.module.my.activity.RealNameAuthActivity;
import com.zhizhong.feishou.module.my.activity.VIPLevelActivity;
import com.zhizhong.feishou.module.my.network.ApiRequest;
import com.zhizhong.feishou.module.my.network.response.InfoObj;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by administartor on 2017/8/1.
 */

public class MyFragment extends BaseFragment {

    @BindView(R.id.civ_my_img)
    CircleImageView civ_my_img;
    @BindView(R.id.tv_info_name)
    TextView tv_info_name;
    @BindView(R.id.tv_info_auth)
    TextView tv_info_auth;
    @BindView(R.id.tv_info_level)
    TextView tv_info_level;
    private Intent intent;

    @Override
    protected int getContentView() {
        return R.layout.frag_my;
    }

    public static MyFragment newInstance() {

        Bundle args = new Bundle();

        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView() {


    }

    @Override
    public void onResume() {
        super.onResume();
        String userName = SPUtils.getPrefString(mContext, Config.nick_name, null);
        String avatar = SPUtils.getPrefString(mContext, Config.avatar, null);
        if (avatar != null) {
            Glide.with(mContext).load(avatar).error(R.color.c_press).into(civ_my_img);
        }
        int level = SPUtils.getPrefInt(mContext, Config.level, 0);
        int auth = SPUtils.getPrefInt(mContext, Config.authentication, 0);
        if (auth == 0) {
            tv_info_auth.setText("身份证未认证");
            tv_info_auth.setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    STActivity(RealNameAuthActivity.class);
                }
            });
        } else {
            tv_info_auth.setText("身份证已认证");
        }
        tv_info_name.setText(userName);
        tv_info_level.setText(level+"");
    }

    @Override
    protected void initData() {
        getData();
    }

    private void getData() {
        showLoading();
        addSubscription(ApiRequest.getInfo(getUserId(), getSign()).subscribe(new MySub<InfoObj>(mContext) {
            @Override
            public void onMyNext(InfoObj obj) {
                SPUtils.setPrefString(mContext,Config.sex,obj.getSex());
                SPUtils.setPrefString(mContext,Config.birthday,obj.getBirthday());
                SPUtils.setPrefString(mContext,Config.nick_name,obj.getNick_name());
                tv_info_name.setText(obj.getNick_name());

                Glide.with(mContext).load(obj.getAvatar()).error(R.color.c_press).into(civ_my_img);
            }
        }));
    }

    @Override
    public void again() {

    }

    @OnClick({R.id.ll_my_vip,R.id.tv_my_wallet, R.id.tv_my_tool, R.id.tv_my_homework, R.id.tv_my_cemu_tool, R.id.iv_my_set, R.id.tv_my_all, R.id.tv_my_djd, R.id.tv_my_yjd, R.id.tv_my_complete})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.ll_my_vip://设置
                STActivity(VIPLevelActivity.class);
                break;
            case R.id.iv_my_set://设置
                STActivity(MyDataActivity.class);
//                STActivity(LoginActivity.class);
                break;
            case R.id.tv_my_wallet://我的钱包
                STActivity(MyWalletActivity.class);
                break;
            case R.id.tv_my_all:
                intent = new Intent();
                intent.putExtra(Constant.IParam.orderType,0);
                STActivity(intent,MyOrderActivity.class);
                break;
            case R.id.tv_my_djd:
                intent = new Intent();
                intent.putExtra(Constant.IParam.orderType,1);
                STActivity(intent,MyOrderActivity.class);
                break;
            case R.id.tv_my_yjd:
                intent = new Intent();
                intent.putExtra(Constant.IParam.orderType,2);
                STActivity(intent,MyOrderActivity.class);
                break;
            case R.id.tv_my_complete:
                intent = new Intent();
                intent.putExtra(Constant.IParam.orderType,4);
                STActivity(intent,MyOrderActivity.class);
                break;
            case R.id.tv_my_tool://我的工具
                STActivity(MyToolListActivity.class);
                break;
            case R.id.tv_my_homework:
                STActivity(HomeworkScopeActivity.class);
                break;
            case R.id.tv_my_cemu_tool:
                STActivity(CeMuToolActivity.class);
                break;
        }
    }

}
