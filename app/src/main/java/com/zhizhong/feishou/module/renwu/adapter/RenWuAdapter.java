package com.zhizhong.feishou.module.renwu.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.zhizhong.feishou.R;
import com.zhizhong.feishou.module.renwu.network.response.ZuoWuObj;

/**
 * Created by administartor on 2017/8/2.
 */

public class RenWuAdapter extends LoadMoreAdapter<ZuoWuObj> {
    public RenWuAdapter(Context mContext, int layoutId, int pageSize) {
        super(mContext, layoutId, pageSize);
    }
    @Override
    public void bindData(LoadMoreViewHolder holder, int i, ZuoWuObj bean) {
        ImageView imageView = holder.getImageView(R.id.iv_rw_img);
        Glide.with(mContext).load(bean.getImage_url()).error(R.color.c_press).into(imageView);
        TextView tv_rw_money = holder.getTextView(R.id.tv_rw_money);
        String totalPrice = bean.getTotal_price() + "元";
        tv_rw_money.setText(totalPrice);
        if(totalPrice.length()>=9){
            tv_rw_money.setTextSize(10);
        }else if(totalPrice.length()>7){
            tv_rw_money.setTextSize(12);
        }else{
            tv_rw_money.setTextSize(14);
        }
        holder.setText(R.id.tv_rw_address,bean.getAddress())
                .setText(R.id.tv_rw_title,bean.getTitle())
                .setText(R.id.tv_rw_dk,bean.getCondition())
                .setText(R.id.tv_rw_time,bean.getRequest_time());
        LinearLayout ll_rw_bao1 = (LinearLayout) holder.getView(R.id.ll_rw_bao1);
        LinearLayout ll_rw_bao2 = (LinearLayout) holder.getView(R.id.ll_rw_bao2);
        LinearLayout ll_rw_bao3 = (LinearLayout) holder.getView(R.id.ll_rw_bao3);
        LinearLayout ll_rw_bao4 = (LinearLayout) holder.getView(R.id.ll_rw_bao4);
        if(bean.getIs_andy()==1){
            ll_rw_bao1.setVisibility(View.VISIBLE);
        }else{
            ll_rw_bao1.setVisibility(View.GONE);
        }
        if(bean.getIs_encase()==1){
            ll_rw_bao2.setVisibility(View.VISIBLE);
        }else{
            ll_rw_bao2.setVisibility(View.GONE);
        }
        if(bean.getIs_rechargeable()==1){
            ll_rw_bao3.setVisibility(View.VISIBLE);
        }else{
            ll_rw_bao3.setVisibility(View.GONE);
        }
        if(bean.getIs_getwater()==1){
            ll_rw_bao4.setVisibility(View.VISIBLE);
        }else{
            ll_rw_bao4.setVisibility(View.GONE);
        }
    }
}
