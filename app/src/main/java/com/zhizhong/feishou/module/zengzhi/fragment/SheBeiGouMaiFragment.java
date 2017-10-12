package com.zhizhong.feishou.module.zengzhi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.zhizhong.feishou.GetSign;
import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseFragment;
import com.zhizhong.feishou.base.MySub;
import com.zhizhong.feishou.module.zengzhi.Constant;
import com.zhizhong.feishou.module.zengzhi.activity.ZengZhiDetailActivity;
import com.zhizhong.feishou.module.zengzhi.network.ApiRequest;
import com.zhizhong.feishou.module.zengzhi.network.response.ZengZhiObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by administartor on 2017/8/1.
 */

public class SheBeiGouMaiFragment extends BaseFragment implements LoadMoreAdapter.OnLoadMoreListener{
    @BindView(R.id.rv_zz_list)
    RecyclerView rv_zz_list;

    LoadMoreAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.frag_she_bei_gou_mai;
    }

    public static SheBeiGouMaiFragment newInstance( ) {
        Bundle args = new Bundle();
        SheBeiGouMaiFragment fragment = new SheBeiGouMaiFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void initView() {
        adapter=new LoadMoreAdapter<ZengZhiObj>(mContext,R.layout.item_zeng_zhi,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int i, ZengZhiObj bean) {
                ImageView imageView = holder.getImageView(R.id.iv_zz_img);
                Glide.with(mContext).load(bean.getImage_url()).error(R.color.c_press).into(imageView);
                holder.setText(R.id.tv_zz_name,bean.getProduct_name())
                        .setText(R.id.tv_zz_model,bean.getProduct_model())
                        .setText(R.id.tv_zz_money,"¥"+bean.getPrice()+"");
                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        Intent intent=new Intent();
                        intent.putExtra(Constant.IParam.zz_detail_type,Constant.IParam.zz_detail_type_1);
                        intent.putExtra(Constant.IParam.id,bean.getId()+"");
                        STActivity(intent,ZengZhiDetailActivity.class);
                    }
                });
            }
        };
        adapter.setOnLoadMoreListener(this);
        rv_zz_list.setLayoutManager(new LinearLayoutManager(mContext));
        rv_zz_list.setAdapter(adapter);

        pcfl.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData(1,false);
            }
        });

    }

    private void getData(int page, boolean isLoad) {
        Map<String,String> map=new HashMap<String,String>();
        map.put("page",page+"");
        map.put("pagesize",pageSize+"");
        map.put("sign", GetSign.getSign(map));
        addSubscription(ApiRequest.getZengZhiList(map).subscribe(new MySub<List<ZengZhiObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onMyNext(List<ZengZhiObj> list) {
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
    protected void initData() {
        showProgress();
       getData(1,false);
    }

    @Override
    protected void onViewClick(View v) {

    }

    @Override
    public void again() {
        initData();
    }

    @Override
    public void loadMore() {
        getData(pageNum,true);
    }
}
