package com.cyht.wykc.mvp.contract.carselect;

import com.cyht.wykc.mvp.contract.base.BaseContract;
import com.cyht.wykc.mvp.modles.bean.CarMediaInfoBean;
import com.cyht.wykc.mvp.modles.bean.CarPriceBean;
import com.cyht.wykc.widget.menu.MenuItem;

import java.util.List;

/**
 * Author： hengzwd on 2017/8/22.
 * Email：hengzwdhengzwd@qq.com
 */

public interface VideoListContract {

    interface View extends BaseContract.View {
        void onRequestMediasFailure(Throwable t);
        void onRequestMediasSuccess(CarMediaInfoBean carMediaInfoBean);
        void onRequestCarPriceSuccess(CarPriceBean carPriceBean);
        void onRequestCarPriceFailure(Throwable t);
        void onRequestChexingSuccess(List<MenuItem> menuItem);
        void onRequestChexingFailue(Throwable t);
    }

    interface Presenter extends BaseContract.presenter {
        void onRequestCarPriceSuccess(CarPriceBean carPriceBean);
        void onRequestCarPriceFailure(Throwable t);
        void onRequestMediasFailure(Throwable t);
        void onRequestMediasSuccess(CarMediaInfoBean carMediaInfoBean);
        void requestCarMedias(String carid);
        void requestCarPrice(String carid);
        void onRequestChexingSuccess(List<MenuItem> menuItem);
        void onRequestChexingFailue(Throwable t);
        void requestCheXing(String carid);
    }

    interface Modle extends BaseContract.Model {

        void requestCarMedias(String carid);

        void requestCarPrice(String carid);

        void requestCheXing(String carid);
    }
}
