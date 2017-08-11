package com.zhizhong.feishou.module.my.network;


import com.zhizhong.feishou.base.BaseObj;
import com.zhizhong.feishou.base.ResponseObj;
import com.zhizhong.feishou.module.my.network.request.UploadImgItem;
import com.zhizhong.feishou.module.my.network.response.HomeworkObj;
import com.zhizhong.feishou.module.my.network.response.InfoObj;
import com.zhizhong.feishou.module.my.network.response.LevelObj;
import com.zhizhong.feishou.module.my.network.response.LoginObj;
import com.zhizhong.feishou.module.my.network.response.MyToolObj;
import com.zhizhong.feishou.module.my.network.response.WalletObj;

import java.util.List;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Administrator on 2017/6/28.
 */

public interface IRequest {
    // @QueryMap Map<String,String> map
    // @Query("user_id") String user_id,@Query("sign") String sign
    //用户注册
    @GET("api/FlyMember/GetMemberRegister")
    Observable<ResponseObj<BaseObj>> register(@QueryMap Map<String,String> map);

    //用户登录
    @GET("api/FlyMember/GetUserLogin")
    Observable<ResponseObj<LoginObj>> login(@QueryMap Map<String,String> map);

    //修改密码
    @GET("api/FlyMember/GetSetNewPassword")
    Observable<ResponseObj<BaseObj>> setNewPassword(@QueryMap Map<String,String> map);

    //忘记密码
    @GET("api/FlyMember/GetSetPassword")
    Observable<ResponseObj<BaseObj>> resetPassword(@QueryMap Map<String,String> map);

    //获取验证码
    @GET("api/Lib/GetSMSCode")
    Observable<ResponseObj<BaseObj>> getMsgCode(@QueryMap Map<String,String> map);

    //获取我的资料
    @GET("api/FlyMember/GetMemberInfo")
    Observable<ResponseObj<InfoObj>> getInfo(@Query("user_id") String user_id, @Query("sign") String sign);

    //用户资料修改
    @GET("api/FlyMember/GetSetMemberInfo")
    Observable<ResponseObj<BaseObj>> updateInfo(@QueryMap Map<String,String> map);

    //获取实名验证信息
    @GET("api/FlyMember/GetMemberAuthentication")
    Observable<ResponseObj<BaseObj>> getMemberAuthentication(@Query("user_id") String user_id,@Query("sign") String sign);

    //设置实名验证信息
    @GET("api/FlyMember/GetSetMemberAuthentication")
    Observable<ResponseObj<BaseObj>> setMemberAuthentication(@QueryMap Map<String,String> map);

    //上传图片
    @POST("api/Lib/PostUploadFileBase64")
    Observable<ResponseObj<BaseObj>> uploadImg(@Query("rnd") String rnd, @Query("sign") String sign, @Body UploadImgItem item);

    //会员等级
    @GET("api/FlyMember/GetMemberLevel")
    Observable<ResponseObj<LevelObj>> getVIPLevel(@Query("user_id") String user_id, @Query("sign") String sign);

    //我的钱包
    @GET("api/FlyMember/GetMemberMoneyBag")
    Observable<ResponseObj<WalletObj>> getMyWallet(@Query("user_id") String user_id, @Query("sign") String sign);

    //收支明细
    @GET("api/FlyMember/GetMemberMoneyLog")
    Observable<ResponseObj<WalletObj>> getWalletDetailsList(@QueryMap Map<String,String> map);

    //获取我的工具列表
    @GET("api/FlyMember/GetMyToolList")
    Observable<ResponseObj<List<MyToolObj>>> getMyToolList(@QueryMap Map<String,String> map);

    //删除工具
    @GET("api/FlyMember/GetDelTool")
    Observable<ResponseObj<BaseObj>> deleteTool(@Query("tool_id") String tool_id, @Query("sign") String sign);

    //添加工具
    @GET("api/FlyMember/GetMyToolList")
    Observable<ResponseObj<BaseObj>> addTool(@QueryMap Map<String,String> map);

    //作业范围列表
    @GET("api/FlyMember/GetOperatingRangeList")
    Observable<ResponseObj<List<HomeworkObj>>> getHomeworkList(@Query("user_id") String user_id, @Query("sign") String sign);

    //删除作业范围
    @GET("api/FlyMember/GetDelOperatingRange")
    Observable<ResponseObj<BaseObj>> deleteHomework(@Query("or_id") String or_id, @Query("sign") String sign);

}
