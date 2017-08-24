package com.zhizhong.feishou.module.my.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.DotOptions;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.github.customview.MyEditText;
import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseActivity;
import com.zhizhong.feishou.tools.DistanceUtil;
import com.zhizhong.feishou.tools.PolygonArea;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/8/3.
 */

public class CeMuToolActivity extends BaseActivity {
//    @BindView(R.id.mv_ce_mu)
//    MapView mv_ce_mu;
    @BindView(R.id.mv_ce_mu)
    TextureMapView mv_ce_mu;
    BaiduMap mBaiduMap;
    @BindView(R.id.tv_cemu_zc)
    TextView tv_cemu_zc;
    @BindView(R.id.tv_cemu_mj)
    TextView tv_cemu_mj;
    @BindView(R.id.tv_cemu_ms)
    TextView tv_cemu_ms;
    @BindView(R.id.et_cemu_search)
    MyEditText et_cemu_search;
    @BindView(R.id.iv_cemu_search)
    ImageView iv_cemu_search;
    @BindView(R.id.iv_cemu_dingwei)
    ImageView iv_cemu_dingwei;
    @BindView(R.id.iv_cemu_fanhui)
    ImageView iv_cemu_fanhui;
    @BindView(R.id.iv_cemu_quandi)
    ImageView iv_cemu_quandi;
    @BindView(R.id.iv_cemu_chexiao)
    ImageView iv_cemu_chexiao;
    @BindView(R.id.iv_cemu_clean)
    ImageView iv_cemu_clean;
    @BindView(R.id.iv_cemu_jisuan)
    ImageView iv_cemu_jisuan;
    @BindView(R.id.ll_ce_mu_search)
    LinearLayout ll_ce_mu_search;
    private boolean isFirstLoc = true;

    public BDLocationListener myListener = new MyLocationListenner();
    private UiSettings mUiSettings;
    private boolean isShowPoint;
    double lengthTotal = 0;
    private List<Overlay> pointList = new ArrayList();//覆盖物点
    private List<Overlay> lineList = new ArrayList();//覆盖物线
    private List<LatLng> clickPointList = new ArrayList<>();

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
        endCeMu();

