package com.cyht.wykc.mvp.presenter.setting;

import com.cyht.wykc.mvp.contract.setting.LettersContract;

import com.cyht.wykc.mvp.modles.bean.MsgBean;
import com.cyht.wykc.mvp.modles.setting.LettersModel;
import com.cyht.wykc.mvp.presenter.base.BasePresenter;
import com.socks.library.KLog;

import java.util.List;
import java.util.Map;

/**
 * Author： hengzwd on 2017/9/5.
 * Email：hengzwdhengzwd@qq.com
 */

public class LettersPresenter extends BasePresenter<LettersContract.View,LettersContract.Modle> implements LettersContract.Presenter {
    public LettersPresenter(LettersContract.View mview) {
        super(mview);
    }

    @Override
    public void start() {

    }

    @Override
    public LettersContract.Modle createModle() {
        return new LettersModel(this);
    }

    @Override
    public void detachView() {

    }

    @Override
    public void onRequestSuccess(List<MsgBean.DataEntity.ListEntity> list) {
        if (getView() != null) {
            getView().onRequestSuccess(list);
        }
    }

    @Override
    public void onrequestFailue(Throwable throwable) {
        if (getView() != null) {
            getView().onrequestFailue(throwable);
        }
    }

    @Override
    public void requestLetters() {
        if (mModle != null&&getView()!=null) {
//            getView().showLoading();
            KLog.e("requestLetters00000000000000");
            mModle.requestLetters();
        }
    }

    @Override
    public void loadmore() {
        if (mModle != null&&getView()!=null) {
            mModle.loadmore();
        }
    }

    @Override
    public void onloadmoreSuccess(List<MsgBean.DataEntity.ListEntity> list) {
        if (getView() != null) {
            getView().onloadmoreSuccess(list);
        }
    }
    @Override
    public void onloadmoreFailue(Throwable t) {
        if (getView() != null) {
            getView().onloadmoreFailue(t);
        }
    }

    @Override
    public void updateFailure(Throwable throwable) {

        if (getView() != null) {
            getView().updateFailure(throwable);
        }
    }

    @Override
    public void updateSuccess(String msgId) {
        if (getView() != null) {
            getView().updateSuccess(msgId);
        }
    }

    @Override
    public void updateMsg(Map map) {
        if (mModle != null&&getView()!=null) {
            KLog.e("updateMsg:");
            mModle.updateMsg(map);
        }
    }
}
