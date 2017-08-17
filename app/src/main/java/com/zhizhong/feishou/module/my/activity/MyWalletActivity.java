package com.zhizhong.feishou.module.my.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseActivity;
import com.zhizhong.feishou.base.MySub;
import com.zhizhong.feishou.module.my.network.ApiRequest;
import com.zhizhong.feishou.module.my.network.response.WalletObj;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/8/2.
 */

public class MyWalletActivity extends BaseActivity {
    LoadMoreAdapter adapter;
    @BindView(R.id.rv_my_wallet)
    RecyclerView rv_my_wallet;
    @BindView(R.id.tv_wallet_total)
    TextView tv_wallet_total;

    @Override
    public void again() {

    }

    @Override
    protected int getContentView() {
        setAppTitle("我的钱包");
        return R.layout.act_my_wallet;
    }

    @Override
    protected void initView() {
        adapter=new LoadMoreAdapter<WalletObj.UserMoneyLogBean>(mContext,R.layout.item_wallet_shouyi,0) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, WalletObj.UserMoneyLogBean item) {
                holder.setText(R.id.tv_wallet_title,item.getRemark())
                .setText(R.id.tv_wallet_time,item.getAdd_time());
                TextView textView = holder.getTextView(R.id.tv_wallet_money);
                if(item.getValue()>0){
                    textView.setTextColor(getResources().getColor(R.color.blue));
                    textView.setText("+"+item.getValue()+"元");
                }else{
                    textView.setTextColor(getResources().getColor(R.color.red));
                    textView.setText(""+item.getValue()+"元");
                }

            }
        };
        rv_my_wallet.setLayoutManager(new LinearLayoutManager(mContext));
        rv_my_wallet.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        showLoading();
        addSubscription(ApiRequest.getMyWallet(getUserId(),getSign()).subscribe(new MySub<WalletObj>(mContext) {
            @Override
            public void onMyNext(WalletObj obj) {
                String totalMoney = obj.getUser_money() + "";
                tv_wallet_total.setText(totalMoney);
                adapter.setList(obj.getUser_money_log(),true);
            }
        }));
    }

    @OnClick({R.id.ll_wallet_szmx,R.id.ll_my_tixian})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.ll_wallet_szmx:
                STActivity(ShouZhiMingXiActivity.class);
                break;
            case R.id.ll_my_tixian:
                STActivityForResult(TiXianActivity.class,1000);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 1000:
                    initData();
                    break;
            }
        }

    }
}
