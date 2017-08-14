package com.zhizhong.feishou.module.renwu.network;

import com.github.retrofitutil.NetWorkManager;
import com.zhizhong.feishou.tools.RxResult;

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

    public static Observable getProductList(Map map){
        return getCommonClient().getProductList(map).compose(RxResult.appSchedulers()).compose(RxResult.listResult());
    }
    public static Observable getNZW(String rnd,String sign){
        return getCommonClient().getNZW(rnd, sign).compose(RxResult.appSchedulers()).compose(RxResult.listResult());
    }
    public static Observable getDQ(String userId,String sign){
        return getCommonClient().getDQ(userId, sign).compose(RxResult.appSchedulers()).compose(RxResult.listResult());
    }

}
