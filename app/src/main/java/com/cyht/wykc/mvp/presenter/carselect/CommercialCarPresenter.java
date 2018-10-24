package com.cyht.wykc.mvp.presenter.carselect;

import android.support.annotation.Nullable;

import com.cyht.wykc.mvp.modles.bean.BrandListBean;
import com.cyht.wykc.mvp.modles.carselect.CommercialCarModel;
import com.cyht.wykc.mvp.presenter.base.BasePresenter;
import com.cyht.wykc.mvp.contract.carselect.CommercialCarContract;
import com.socks.library.KLog;

import java.util.List;

/**
 * Author： hengzwd on 2017/6/1.
 * Email：hengzwdhengzwd@qq.com
 */

public class CommercialCarPresenter extends BasePresenter<CommercialCarContract.View, CommercialCarContract.Modle> implements CommercialCarContract.Presenter {
    public CommercialCarPresenter(CommercialCarContract.View mview) {
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
    public CommercialCarContract.Modle createModle() {
        return new CommercialCarModel(this);
    }


    @Override
    public void onRequestBrandSuccess(List list) {

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

        if (mModle != null&&getView()!=null) {
            mModle.requestPassengerCarList(entity);
        }
    }
}
