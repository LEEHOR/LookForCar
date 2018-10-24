package com.cyht.wykc.mvp.presenter.distributor;

import com.baidu.location.BDLocation;
import com.cyht.wykc.mvp.contract.distributor.DistributorContract;
import com.cyht.wykc.mvp.modles.bean.DistributorInfoBean;
import com.cyht.wykc.mvp.modles.distributor.DistributorModel;
import com.cyht.wykc.mvp.presenter.base.BasePresenter;
import com.socks.library.KLog;

import java.util.Map;

/**
 * Author： hengzwd on 2017/8/30.
 * Email：hengzwdhengzwd@qq.com
 */

public class DistributorPresenter extends BasePresenter<DistributorContract.View,DistributorContract.Modle> implements  DistributorContract.Presenter {


    public DistributorPresenter(DistributorContract.View mview) {
        super(mview);
    }

    @Override
    public void start() {

    }

    @Override
    public DistributorContract.Modle createModle() {
        return new DistributorModel(this);
    }

    @Override
    public void onRequestDistributorSuccess(DistributorInfoBean distributorBean) {
        if (getView() != null)
        getView().onRequestDistributorSuccess(distributorBean);
    }

    @Override
    public void onRequestDistributoreFailure(Throwable t) {
        if (getView() != null)
        getView().onRequestDistributorFailure(t);
    }

    @Override
    public void requestDistributor(Map map) {
        KLog.e("requestdistributor");
        if (getView() != null)
            getView().showLoading();
        mModle.requestDistributor(map);
    }

    @Override
    public void locationSuccess(BDLocation bdLocation) {
        if (getView() != null)
        getView().locationSuccess(bdLocation);
    }

    @Override
    public void locationFailue(Throwable t) {
        if (getView() != null)
        getView().locationFailue(t);
    }

    @Override
    public void startLocation() {
        mModle.startLocation();
    }

    @Override
    public void loadMore(Map map) {
        mModle.loadMore(map);
    }

    @Override
    public void onloadmoreSuccess(DistributorInfoBean distributorBean) {
        if (getView() != null)
        getView().onloadmoreSuccess(distributorBean);
    }

    @Override
    public void onloadmoreFailue(Throwable t) {
        if (getView() != null)
        getView().onloadmoreFailue(t);
    }
}
