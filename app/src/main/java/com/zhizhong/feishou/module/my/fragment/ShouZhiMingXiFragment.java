package com.zhizhong.feishou.module.my.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.zhizhong.feishou.GetSign;
import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseFragment;
import com.zhizhong.feishou.base.MySub;
import com.zhizhong.feishou.module.my.network.ApiRequest;
import com.zhizhong.feishou.module.my.network.response.WalletObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by administartor on 2017/8/2.
 */

public class ShouZhiMingXiFragment extends BaseFragment {
    @BindView(R.id.rv_szmx)
    RecyclerView rv_szmx;

    LoadMoreAdapter adapter;
    String type;
    @Override
    public void again() {

    }

    public static ShouZhiMingXiFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putString("type",type+"");
        ShouZhiMingXiFragment fragment = new ShouZhiMingXiFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getContentView() {
        return R.layout.frag_shou_zhi_ming_xi;
    }

    @Override
    protected void initView() {
        type=getArguments().getString("type");
        adapter=new LoadMoreAdapter(mContext,R.layout.item_wallet_shouyi,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder loadMoreViewHolder, int i, Object o) {

            }
        };
        rv_szmx.setLayoutManager(new LinearLayoutManager(mContext));
        rv_szmx.setAdapter(adapter);
    }

    @Override
    protected void initData() {
//        showPro();
        getData(1,false);
    }
    private void getData(int page, boolean isLoad) {
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("type",type);
        map.put("pagesize",pageSize+"");
        map.put("page",page+"");
        map.put("sign", GetSign.getSign(map));
        addSubscription(ApiRequest.getWalletDetailsList(map).subscribe(new MySub<WalletObj>(mContext,pcfl,pl_load) {
            @Override
            public void onMyNext(WalletObj obj) {
                if(isLoad){
                    pageNum++;
                    adapter.addList(obj.getUser_money_log(),true);
                }else{
                    adapter.setList(obj.getUser_money_log(),true);
                }
            }
        }));
    }
    @Override
    protected void onViewClick(View v) {

    }
}
