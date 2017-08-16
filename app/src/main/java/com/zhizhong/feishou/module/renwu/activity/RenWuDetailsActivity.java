package com.zhizhong.feishou.module.renwu.activity;

import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.bumptech.glide.Glide;
import com.github.baseclass.view.MyDialog;
import com.github.customview.MyTextView;
import com.zhizhong.feishou.GetSign;
import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseActivity;
import com.zhizhong.feishou.base.BaseObj;
import com.zhizhong.feishou.base.MySub;
import com.zhizhong.feishou.module.my.activity.LoginActivity;
import com.zhizhong.feishou.module.renwu.Constant;
import com.zhizhong.feishou.module.renwu.network.ApiRequest;
import com.zhizhong.feishou.module.renwu.network.response.RenWuDetailObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/8/2.
 */

public class RenWuDetailsActivity extends BaseActivity {
    @BindView(R.id.sv_renwu_detail)
    ScrollView sv_renwu_detail;
    @BindView(R.id.iv_rw_detail_img)
    ImageView iv_rw_detail_img;
    @BindView(R.id.tv_rw_detail_number)
    TextView tv_rw_detail_number;

    @BindView(R.id.tv_rw_detail_total)
    TextView tv_rw_detail_total;

    @BindView(R.id.tv_rw_detail_dq)
    TextView tv_rw_detail_dq;

    @BindView(R.id.tv_rw_detail_nzw)
    TextView tv_rw_detail_nzw ;

    @BindView(R.id.tv_rw_detail_dj)
    TextView tv_rw_detail_dj ;

    @BindView(R.id.tv_rw_detail_ms)
    TextView tv_rw_detail_ms ;

    @BindView(R.id.tv_rw_detail_time)
    TextView tv_rw_detail_time ;

    @BindView(R.id.tv_rw_detail_dk)
    TextView tv_rw_detail_dk ;

    @BindView(R.id.tv_rw_detail_address)
    TextView tv_rw_detail_address ;

    @BindView(R.id.ll_rw_details_bao1)
    LinearLayout ll_rw_details_bao1;
    @BindView(R.id.ll_rw_details_bao2)
    LinearLayout ll_rw_details_bao2;
    @BindView(R.id.ll_rw_details_bao3)
    LinearLayout ll_rw_details_bao3;
    @BindView(R.id.ll_rw_details_bao4)
    LinearLayout ll_rw_details_bao4;
    @BindView(R.id.tv_rw_detail_commit)
    MyTextView tv_rw_detail_commit;
    @BindView(R.id.mv_renwu_detail)
    MapView mv_renwu_detail;
    private String productId;
    private String orderNo;

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListenner();
    BaiduMap mBaiduMap;
    private boolean isFirstLoc=true;
    @Override
    protected int getContentView() {
        setAppTitle("任务详情");
        return R.layout.act_renwu_details;
    }

    @Override
    protected void initView() {
        productId = getIntent().getStringExtra(Constant.IParam.productId);

        setBaiDuMap();
    }

    private void setBaiDuMap() {
        View childAt = mv_renwu_detail.getChildAt(0);
        childAt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    sv_renwu_detail.requestDisallowInterceptTouchEvent(false);
                }else{
                    sv_renwu_detail.requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });

        mBaiduMap = mv_renwu_detail.getMap();
// 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.drawable.back);
        MyLocationConfiguration config = new MyLocationConfiguration( MyLocationConfiguration.LocationMode.FOLLOWING, true, null);
        mBaiduMap.setMyLocationConfiguration(config);

