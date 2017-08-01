package com.zhizhong.feishou.module.home.fragment;

import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.SPUtils;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.baseclass.rx.RxBus;
import com.haitaoit.yinya.Config;
import com.haitaoit.yinya.GetSign;
import com.haitaoit.yinya.R;
import com.haitaoit.yinya.base.BaseFragment;
import com.haitaoit.yinya.base.MySub;
import com.haitaoit.yinya.base.ResponseObj;
import com.haitaoit.yinya.module.home.Constant;
import com.haitaoit.yinya.module.home.activity.WebActivity;
import com.haitaoit.yinya.module.home.event.SelectViewEvent;
import com.haitaoit.yinya.module.home.network.ApiRequest;
import com.haitaoit.yinya.module.home.network.response.BannerObj;
import com.haitaoit.yinya.module.home.network.response.GongGaoObj;
import com.haitaoit.yinya.module.home.network.response.IndexListObj;
import com.haitaoit.yinya.module.person.activity.LoginActivity;
import com.haitaoit.yinya.module.person.activity.PersonMessageActivity;
import com.haitaoit.yinya.tools.GlideLoader;
import com.haitaoit.yinya.view.ProgressLayout;
import com.sunfusheng.marqueeview.MarqueeView;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/12.
 * 首页
 */

public class HomePageFragment extends BaseFragment implements ProgressLayout.OnAgainInter {
    @BindView(R.id.marqueeView)
    MarqueeView marqueeView;
    @BindView(R.id.tv_home_search)
    TextView tv_home_search;
    @BindView(R.id.ll_home_tuijian)
    LinearLayout ll_home_tuijian;
    @BindView(R.id.nsv_home)
    NestedScrollView nsv_home;
    @BindView(R.id.ll_home)
    LinearLayout ll_home;
    @BindView(R.id.bn_home)
    Banner bn_home;

    @BindView(R.id.tv_home_edu1)
    TextView tv_home_edu1;

    @BindView(R.id.tv_home_edu2)
    TextView tv_home_edu2;

    @BindView(R.id.tv_home_edu3)
    TextView tv_home_edu3;

    @BindView(R.id.tv_home_edu4)
    TextView tv_home_edu4;
    @BindView(R.id.rv_home_hot)
    RecyclerView rv_home_hot;

