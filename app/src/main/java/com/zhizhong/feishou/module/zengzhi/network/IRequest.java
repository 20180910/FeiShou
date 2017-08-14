package com.zhizhong.feishou.module.zengzhi.network;

import com.zhizhong.feishou.base.ResponseObj;
import com.zhizhong.feishou.module.zengzhi.network.response.ZengZhiObj;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Administrator on 2017/6/28.
 */

public interface IRequest {
    // @QueryMap Map<String,String> map
    // @Query("user_id") String user_id,@Query("sign") String sign

    //增值服务列表
    @GET("api/FlyMember/GetAppreciationService")
    Observable<ResponseObj<List<ZengZhiObj>>> getZengZhiList(@QueryMap Map<String,String> map);
}
