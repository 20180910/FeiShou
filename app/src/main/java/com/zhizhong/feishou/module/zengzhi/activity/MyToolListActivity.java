package com.zhizhong.feishou.module.zengzhi.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by administartor on 2017/8/3.
 */

public class MyToolListActivity extends BaseActivity {
    @BindView(R.id.rv_my_tool)
    RecyclerView rv_my_tool;

    LoadMoreAdapter adapter;
    @Override
    public void again() {

    }

    @Override
    protected int getContentView() {
        setAppTitle("添加工具");
        setAppRightTitle("编辑");
        setAppRightTitleColor(getResources().getColor(R.color.blue));
        return R.layout.act_my_tool_list;
    }

    @Override
    protected void initView() {
        adapter=new LoadMoreAdapter(mContext,R.layout.item_my_tool,0) {
            @Override
            public void bindData(LoadMoreViewHolder loadMoreViewHolder, int i, Object o) {

            }
        };
        adapter.setTestListSize(10);

        rv_my_tool.setLayoutManager(new LinearLayoutManager(mContext));
        rv_my_tool.setAdapter(adapter);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}
