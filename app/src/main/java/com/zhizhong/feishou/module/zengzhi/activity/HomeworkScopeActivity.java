package com.zhizhong.feishou.module.zengzhi.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseActivity;
import com.zhizhong.feishou.module.zengzhi.adapter.HomeworkScopeAdapter;
import com.zhizhong.feishou.tools.SpaceItemDecoration;

import butterknife.BindView;

/**
 * Created by administartor on 2017/8/3.
 */

public class HomeworkScopeActivity extends BaseActivity {
    @BindView(R.id.rv_homework_scope)
    RecyclerView rv_homework_scope;


    HomeworkScopeAdapter adapter;

    @Override
    public void again() {

    }

    @Override
    protected int getContentView() {
        setAppTitle("作业范围");
        setAppRightTitle("确定");
        setAppRightTitleColor(getResources().getColor(R.color.blue));
        return R.layout.act_homework_scope;
    }

    @Override
    protected void initView() {
        adapter=new HomeworkScopeAdapter(mContext,R.layout.item_homework_scope,0);
        adapter.setTestListSize(6);

        rv_homework_scope.setLayoutManager(new GridLayoutManager(mContext,3));
        rv_homework_scope.addItemDecoration(new SpaceItemDecoration(10));
        rv_homework_scope.setAdapter(adapter);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}
