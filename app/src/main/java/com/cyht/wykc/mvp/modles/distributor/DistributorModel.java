package com.cyht.wykc.mvp.modles.distributor;

import android.accounts.NetworkErrorException;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.cyht.wykc.common.Constants;
import com.cyht.wykc.mvp.contract.distributor.DistributorContract;
import com.cyht.wykc.mvp.modles.HttpHelper;
import com.cyht.wykc.mvp.modles.base.BaseModel;
import com.cyht.wykc.mvp.modles.bean.DistributorInfoBean;
import com.cyht.wykc.mvp.view.base.BaseApplication;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.Map;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author： hengzwd on 2017/8/30.
 * Email：hengzwdhengzwd@qq.com
 */

public class DistributorModel extends BaseModel<DistributorContract.Presenter> implements DistributorContract.Modle {
    // 百度地图获取经纬度
    private LocationClient locationClient ;
    private static final int UPDATE_TIME = 300000;
    private static int LOCATION_COUTNS = 0;
    private String city;
    private  int currentPage = 0;
    public DistributorModel(DistributorContract.Presenter mpresenter) {
        super(mpresenter);
        initlocation();
    }

    private void initlocation() {
        locationClient = new LocationClient(BaseApplication.mContext);
        // 设置定位条件
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 是否打开GPS
        option.setCoorType("bd09ll"); // 设置返回值的坐标类型。国测局经纬度坐标系:gcj02
        // 百度墨卡托坐标系:bd09 百度经纬度坐标系:bd09ll
        option.setProdName("lookforcar"); // 设置产品线名称。
        option.setScanSpan(UPDATE_TIME); // 设置定时定位的时间间隔。单位毫秒
        option.setAddrType("all");
        locationClient.setLocOption(option);

        // 注册位置监听器
        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                // TODO Auto-generated method stub
                if (location == null) {
                    return;
                }
                city=location.getCity();
                StringBuffer sb = new StringBuffer(256);
                sb.append("Time : ");
                sb.append(location.getTime());
                sb.append("\nError code : ");
                sb.append(location.getLocType());
                sb.append("\nLatitude : ");
                sb.append(location.getLatitude());
                sb.append("\nLontitude : ");
                sb.append(location.getLongitude());
                sb.append("\nRadius : ");
                sb.append(location.getRadius());
                if (location.getLocType() == BDLocation.TypeGpsLocation) {
                    sb.append("\nSpeed : ");
                    sb.append(location.getSpeed());
                    sb.append("\nSatellite : ");
                    sb.append(location.getSatelliteNumber());
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                    sb.append("\nAddress : ");
                    sb.append(location.getAddrStr());
                }
                LOCATION_COUTNS++;
                sb.append("\n检查位置更新次数：");
                sb.append(String.valueOf(LOCATION_COUTNS));

                Constants.latitude = location.getLatitude();
                Constants.longitude = location.getLongitude();
                if (location.getAddrStr() != null
                        && !"".equals(location.getAddrStr())) {
                    String a[] = location.getAddrStr().split("-");
                    Constants.locationAddress = a[0];
                    if (getPresenter() != null)
                        getPresenter().locationSuccess(location);
                }else {
                    if (getPresenter() != null)
                        getPresenter().locationFailue(null);
                }
                sb.append("\nlocationAddress : ");
                sb.append(location.getAddrStr());
                KLog.e(sb.toString() + "");
            }
        });
//            locationClient.start();
			/*
			 * 当所设的整数值大于等于1000（ms）时，定位SDK内部使用定时定位模式。调用requestLocation(
			 * )后，每隔设定的时间，定位SDK就会进行一次定位。如果定位SDK根据定位依据发现位置没有发生变化，就不会发起网络请求，
			 * 返回上一次定位的结果；如果发现位置改变，就进行网络请求进行定位，得到新的定位结果。
			 * 定时定位时，调用一次requestLocation，会定时监听到定位结果。
			 */
//            locationClient.requestLocation();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadMore(Map map) {

        HttpHelper.getInstance().initService().getDistributorInfo(map,Constants.city).enqueue(new Callback<DistributorInfoBean>() {
            @Override
            public void onResponse(Call<DistributorInfoBean> call, Response<DistributorInfoBean> response) {

                if (response.isSuccessful()) {
                    DistributorInfoBean distributorInfoBean = response.body();
                    if (distributorInfoBean.getResult() == 1) {
                        if (getPresenter() != null)
                            getPresenter().onloadmoreSuccess(distributorInfoBean);
                        currentPage++;
                    } else {
                        if (getPresenter() != null)
                            getPresenter().onloadmoreFailue(new NetworkErrorException());
                    }
                } else {
                    if (getPresenter() != null)
                        getPresenter().onloadmoreFailue(new NetworkErrorException());
                }
            }

            @Override
            public void onFailure(Call<DistributorInfoBean> call, Throwable t) {
                if (getPresenter() != null)
                    getPresenter().onloadmoreFailue(t) ;
            }
        });
    }

    @Override
    public void requestDistributor(Map map) {

        HttpHelper.getInstance().initService().getDistributorInfo(map,Constants.city).enqueue(new Callback<DistributorInfoBean>() {
            @Override
            public void onResponse(Call<DistributorInfoBean> call, Response<DistributorInfoBean> response) {

                if (response.isSuccessful()) {
                    DistributorInfoBean distributorInfoBean = response.body();
                    if (distributorInfoBean.getResult() == 1) {
                        if (getPresenter() != null)
                            getPresenter().onRequestDistributorSuccess(distributorInfoBean);
                        currentPage = 1;
                    } else {
                        if (getPresenter() != null)
                            getPresenter().onRequestDistributoreFailure(new NetworkErrorException());
                        currentPage = 0;
                    }
                } else {
                    if (getPresenter() != null)
                        getPresenter().onRequestDistributoreFailure(new NetworkErrorException());
                    currentPage = 0;
                }
            }

            @Override
            public void onFailure(Call<DistributorInfoBean> call, Throwable t) {
                if (getPresenter() != null)
                    getPresenter().onRequestDistributoreFailure(t);
                currentPage = 0;
            }
        });
    }


    @Override
    public void startLocation() {
        locationClient.start();
    }
}
