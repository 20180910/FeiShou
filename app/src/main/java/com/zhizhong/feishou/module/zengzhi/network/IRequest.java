package com.zhizhong.feishou.module.zengzhi.network;

import com.haitaoit.yinya.base.BaseObj;
import com.haitaoit.yinya.base.ResponseObj;
import com.haitaoit.yinya.module.daikuan.network.response.DaiKuanDetailsObj;
import com.haitaoit.yinya.module.daikuan.network.response.DaiKuanListObj;
import com.haitaoit.yinya.module.daikuan.network.response.DaiKuanOrderObj;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Administrator on 2017/6/28.
 */

public interface IRequest {
    //获取贷款列表排序条件
    @GET("api/Goods/GetGoodsCategoryList")
    Observable<ResponseObj<DaiKuanOrderObj>>getDaiKuanOrder();

    //获取贷款列表数据
    @GET("api/Goods/GetGoodsList")
    Observable<ResponseObj<List<DaiKuanListObj>>> getDaiKuanList(@QueryMap Map<String, String> map);

    //查询贷款专区
    @GET("api/Goods/GetGoodsDetail")
    Observable<ResponseObj<DaiKuanDetailsObj>> getDaiKuanDetails(@QueryMap Map<String, String> map);

    //添加收藏
    @GET("api/User/GetCollectAdd")
    Observable<ResponseObj<BaseObj>> addCollect(@QueryMap Map<String, String> map);

    //取消收藏
    @GET("api/User/GetCollectDelete")
    Observable<ResponseObj<BaseObj>> deleteCollect(@QueryMap Map<String, String> map);

}
