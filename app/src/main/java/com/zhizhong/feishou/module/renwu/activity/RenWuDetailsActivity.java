package com.zhizhong.feishou.module.renwu.activity;

import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.baseclass.view.MyDialog;
import com.github.customview.MyTextView;
import com.zhizhong.feishou.GetSign;
import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseActivity;
import com.zhizhong.feishou.base.BaseObj;
import com.zhizhong.feishou.base.MySub;
import com.zhizhong.feishou.module.renwu.Constant;
import com.zhizhong.feishou.module.renwu.network.ApiRequest;
import com.zhizhong.feishou.module.renwu.network.response.RenWuDetailObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/8/2.
 */

public class RenWuDetailsActivity extends BaseActivity {
    @BindView(R.id.iv_rw_detail_img)
    ImageView iv_rw_detail_img;
    @BindView(R.id.tv_rw_detail_number)
    TextView tv_rw_detail_number;

    @BindView(R.id.tv_rw_detail_total)
    TextView tv_rw_detail_total;

    @BindView(R.id.tv_rw_detail_dq)
    TextView tv_rw_detail_dq;

    @BindView(R.id.tv_rw_detail_nzw)
    TextView tv_rw_detail_nzw ;

    @BindView(R.id.tv_rw_detail_dj)
    TextView tv_rw_detail_dj ;

    @BindView(R.id.tv_rw_detail_ms)
    TextView tv_rw_detail_ms ;

    @BindView(R.id.tv_rw_detail_time)
    TextView tv_rw_detail_time ;

    @BindView(R.id.tv_rw_detail_dk)
    TextView tv_rw_detail_dk ;

    @BindView(R.id.tv_rw_detail_address)
    TextView tv_rw_detail_address ;

    @BindView(R.id.ll_rw_details_bao1)
    LinearLayout ll_rw_details_bao1;
    @BindView(R.id.ll_rw_details_bao2)
    LinearLayout ll_rw_details_bao2;
    @BindView(R.id.ll_rw_details_bao3)
    LinearLayout ll_rw_details_bao3;
    @BindView(R.id.ll_rw_details_bao4)
    LinearLayout ll_rw_details_bao4;
    @BindView(R.id.tv_rw_detail_commit)
    MyTextView tv_rw_detail_commit;
    private String productId;
    private String orderNo;

    @Override
    protected int getContentView() {
        setAppTitle("产品详情");
        return R.layout.act_renwu_details;
    }

    @Override
    protected void initView() {
        productId = getIntent().getStringExtra(Constant.IParam.productId);
    }

    @Override
    protected void initData() {
        showProgress();
        getData();
    }

    private void getData() {
        addSubscription(ApiRequest.getRenWuDetails(productId, getSign("product_id", productId)).subscribe(new MySub<RenWuDetailObj>(mContext, pl_load) {
            @Override
            public void onMyNext(RenWuDetailObj obj) {
                Glide.with(mContext).load(obj.getImage_url()).error(R.color.c_press).into(iv_rw_detail_img);
                orderNo = obj.getNh_order_no();
                tv_rw_detail_number.setText(obj.getNh_order_no());
                tv_rw_detail_dq.setText(obj.getRegion());
                tv_rw_detail_nzw.setText(obj.getCrops());
                tv_rw_detail_dj.setText(obj.getPrice());
                tv_rw_detail_ms.setText(obj.getArea());
                tv_rw_detail_time.setText(obj.getRequest_time());
                tv_rw_detail_dk.setText(obj.getCondition());
                tv_rw_detail_address.setText(obj.getAddress());
                tv_rw_detail_total.setText(obj.getTotal_price());


                if(obj.getIs_andy()==1){
                    ll_rw_details_bao1.setVisibility(View.VISIBLE);
                }else{
                    ll_rw_details_bao1.setVisibility(View.GONE);
                }
                if(obj.getIs_encase()==1){
                    ll_rw_details_bao2.setVisibility(View.VISIBLE);
                }else{
                    ll_rw_details_bao2.setVisibility(View.GONE);
                }
                if(obj.getIs_rechargeable()==1){
                    ll_rw_details_bao3.setVisibility(View.VISIBLE);
                }else{
                    ll_rw_details_bao3.setVisibility(View.GONE);
                }
                if(obj.getIs_getwater()==1){
                    ll_rw_details_bao4.setVisibility(View.VISIBLE);
                }else{
                    ll_rw_details_bao4.setVisibility(View.GONE);
                }
            }
        }));
    }

    @OnClick({R.id.tv_rw_detail_commit})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_rw_detail_commit:
                mDialog=new MyDialog.Builder(mContext);
                mDialog.setMessage("确认接单吗?");
                mDialog.setNegativeButton(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                mDialog.setPositiveButton(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        commitOrder();
                    }
                });
                mDialog.create().show();
            break;
        }
    }
    private void commitOrder() {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("userid",getUserId());
        map.put("order_no",orderNo);
        map.put("sign", GetSign.getSign(map));
        addSubscription(ApiRequest.getAddOrder(map).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                showMsg(obj.getMsg());
                setResult(RESULT_OK);
                finish();
            }
        }));
    }

    @Override
    public void again() {
        initData();
    }



}
