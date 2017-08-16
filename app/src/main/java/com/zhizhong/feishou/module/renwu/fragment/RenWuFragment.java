package com.zhizhong.feishou.module.renwu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.androidtools.PhoneUtils;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.baseclass.utils.ActUtils;
import com.github.baseclass.view.MyPopupwindow;
import com.zhizhong.feishou.GetSign;
import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseFragment;
import com.zhizhong.feishou.base.MySub;
import com.zhizhong.feishou.module.my.activity.LoginActivity;
import com.zhizhong.feishou.module.renwu.Constant;
import com.zhizhong.feishou.module.renwu.activity.RenWuDetailsActivity;
import com.zhizhong.feishou.module.renwu.adapter.RenWuAdapter;
import com.zhizhong.feishou.module.renwu.network.ApiRequest;
import com.zhizhong.feishou.module.renwu.network.response.NZWTypeObj;
import com.zhizhong.feishou.module.renwu.network.response.ZuoWuObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by administartor on 2017/8/1.
 */

public class RenWuFragment extends BaseFragment implements LoadMoreAdapter.OnLoadMoreListener {
    RenWuAdapter renWuAdapter;

    @BindView(R.id.rv_renwu)
    RecyclerView rv_renwu;
    @BindView(R.id.tv_rw_zuowu)
    TextView tv_rw_zuowu;
    @BindView(R.id.tv_rw_area)
    TextView tv_rw_area;
    @BindView(R.id.tv_rw_diqu)
    TextView tv_rw_diqu;
    @BindView(R.id.tv_rw_homework)
    TextView tv_rw_homework;

    private String zuoWu = "";
    private String diQu = "";
    protected int areaSort = 0;
    protected int homeworkSort = 0;

    protected List<NZWTypeObj> nzwTypeList;
    protected List<NZWTypeObj> dqTypeList;


    MyPopupwindow bankPopu,typePopu;
    @Override
    protected int getContentView() {
        return R.layout.frag_ren_wu;
    }

    public static RenWuFragment newInstance() {
        Bundle args = new Bundle();
        RenWuFragment fragment = new RenWuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView() {
        renWuAdapter = new RenWuAdapter(mContext, R.layout.item_ren_wu, pageSize);
        renWuAdapter.setOnLoadMoreListener(this);
        renWuAdapter.setClickListener(new LoadMoreAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                Intent intent=new Intent();
                intent.putExtra(Constant.IParam.productId,renWuAdapter.getList().get(i).getId()+"");
                ActUtils.STActivityForResult(mContext,intent,RenWuDetailsActivity.class, Constant.RCode.getOrder);
            }
        });
        rv_renwu.setLayoutManager(new LinearLayoutManager(mContext));
        rv_renwu.setAdapter(renWuAdapter);

        pcfl.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                reset();
                getData(1, false);
            }
        });

        getNZW(false);
        getDQ(false);

    }
    private void getNZW(boolean manual) {

        String rnd = getRnd();
        addSubscription(ApiRequest.getNZW(rnd,getSign("rnd",rnd)).subscribe(new MySub<List<NZWTypeObj>>(mContext) {
            @Override
            public void onMyNext(List<NZWTypeObj> list) {
                NZWTypeObj obj=new NZWTypeObj();
                obj.setTitle("");
                nzwTypeList=list;
                nzwTypeList.add(0,obj);
                if(manual){
                    showNZW(nzwTypeList);
                }
            }
        }));
    }
    private void getDQ(boolean manual) {
        addSubscription(ApiRequest.getDQ(getUserId(),getSign()).subscribe(new MySub<List<NZWTypeObj>>(mContext) {
            @Override
            public void onMyNext(List<NZWTypeObj> list) {
                NZWTypeObj obj=new NZWTypeObj();
                obj.setTitle("");
                dqTypeList=list;
                dqTypeList.add(0,obj);
                if(manual){
                    showDQ(dqTypeList);
                }
            }
        }));
    }


    private void reset() {
        tv_rw_zuowu.setText("农作物");
        tv_rw_zuowu.setTextColor(getResources().getColor(R.color.gray_66));

        tv_rw_area.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.jiantou_bottom_white,0);
        tv_rw_area.setTextColor(getResources().getColor(R.color.gray_66));

        tv_rw_diqu.setText("地区");
        tv_rw_diqu.setTextColor(getResources().getColor(R.color.gray_66));

        tv_rw_homework.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.jiantou_bottom_white,0);
        tv_rw_homework.setTextColor(getResources().getColor(R.color.gray_66));

        zuoWu = "";
        areaSort = 0;
        homeworkSort = 0;
        diQu = "";
    }

    @Override
    protected void initData() {
        showProgress();
        getData(1, false);
    }

    private void getData(int page, boolean isLoad) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("crops", zuoWu);
        map.put("area", areaSort + "");
        map.put("operation_time", homeworkSort + "");
        map.put("region", diQu);
        map.put("pagesize", pageSize + "");
        map.put("page", page + "");
        map.put("sign", GetSign.getSign(map));
        addSubscription(ApiRequest.getProductList(map).subscribe(new MySub<List<ZuoWuObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onMyNext(List<ZuoWuObj> list) {
                if (isLoad) {
                    pageNum++;
                    renWuAdapter.addList(list, true);
                } else {
                    pageNum = 2;
                    renWuAdapter.setList(list, true);
                }
            }
        }));
    }

    @OnClick({R.id.tv_rw_zuowu, R.id.tv_rw_area, R.id.tv_rw_diqu, R.id.tv_rw_homework})
    protected void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.tv_rw_zuowu:
                if (nzwTypeList == null) {
                    showLoading();
                    getNZW(true);
                }else{
                    showNZW(nzwTypeList);
                }
                break;
            case R.id.tv_rw_area:
