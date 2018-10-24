package com.cyht.wykc.mvp.presenter.carselect;

import com.cyht.wykc.mvp.modles.carselect.CarBrandModle;
import com.cyht.wykc.mvp.presenter.base.BasePresenter;
import com.cyht.wykc.mvp.contract.carselect.CarBrandContract;

/**
 * Author： hengzwd on 2017/6/1.
 * Email：hengzwdhengzwd@qq.com
 */

public class CarBrandPresenter extends BasePresenter<CarBrandContract.View, CarBrandContract.Model> implements CarBrandContract.Presenter {
    public CarBrandPresenter(CarBrandContract.View mview) {
        super(mview);
    }

    @Override
    public void onRequestSuccess() {

    }

    @Override
    public void onrequestFailue() {

    }

    @Override
    public void start() {

        mModle.start();
    }

    @Override
    public CarBrandContract.Model createModle() {
        return new CarBrandModle(this);
    }
}
