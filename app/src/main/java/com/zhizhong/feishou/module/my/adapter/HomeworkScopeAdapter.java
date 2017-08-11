package com.zhizhong.feishou.module.my.adapter;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.github.androidtools.ToastUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.baseclass.view.Loading;
import com.github.baseclass.view.MyDialog;
import com.zhizhong.feishou.GetSign;
import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseObj;
import com.zhizhong.feishou.base.MySub;
import com.zhizhong.feishou.module.my.network.ApiRequest;
import com.zhizhong.feishou.module.my.network.response.HomeworkObj;

/**
 * Created by administartor on 2017/8/3.
 */

public class HomeworkScopeAdapter extends LoadMoreAdapter<HomeworkObj> {
    private boolean isDelete;
    public HomeworkScopeAdapter(Context mContext, int layoutId, int pageSize) {
        super(mContext, layoutId, pageSize);
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
        notifyDataSetChanged();
    }

    public boolean isDelete() {
        return isDelete;
    }

    @Override
    public void bindData(LoadMoreViewHolder holder, int position, HomeworkObj bean) {
        holder.setText(R.id.tv_homework_city,bean.getCity_name());
        FrameLayout deleteView = (FrameLayout) holder.getView(R.id.fl_homework_delete);
        if(isDelete){
            deleteView.setVisibility(View.VISIBLE);
            deleteView.setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
                    mDialog.setMessage("确认删除吗?")
                            .setNegativeButton((dialog, which) -> dialog.dismiss())
                            .setPositiveButton((dialog, which) -> {
                                dialog.dismiss();
                                delete(position,bean.getId());
                            }).create().show();

                }
            });
        }else{
            deleteView.setVisibility(View.INVISIBLE);
        }
    }

    private void delete(int position,int id){
        Loading.show(mContext);
        String sign = GetSign.getSign("or_id", id + "");
        ApiRequest.deleteHomework(id+"",sign).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                ToastUtils.showToast(mContext,obj.getMsg());
                getList().remove(position);
                notifyDataSetChanged();
            }
        });
    }

}
