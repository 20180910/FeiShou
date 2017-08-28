package com.zhizhong.feishou;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by administartor on 2017/8/28.
 */

public abstract class MCallBack<T> implements Callback<T> {
    public abstract void onSuccess(T obj);
    public void onError(Throwable e){
    }
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        onSuccess(response.body());
    }
    @Override
    public void onFailure(Call<T> call, Throwable e) {
        onError(e);
    }
}
