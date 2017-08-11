package com.zhizhong.feishou.module.my.activity;

import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.SPUtils;
import com.github.customview.MyTextView;
import com.zhizhong.feishou.Config;
import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseActivity;
import com.zhizhong.feishou.base.MySub;
import com.zhizhong.feishou.module.my.network.ApiRequest;
import com.zhizhong.feishou.module.my.network.response.LevelObj;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by administartor on 2017/8/2.
 */

public class VIPLevelActivity extends BaseActivity {

    @BindView(R.id.tv_vip_num0)
    MyTextView tv_vip_num0;

    @BindView(R.id.tv_vip_num1)
    MyTextView tv_vip_num1;

    @BindView(R.id.tv_vip_num2)
    MyTextView tv_vip_num2;

    @BindView(R.id.tv_vip_num3)
    MyTextView tv_vip_num3;

    @BindView(R.id.tv_vip_num4)
    MyTextView tv_vip_num4;

    @BindView(R.id.icv_vip_img)
    CircleImageView icv_vip_img;
    @BindView(R.id.tv_vip_level)
    TextView tv_vip_level;
    @BindView(R.id.tv_vip_content)
    TextView tv_vip_content;

    @Override
    public void again() {

    }

    @Override
    protected int getContentView() {
        setAppTitle("会员等级");
        return R.layout.act_vip_level;
    }

    @Override
    protected void initView() {
        String avatar = SPUtils.getPrefString(mContext, Config.avatar, null);
        Glide.with(mContext).load(avatar).error(R.color.c_press).into(icv_vip_img);
    }

    @Override
    protected void initData() {
        showLoading();
        addSubscription(ApiRequest.getVIPLevel(getUserId(), getSign()).subscribe(new MySub<LevelObj>(mContext) {
            @Override
            public void onMyNext(LevelObj obj) {
                tv_vip_level.setText(obj.getUser_level()+"");
                tv_vip_content.setText(obj.getContent());
                switch (obj.getUser_level()){
                    case 0:
                        tv_vip_num0.setSolidColor(getResources().getColor(R.color.gray_d7));
                        tv_vip_num0.complete();
                    break;
                    case 1:
                        tv_vip_num0.setSolidColor(getResources().getColor(R.color.blue));
                        tv_vip_num0.complete();
                    break;
                    case 2:
                        tv_vip_num0.setSolidColor(getResources().getColor(R.color.blue));
                        tv_vip_num0.complete();
                        tv_vip_num1.setSolidColor(getResources().getColor(R.color.blue));
                        tv_vip_num1.complete();
                    break;
                    case 3:
                        tv_vip_num0.setSolidColor(getResources().getColor(R.color.blue));
                        tv_vip_num0.complete();
                        tv_vip_num1.setSolidColor(getResources().getColor(R.color.blue));
                        tv_vip_num1.complete();
                        tv_vip_num2.setSolidColor(getResources().getColor(R.color.blue));
                        tv_vip_num2.complete();
                    break;
                    case 4:
                        tv_vip_num0.setSolidColor(getResources().getColor(R.color.blue));
                        tv_vip_num0.complete();
                        tv_vip_num1.setSolidColor(getResources().getColor(R.color.blue));
                        tv_vip_num1.complete();
                        tv_vip_num2.setSolidColor(getResources().getColor(R.color.blue));
                        tv_vip_num2.complete();
                        tv_vip_num3.setSolidColor(getResources().getColor(R.color.blue));
                        tv_vip_num3.complete();
                    break;
                    default:
                        tv_vip_num0.setSolidColor(getResources().getColor(R.color.blue));
                        tv_vip_num0.complete();
                        tv_vip_num1.setSolidColor(getResources().getColor(R.color.blue));
                        tv_vip_num1.complete();
                        tv_vip_num2.setSolidColor(getResources().getColor(R.color.blue));
                        tv_vip_num2.complete();
                        tv_vip_num3.setSolidColor(getResources().getColor(R.color.blue));
                        tv_vip_num3.complete();
                        tv_vip_num4.setSolidColor(getResources().getColor(R.color.blue));
                        tv_vip_num4.complete();
                        break;
                }
            }
        }));
    }
    @OnClick({R.id.icv_vip_img})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.icv_vip_img:
            break;
        }
    }
}
