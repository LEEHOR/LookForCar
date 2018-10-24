package com.cyht.wykc.mvp.presenter;

import com.cyht.wykc.mvp.contract.CarSearchContract;
import com.cyht.wykc.mvp.contract.base.BaseContract;
import com.cyht.wykc.mvp.modles.CarSearchModel;
import com.cyht.wykc.mvp.modles.bean.CarBean;
import com.cyht.wykc.mvp.presenter.base.BasePresenter;

import java.util.List;
import java.util.Map;

/**
 * Author： hengzwd on 2017/9/8.
 * Email：hengzwdhengzwd@qq.com
 */

public class CarSearchPresenter extends BasePresenter<CarSearchContract.view,CarSearchContract.model> implements CarSearchContract.Presenter {
    public CarSearchPresenter(CarSearchContract.view mview) {
        super(mview);
    }

    @Override
    public void start() {

    }

    @Override
    public CarSearchContract.model createModle() {
        return new CarSearchModel(this);
    }

    @Override
    public void onHotFailue(Throwable throwable) {
        if (getView() != null) {
            getView().onHotFailue(throwable);
        }
    }

    @Override
    public void onHotSuccess(List<CarBean> carBeanList) {
        if (getView() != null) {
            getView().onHotSuccess(carBeanList);
        }
    }

    @Override
    public void onHistorySuccess(List<CarBean> carBeanList) {
        if (getView() != null) {
            getView().onHistorySuccess(carBeanList);
        }
    }

    @Override
    public void requestHotSearch() {
        if (mModle != null) {
            mModle.requestHotSearch();
        }
    }

    @Override
    public void getSearchHistory() {
        if (mModle != null) {
            mModle.getSearchHistory();
        }
    }
}
