package com.zhizhong.feishou.module.renwu.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.baseclass.adapter.LoadMoreAdapter;
import com.zhizhong.feishou.GetSign;
import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseFragment;
import com.zhizhong.feishou.base.MySub;
import com.zhizhong.feishou.module.renwu.adapter.RenWuAdapter;
import com.zhizhong.feishou.module.renwu.network.ApiRequest;
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
        rv_renwu.setLayoutManager(new LinearLayoutManager(mContext));
        rv_renwu.setAdapter(renWuAdapter);

        pcfl.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                reset();
                getData(1, false);
            }
        });

    }

    private void reset() {
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
                break;
            case R.id.tv_rw_area:
                break;
            case R.id.tv_rw_diqu:
                break;
            case R.id.tv_rw_homework:
                break;
        }
    }

    @Override
    public void again() {
        initData();
    }

    @Override
    public void loadMore() {
        getData(pageNum, true);
    }


}