// 定位初始化
        LocationClient mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    @Override
    protected void initData() {
        showProgress();
        getData();
    }

    private void getData() {
        addSubscription(ApiRequest.getRenWuDetails(productId, getSign("product_id", productId)).subscribe(new MySub<RenWuDetailObj>(mContext, pl_load) {
            @Override
            public void onMyNext(RenWuDetailObj obj) {
                setMapAddress(obj.getRegion(),obj.getAddress());
                Glide.with(mContext).load(obj.getImage_url()).error(R.color.c_press).into(iv_rw_detail_img);
                orderNo = obj.getNh_order_no();
                tv_rw_detail_number.setText(obj.getNh_order_no());
                tv_rw_detail_dq.setText(obj.getRegion());
                tv_rw_detail_nzw.setText(obj.getCrops());
                tv_rw_detail_dj.setText(obj.getPrice());
                tv_rw_detail_ms.setText(obj.getArea());
                tv_rw_detail_time.setText(obj.getRequest_time());
                tv_rw_detail_dk.setText(obj.getCondition());
                tv_rw_detail_address.setText(obj.getAddress());
                tv_rw_detail_total.setText(obj.getTotal_price());


                if(obj.getIs_andy()==1){
                    ll_rw_details_bao1.setVisibility(View.VISIBLE);
                }else{
                    ll_rw_details_bao1.setVisibility(View.GONE);
                }
                if(obj.getIs_encase()==1){
                    ll_rw_details_bao2.setVisibility(View.VISIBLE);
                }else{
                    ll_rw_details_bao2.setVisibility(View.GONE);
                }
                if(obj.getIs_rechargeable()==1){
                    ll_rw_details_bao3.setVisibility(View.VISIBLE);
                }else{
                    ll_rw_details_bao3.setVisibility(View.GONE);
                }
                if(obj.getIs_getwater()==1){
                    ll_rw_details_bao4.setVisibility(View.VISIBLE);
                }else{
                    ll_rw_details_bao4.setVisibility(View.GONE);
                }
            }
        }));
    }

    public void setMapAddress(String city,String address){
        GeoCoder geoCoder = GeoCoder.newInstance();
        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
            public void onGetGeoCodeResult(GeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有检索到结果
                }else {
                    //获取地理编码结果
                    float latitude = (float) result.getLocation().latitude;
                    float longitude = (float) result.getLocation().longitude;
                    final LatLng point = new LatLng(latitude, longitude);
                    //加载自定义marker
                    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.map);
                    //构建MarkerOption，用于在地图上添加Marker
                    OverlayOptions option = new MarkerOptions()
                            .position(point)
                            .icon(bitmapDescriptor);
                    //在地图上添加Marker，并显示
                    Marker marker = (Marker) mBaiduMap.addOverlay(option);

                }
            }
            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有找到检索结果
                }
                //获取反向地理编码结果
            }
        };
//        第三步，设置地理编码检索监听者；
        geoCoder.setOnGetGeoCodeResultListener(listener);
//        第四步，发起地理编码检索；

        geoCoder.geocode(new GeoCodeOption()
                .city(city)
                .address(address));//百度地图上少一个括号
    }

    @OnClick({R.id.tv_rw_detail_commit})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_rw_detail_commit:
                if(TextUtils.isEmpty(getUserId())){
                    STActivity(LoginActivity.class);
                    return;
                }
                mDialog=new MyDialog.Builder(mContext);
                mDialog.setMessage("确认接单吗?");
                mDialog.setNegativeButton(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                mDialog.setPositiveButton(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        commitOrder();
                    }
                });
                mDialog.create().show();
            break;
        }
    }
    private void commitOrder() {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("userid",getUserId());
        map.put("order_no",orderNo);
        map.put("sign", GetSign.getSign(map));
        addSubscription(ApiRequest.getAddOrder(map).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                showMsg(obj.getMsg());
                setResult(RESULT_OK);
                finish();
            }
        }));
    }

    @Override
    public void again() {
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mv_renwu_detail.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mv_renwu_detail.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mv_renwu_detail.onDestroy();
    }
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mv_renwu_detail == null)
                return;

//            mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(15).build()));
            if (isFirstLoc) {
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
                mBaiduMap.setMyLocationData(locData);
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(15).build()));
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

}
