package com.zhizhong.feishou.module.my.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.baseclass.rx.RxBus;
import com.github.baseclass.view.MyDialog;
import com.zhizhong.feishou.R;
import com.zhizhong.feishou.module.my.Constant;
import com.zhizhong.feishou.module.my.event.OrderEvent;
import com.zhizhong.feishou.module.my.network.response.OrderObj;

/**
 * Created by administartor on 2017/8/2.
 */

public class AllOrderAdapter extends LoadMoreAdapter<OrderObj> {
    public AllOrderAdapter(Context mContext, int layoutId, int pageSize) {
        super(mContext, layoutId, pageSize);
    }

    @Override
    public void bindData(LoadMoreViewHolder holder, int position, OrderObj bean) {
        TextView tv_order_jiedan = holder.getTextView(R.id.tv_order_jiedan);
        TextView tv_order_zhixing = holder.getTextView(R.id.tv_order_zhixing);
        TextView tv_order_tixing = holder.getTextView(R.id.tv_order_tixing);
        TextView tv_order_quxiao = holder.getTextView(R.id.tv_order_quxiao);
        TextView tv_order_jiesuan = holder.getTextView(R.id.tv_order_jiesuan);
        ImageView imageView = holder.getImageView(R.id.iv_order_img);
        Glide.with(mContext).load(bean.getImage_url()).error(R.color.c_press).into(imageView);
        TextView tv_order_type = holder.getTextView(R.id.tv_order_type);
        switch (bean.getOrder_status()+""){
            case Constant.daiJieDanOrder:
                tv_order_type.setText("待接单");
                tv_order_jiedan.setVisibility(View.VISIBLE);
                setGone(tv_order_zhixing);
                setGone(tv_order_tixing);
                setGone(tv_order_quxiao);
                setGone(tv_order_jiesuan);

                tv_order_jiedan.setOnClickListener(new MyOnClickListener() {
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
                                RxBus.getInstance().post(new OrderEvent(Constant.daiJieDanOrder,bean.getOrder_no()));
                            }
                        });
                        mDialog.create().show();
                    }
                });
            break;
            case Constant.daiZhiXingOrder:
                tv_order_type.setText("待执行");
                tv_order_zhixing.setVisibility(View.VISIBLE);

                setGone(tv_order_jiedan);
                setGone(tv_order_jiesuan);

                tv_order_zhixing.setOnClickListener(new MyOnClickListener() {
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
                                RxBus.getInstance().post(new OrderEvent(Constant.daiZhiXingOrder,bean.getOrder_no()));
                            }
                        });
                        mDialog.create().show();
                    }
                });

                tv_order_quxiao.setVisibility(View.VISIBLE);
                tv_order_quxiao.setOnClickListener(new MyOnClickListener() {
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
                                RxBus.getInstance().post(new OrderEvent(Constant.quXiaoOrder,bean.getOrder_no()));
                            }
                        });
                        mDialog.create().show();
                    }
                });

                tv_order_tixing.setVisibility(View.VISIBLE);
                tv_order_tixing.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        RxBus.getInstance().post(new OrderEvent(Constant.shiJianTiXing,bean.getOrder_no()));
                    }
                });
            break;
            case Constant.daiJieSuanOrder:
                tv_order_type.setText("待结算");
                tv_order_jiesuan.setVisibility(View.VISIBLE);

                setGone(tv_order_jiedan);
                setGone(tv_order_zhixing);
                setGone(tv_order_tixing);
                setGone(tv_order_quxiao);
                tv_order_jiesuan.setOnClickListener(new MyOnClickListener() {
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
                                RxBus.getInstance().post(new OrderEvent(Constant.daiJieSuanOrder,bean.getOrder_no()));
                            }
                        });
                        mDialog.create().show();

                    }
                });
            break;
            case Constant.yiWanChengOrder:
                tv_order_type.setText("已完成");

                setGone(tv_order_jiedan);
                setGone(tv_order_zhixing);
                setGone(tv_order_tixing);
                setGone(tv_order_quxiao);
                setGone(tv_order_jiesuan);
            break;
        }
        holder.setText(R.id.tv_order_no,bean.getOrder_no())
                .setText(R.id.tv_order_title,bean.getTitle())
                .setText(R.id.tv_order_address,bean.getAddress())
                .setText(R.id.tv_order_time,bean.getRequest_time())
                .setText(R.id.tv_order_dk,bean.getCondition())
                .setText(R.id.tv_order_money,bean.getTotal_price()+"元")
                .setText(R.id.tv_order_status_title,bean.getStatus_title());


    }

    private void setVisible(TextView textView){
        textView.setVisibility(View.VISIBLE);
    }
    private void setGone(TextView textView){
        textView.setVisibility(View.GONE);
    }
}
