package com.cyht.wykc.mvp.presenter.carselect;

import android.support.annotation.Nullable;

import com.cyht.wykc.mvp.modles.bean.BrandListBean;
import com.cyht.wykc.mvp.modles.carselect.PassengerCarModel;
import com.cyht.wykc.mvp.presenter.base.BasePresenter;
import com.cyht.wykc.mvp.contract.carselect.PassengerCarContract;
import com.socks.library.KLog;

import java.util.List;

/**
 * Author： hengzwd on 2017/6/1.
 * Email：hengzwdhengzwd@qq.com
 */

public class PassengerCarPresenter extends BasePresenter<PassengerCarContract.View,PassengerCarContract.Modle> implements PassengerCarContract.Presenter {


    public PassengerCarPresenter(PassengerCarContract.View mview) {
        super(mview);
    }


    @Override
    public void start() {
        if (mModle != null&&getView()!=null) {
            mModle.start();
            KLog.e("start");
        }
    }

    @Override
    public PassengerCarContract.Modle createModle() {
        return new PassengerCarModel(this);
    }

    @Override
    public void onRequestBrandSuccess(List list) {

        KLog.e("onrequestsuccess");
        if (getView() != null)
            getView().onRequestBrandSuccess(list);
    }

    @Override
    public void onRequestCarSuccess(List list) {
        if (getView() != null)
        getView().onRequestCarSuccess(list);
    }



    @Override
    public void onrequestBrandFailue(@Nullable Throwable e) {
        if (getView() != null)
            getView().onrequestBrandFailue(e);
    }

    @Override
    public void onrequestCarFailue(@Nullable Throwable e) {
        if (getView() != null)
            getView().onrequestCarFailue(e);
    }

    @Override
    public void requestCarForBrand(BrandListBean.DataEntity.BrandListEntity entity) {
        mModle.requestPassengerCarList(entity);
    }


}
