package com.zhizhong.feishou.module.my.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.baseclass.adapter.LoadMoreAdapter;
import com.zhizhong.feishou.GetSign;
import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseFragment;
import com.zhizhong.feishou.base.MySub;
import com.zhizhong.feishou.module.my.Constant;
import com.zhizhong.feishou.module.my.activity.OrderDetailsActivity;
import com.zhizhong.feishou.module.my.adapter.AllOrderAdapter;
import com.zhizhong.feishou.module.my.network.ApiRequest;
import com.zhizhong.feishou.module.my.network.response.OrderObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by administartor on 2017/8/2.
 */

public class AllOrderFragment extends BaseFragment implements LoadMoreAdapter.OnLoadMoreListener{
    @BindView(R.id.rv_all_order)
    RecyclerView rv_all_order;

    AllOrderAdapter allOrderAdapter;
    @Override
    public void again() {
        initData();
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_all_order;
    }

    public static AllOrderFragment newInstance(String type) {

        Bundle args = new Bundle();
        args.putString(Constant.orderType,type);
        AllOrderFragment fragment = new AllOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void initView() {
        allOrderAdapter=new AllOrderAdapter(mContext,R.layout.item_all_order,pageSize);
        allOrderAdapter.setOnLoadMoreListener(this);
        allOrderAdapter.setClickListener(new LoadMoreAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String orderNo = allOrderAdapter.getList().get(position).getOrder_no();
                Intent intent=new Intent();
                intent.putExtra(Constant.IParam.orderNo,orderNo);
                STActivityForResult(intent, OrderDetailsActivity.class,1000);

            }
        });
        rv_all_order.setLayoutManager(new LinearLayoutManager(mContext));
        rv_all_order.setAdapter(allOrderAdapter);
        pcfl.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData(1,false);
            }
        });
    }

    @Override
    protected void initData() {
        showProgress();
        getData(1,false);
    }

    public void getData(int page, boolean isLoad) {
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("type", getArguments().getString(Constant.orderType));
        map.put("page",page+"");
        map.put("pagesize",pageSize+"");
        map.put("sign", GetSign.getSign(map));
        addSubscription(ApiRequest.getAllOrder(map).subscribe(new MySub<List<OrderObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onMyNext(List<OrderObj> list) {
                if(isLoad){
                    pageNum++;
                    allOrderAdapter.addList(list,true);
                }else{
                    pageNum=2;
                    allOrderAdapter.setList(list,true);
                }
            }
        }));
    }

    @Override
    protected void onViewClick(View v) {

    }

    @Override
    public void loadMore() {
        getData(pageNum,true);
    }
}
