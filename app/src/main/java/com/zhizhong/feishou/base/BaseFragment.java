package com.zhizhong.feishou.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.github.androidtools.ClickUtils;
import com.github.androidtools.SPUtils;
import com.github.baseclass.fragment.IBaseFragment;
import com.github.baseclass.rx.RxBus;
import com.zhizhong.feishou.Config;
import com.zhizhong.feishou.GetSign;
import com.zhizhong.feishou.R;
import com.zhizhong.feishou.view.ProgressLayout;

import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 * Created by Administrator on 2017/7/13.
 */

public abstract class BaseFragment extends IBaseFragment implements View.OnClickListener,ProgressLayout.OnAgainInter{
    protected int pageNum=1;
    protected int pageSize=20;

    private boolean isFirstLoadData=true;
    private boolean isPrepared;
   protected PtrClassicFrameLayout pcfl;
    /************************************************/
    protected abstract int getContentView();
    protected abstract void initView();
    protected abstract void initData();
    protected abstract void onViewClick(View v);
    protected void initRxBus(){};
    protected Unbinder mUnBind;

    protected ProgressLayout pl_load;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getContentView(), container, false);
        mUnBind = ButterKnife.bind(this, view);
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(null!=view.findViewById(R.id.pcfl_refresh)){
            pcfl = (PtrClassicFrameLayout) view.findViewById(R.id.pcfl_refresh);
            pcfl.setLastUpdateTimeRelateObject(this);
        }
        if(null!=view.findViewById(R.id.pl_load)){
            pl_load = (ProgressLayout) view.findViewById(R.id.pl_load);
            pl_load.setInter(this);
        }
        initView();
        initRxBus();
        isPrepared=true;
        setUserVisibleHint(true);
    }
    public void showProgress(){
        if (pl_load != null) {
            pl_load.showProgress();
        }
    }
    public void showContent(){
        if (pl_load != null) {
            pl_load.showContent();
        }
    }
    public void showErrorText(){
        if (pl_load != null) {
            pl_load.showErrorText();
        }
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isFirstLoadData&&isPrepared&&getUserVisibleHint()&&isVisibleToUser){
            initData();
            isFirstLoadData=false;
        }
    }
    protected String getSStr(View view){
        if(view instanceof TextView){
            return ((TextView)view).getText().toString();
        } else if (view instanceof EditText) {
            return ((EditText)view).getText().toString();
        }else{
            return null;
        }
    }
    @Override
    public void onClick(View v) {
        if(!ClickUtils.isFastClick(v)){
            onViewClick(v);
        }
    }
    public void onDestroy() {
        super.onDestroy();
        mUnBind.unbind();
        RxBus.getInstance().removeAllStickyEvents();
    }

    protected String getUserId(){
        return SPUtils.getPrefString(mContext, Config.user_id,null);
    }
    protected String getSign(){
        return getSign("user_id",getUserId());
    }
    protected String getSign(String key,String value){
        return GetSign.getSign(key,value);
    }

    protected boolean isEmpty(List list){
        if(list==null||list.size()==0){
            return true;
        }
        return false;
    }
    protected boolean notEmpty(List list){
        if(list==null||list.size()==0){
            return false;
        }
        return true;
    }
    protected String getRnd(){
        Random random = new Random();
        int rnd = random.nextInt(9000) + 1000;
        return rnd+"";
    }
}
