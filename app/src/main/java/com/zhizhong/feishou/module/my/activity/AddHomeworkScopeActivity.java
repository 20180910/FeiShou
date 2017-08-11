package com.zhizhong.feishou.module.my.activity;

import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.customview.MyTextView;
import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseActivity;
import com.zhizhong.feishou.base.BaseObj;
import com.zhizhong.feishou.base.MySub;
import com.zhizhong.feishou.module.my.network.ApiRequest;
import com.zhizhong.feishou.module.my.network.request.HomeworkCityItem;
import com.zhizhong.feishou.module.my.network.response.CityObj;
import com.zhizhong.feishou.module.my.network.response.ProvinceObj;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/8/3.
 */

public class AddHomeworkScopeActivity extends BaseActivity {
    @BindView(R.id.tv_homework_select_province)
    MyTextView tv_homework_select_province;
    @BindView(R.id.rv_homework_select_city)
    RecyclerView rv_homework_select_city;

    LoadMoreAdapter adapter,provinceAdapter;



    protected BottomSheetDialog provinceDialog;
    private List<HomeworkCityItem.BodyBean> selectCityList=new ArrayList<>();

    @Override
    public void again() {

    }

    @Override
    protected int getContentView() {
        setAppTitle("新增作业范围");
        setAppRightTitle("确定");
        setAppRightTitleColor(getResources().getColor(R.color.blue));
        return R.layout.act_add_homework_scope;
    }

    @Override
    protected void initView() {
        adapter=new LoadMoreAdapter<CityObj>(mContext,R.layout.item_add_city,0) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, CityObj bean) {
                ImageView imageView = holder.getImageView(R.id.tv_homework_city_flag);
                if(bean.isSelect()){
                    imageView.setVisibility(View.VISIBLE);
                }else{
                    imageView.setVisibility(View.INVISIBLE);
                }
                TextView cityName = holder.getTextView(R.id.tv_homework_city_name);
                cityName.setText(bean.getTitle());
                cityName.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        bean.setSelect(!bean.isSelect());
                        if(bean.isSelect()){
                            imageView.setVisibility(View.VISIBLE);
                        }else{
                            imageView.setVisibility(View.INVISIBLE);
                        }
//                        notifyDataSetChanged();
                    }
                });
            }
        };
        rv_homework_select_city.setLayoutManager(new LinearLayoutManager(mContext));
        rv_homework_select_city.setAdapter(adapter);

    }

    @Override
    protected void initData() {
        showLoading();
        String rnd = getRnd();
        String sign = getSign("rnd", rnd);
        addSubscription(ApiRequest.getAllCity(rnd,sign).subscribe(new MySub<List<CityObj>>(mContext) {
            @Override
            public void onMyNext(List<CityObj> list) {
                adapter.setList(list,true);
            }
        }));
    }

    @OnClick({R.id.app_right_tv,R.id.tv_homework_select_province })
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.app_right_tv:
                if(isEmpty(adapter.getList())){
                    showMsg("请选择城市");
                    return;
                }
                for (int i = 0; i < adapter.getList().size(); i++) {
                    CityObj city = (CityObj) adapter.getList().get(i);
                    if(city.isSelect()){
                        selectCityList.add(new HomeworkCityItem.BodyBean(city.getTitle()));
                    }
                }
                if(isEmpty(selectCityList)){
                    showMsg("请选择城市");
                    return;
                }
                addHomeworkCity();
                break;
            case R.id.tv_homework_select_province:
                selectProvince();
                break;
        }
    }

    private void selectProvince() {
        showLoading();
        String rnd = getRnd();
        addSubscription(ApiRequest.getProvince(rnd,getSign("rnd",rnd)).subscribe(new MySub<List<ProvinceObj>>(mContext) {
            @Override
            public void onMyNext(List<ProvinceObj> list) {
                showProvince(list);
            }
        }));
    }
    private void showProvince(List<ProvinceObj> list) {
        if (provinceDialog == null) {
            provinceAdapter=new LoadMoreAdapter<ProvinceObj>(mContext,R.layout.item_select_province,0) {
                @Override
                public void bindData(LoadMoreViewHolder holder, int position, ProvinceObj bean) {
                    TextView textView = holder.getTextView(R.id.tv_province_name);
                    textView.setText(bean.getTitle());
                    textView.setOnClickListener(new MyOnClickListener() {
                        @Override
                        protected void onNoDoubleClick(View view) {
                            provinceDialog.dismiss();
                            tv_homework_select_province.setText(bean.getTitle());
                            getCityForProvince(bean.getId()+"");
                        }
                    });
                }
            };
            provinceAdapter.setList(list);
            View province= LayoutInflater.from(mContext).inflate(R.layout.popu_province,null);
            TextView tv_province_cancle = (TextView) province.findViewById(R.id.tv_province_cancle);
            tv_province_cancle.setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    provinceDialog.dismiss();
                }
            });
            RecyclerView rv_province = (RecyclerView) province.findViewById(R.id.rv_province);
            rv_province.setLayoutManager(new LinearLayoutManager(mContext));
            rv_province.setAdapter(provinceAdapter);

            provinceDialog=new BottomSheetDialog(mContext);
            provinceDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            provinceDialog.setCanceledOnTouchOutside(true);
            provinceDialog.setContentView(province);
        }else{

        }
        provinceDialog.show();
    }

    private void getCityForProvince(String parentId) {
        showLoading();
        String sign = getSign("parent_id", parentId);
        addSubscription(ApiRequest.getCityForProvince(parentId,sign).subscribe(new MySub<List<CityObj>>(mContext) {
            @Override
            public void onMyNext(List<CityObj> list) {
                adapter.setList(list,true);
            }
        }));
    }

    private void addHomeworkCity() {
        showLoading();
        HomeworkCityItem item = new HomeworkCityItem();
        item.setBody(selectCityList);

        addSubscription(ApiRequest.addHomework(getUserId(),getSign("userid",getUserId()), item).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                showMsg(obj.getMsg());
                setResult(RESULT_OK);
                finish();
            }
        }));
    }
}