    private List<String> bannerList,zhengXinList;
    private LoadMoreAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.frag_home;
    }

    @Override
    protected void initView() {
        showProgress(this);
        ll_home.getBackground().setAlpha(0);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.height= (PhoneUtils.getScreenWidth(mContext)- PhoneUtils.dip2px(mContext,40))/3+ PhoneUtils.dip2px(mContext,20);
        ll_home_tuijian.setLayoutParams(layoutParams);
        ll_home_tuijian.setPadding(0, PhoneUtils.dip2px(mContext,10),0, PhoneUtils.dip2px(mContext,10));

        nsv_home.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY>=0&&scrollY<=400){
                    double alpha = (double)scrollY / 400;
                    ll_home.getBackground().setAlpha((int)(alpha*255));
                }else{
                    ll_home.getBackground().setAlpha(255);
                }
            }
        });

        rv_home_hot.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_home_hot.setNestedScrollingEnabled(false);
        adapter=new LoadMoreAdapter<IndexListObj.DyredgoodslistBean>(getActivity(),R.layout.item_home_hot,0) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int i, IndexListObj.DyredgoodslistBean item) {
                holder.setText(R.id.iv_hot_title,item.getTitle())
                        .setText(R.id.iv_hot_num,item.getPerson()+"人")
                        .setText(R.id.iv_hot_money,item.getLoanamount()+"");
                Glide.with(mContext).load(item.getImg_url()).into(holder.getImageView(R.id.iv_hot_img));
            }
        };
        rv_home_hot.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        showProgress(this);
        getBanner();
        getZhengXin();
        getGongGao();
        getData();
    }

    private void getZhengXin() {
        String code="zx_roll";
        String sign = GetSign.getSign("code", code);
        addSubscription(ApiRequest.getIndexBanner(code,sign).subscribe(new MySub<BannerObj>(mContext) {
            @Override
            public void onMyNext(BannerObj obj) {
                if(obj.getDyadroollist()!=null&&obj.getDyadroollist().size()>0){
                    zhengXinList = new ArrayList<String>();
                    for (int i = 0; i < obj.getDyadroollist().size(); i++) {
                        zhengXinList.add(obj.getDyadroollist().get(i).getUrl());
                    }
                }
            }
        }));
    }

    private void getGongGao() {
        addSubscription(ApiRequest.getGongGaoList().subscribe(new MySub<ResponseObj<List<GongGaoObj>>>(mContext,pl_load) {
            @Override
            public void onMyNext(ResponseObj<List<GongGaoObj>> obj) {
                if(obj.getErrCode()==0){
                    List<GongGaoObj> list = obj.getResponse();
                    if(list!=null&&list.size()>0){
                        List<String> info = new ArrayList<>();
                        for (int i = 0; i < list.size(); i++) {
                            info.add(list.get(i).getTitle());
                        }
                        marqueeView.startWithList(info);
                    }
                }else{
                    showToastS(obj.getErrMsg());
                }

            }
        }));
    }

    private void getBanner() {
        String code="index_roll";
        String sign = GetSign.getSign("code", code);
        addSubscription(ApiRequest.getIndexBanner(code,sign).subscribe(new MySub<BannerObj>(mContext) {
            @Override
            public void onMyNext(BannerObj obj) {
                if(obj.getDyadroollist()!=null&&obj.getDyadroollist().size()>0){
                    bannerList = new ArrayList<String>();
                    for (int i = 0; i < obj.getDyadroollist().size(); i++) {
                        bannerList.add(obj.getDyadroollist().get(i).getImg_url());
                    }
                    bn_home.setImages(bannerList);
                    bn_home.setImageLoader(new GlideLoader());
                    bn_home.start();
                }
            }
        }));
    }

    private void getData() {
//        showLoading();
        addSubscription(ApiRequest.getIndexList().subscribe(new MySub<IndexListObj>(mContext,pl_load) {
            @Override
            public void onMyNext(IndexListObj list) {
                List<IndexListObj.DyadroollistBean> eDuList = list.getDyadroollist();
                if(eDuList!=null&&eDuList.size()>0){
                    tv_home_edu1.setText(eDuList.get(3).getTitle());
                    tv_home_edu2.setText(eDuList.get(2).getTitle());
                    tv_home_edu3.setText(eDuList.get(1).getTitle());
                    tv_home_edu4.setText(eDuList.get(0).getTitle());
                }
                List<IndexListObj.DyredgoodslistBean> hotList = list.getDyredgoodslist();
                adapter.setList(hotList,true);
            }
        }));
    }

    @Override
    public void onStop() {
        super.onStop();
        if (bn_home != null&&bannerList!=null) {
            bn_home.stopAutoPlay();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (bn_home != null&&bannerList!=null) {
            bn_home.startAutoPlay();
        }
    }

    @OnClick({R.id.tv_home_zx,R.id.tv_home_zm,R.id.ll_home_daikuan,R.id.ll_home_more_daikuan,R.id.tv_home_search,R.id.iv_home_message})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_home_zx:
                Intent intent=new Intent();
                intent.putExtra(Constant.title,"征信报告");
                intent.putExtra(Constant.url,zhengXinList.get(0));
                STActivity(intent,WebActivity.class);
                break;
            case R.id.tv_home_zm:
                Intent zmIntent=new Intent();
                zmIntent.putExtra(Constant.title,"芝麻信用");
                zmIntent.putExtra(Constant.url,zhengXinList.get(1));
                STActivity(zmIntent,WebActivity.class);
                break;
            case R.id.ll_home_daikuan:
            case R.id.ll_home_more_daikuan:
                RxBus.getInstance().post(new SelectViewEvent(false));
                break;
            case R.id.tv_home_search:
                RxBus.getInstance().post(new SelectViewEvent(true));
                break;
            case R.id.iv_home_message:
                if(TextUtils.isEmpty(SPUtils.getPrefString(mContext, Config.user_id,null))){
                    mContext.startActivity(new Intent(mContext, LoginActivity.class));
                    return;
                }
                STActivity(PersonMessageActivity.class);
                break;
        }
    }

    @Override
    public void again() {
        initData();
    }
}