        initMap();
    }



    @Override
    protected void initData() {

    }

    @OnClick({1})
    protected void onViewClick(View v) {
        switch (v.getId()) {

        }
    }
    private void initMap() {
        mBaiduMap = mv_ce_mu.getMap();

        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
//        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
        mUiSettings = mBaiduMap.getUiSettings();
        /*
         * 1、 地图-----先打开定位图层，将单位的中心点和管辖范围显示在地图上
		 * 界面-----将头部单位管辖的按钮给显示出来，搜索框隐藏，底部显示为管辖 数据-----将数据保存至执勤数组内 开启定位图层
		 */
//        mBaiduMap.setMyLocationEnabled(false);
        // 禁用指南针图层
        mUiSettings.setCompassEnabled(false);
        // 禁用俯视手势
        mUiSettings.setOverlookingGesturesEnabled(false);
//        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(18.0f).build()));


        mBaiduMap = mv_ce_mu.getMap();
// 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        MyLocationConfiguration config = new MyLocationConfiguration( MyLocationConfiguration.LocationMode.NORMAL, true, null);
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
/*// 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.drawable.back);
        MyLocationConfiguration config = new MyLocationConfiguration( MyLocationConfiguration.LocationMode.NORMAL, true, null);
        mBaiduMap.setMyLocationConfiguration(config);

// 定位初始化
        LocationClient mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();*/


        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                if (!isShowPoint) {
                    return;
                }
                clickPointList.add(point);
                DotOptions teDot = new DotOptions().center(point).radius(10).color(getResources().getColor(R.color.colorAccent));
                Overlay pointOverlay = mBaiduMap.addOverlay(teDot);
                pointList.add(pointOverlay);

                if (clickPointList.size() >= 2) {
                    PolylineOptions ooA = new PolylineOptions().width(5).color(getResources().getColor(R.color.blue_point)).points(clickPointList);
                    Overlay lineOverlay = mBaiduMap.addOverlay(ooA);
                    lineList.add(lineOverlay);
                    lengthTotal += DistanceUtil.getDistance(clickPointList.get(clickPointList.size() - 2), clickPointList.get(clickPointList.size() - 1));
                    // double dis = 0;
                    //dis = dis + DistanceUtil.getDistance(last, teacharr.get(i));
                    BigDecimal length = new BigDecimal(lengthTotal);
                    length = length.setScale(2, BigDecimal.ROUND_HALF_UP);
                    tv_cemu_zc.setText(length + "");

                }
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
    }

    @OnClick({R.id.iv_cemu_jisuan,R.id.iv_cemu_search, R.id.iv_cemu_dingwei, R.id.iv_cemu_fanhui, R.id.iv_cemu_quandi, R.id.iv_cemu_chexiao, R.id.iv_cemu_clean})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_cemu_jisuan://计算面积
                if(clickPointList==null||clickPointList.size()<3){
                    Toast.makeText(this,"最少选择三个点才能计算面积",Toast.LENGTH_SHORT).show();
                    return;
                }
                endCeMu();
                jiSuanMianJi();
                BigDecimal endLength = new BigDecimal(jiSuanLength(true));
                endLength = endLength.setScale(2, BigDecimal.ROUND_HALF_UP);
                tv_cemu_zc.setText(endLength+"");
                isShowPoint=false;
                break;
            case R.id.iv_cemu_search://搜索
                break;
            case R.id.iv_cemu_dingwei://定位
                isFirstLoc=true;
                break;
            case R.id.iv_cemu_fanhui://返回
                clearOverlay();

                isShowPoint=false;
                endCeMu();
                break;
            case R.id.iv_cemu_quandi://圈地
                clearOverlay();
                tv_cemu_zc.setText("0.00");
                tv_cemu_mj.setText("0.00");
                tv_cemu_ms.setText("0.00亩");
                isShowPoint=true;
                startCeMu();
                break;
            case R.id.iv_cemu_chexiao://撤销
                ceXiao();
                BigDecimal length = new BigDecimal(jiSuanLength(false));
                length = length.setScale(2, BigDecimal.ROUND_HALF_UP);
                tv_cemu_zc.setText(length+"");
                break;
            case R.id.iv_cemu_clean://清空
                tv_cemu_zc.setText("0.00");
                tv_cemu_mj.setText("0.00");
                tv_cemu_ms.setText("0.00亩");
                clearOverlay();
                break;
        }
    }

    private void startCeMu() {
        iv_cemu_quandi.setVisibility(View.INVISIBLE);
        ll_ce_mu_search.setVisibility(View.INVISIBLE);
        ll_ce_mu_search.setVisibility(View.INVISIBLE);

        iv_cemu_fanhui.setVisibility(View.VISIBLE);
        iv_cemu_chexiao.setVisibility(View.VISIBLE);
        iv_cemu_clean.setVisibility(View.VISIBLE);
        iv_cemu_jisuan.setVisibility(View.VISIBLE);

    }
    private void endCeMu() {
        ll_ce_mu_search.setVisibility(View.VISIBLE);
        iv_cemu_quandi.setVisibility(View.VISIBLE);
        ll_ce_mu_search.setVisibility(View.VISIBLE);

        iv_cemu_fanhui.setVisibility(View.INVISIBLE);
        iv_cemu_chexiao.setVisibility(View.INVISIBLE);
        iv_cemu_clean.setVisibility(View.INVISIBLE);
        iv_cemu_jisuan.setVisibility(View.INVISIBLE);
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

    private double jiSuanLength(boolean end) {
        lengthTotal = 0;
        if (clickPointList == null || clickPointList.size() < 2) {
            return 0;
        }
        //0,1,2
        for (int i = 0; i < clickPointList.size(); i++) {
            if ((i + 1) < clickPointList.size()) {
                lengthTotal += DistanceUtil.getDistance(clickPointList.get(i), clickPointList.get(i + 1));
            } else if (end) {//首位长度
                lengthTotal += DistanceUtil.getDistance(clickPointList.get(i), clickPointList.get(0));
            }
        }

        return lengthTotal;
    }

    private void clearOverlay() {
        mBaiduMap.clear();
        pointList.clear();
        lineList.clear();
        clickPointList.clear();
        lengthTotal = 0;
    }

    private void removeListForOverlay(List<Overlay> list) {
        if (list != null && list.size() > 0) {
            list.get(list.size() - 1).remove();
            list.remove(list.size() - 1);
        }
    }

    ;

    private void ceXiao() {
        if (pointList != null && pointList.size() > 0) {
            if (lineList != null && lineList.size() > 0) {
                removeListForOverlay(pointList);
                removeListForOverlay(lineList);
                clickPointList.remove(clickPointList.size() - 1);
            } else {
                removeListForOverlay(pointList);
                clickPointList.remove(clickPointList.size() - 1);
            }
        }
    }

    private void jiSuanMianJi() {
        OverlayOptions polygonOption = new PolygonOptions()
                .points(clickPointList)
                .stroke(new Stroke(5, getResources().getColor(R.color.blue)))
                .fillColor(getResources().getColor(R.color.active_lipin1));
//在地图上添加多边形Option，用于显示
        mBaiduMap.addOverlay(polygonOption);

        List<double[]> pointMian = new ArrayList<>();
        for (int i = 0; i < clickPointList.size(); i++) {
            Double lat1 = clickPointList.get(i).latitude;
            Double lng1 = clickPointList.get(i).longitude;
            double[] point = {lat1, lng1};
            pointMian.add(point);
        }

        PolygonArea tp = new PolygonArea();
        double mianJi = tp.calculateArea(pointMian);
        BigDecimal bd = new BigDecimal(mianJi);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        double v = mianJi * 0.0015;
        BigDecimal mj = new BigDecimal(v);
        mj = mj.setScale(2, BigDecimal.ROUND_HALF_UP);
        tv_cemu_mj.setText(bd+"");
        tv_cemu_ms.setText(mj + "亩");
        //mj/10000*15
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
}
