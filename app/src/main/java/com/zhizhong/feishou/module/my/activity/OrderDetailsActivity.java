package com.zhizhong.feishou.module.my.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.customview.MyLinearLayout;
import com.github.customview.MyTextView;
import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseActivity;
import com.zhizhong.feishou.base.MySub;
import com.zhizhong.feishou.module.my.Constant;
import com.zhizhong.feishou.module.my.network.ApiRequest;
import com.zhizhong.feishou.module.my.network.response.OrderDetailObj;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/8/3.
 */

public class OrderDetailsActivity extends BaseActivity {


    @BindView(R.id.tv_order_detail_type)
    TextView tv_order_detail_type;

    @BindView(R.id.tv_order_detail_status_title)
    TextView tv_order_detail_status_title;

    @BindView(R.id.tv_order_detail_no)
    TextView tv_order_detail_no;
    @BindView(R.id.tv_order_detail_dq)
    TextView tv_order_detail_dq;
    @BindView(R.id.tv_order_detail_nzw)
    TextView tv_order_detail_nzw;
    @BindView(R.id.tv_order_detail_dj)
    TextView tv_order_detail_dj;
    @BindView(R.id.tv_order_detail_ms)
    TextView tv_order_detail_ms;
    @BindView(R.id.tv_order_detail_qwsj)
    TextView tv_order_detail_qwsj;
    @BindView(R.id.tv_order_detail_zccs)
    TextView tv_order_detail_zccs;
    @BindView(R.id.tv_order_detail_zcsm)
    TextView tv_order_detail_zcsm;
    @BindView(R.id.tv_order_detail_dk)
    TextView tv_order_detail_dk;
    @BindView(R.id.tv_order_detail_zaw)
    TextView tv_order_detail_zaw;
    @BindView(R.id.tv_order_detail_beizhu)
    TextView tv_order_detail_beizhu;
    @BindView(R.id.ll_order_detail_bao1)
    LinearLayout ll_order_detail_bao1;
    @BindView(R.id.ll_order_detail_bao2)
    LinearLayout ll_order_detail_bao2;
    @BindView(R.id.ll_order_detail_bao3)
    LinearLayout ll_order_detail_bao3;
    @BindView(R.id.ll_order_detail_bao4)
    LinearLayout ll_order_detail_bao4;
    @BindView(R.id.tv_order_detail_address)
    TextView tv_order_detail_address;
    @BindView(R.id.tv_order_detail_jiedan)
    MyTextView tv_order_detail_jiedan;
    @BindView(R.id.tv_order_detail_zhixing)
    MyTextView tv_order_detail_zhixing;
    @BindView(R.id.tv_order_detail_tixing)
    MyTextView tv_order_detail_tixing;
    @BindView(R.id.tv_order_detail_quxiao)
    MyTextView tv_order_detail_quxiao;
    @BindView(R.id.tv_order_detail_jiesuan)
    MyTextView tv_order_detail_jiesuan;
    @BindView(R.id.ll_order_detail_bottom)
    MyLinearLayout ll_order_detail_bottom;
    private String orderNo;

    @Override
    public void again() {
        initData();
    }

    @Override
    protected int getContentView() {
        setAppTitle("订单详情");
        return R.layout.act_order_details;
    }

    @Override
    protected void initView() {
        orderNo = getIntent().getStringExtra(Constant.IParam.orderNo);
    }

    @Override
    protected void initData() {
        showProgress();
        getData();
    }

    private void getData() {
        addSubscription(ApiRequest.getOrderDetail(orderNo,getSign("order_no",orderNo)).subscribe(new MySub<OrderDetailObj>(mContext) {
            @Override
            public void onMyNext(OrderDetailObj obj) {
                tv_order_detail_no.setText(obj.getOrder_no());
                tv_order_detail_dq.setText(obj.getRegion());
                tv_order_detail_nzw.setText(obj.getCrops());
                tv_order_detail_dj.setText(obj.getPrice());
                tv_order_detail_ms.setText(obj.getArea());
                tv_order_detail_zccs.setText(obj.getTransitions_number());
                tv_order_detail_zcsm.setText(obj.getTransitions_instructions());
                tv_order_detail_qwsj.setText(obj.getRequest_time());
                tv_order_detail_dk.setText(obj.getCondition());
                tv_order_detail_zaw.setText(obj.getObstacles());
                tv_order_detail_beizhu.setText(obj.getRemark());
                tv_order_detail_address.setText(obj.getAddress());
                tv_order_detail_status_title.setText(obj.getStatus_title());

                switch (obj.getOrder_status()+""){
                    case Constant.daiJieDanOrder:
                        tv_order_detail_type.setText("订单待接单");
                    break;
                    case Constant.daiZhiXingOrder:
                        tv_order_detail_type.setText("订单待执行");
                    break;
                    case Constant.daiJieSuanOrder:
                        tv_order_detail_type.setText("订单待结算");
                    break;
                    case Constant.yiWanChengOrder:
                        tv_order_detail_type.setText("订单已完成");
                        ll_order_detail_bottom.setVisibility(View.GONE);
                    break;
                }
            }
        }));
    }


    @OnClick({R.id.tv_order_detail_jiedan, R.id.tv_order_detail_zhixing, R.id.tv_order_detail_tixing, R.id.tv_order_detail_quxiao, R.id.tv_order_detail_jiesuan})
    protected void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_order_detail_jiedan:
                break;
            case R.id.tv_order_detail_zhixing:
                break;
            case R.id.tv_order_detail_tixing:
                break;
            case R.id.tv_order_detail_quxiao:
                break;
            case R.id.tv_order_detail_jiesuan:
                break;
        }
    }
}
