package com.zhizhong.feishou.module.renwu.network;


import com.zhizhong.feishou.base.BaseObj;
import com.zhizhong.feishou.base.ResponseObj;
import com.zhizhong.feishou.module.renwu.network.response.NZWTypeObj;
import com.zhizhong.feishou.module.renwu.network.response.RenWuDetailObj;
import com.zhizhong.feishou.module.renwu.network.response.ZuoWuObj;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Administrator on 2017/6/28.
 */

public interface IRequest {
    // @QueryMap Map<String,String> map
    // @Query("user_id") String user_id,@Query("sign") String sign

    //任务大厅列表
    @GET("api/FlyMember/GetProductList")
    Observable<ResponseObj<List<ZuoWuObj>>> getProductList(@QueryMap Map<String, String> map);

    //获取农作物
    @GET("api/FlyMember/GetProductCrops")
    Observable<ResponseObj<List<NZWTypeObj>>> getNZW(@Query("rnd") String rnd, @Query("sign") String sign);

    //获取地区筛选条件
    @GET("api/FlyMember/GetProductCropsRegion")
    Observable<ResponseObj<List<NZWTypeObj>>> getDQ(@Query("user_id") String user_id, @Query("sign") String sign);

    //任务大厅-详情
    @GET("api/FlyMember/GetProductMore")
    Observable<ResponseObj<RenWuDetailObj>> getRenWuDetails(@Query("product_id") String user_id, @Query("sign") String sign);

    //任务大厅-详情-确认订单
    @GET("api/FlyMember/GetAddOrder")
    Observable<ResponseObj<BaseObj>> getAddOrder(@QueryMap Map<String, String> map);

}
