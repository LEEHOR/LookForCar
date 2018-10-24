package com.cyht.wykc.mvp.view.distributor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;

import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.cyht.wykc.R;
import com.cyht.wykc.common.Constants;
import com.cyht.wykc.mvp.contract.base.BaseContract;
import com.cyht.wykc.mvp.view.base.BaseActivity;
import com.cyht.wykc.mvp.view.base.BaseApplication;
import com.cyht.wykc.utils.ScreenUtils;
import com.cyht.wykc.widget.MyTittleBar.NormalTittleBar;
import com.socks.library.KLog;


import butterknife.BindView;
import mapapi.overlayutil.DrivingRouteOverlay;


/**
 * Author： hengzwd on 2017/9/1.
 * Email：hengzwdhengzwd@qq.com
 */

public class StationMapLocationActivity extends BaseActivity {

    @BindView(R.id.mapview_station)
    MapView mapviewStation;
    @BindView(R.id.login_layout)
    LinearLayout loginLayout;
    @BindView(R.id.tb_tittle)
    NormalTittleBar tbTittle;


    private BaiduMap mBaiduMap;
//    private BitmapDescriptor mCurrentMarker;
    private BMapManager mBMapMan;
    private RoutePlanSearch routePlanSearch;
    private InfoWindow mInfoWindow;
    private double pointLatitude;
    private double pointLongitude;
    private String distributorName;
//    private Marker marker;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public BaseContract.presenter createPresenter() {
        return null;
    }

    @Override
    public int binLayout() {
        return R.layout.activity_station_map_location_layout;
    }

    @Override
    public void initView() {
        // 开启定位图层
        tbTittle.setPadding(tbTittle.getPaddingLeft(), ScreenUtils.getStatusBarHeight(BaseApplication.mContext), tbTittle.getPaddingRight(), tbTittle.getPaddingBottom());
        tbTittle.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressedSupport();
            }
        });
        tbTittle.getTvTittle().setText("地图");
        mBaiduMap = mapviewStation.getMap();
        mBaiduMap.setMyLocationEnabled(true);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        pointLatitude = intent.getDoubleExtra("latitude", 0.0000);
        pointLongitude = intent.getDoubleExtra("longitude", 0.0000);
        distributorName=intent.getStringExtra("distributorName");
        routePlanSearch = RoutePlanSearch.newInstance();
        routePlanSearch.setOnGetRoutePlanResultListener(listener);
        // 构造定位数据
        MyLocationData locData = new MyLocationData.Builder().accuracy(20)
                // 定位精度20米
                .direction(100)
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .latitude(Constants.latitude)
                .longitude(Constants.longitude).build();
//        mCurrentMarker = BitmapDescriptorFactory
//                .fromResource(R.mipmap.icon_gcoding); // 设置定位数据
        mBaiduMap.setMyLocationData(locData); // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
        MyLocationConfiguration config = new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.COMPASS, true, null);
        mBaiduMap.setMyLocationConfigeration(config);

//        // 构建Marker图标
//        BitmapDescriptor bitmap = BitmapDescriptorFactory
//                .fromResource(R.mipmap.icon_gcoding);
//        // 构建MarkerOption，用于在地图上添加Marker

//        OverlayOptions option = new MarkerOptions().position(
//                new LatLng(pointLatitude, pointLongitude)).title(distributorName);
//
//        // 在地图上添加Marker，并显示 //
//        marker= (Marker) mBaiduMap.addOverlay(option);

        startSearch(new LatLng(Constants.latitude, Constants.longitude), new LatLng(pointLatitude, pointLongitude));
    }

    OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {

        @Override
        public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

        }

        @Override
        public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

        }

        @Override
        public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

        }

        @Override
        public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
            //TODO: 在地图上绘制驾车路线
            if (drivingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                //result.getSuggestAddrInfo()
                KLog.d("baiduMap", "起终点或途经点地址有岐义");
                return;
            }
            if (drivingRouteResult.error == SearchResult.ERRORNO.PERMISSION_UNFINISHED) {
                //权限鉴定未完成则再次尝试
                KLog.d("baiduMap", "权限鉴定未完成,再次尝试");
                startSearch(new LatLng(Constants.latitude, Constants.longitude), new LatLng(pointLatitude, pointLongitude));
                return;
            }
            if (drivingRouteResult == null || drivingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                Toast.makeText(StationMapLocationActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
                return;
            }
            if (drivingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
//                route = drivingRouteResult.getRouteLines().get(0);
                DrivingRouteOverlay overlay = new DrivingRouteOverlay(mBaiduMap);
                mBaiduMap.setOnMarkerClickListener(overlay);
                overlay.setData(drivingRouteResult.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();
//                showLocation(marker);
                showLocation();
            }
        }



        @Override
        public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

        }

        @Override
        public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

        }
    };


    private void startSearch(LatLng start, LatLng end) {
        PlanNode stNode = PlanNode.withLocation(start);
        PlanNode enNode = PlanNode.withLocation(end);
        routePlanSearch.drivingSearch((new DrivingRoutePlanOption()).from(stNode).to(enNode));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }




    private void showLocation() {  //显示气泡
        // 创建InfoWindow展示的view

        LatLng pt = null;
//        double latitude, longitude;
//        latitude = marker.getPosition().latitude;
//        longitude = marker.getPosition().longitude;

        View view = LayoutInflater.from(this).inflate(R.layout.layout_distributormap_item, null); //自定义气泡形状
        TextView textView = (TextView) view.findViewById(R.id.tv_distributor_info);
        pt = new LatLng(pointLatitude , pointLongitude + 0.00005);
        textView.setText(distributorName);

        // 创建InfoWindow
        mInfoWindow = new InfoWindow(view, pt, -47);
        mBaiduMap.showInfoWindow(mInfoWindow); //显示气泡

    }

//    private void mark(double latitude, double longitude, String title) {//显示覆盖物
//
//        // 定义Maker坐标点
//        LatLng point = new LatLng(latitude, longitude);
//        // 构建Marker图标
//
//        bitmap = BitmapDescriptorFactory.fromResource(R.drawable.pointe_map);
//
//        // 构建MarkerOption，用于在地图上添加Marker
//        OverlayOptions option = new MarkerOptions().position(point)
//                .icon(bitmap);
//        // 在地图上添加Marker，并显示
//
//        Marker marker = (Marker) mBaiduMap.addOverlay(option);
//        marker.setTitle(title);
//        Bundle bundle = new Bundle();
//
//        bundle.putSerializable("recore", "ddd");
//        marker.setExtraInfo(bundle);
//
//    }

}
