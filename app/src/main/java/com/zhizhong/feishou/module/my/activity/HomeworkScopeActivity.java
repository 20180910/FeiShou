package com.zhizhong.feishou.module.my.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseActivity;
import com.zhizhong.feishou.base.MySub;
import com.zhizhong.feishou.module.my.Constant;
import com.zhizhong.feishou.module.my.adapter.HomeworkScopeAdapter;
import com.zhizhong.feishou.module.my.network.ApiRequest;
import com.zhizhong.feishou.module.my.network.response.HomeworkObj;
import com.zhizhong.feishou.tools.SpaceItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/8/3.
 */

public class HomeworkScopeActivity extends BaseActivity {
    @BindView(R.id.rv_homework_scope)
    RecyclerView rv_homework_scope;

    @BindView(R.id.tv_homework_add)
    TextView tv_homework_add;


    HomeworkScopeAdapter adapter;

    @Override
    public void again() {
        initData();
    }

    @Override
    protected int getContentView() {
        setAppTitle("作业范围");
        setAppRightTitle("编辑");
        setAppRightTitleColor(getResources().getColor(R.color.app_title_white));
        return R.layout.act_homework_scope;
    }

    @Override
    protected void initView() {
        adapter=new HomeworkScopeAdapter(mContext,R.layout.item_homework_scope,0);

        rv_homework_scope.setLayoutManager(new GridLayoutManager(mContext,3));
        rv_homework_scope.addItemDecoration(new SpaceItemDecoration(10));
        rv_homework_scope.setAdapter(adapter);

    }

    @Override
    protected void initData() {
        showProgress();
        getData();
    }

    private void getData() {

        addSubscription(ApiRequest.getHomeworkList(getUserId(),getSign()).subscribe(new MySub<List<HomeworkObj>>(mContext,pl_load) {
            @Override
            public void onMyNext(List<HomeworkObj> list) {
                adapter.setList(list,true);
            }
        }));
    }

    @OnClick({R.id.app_right_tv,R.id.tv_homework_add})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_homework_add:
                STActivityForResult(AddHomeworkScopeActivity.class, Constant.RCode.addHomework);
                break;
            case R.id.app_right_tv:
                adapter.setDelete(!adapter.isDelete());
                if(adapter.isDelete()){
                    setAppRightTitle("完成");
                }else{
                    setAppRightTitle("编辑");
                }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case Constant.RCode.addHomework:
                    initData();
                break;
            }
        }
    }
}
