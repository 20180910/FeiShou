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
    public static Observable getTXXieYi(String rnd,String sign){
        return getCommonClient().getTXXieYi(rnd,sign).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable getRegisterXieYi(String rnd,String sign){
        return getCommonClient().getRegisterXieYi(rnd,sign).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable getAuthXieYi(String rnd,String sign){
        return getCommonClient().getAuthXieYi(rnd,sign).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
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
    public static Observable getBankList(String rnd,String sign){
        return getCommonClient().getBankList(rnd, sign).compose(RxResult.appSchedulers()).compose(RxResult.listResult());
    }
    public static Observable addBank(Map map){
        return getCommonClient().addBank(map).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable getAccount(String userId,String sign){
        return getCommonClient().getAccount(userId, sign).compose(RxResult.appSchedulers()).compose(RxResult.listResult());
    }
    public static Observable getDefaultAccount(String userId,String sign){
        return getCommonClient().getDefaultAccount(userId, sign).compose(RxResult.appSchedulers()).compose(RxResult.listResult());
    }
    public static Observable setDefaultAccount(Map map){
        return getCommonClient().setDefaultAccount(map).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable getAllMoney(String userId,String sign){
        return getCommonClient().getAllMoney(userId, sign).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable tiXian(Map map){
        return getCommonClient().tiXian(map).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable authCommit(Map map){
        return getCommonClient().authCommit(map).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable getAllOrder(Map map){
        return getCommonClient().getAllOrder(map).compose(RxResult.appSchedulers()).compose(RxResult.listResult());
    }
    public static Observable jieDan(String orderNo,String sign){
        return getCommonClient().jieDan(orderNo,sign).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable zhiXing(String orderNo,String sign){
        return getCommonClient().zhiXing(orderNo,sign).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable complete(String orderNo,String sign){
        return getCommonClient().complete(orderNo,sign).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable quXiao(String orderNo,String sign){
        return getCommonClient().quXiao(orderNo,sign).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable tiXing(String orderNo,String sign){
        return getCommonClient().tiXing(orderNo,sign).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable getOrderDetail(String orderNo,String sign){
        return getCommonClient().getOrderDetail(orderNo,sign).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }

}
