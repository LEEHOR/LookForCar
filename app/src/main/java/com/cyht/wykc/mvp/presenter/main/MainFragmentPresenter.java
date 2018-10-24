package com.cyht.wykc.mvp.presenter.main;

import com.cyht.wykc.mvp.contract.main.MainContract;
import com.cyht.wykc.mvp.modles.bean.MainBean;
import com.cyht.wykc.mvp.modles.main.MainModel;
import com.cyht.wykc.mvp.presenter.base.BasePresenter;

import java.util.List;

/**
 * Author： hengzwd on 2017/8/7.
 * Email：hengzwdhengzwd@qq.com
 */

public class MainFragmentPresenter extends BasePresenter<MainContract.View,MainContract.Model> implements  MainContract.Presenter {

    public MainFragmentPresenter(MainContract.View mview) {
        super(mview);
    }



    @Override
    public void start() {
        if (getView() != null)
            getView().showLoading();
        mModle.start();

    }

    @Override
    public MainContract.Model createModle() {
        return new MainModel(this);
    }


    @Override
    public void loadMore() {

        mModle.loadmore();
    }

    @Override
    public void onRequestMainSuccess(MainBean mainBean) {
        if (getView() != null)
        getView().onRequestMainSuccess(mainBean);
    }

    @Override
    public void onRequestMainFailue(Throwable t) {
        if (getView() != null)
        getView().onRequestMainFailue(t);
    }

    @Override
    public void onloadmoreSuccess(List<MainBean.DataEntity.VideoListEntity> list) {
        if (getView() != null)
        getView().onloadmoreSuccess(list);
    }

    @Override
    public void onloadmoreFailue(Throwable t) {
        if (getView() != null)
        getView().onloadmoreFailue(t);
    }
}
