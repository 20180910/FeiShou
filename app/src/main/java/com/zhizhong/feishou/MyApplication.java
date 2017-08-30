package com.zhizhong.feishou;


import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.github.retrofitutil.NetWorkManager;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by administartor on 2017/8/8.
 */

public class MyApplication extends Application  {
    @Override
    public void onCreate() {
        super.onCreate();
        NetWorkManager.getInstance(getApplicationContext(),"http://121.40.186.118:5009/",BuildConfig.DEBUG).complete();
        SDKInitializer.initialize(getApplicationContext());
        JPushInterface.setDebugMode(BuildConfig.DEBUG); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
    }
}
