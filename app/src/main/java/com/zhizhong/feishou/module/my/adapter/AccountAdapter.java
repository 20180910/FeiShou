package com.zhizhong.feishou.module.my.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.zhizhong.feishou.R;
import com.zhizhong.feishou.module.my.network.response.AccountObj;

/**
 * Created by administartor on 2017/8/2.
 */

public class AccountAdapter extends LoadMoreAdapter<AccountObj> {
    public AccountAdapter(Context mContext, int layoutId, int pageSize) {
        super(mContext, layoutId, pageSize);
    }
    @Override
    public void bindData(LoadMoreViewHolder holder, int i, AccountObj bean) {
        holder.setText(R.id.tv_account_account,bean.getBank_card())
                .setText(R.id.tv_account_bank,bean.getBank_name());
        ImageView imageView = holder.getImageView(R.id.iv_account_icon);
        Glide.with(mContext).load(bean.getBank_image()).error(R.color.c_press).into(imageView);
        ImageView imageFlag = holder.getImageView(R.id.iv_account_flag);
        if(bean.getIs_default()==1){
            imageFlag.setVisibility(View.VISIBLE);
        }else{
            imageFlag.setVisibility(View.INVISIBLE);
        }
    }

}
