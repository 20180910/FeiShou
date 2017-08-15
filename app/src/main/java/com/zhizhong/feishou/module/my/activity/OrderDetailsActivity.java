package com.zhizhong.feishou.module.my.activity;

import android.content.DialogInterface;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.view.MyDialog;
import com.github.customview.MyTextView;
import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseActivity;
import com.zhizhong.feishou.base.BaseObj;
import com.zhizhong.feishou.base.MySub;
import com.zhizhong.feishou.module.my.Constant;
import com.zhizhong.feishou.module.my.network.ApiRequest;
import com.zhizhong.feishou.module.my.network.response.OrderDetailObj;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/8/3.
 */

public class OrderDetailsActivity extends BaseActivity {


    @BindView(R.id.tv_order_detail_type)
    TextView tv_order_detail_type;

    @BindView(R.id.tv_order_detail_status_title)
    TextView tv_order_detail_status_title;

    @BindView(R.id.tv_order_detail_no)
    TextView tv_order_detail_no;
    @BindView(R.id.tv_order_detail_dq)
    TextView tv_order_detail_dq;
    @BindView(R.id.tv_order_detail_nzw)
    TextView tv_order_detail_nzw;
    @BindView(R.id.tv_order_detail_dj)
    TextView tv_order_detail_dj;
    @BindView(R.id.tv_order_detail_ms)
    TextView tv_order_detail_ms;
    @BindView(R.id.tv_order_detail_qwsj)
    TextView tv_order_detail_qwsj;
    @BindView(R.id.tv_order_detail_zccs)
    TextView tv_order_detail_zccs;
    @BindView(R.id.tv_order_detail_zcsm)
    TextView tv_order_detail_zcsm;
    @BindView(R.id.tv_order_detail_dk)
    TextView tv_order_detail_dk;
    @BindView(R.id.tv_order_detail_zaw)
    TextView tv_order_detail_zaw;
    @BindView(R.id.tv_order_detail_beizhu)
    TextView tv_order_detail_beizhu;
    @BindView(R.id.ll_order_detail_bao1)
    LinearLayout ll_order_detail_bao1;
    @BindView(R.id.ll_order_detail_bao2)
    LinearLayout ll_order_detail_bao2;
    @BindView(R.id.ll_order_detail_bao3)
    LinearLayout ll_order_detail_bao3;
    @BindView(R.id.ll_order_detail_bao4)
    LinearLayout ll_order_detail_bao4;
    @BindView(R.id.tv_order_detail_address)
    TextView tv_order_detail_address;
    @BindView(R.id.tv_order_detail_jiedan)
    MyTextView tv_order_detail_jiedan;
    @BindView(R.id.tv_order_detail_zhixing)
    MyTextView tv_order_detail_zhixing;
    @BindView(R.id.tv_order_detail_tixing)
    MyTextView tv_order_detail_tixing;
    @BindView(R.id.tv_order_detail_quxiao)
    MyTextView tv_order_detail_quxiao;
    @BindView(R.id.tv_order_detail_jiesuan)
    MyTextView tv_order_detail_jiesuan;
    @BindView(R.id.ll_order_detail_bottom)
    LinearLayout ll_order_detail_bottom;
    private String orderNo;

    @Override
    public void again() {
        initData();
    }

    @Override
    protected int getContentView() {
        setAppTitle("订单详情");
        return R.layout.act_order_details;
    }

    @Override
    protected void initView() {
        orderNo = getIntent().getStringExtra(Constant.IParam.orderNo);
    }

    @Override
    protected void initData() {
        showProgress();
        getData();
    }

