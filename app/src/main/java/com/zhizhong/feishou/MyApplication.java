package com.zhizhong.feishou;


import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.github.retrofitutil.NetWorkManager;

/**
 * Created by administartor on 2017/8/8.
 */

public class MyApplication extends Application  {
    @Override
    public void onCreate() {
        super.onCreate();
        NetWorkManager.getInstance(getApplicationContext(),"http://121.40.186.118:5009/",BuildConfig.DEBUG).complete();
        SDKInitializer.initialize(getApplicationContext());

    }
}
