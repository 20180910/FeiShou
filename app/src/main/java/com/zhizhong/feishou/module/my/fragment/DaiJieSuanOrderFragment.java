package com.zhizhong.feishou.module.my.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.baseclass.adapter.LoadMoreAdapter;
import com.zhizhong.feishou.GetSign;
import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseFragment;
import com.zhizhong.feishou.base.MySub;
import com.zhizhong.feishou.module.my.Constant;
import com.zhizhong.feishou.module.my.adapter.DaiJieSuanOrderAdapter;
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

public class DaiJieSuanOrderFragment extends BaseFragment implements LoadMoreAdapter.OnLoadMoreListener{
    @BindView(R.id.rv_djs_order)
    RecyclerView rv_djs_order;

    DaiJieSuanOrderAdapter adapter;
    @Override
    public void again() {
        initData();
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_daijiesuan_order;
    }

    @Override
    protected void initView() {
        adapter=new DaiJieSuanOrderAdapter(mContext,R.layout.item_daijiesuan_order,pageSize);

        rv_djs_order.setLayoutManager(new LinearLayoutManager(mContext));
        rv_djs_order.setAdapter(adapter);

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
    private void getData(int page, boolean isLoad) {
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("type", Constant.yiWanChengOrder);
        map.put("page",page+"");
        map.put("pagesize",pageSize+"");
        map.put("sign", GetSign.getSign(map));
        addSubscription(ApiRequest.getAllOrder(map).subscribe(new MySub<List<OrderObj>>(mContext) {
            @Override
            public void onMyNext(List<OrderObj> list) {
                if(isLoad){
                    pageNum++;
                    adapter.addList(list,true);
                }else{
                    pageNum=2;
                    adapter.setList(list,true);
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
