package com.zhizhong.feishou.module.my.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.github.baseclass.rx.MySubscriber;
import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseActivity;
import com.zhizhong.feishou.base.BaseObj;
import com.zhizhong.feishou.base.MySub;
import com.zhizhong.feishou.module.my.Constant;
import com.zhizhong.feishou.module.my.adapter.OrderFragmentAdapter;
import com.zhizhong.feishou.module.my.event.OrderEvent;
import com.zhizhong.feishou.module.my.fragment.AllOrderFragment;
import com.zhizhong.feishou.module.my.network.ApiRequest;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by administartor on 2017/8/2.
 */

public class MyOrderActivity extends BaseActivity {

    @BindView(R.id.tl_my_order)
    TabLayout tl_all_order;
    @BindView(R.id.vp_my_order)
    ViewPager vp_my_order;

    OrderFragmentAdapter adapter;
    List<Fragment> list;

    AllOrderFragment allOrderFragment;
    AllOrderFragment daiJieDanOrderFragment;
    AllOrderFragment daiZhiXingOrderFragment;
    AllOrderFragment daiJieSuanOrderFragment;
    AllOrderFragment completeOrderFragment;
//    AllOrderFragment yiQuXiaoOrderFragment;

    @Override
    public void again() {

    }

    @Override
    protected int getContentView() {
        setAppTitle("我的订单");
        return R.layout.act_my_order;
    }

    @Override
    protected void initView() {
//        Constant.IParam.orderType
        int orderType = getIntent().getIntExtra(Constant.IParam.orderType, 0);
        adapter = new OrderFragmentAdapter(getSupportFragmentManager());

        allOrderFragment = AllOrderFragment.newInstance(Constant.allOrder);
        daiJieDanOrderFragment = AllOrderFragment.newInstance(Constant.daiJieDanOrder);
        daiZhiXingOrderFragment = AllOrderFragment.newInstance(Constant.daiZhiXingOrder);
        daiJieSuanOrderFragment = AllOrderFragment.newInstance(Constant.daiJieSuanOrder);
        completeOrderFragment = AllOrderFragment.newInstance(Constant.yiWanChengOrder);

        list = new ArrayList<>();
        list.add(allOrderFragment);
        list.add(daiJieDanOrderFragment);
        list.add(daiZhiXingOrderFragment);
        list.add(daiJieSuanOrderFragment);
        list.add(completeOrderFragment);

        adapter.setList(list);
        vp_my_order.setAdapter(adapter);
        vp_my_order.setOffscreenPageLimit(list.size()-1);
        vp_my_order.postDelayed(new Runnable() {
            @Override
            public void run() {
                vp_my_order.setCurrentItem(orderType);
            }
        },100);
        tl_all_order.setupWithViewPager(vp_my_order);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initRxBus() {
        super.initRxBus();
        getRxBusEvent(OrderEvent.class, new MySubscriber<OrderEvent>() {
            @Override
            public void onMyNext(OrderEvent event) {
                switch (event.type){
                    case Constant.daiJieDanOrder:
                        jieDan(event.orderNo);
                    break;
                    case Constant.daiZhiXingOrder:
                        zhiXing(event.orderNo);
                    break;
                    case Constant.daiJieSuanOrder:
                        jieSuan(event.orderNo);
                    break;
                    case Constant.quXiaoOrder:
                        quXiao(event.orderNo);
                    break;
                    case Constant.shiJianTiXing:
                        tiXing(event.orderNo);
                    break;
                }
            }
        });
    }

    private void tiXing(String orderNo) {
        showLoading();
        addSubscription(ApiRequest.tiXing(orderNo,getSign("order_no",orderNo)).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                showMsg(obj.getMsg());
                allOrderFragment.getData(1,false);

                daiZhiXingOrderFragment.getData(1,false);
            }
        }));

    }

    private void quXiao(String orderNo) {
        showLoading();
        addSubscription(ApiRequest.quXiao(orderNo,getSign("order_no",orderNo)).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                showMsg(obj.getMsg());
                allOrderFragment.getData(1,false);

                daiZhiXingOrderFragment.getData(1,false);
            }
        }));
    }

    private void jieSuan(String orderNo) {
        showLoading();
        addSubscription(ApiRequest.complete(orderNo,getSign("order_no",orderNo)).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                showMsg(obj.getMsg());
                allOrderFragment.getData(1,false);

                daiJieSuanOrderFragment.getData(1,false);
                completeOrderFragment.getData(1,false);
            }
        }));
    }

    private void zhiXing(String orderNo) {
        showLoading();
        addSubscription(ApiRequest.zhiXing(orderNo,getSign("order_no",orderNo)).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                showMsg(obj.getMsg());
                allOrderFragment.getData(1,false);
                daiZhiXingOrderFragment.getData(1,false);
                daiJieSuanOrderFragment.getData(1,false);
            }
        }));
    }

    private void jieDan(String orderNo) {
        showLoading();
        addSubscription(ApiRequest.jieDan(orderNo,getSign("order_no",orderNo)).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                showMsg(obj.getMsg());
                allOrderFragment.getData(1,false);
                daiJieDanOrderFragment.getData(1,false);
                daiZhiXingOrderFragment.getData(1,false);
            }
        }));
    }

    @Override
    protected void onViewClick(View v) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 1000:
                    allOrderFragment.getData(1,false);
                    daiJieDanOrderFragment.getData(1,false);
                    daiZhiXingOrderFragment.getData(1,false);
                    daiJieSuanOrderFragment.getData(1,false);
                    completeOrderFragment.getData(1,false);
                break;
            }
        }
    }
}
