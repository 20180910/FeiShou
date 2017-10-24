package com.zhizhong.feishou.module.my.network;


import com.zhizhong.feishou.base.BaseObj;
import com.zhizhong.feishou.base.ResponseObj;
import com.zhizhong.feishou.module.my.network.request.HomeworkCityItem;
import com.zhizhong.feishou.module.my.network.request.UploadImgItem;
import com.zhizhong.feishou.module.my.network.response.AccountObj;
import com.zhizhong.feishou.module.my.network.response.AuthObj;
import com.zhizhong.feishou.module.my.network.response.BankObj;
import com.zhizhong.feishou.module.my.network.response.CityObj;
import com.zhizhong.feishou.module.my.network.response.HomeworkObj;
import com.zhizhong.feishou.module.my.network.response.InfoObj;
import com.zhizhong.feishou.module.my.network.response.LevelObj;
import com.zhizhong.feishou.module.my.network.response.LoginObj;
import com.zhizhong.feishou.module.my.network.response.MyToolObj;
import com.zhizhong.feishou.module.my.network.response.OrderDetailObj;
import com.zhizhong.feishou.module.my.network.response.OrderObj;
import com.zhizhong.feishou.module.my.network.response.ProvinceObj;
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
    //获取资金快捷收付及授权协议
    @GET("api/FlyMember/GetQuickCashPaymentLicensingAgreement")
    Observable<ResponseObj<BaseObj>> getTXXieYi(@Query("rnd") String rnd, @Query("sign") String sign);

    //注册授权协议
    @GET("api/FlyMember/GetFlyAgreement")
    Observable<ResponseObj<BaseObj>> getRegisterXieYi(@Query("rnd") String rnd, @Query("sign") String sign);

    //实名认证协议
    @GET("api/FlyMember/GetRealnameAuthenticationAgreement")
    Observable<ResponseObj<BaseObj>> getAuthXieYi(@Query("rnd") String rnd, @Query("sign") String sign);

    @GET("api/FlyMember/GetMemberRegister")
    Observable<ResponseObj<BaseObj>> register(@QueryMap Map<String,String> map);

    //用户登录
    @GET("api/FlyMember/GetUserLogin")
    Observable<ResponseObj<LoginObj>> login(@QueryMap Map<String,String> map);

    //退出登录
    @GET("api/Lib/GetLogOut")
    Observable<ResponseObj<BaseObj>> exitApp(@QueryMap Map<String,String> map);

    //单独修改图片
    @GET("api/FlyMember/GetSetUserAvatar")
    Observable<ResponseObj<BaseObj>> uploadImgForInfo(@QueryMap Map<String,String> map);

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
    Observable<ResponseObj<AuthObj>> getMemberAuthentication(@Query("user_id") String user_id, @Query("sign") String sign);

    //实名认证申请
    @GET("api/FlyMember/GetSetMemberAuthentication")
    Observable<ResponseObj<BaseObj>> authCommit(@QueryMap Map<String,String> map);

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

    //添加作业范围
    @POST("api/FlyMember/PostAddOperatingRange")
    Observable<ResponseObj<BaseObj>> addHomework(@Query("userid") String userid, @Query("sign") String sign, @Body HomeworkCityItem item);

    //获取中国所有的城市
    @GET("api/Lib/GetAllCity")
    Observable<ResponseObj<List<CityObj>>> getAllCity(@Query("rnd") String rnd, @Query("sign") String sign);

    //获取省份
    @GET("api/Lib/GetProvince")
    Observable<ResponseObj<List<ProvinceObj>>> getProvince(@Query("rnd") String rnd, @Query("sign") String sign);

    //获取城市
    @GET("api/Lib/GetCity")
    Observable<ResponseObj<List<CityObj>>> getCityForProvince(@Query("parent_id") String parent_id, @Query("sign") String sign);

    //获取银行列表
    @GET("api/FlyMember/GetBankList")
    Observable<ResponseObj<List<BankObj>>> getBankList(@Query("rnd") String rnd, @Query("sign") String sign);

    //添加银行卡
    @GET("api/FlyMember/GetAddAccount")
    Observable<ResponseObj<BaseObj>> addBank(@QueryMap Map<String,String> map);

    //获取账户列表
    @GET("api/FlyMember/GetAccount")
    Observable<ResponseObj<List<AccountObj>>> getAccount(@Query("user_id") String user_id, @Query("sign") String sign);

    //删除账户
    @GET("api/FlyMember/GetDelAccount")
    Observable<ResponseObj<BaseObj>> deleteAccount(@Query("account_id") String account_id, @Query("sign") String sign);

    //设置默认账户
    @GET("api/FlyMember/GetEditDefalut")
    Observable<ResponseObj<BaseObj>> setDefaultAccount(@QueryMap Map<String,String> map);

    //获取默认账户
    @GET("api/FlyMember/GetAccountDefault")
    Observable<ResponseObj<List<AccountObj>>> getDefaultAccount(@Query("user_id") String user_id, @Query("sign") String sign);

    //获取所有金额(全部转出)
    @GET("api/FlyMember/GetAllMoney")
    Observable<ResponseObj<BaseObj>> getAllMoney(@Query("user_id") String user_id, @Query("sign") String sign);

    //提现申请
    @GET("api/FlyMember/GetWithdrawals")
    Observable<ResponseObj<BaseObj>> tiXian(@QueryMap Map<String,String> map);


    //获取订单
    @GET("api/FlyMember/GetOrderList")
    Observable<ResponseObj<List<OrderObj>>> getAllOrder(@QueryMap Map<String,String> map);

    //订单-接单
    @GET("api/FlyMember/GetGoAddOrder")
    Observable<ResponseObj<BaseObj>> jieDan(@QueryMap Map<String,String> map);

    //订单-去执行
    @GET("api/FlyMember/GetGoCompleteOrder")
    Observable<ResponseObj<BaseObj>> zhiXing(@Query("order_no") String order_no, @Query("sign") String sign);

    //订单-执行完成
    @GET("api/FlyMember/GetCompleteOrder")
    Observable<ResponseObj<BaseObj>> complete(@Query("order_no") String order_no, @Query("sign") String sign);

    //订单-取消订单
    @GET("api/FlyMember/GetCancelOrder")
    Observable<ResponseObj<BaseObj>> quXiao(@Query("order_no") String order_no, @Query("sign") String sign);

    //订单-时间提醒
    @GET("api/FlyMember/GetTeaTimer")
    Observable<ResponseObj<BaseObj>> tiXing(@Query("order_no") String order_no, @Query("sign") String sign);

    //订单-详情
    @GET("api/FlyMember/GetOrderMore")
    Observable<ResponseObj<OrderDetailObj>> getOrderDetail(@Query("order_no") String order_no, @Query("sign") String sign);

}
