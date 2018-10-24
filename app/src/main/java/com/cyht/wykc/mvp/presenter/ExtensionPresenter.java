package com.cyht.wykc.mvp.presenter;

import com.cyht.wykc.mvp.contract.ExtensionContract;
import com.cyht.wykc.mvp.modles.ExtensionModel;
import com.cyht.wykc.mvp.modles.bean.ExtensionBean;
import com.cyht.wykc.mvp.presenter.base.BasePresenter;

/**
 * Author： hengzwd on 2017/11/10.
 * Email：hengzwdhengzwd@qq.com
 */

public class ExtensionPresenter extends BasePresenter<ExtensionContract.view,ExtensionContract.model> implements  ExtensionContract.Presenter{
    public ExtensionPresenter(ExtensionContract.view mview) {
        super(mview);
    }



    @Override
    public void start() {

    }

    @Override
    public ExtensionContract.model createModle() {
        return new ExtensionModel(this);
    }

    @Override
    public void onRequestSuccess(ExtensionBean.DataEntity dataEntity) {
        if (getView() != null) {
            getView().onRequestSuccess(dataEntity);
        }
    }

    @Override
    public void onRequestFailure(Throwable throwable) {
        if (getView() != null) {
            getView().onRequestFailure(throwable);
        }
    }

    @Override
    public void requestExtension(String msgId) {
        if (getView() != null&&mModle!=null) {
            mModle.requestExtension(msgId);
        }
    }
}
