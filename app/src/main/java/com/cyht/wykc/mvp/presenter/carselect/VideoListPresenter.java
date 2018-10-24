package com.cyht.wykc.mvp.presenter.carselect;

import com.cyht.wykc.mvp.contract.carselect.VideoListContract;
import com.cyht.wykc.mvp.modles.bean.CarMediaInfoBean;
import com.cyht.wykc.mvp.modles.bean.CarPriceBean;
import com.cyht.wykc.mvp.modles.carselect.VideoListModel;
import com.cyht.wykc.mvp.presenter.base.BasePresenter;
import com.cyht.wykc.widget.menu.MenuItem;

import java.util.List;

/**
 * Author： hengzwd on 2017/8/22.
 * Email：hengzwdhengzwd@qq.com
 */

public class VideoListPresenter extends BasePresenter<VideoListContract.View, VideoListContract.Modle> implements VideoListContract.Presenter {

    public VideoListPresenter(VideoListContract.View mview) {
        super(mview);
    }

    @Override
    public void start() {

    }

    @Override
    public VideoListContract.Modle createModle() {
        return new VideoListModel(this);
    }



    @Override
    public void onRequestCarPriceSuccess(CarPriceBean carPriceBean) {
        if (getView() != null)
            getView().onRequestCarPriceSuccess(carPriceBean);
    }

    @Override
    public void onRequestCarPriceFailure(Throwable t) {
        if (getView() != null)
        getView().onRequestCarPriceFailure(t);
    }



    @Override
    public void onRequestMediasFailure(Throwable t) {
        getView().onRequestMediasFailure(t);
    }

    @Override
    public void onRequestMediasSuccess(CarMediaInfoBean carMediaInfoBean) {
        if (getView() != null) {
            getView().onRequestMediasSuccess(carMediaInfoBean);
        }
    }

    @Override
    public void requestCarMedias(String carid) {
        if (getView() != null)
            getView().showLoading();
        mModle.requestCarMedias(carid);
    }

    @Override
    public void requestCarPrice(String carid) {

        mModle.requestCarPrice(carid);
    }

    @Override
    public void onRequestChexingSuccess(List<MenuItem> menuItems) {
        if (getView() != null)
        getView().onRequestChexingSuccess(menuItems);
    }

    @Override
    public void onRequestChexingFailue(Throwable t) {
        if (getView() != null)
        getView().onRequestChexingFailue(t);
    }

    @Override
    public void requestCheXing(String carid) {

        mModle.requestCheXing(carid);
    }
}
