package com.zhizhong.feishou.module.my.network;

import com.github.retrofitutil.NetWorkManager;
import com.zhizhong.feishou.module.my.network.request.HomeworkCityItem;
import com.zhizhong.feishou.module.my.network.request.UploadImgItem;
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

    public static Observable register(Map map){
        return getCommonClient().register(map).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable getMsgCode(Map map){
        return getCommonClient().getMsgCode(map).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable login(Map map){
        return getCommonClient().login(map).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable getInfo(String userId,String sign){
        return getCommonClient().getInfo(userId,sign).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable setNewPassword(Map map){
        return getCommonClient().setNewPassword(map).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable resetPassword(Map map){
        return getCommonClient().resetPassword(map).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable uploadImg(String rnd,String sign, UploadImgItem imgItem){
        return getCommonClient().uploadImg(rnd,sign,imgItem).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable updateInfo(Map map){
        return getCommonClient().updateInfo(map).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable getVIPLevel(String userId,String sign){
        return getCommonClient().getVIPLevel(userId,sign).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable getMyWallet(String userId,String sign){
        return getCommonClient().getMyWallet(userId,sign).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable getWalletDetailsList(Map map){
        return getCommonClient().getWalletDetailsList(map).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable getMyToolList(Map map){
        return getCommonClient().getMyToolList(map).compose(RxResult.appSchedulers()).compose(RxResult.listResult());
    }
    public static Observable deleteTool(String id,String sign){
        return getCommonClient().deleteTool(id,sign).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable addTool(Map map){
        return getCommonClient().addTool(map).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable getHomeworkList(String userId,String sign){
        return getCommonClient().getHomeworkList(userId, sign).compose(RxResult.appSchedulers()).compose(RxResult.listResult());
    }
    public static Observable deleteHomework(String id,String sign){
        return getCommonClient().deleteHomework(id, sign).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable addHomework(String userId, String sign, HomeworkCityItem item){
        return getCommonClient().addHomework(userId, sign,item).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable getProvince(String rnd, String sign){
        return getCommonClient().getProvince(rnd, sign).compose(RxResult.appSchedulers()).compose(RxResult.listResult());
    }
    public static Observable getAllCity(String rnd,String sign){
        return getCommonClient().getAllCity(rnd, sign).compose(RxResult.appSchedulers()).compose(RxResult.listResult());
    }
    public static Observable getCityForProvince(String rnd,String sign){
        return getCommonClient().getCityForProvince(rnd, sign).compose(RxResult.appSchedulers()).compose(RxResult.listResult());
    }

}
