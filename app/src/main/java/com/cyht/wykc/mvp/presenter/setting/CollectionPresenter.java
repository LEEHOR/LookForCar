package com.cyht.wykc.mvp.presenter.setting;

import com.cyht.wykc.mvp.modles.bean.CollectionBean;
import com.cyht.wykc.mvp.modles.bean.DeletedBean;
import com.cyht.wykc.mvp.modles.setting.CollectionModel;
import com.cyht.wykc.mvp.presenter.base.BasePresenter;
import com.cyht.wykc.mvp.contract.setting.CollectionContract;

import java.util.List;
import java.util.Map;

/**
 * Author： hengzwd on 2017/6/1.
 * Email：hengzwdhengzwd@qq.com
 */

public class CollectionPresenter extends BasePresenter<CollectionContract.View,CollectionContract.Modle> implements CollectionContract.Presenter {

    public CollectionPresenter(CollectionContract.View mview) {
        super(mview);
    }

    @Override
    public void start() {

    }



    @Override
    public CollectionContract.Modle createModle() {
        return new CollectionModel(this);
    }

    @Override
    public void onRequestSuccess(CollectionBean collectionBean) {

        if (getView() != null) {
            getView().onRequestSuccess(collectionBean);
        }
    }

    @Override
    public void onrequestFailue(Throwable throwable) {

        if (getView() != null) {
            getView().onrequestFailue(throwable);
        }
    }

    @Override
    public void ondeleteSuccess(List<CollectionBean.ListEntity> listEntities) {
        if (getView() != null) {
            getView().ondeleteSuccess(listEntities);
    }
    }

    @Override
    public void ondeleteFailue(Throwable throwable) {
        if (getView() != null) {
            getView().ondeleteFailue(throwable);
        }
    }

    @Override
    public void deleleCollection(List<CollectionBean.ListEntity> listEntities) {

        if (mModle != null) {
            mModle.deleleCollection(listEntities);
        }
    }

    @Override
    public void requestMyCollection() {

        if (mModle != null) {
            mModle.requestMyCollection();
        }
    }
}
