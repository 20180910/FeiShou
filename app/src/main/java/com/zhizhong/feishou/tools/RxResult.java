package com.zhizhong.feishou.tools;

import com.github.rxjava.rxbus.RxUtils;
import com.zhizhong.feishou.base.BaseObj;
import com.zhizhong.feishou.base.ResponseObj;
import com.zhizhong.feishou.base.ServerException;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2017/7/20.
 */

public class RxResult extends RxUtils {
    public static <T extends BaseObj> Observable.Transformer<ResponseObj<T>, T> handleResult(){
        return apiResponse -> apiResponse.flatMap(
                new Func1<ResponseObj<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(ResponseObj<T> response) {
                        if (response==null){
                            return Observable.empty();
                        }else if (response.isSuccess()){
                            try {
                                return returnDataForMsg(response.getResponse(),response.getErrMsg());
                            }catch (ClassCastException e){
                                return returnData(response.getResponse());
                            }
                        }else{
                            return Observable.error(new ServerException(response.getErrMsg()+""));
                        }
                    }
                });
    }
    private static <T> Observable<T> returnData(final T result) {
        return Observable.create(subscriber -> {
            try {
                subscriber.onNext(result);
                subscriber.onCompleted();
            }catch (Exception e){
                subscriber.onError(e);
            }
        });
    }
    private static <T extends BaseObj> Observable<T> returnDataForMsg(final T result, final String msg) {
        return Observable.create(subscriber -> {
            try {
                if(result!=null){
                    result.setMsg(msg);
                    subscriber.onNext(result);
                }else{
                    T newResult = (T) new BaseObj();
                    newResult.setMsg(msg);
                    subscriber.onNext(newResult);
                }
                subscriber.onCompleted();
            }catch (Exception e){
                subscriber.onError(e);
            }
        });
    }

    public static <T> Observable.Transformer<ResponseObj<T>, T> handleListResult(){
        return apiResponse -> apiResponse.flatMap(
                new Func1<ResponseObj<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(ResponseObj<T> response) {
                        if (response==null){
                            return Observable.empty();
                        }else if (response.isSuccess()){
                            return returnData(response.getResponse());
                        }else{
                            return Observable.error(new ServerException(response.getErrMsg()+""));
                        }
                    }
                });
    }
}
