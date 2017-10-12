package com.zhizhong.feishou.module.zengzhi.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.zhizhong.feishou.GetSign;
import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseFragment;
import com.zhizhong.feishou.base.DividerGridItemDecoration;
import com.zhizhong.feishou.base.MySub;
import com.zhizhong.feishou.module.zengzhi.network.ApiRequest;
import com.zhizhong.feishou.module.zengzhi.network.response.ZhuanYePeiXunObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by administartor on 2017/8/1.
 */

public class ZhuanYePeiXunFragment extends BaseFragment implements LoadMoreAdapter.OnLoadMoreListener{
    @BindView(R.id.rv_zz_list)
    RecyclerView rv_zz_list;

    LoadMoreAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.frag_zhuan_ye_pei_xun;
    }

    public static ZhuanYePeiXunFragment newInstance() {
        Bundle args = new Bundle();
        ZhuanYePeiXunFragment fragment = new ZhuanYePeiXunFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void initView() {
        adapter=new LoadMoreAdapter<ZhuanYePeiXunObj>(mContext,R.layout.item_zhuan_ye_pei_xun,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int i, ZhuanYePeiXunObj bean) {
                ImageView imageView = holder.getImageView(R.id.iv_pei_xun);
                Glide.with(mContext).load(bean.getImage_url()).error(R.color.c_press).into(imageView);
                holder.setText(R.id.tv_pei_xun_title,bean.getTitle())
                        .setText(R.id.tv_pei_xun_people,"培训讲师:"+bean.getTraining_teacher());
            }
        };
        adapter.setOnLoadMoreListener(this);
        rv_zz_list.setLayoutManager(new GridLayoutManager(mContext,2));
        rv_zz_list.addItemDecoration(new DividerGridItemDecoration(mContext,2));
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
        addSubscription(ApiRequest.getZhuanYePeiXunList(map).subscribe(new MySub<List<ZhuanYePeiXunObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onMyNext(List<ZhuanYePeiXunObj> list) {
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
