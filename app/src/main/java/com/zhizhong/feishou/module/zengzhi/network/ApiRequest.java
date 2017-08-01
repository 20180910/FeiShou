package com.zhizhong.feishou.module.zengzhi.network;

import com.github.retrofitutil.NetWorkManager;
import com.haitaoit.yinya.tools.RxResult;

import java.util.Map;

import rx.Observable;

/**
 * Created by Administrator on 2017/6/28.
 */

public class ApiRequest {
    //返回对象-rxjava无缓存
    private static IRequest getCommonClient(){
        return NetWorkManager.getCommonClient().create(IRequest.class);
    }
    //返回对象无缓存
    private static IRequest getGeneralClient(){
        return NetWorkManager.getGeneralClient().create(IRequest.class);
    }
    //返回String无缓存
    private static IRequest getGeneralStringClient(){
        return NetWorkManager.getGeneralStringClient().create(IRequest.class);
    }


    public static Observable getDaiKuanOrder(){
        return getCommonClient().getDaiKuanOrder().compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }

    public static Observable getDaiKuanList(Map map){
        return getCommonClient().getDaiKuanList(map).compose(RxResult.appSchedulers());
    }

    public static Observable getDaiKuanDetails(Map map){
        return getCommonClient().getDaiKuanDetails(map).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }

    public static Observable addCollect(Map map){
        return getCommonClient().addCollect(map).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }

    public static Observable deleteCollect(Map map){
        return getCommonClient().deleteCollect(map).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }

}
