package com.zhizhong.feishou.module.my.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.baseclass.adapter.LoadMoreAdapter;
import com.zhizhong.feishou.GetSign;
import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseActivity;
import com.zhizhong.feishou.base.BaseObj;
import com.zhizhong.feishou.base.MySub;
import com.zhizhong.feishou.module.my.Constant;
import com.zhizhong.feishou.module.my.adapter.AccountAdapter;
import com.zhizhong.feishou.module.my.network.ApiRequest;
import com.zhizhong.feishou.module.my.network.response.AccountObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by administartor on 2017/8/2.
 */

public class AccountListActivity extends BaseActivity implements LoadMoreAdapter.OnLoadMoreListener{
    @BindView(R.id.rv_account)
    RecyclerView rv_account;

    AccountAdapter adapter;
    @Override
    public void again() {
        initData();
    }

    @Override
    protected int getContentView() {
        setAppTitle("账户列表");
        setAppRightImg(R.drawable.img34);
        return R.layout.act_account_list;
    }

    @Override
    protected void initView() {
        adapter=new AccountAdapter(mContext,R.layout.item_account,pageSize);
        adapter.setOnLoadMoreListener(this);
        rv_account.setLayoutManager(new LinearLayoutManager(mContext));
        rv_account.setAdapter(adapter);

        pcfl.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData(1,false);
            }
        });
        adapter.setClickListener(new LoadMoreAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                setDefault(position);
            }
        });
    }

    private void setDefault(int position) {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("account_id",adapter.getList().get(position).getId()+"");
        map.put("user_id",getUserId());
        map.put("sign", GetSign.getSign(map));
        addSubscription(ApiRequest.setDefaultAccount(map).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                Intent intent=new Intent();
                intent.putExtra(Constant.IParam.account,adapter.getList().get(position));
                setResult(RESULT_OK,intent);
                finish();
            }
        }));
    }

    @Override
    protected void initData() {
        showProgress();
        getData(1,false);
    }

    @OnClick({R.id.app_right_iv})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.app_right_iv:
                STActivityForResult(AddBankCardActivity.class,100);
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 100:
                    showLoading();
                    getData(1,false);
                break;
            }
        }
    }

    private void getData(int page,boolean isLoad) {

        addSubscription(ApiRequest.getAccount(getUserId(),getSign()).subscribe(new MySub<List<AccountObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onMyNext(List<AccountObj> list) {
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
    public void loadMore() {
        getData(pageNum,true);
    }
}
