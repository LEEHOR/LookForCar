package com.cyht.wykc.mvp.presenter.setting;

import com.cyht.wykc.mvp.contract.setting.HistoryContract;
import com.cyht.wykc.mvp.modles.bean.DeletedBean;
import com.cyht.wykc.mvp.modles.bean.HistoryBean;
import com.cyht.wykc.mvp.modles.setting.HistoryModel;
import com.cyht.wykc.mvp.presenter.base.BasePresenter;

import org.w3c.dom.NamedNodeMap;

import java.util.List;
import java.util.Map;

/**
 * Author： hengzwd on 2017/9/6.
 * Email：hengzwdhengzwd@qq.com
 */

public class HistoryPresenter extends BasePresenter<HistoryContract.View,HistoryContract.Modle> implements HistoryContract.Presenter {
    public HistoryPresenter(HistoryContract.View mview) {
        super(mview);
    }

    @Override
    public void start() {

    }

    @Override
    public void onRequestSuccess(HistoryBean historyBean) {

        if (getView() != null) {
            getView().onRequestSuccess(historyBean);
        }
    }

    @Override
    public void onrequestFailue(Throwable throwable) {

        if (getView() != null) {
            getView().onrequestFailue(throwable);
        }
    }

    @Override
    public void requestMyHistory() {
        if (mModle != null&&getView() != null) {
            getView().showLoading();
            mModle.requestMyMyHistory();
        }
    }

    @Override
    public void ondeleteSuccess(List<HistoryBean.ListEntity> listEntities) {
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
    public void deleleHistory(List<HistoryBean.ListEntity> listEntities) {
        if (mModle != null) {
            mModle.deleleHistory(listEntities);
        }
    }


    @Override
    public HistoryContract.Modle createModle() {
        return new HistoryModel(this);
    }
}
