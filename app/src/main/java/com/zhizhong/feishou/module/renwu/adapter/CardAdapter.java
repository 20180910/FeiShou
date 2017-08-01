package com.zhizhong.feishou.module.renwu.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.haitaoit.yinya.R;
import com.haitaoit.yinya.module.credit.Constant;
import com.haitaoit.yinya.module.credit.activity.CreditCardDetailsActivity;
import com.haitaoit.yinya.module.credit.network.response.XinYongObj;

/**
 * Created by Administrator on 2017/7/25.
 */

public class CardAdapter extends LoadMoreAdapter<XinYongObj.DyredcardslistBean> {
    public CardAdapter(Context mContext, int layoutId, int pageSize) {
        super(mContext, layoutId, pageSize);
    }
    @Override
    public void bindData(LoadMoreViewHolder holder, int i, XinYongObj.DyredcardslistBean item) {
        ImageView imageView = holder.getImageView(R.id.iv_xinyong_card);
        Glide.with(mContext).load(item.getImg_url()).into(imageView);
        holder.setText(R.id.tv_card_title,item.getTitle())
                .setText(R.id.tv_card_second_title,item.getZhaiyao());
        holder.itemView.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                Intent intent = new Intent(mContext, CreditCardDetailsActivity.class);
                intent.putExtra(Constant.IParam.cardId,item.getId()+"");
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation((Activity)mContext,
                                    Pair.create(holder.getView(R.id.iv_xinyong_card),"content")
                            );
                    mContext.startActivity(intent, options.toBundle());
                }else{
                    mContext.startActivity(intent);
                }
            }
        });
    }


}