//                showLoading();
                areaSort++;
                if(areaSort==0){
                    tv_rw_area.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.jiantou_bottom_white,0);
                }else if(areaSort==1){
                    homeworkSort=0;
                    tv_rw_homework.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.jiantou_bottom_white,0);

                    tv_rw_area.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.jiantou_bottom,0);
                }else if(areaSort==2){
                    homeworkSort=0;
                    tv_rw_homework.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.jiantou_bottom_white,0);

                    tv_rw_area.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.jiantou_up,0);
                }else{
                    areaSort=0;
                    tv_rw_area.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.jiantou_bottom_white,0);
                }
                showLoading();
                getData(1,false);
                break;
            case R.id.tv_rw_diqu:
                if(TextUtils.isEmpty(getUserId())){
                    STActivity(LoginActivity.class);
                    return;
                }else{
                    if(dqTypeList==null){
                        showLoading();
                        getDQ(true);
                    }else{
                        showDQ(dqTypeList);
                    }
                }

                break;
            case R.id.tv_rw_homework:
                //                showLoading();
                homeworkSort++;
                if(homeworkSort==0){
                    tv_rw_homework.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.jiantou_bottom_white,0);
                }else if(homeworkSort==1){
                    areaSort=0;
                    tv_rw_area.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.jiantou_bottom_white,0);

                    tv_rw_homework.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.jiantou_bottom,0);
                }else if(homeworkSort==2){
                    areaSort=0;
                    tv_rw_area.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.jiantou_bottom_white,0);

                    tv_rw_homework.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.jiantou_up,0);
                }else{
                    homeworkSort=0;
                    tv_rw_homework.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.jiantou_bottom_white,0);
                }
                showLoading();
                getData(1,false);
                break;
        }
    }



    private void showNZW(List<NZWTypeObj> nzwTypeList) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.popu_nzw, null);
            RecyclerView rv_daikuan_sort = (RecyclerView) view.findViewById(R.id.rv_nzw);
            rv_daikuan_sort.setLayoutManager(new LinearLayoutManager(mContext));
            LoadMoreAdapter sortAdapter = new LoadMoreAdapter<NZWTypeObj>(mContext,R.layout.item_nzw, 10) {
                @Override
                public void bindData(LoadMoreViewHolder holder, final int i,NZWTypeObj item) {
                    String title=item.getTitle();
                    if(TextUtils.isEmpty(item.getTitle())){
                        title="全部";
                    }
                    holder.setText(R.id.tv_nzw_name,title);
                    holder.itemView.setOnClickListener(v1 -> {
                        zuoWu=item.getTitle();
                        if(TextUtils.isEmpty(item.getTitle())){
                            tv_rw_zuowu.setText("农作物");
                            tv_rw_zuowu.setTextColor(getResources().getColor(R.color.gray_66));
                        }else{
                            tv_rw_zuowu.setText(item.getTitle());
                            tv_rw_zuowu.setTextColor(getResources().getColor(R.color.blue));
                        }
                        bankPopu.dismiss();
                        showLoading();
                        getData(1,false);
                    });

                }
            };
            sortAdapter.setList(nzwTypeList);
            rv_daikuan_sort.setAdapter(sortAdapter);
            bankPopu = new MyPopupwindow(mContext, view);
            bankPopu.showAsDropDown(tv_rw_zuowu,-PhoneUtils.dip2px(mContext,3),0);
    }
    private void showDQ(List<NZWTypeObj> dqTypeList) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.popu_nzw, null);
            RecyclerView rv_daikuan_sort = (RecyclerView) view.findViewById(R.id.rv_nzw);
            rv_daikuan_sort.setLayoutManager(new LinearLayoutManager(mContext));
            LoadMoreAdapter sortAdapter = new LoadMoreAdapter<NZWTypeObj>(mContext,R.layout.item_nzw, 10) {
                @Override
                public void bindData(LoadMoreViewHolder holder, final int i,NZWTypeObj item) {
                    String title=item.getTitle();
                    if(TextUtils.isEmpty(item.getTitle())){
                        title="全部";
                    }
                    holder.setText(R.id.tv_nzw_name,title);
                    holder.itemView.setOnClickListener(v1 -> {
                        diQu=item.getTitle();
                        if(TextUtils.isEmpty(item.getTitle())){
                            tv_rw_diqu.setText("地区");
                            tv_rw_diqu.setTextColor(getResources().getColor(R.color.gray_66));
                        }else{
                            tv_rw_diqu.setText(item.getTitle());
                            tv_rw_diqu.setTextColor(getResources().getColor(R.color.blue));
                        }
                        typePopu.dismiss();
                        showLoading();
                        getData(1,false);
                    });
                }
            };
            sortAdapter.setList(dqTypeList);
            rv_daikuan_sort.setAdapter(sortAdapter);
            typePopu = new MyPopupwindow(mContext, view);
            typePopu.showAsDropDown(tv_rw_diqu,-PhoneUtils.dip2px(mContext,3),0);
    }

    @Override
    public void again() {
        initData();
    }

    @Override
    public void loadMore() {
        getData(pageNum, true);
    }

    public void getHomework() {
        addSubscription(ApiRequest.getDQ(getUserId(),getSign()).subscribe(new MySub<List<NZWTypeObj>>(mContext) {
            @Override
            public void onMyNext(List<NZWTypeObj> list) {
                dqTypeList=null;
                NZWTypeObj obj=new NZWTypeObj();
                obj.setTitle("");
                dqTypeList=list;
                dqTypeList.add(0,obj);
            }
        }));
    }
}