    private void getData() {
        addSubscription(ApiRequest.getOrderDetail(orderNo,getSign("order_no",orderNo)).subscribe(new MySub<OrderDetailObj>(mContext,pl_load) {


            @Override
            public void onMyNext(OrderDetailObj obj) {
                tv_order_detail_no.setText(obj.getOrder_no());
                tv_order_detail_dq.setText(obj.getRegion());
                tv_order_detail_nzw.setText(obj.getCrops());
                tv_order_detail_dj.setText(obj.getPrice());
                tv_order_detail_ms.setText(obj.getArea());
                tv_order_detail_zccs.setText(obj.getTransitions_number());
                tv_order_detail_zcsm.setText(obj.getTransitions_instructions());
                tv_order_detail_qwsj.setText(obj.getRequest_time());
                tv_order_detail_dk.setText(obj.getCondition());
                tv_order_detail_zaw.setText(obj.getObstacles());
                tv_order_detail_beizhu.setText(obj.getRemark());
                tv_order_detail_address.setText(obj.getAddress());
                tv_order_detail_status_title.setText(obj.getStatus_title());

                switch (obj.getOrder_status()+""){
                    case Constant.daiJieDanOrder:
                        tv_order_detail_type.setText("订单待接单");
                        tv_order_detail_jiedan.setVisibility(View.VISIBLE);
                        tv_order_detail_jiedan.setOnClickListener(new MyOnClickListener() {
                            @Override
                            protected void onNoDoubleClick(View view) {
                                MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
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
                                        jieDan(orderNo);
                                    }
                                });
                                mDialog.create().show();

                            }
                        });
                    break;
                    case Constant.daiZhiXingOrder:
                        tv_order_detail_type.setText("订单待执行");
                        tv_order_detail_zhixing.setVisibility(View.VISIBLE);
                        tv_order_detail_zhixing.setOnClickListener(new MyOnClickListener() {
                            @Override
                            protected void onNoDoubleClick(View view) {
                                MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
                                mDialog.setMessage("确认执行吗?");
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
                                        zhiXing(orderNo);
                                    }
                                });
                                mDialog.create().show();
                            }
                        });
                        tv_order_detail_tixing.setVisibility(View.VISIBLE);
                        tv_order_detail_tixing.setOnClickListener(new MyOnClickListener() {
                            @Override
                            protected void onNoDoubleClick(View view) {
                                tiXing(orderNo);
                            }
                        });
                        tv_order_detail_quxiao.setVisibility(View.VISIBLE);
                        tv_order_detail_quxiao.setOnClickListener(new MyOnClickListener() {
                            @Override
                            protected void onNoDoubleClick(View view) {
                                MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
                                mDialog.setMessage("确认取消吗?");
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
                                        quXiao(orderNo);
                                    }
                                });
                                mDialog.create().show();
                            }
                        });
                    break;
                    case Constant.daiJieSuanOrder:
                        tv_order_detail_type.setText("订单待结算");
                        tv_order_detail_jiesuan.setVisibility(View.VISIBLE);
                        tv_order_detail_jiesuan.setOnClickListener(new MyOnClickListener() {
                            @Override
                            protected void onNoDoubleClick(View view) {
                                MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
                                mDialog.setMessage("确认结算吗?");
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
                                        jieSuan(orderNo);
                                    }
                                });
                                mDialog.create().show();
                            }
                        });
                    break;
                    case Constant.yiWanChengOrder:
                        tv_order_detail_type.setText("订单已完成");
                        ll_order_detail_bottom.setVisibility(View.GONE);
                    break;
                    case Constant.yiQuXiaoOrder:
                        tv_order_detail_type.setText("订单已取消");
                        ll_order_detail_bottom.setVisibility(View.GONE);
                    break;
                }

                if(obj.getIs_andy()==1){
                    ll_order_detail_bao1.setVisibility(View.VISIBLE);
                }else{
                    ll_order_detail_bao1.setVisibility(View.GONE);
                }
                if(obj.getIs_encase()==1){
                    ll_order_detail_bao2.setVisibility(View.VISIBLE);
                }else{
                    ll_order_detail_bao2.setVisibility(View.GONE);
                }
                if(obj.getIs_rechargeable()==1){
                    ll_order_detail_bao3.setVisibility(View.VISIBLE);
                }else{
                    ll_order_detail_bao3.setVisibility(View.GONE);
                }
                if(obj.getIs_getwater()==1){
                    ll_order_detail_bao4.setVisibility(View.VISIBLE);
                }else{
                    ll_order_detail_bao4.setVisibility(View.GONE);
                }

            }
        }));
    }


    @OnClick({R.id.tv_order_detail_jiedan, R.id.tv_order_detail_zhixing, R.id.tv_order_detail_tixing, R.id.tv_order_detail_quxiao, R.id.tv_order_detail_jiesuan})
    protected void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_order_detail_jiedan:
                break;
            case R.id.tv_order_detail_zhixing:
                break;
            case R.id.tv_order_detail_tixing:
                break;
            case R.id.tv_order_detail_quxiao:
                break;
            case R.id.tv_order_detail_jiesuan:
                break;
        }
    }

    private void tiXing(String orderNo) {
        showLoading();
        addSubscription(ApiRequest.tiXing(orderNo,getSign("order_no",orderNo)).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                showMsg(obj.getMsg());
            }
        }));

    }

    private void quXiao(String orderNo) {
        showLoading();
        addSubscription(ApiRequest.quXiao(orderNo,getSign("order_no",orderNo)).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                showMsg(obj.getMsg());
                setResult(RESULT_OK);
                finish();
            }
        }));
    }

    private void jieSuan(String orderNo) {
        showLoading();
        addSubscription(ApiRequest.complete(orderNo,getSign("order_no",orderNo)).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                showMsg(obj.getMsg());
                setResult(RESULT_OK);
                finish();
            }
        }));
    }

    private void zhiXing(String orderNo) {
        showLoading();
        addSubscription(ApiRequest.zhiXing(orderNo,getSign("order_no",orderNo)).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                showMsg(obj.getMsg());
                setResult(RESULT_OK);
                finish();
            }
        }));
    }

    private void jieDan(String orderNo) {
        showLoading();
        addSubscription(ApiRequest.jieDan(orderNo,getSign("order_no",orderNo)).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                showMsg(obj.getMsg());
                setResult(RESULT_OK);
                finish();
            }
        }));
    }
}
