package com.zhizhong.feishou.module.my.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.baseclass.view.MyDialog;
import com.zhizhong.feishou.GetSign;
import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseActivity;
import com.zhizhong.feishou.base.BaseObj;
import com.zhizhong.feishou.base.MySub;
import com.zhizhong.feishou.module.my.Constant;
import com.zhizhong.feishou.module.my.network.ApiRequest;
import com.zhizhong.feishou.module.my.network.response.MyToolObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by administartor on 2017/8/3.
 */

public class MyToolListActivity extends BaseActivity implements LoadMoreAdapter.OnLoadMoreListener{
    @BindView(R.id.rv_my_tool)
    RecyclerView rv_my_tool;
    private boolean isDelete;
    LoadMoreAdapter adapter;
    @Override
    public void again() {
        initData();
    }
    @Override
    protected int getContentView() {
        setAppTitle("我的工具");
        setAppRightTitle("编辑");
        setAppRightTitleColor(getResources().getColor(R.color.blue));
        return R.layout.act_my_tool_list;
    }

    @Override
    protected void initView() {
        adapter=new LoadMoreAdapter<MyToolObj>(mContext,R.layout.item_my_tool,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder,final int i, MyToolObj bean) {
                TextView textView = holder.getTextView(R.id.tv_tool_delete);
                if(isDelete){
                    textView.setVisibility(View.VISIBLE);
                    textView.setOnClickListener(new MyOnClickListener() {
                        @Override
                        protected void onNoDoubleClick(View view) {
                            mDialog=new MyDialog.Builder(mContext);
                            mDialog.setMessage("确认删除吗?")
                            .setNegativeButton((dialog, which) -> dialog.dismiss())
                            .setPositiveButton((dialog, which) -> {
                                dialog.dismiss();
                                deleteTool(i,bean.getId()+"");
                            }).create().show();
                        }
                    });
                }else{
                    textView.setVisibility(View.GONE);
                }
                holder.setText(R.id.tv_tool_name,bean.getProduct_name())
                        .setText(R.id.tv_tool_model,bean.getProduct_model())
                        .setText(R.id.tv_tool_num,"数量："+bean.getProduct_number()+"架");

                ImageView imageView = holder.getImageView(R.id.iv_tool_img);
                Glide.with(mContext).load(bean.getImage_url()).error(R.color.c_press).into(imageView);
            }
        };
        adapter.setOnLoadMoreListener(this);

        rv_my_tool.setLayoutManager(new LinearLayoutManager(mContext));
        rv_my_tool.setAdapter(adapter);

        pcfl.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData(1,false);
            }
        });

    }

    private void deleteTool(int position,String toolId) {
        showLoading();
        addSubscription(ApiRequest.deleteTool(toolId,getSign("tool_id",toolId)).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                showMsg(obj.getMsg());
                adapter.getList().remove(position);
                adapter.notifyDataSetChanged();
            }
        }));
    }

    @Override
    protected void initData() {
        showProgress();
        getData(1,false);
       
    }

    private void getData(int page, boolean isLoad) {
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("pagesize",pageSize+"");
        map.put("page",page+"");
        map.put("sign", GetSign.getSign(map));
        addSubscription(ApiRequest.getMyToolList(map).subscribe(new MySub<List<MyToolObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onMyNext(List<MyToolObj> list) {
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
    @OnClick({R.id.app_right_tv,R.id.tv_tool_add})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_tool_add:
                STActivityForResult(AddToolActivity.class, Constant.RCode.addTool);
                break;
            case R.id.app_right_tv:
                isDelete=!isDelete;
                if(isDelete){
                    setAppRightTitle("完成");
                }else{
                    setAppRightTitle("编辑");
                }
                adapter.notifyDataSetChanged();
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case Constant.RCode.addTool:
                    showLoading();
                    getData(1,false);
                    break;
            }
        }
    }

    @Override
    public void loadMore() {
        getData(pageNum,true);
    }
}
