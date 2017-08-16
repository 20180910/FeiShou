package com.zhizhong.feishou.module.my.activity;

import android.view.View;

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
import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by administartor on 2017/8/3.
 */

public class CeMuToolActivity extends BaseActivity {
    @BindView(R.id.mv_ce_mu)
    MapView mv_ce_mu;



    public BDLocationListener myListener = new MyLocationListenner();
    BaiduMap mBaiduMap;
    private boolean isFirstLoc=true;

    @Override
    public void again() {

    }

    @Override
    protected int getContentView() {
        setAppTitle("测亩工具");
        return R.layout.act_ce_mu_tool;
    }

    @Override
    protected void initView() {
        setBaiDuMap();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
    @Override
    protected void onResume() {
        super.onResume();
        mv_ce_mu.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mv_ce_mu.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mv_ce_mu.onDestroy();
    }
    private void setBaiDuMap() {

        mBaiduMap = mv_ce_mu.getMap();
// 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.drawable.back);
        MyLocationConfiguration config = new MyLocationConfiguration( MyLocationConfiguration.LocationMode.COMPASS, true, null);
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
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mv_ce_mu == null)
                return;

//            mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(15).build()));
            if (isFirstLoc) {
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
                mBaiduMap.setMyLocationData(locData);
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(18).build()));
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
