package com.zhizhong.feishou.module.zengzhi.network;

import com.zhizhong.feishou.base.ResponseObj;
import com.zhizhong.feishou.module.zengzhi.network.response.WebZengZhiContentObj;
import com.zhizhong.feishou.module.zengzhi.network.response.ZengZhiObj;
import com.zhizhong.feishou.module.zengzhi.network.response.ZhuanYePeiXunObj;

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

    //增值服务列表-设备购买
    @GET("api/FlyMember/GetAppreciationService")
    Observable<ResponseObj<List<ZengZhiObj>>> getZengZhiList(@QueryMap Map<String,String> map);

    //增值服务列表-专业培训
    @GET("api/FlyMember/GetProfessionalTraining")
    Observable<ResponseObj<List<ZhuanYePeiXunObj>>> getZhuanYePeiXunList(@QueryMap Map<String,String> map);

    //增值服务列表-设备购买-详情
    @GET("api/FlyMember/GetAppreciationServiceDetails")
    Observable<ResponseObj<WebZengZhiContentObj>> getSheBeiDetail(@QueryMap Map<String,String> map);

    //增值服务列表-专业培训-详情
    @GET("api/FlyMember/GetProfessionalTrainingDetails")
    Observable<ResponseObj<WebZengZhiContentObj>> getPeiXunDetail(@QueryMap Map<String,String> map);
}
