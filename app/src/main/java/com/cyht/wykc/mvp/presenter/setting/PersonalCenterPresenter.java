package com.cyht.wykc.mvp.presenter.setting;

import com.cyht.wykc.mvp.modles.setting.PersonalCenterModel;
import com.cyht.wykc.mvp.presenter.base.BasePresenter;
import com.cyht.wykc.mvp.contract.setting.PersonalCenterContract;

/**
 * Author： hengzwd on 2017/6/1.
 * Email：hengzwdhengzwd@qq.com
 */

public class PersonalCenterPresenter extends BasePresenter<PersonalCenterContract.View,PersonalCenterContract.Model> implements PersonalCenterContract.Presenter {
    public PersonalCenterPresenter(PersonalCenterContract.View mview) {
        super(mview);
    }



    @Override
    public void start() {

    }

    @Override
    public PersonalCenterContract.Model createModle() {
        return new PersonalCenterModel(this);
    }

    @Override
    public void onRequestFailure(Throwable throwable) {
        if (getView() != null) {
            getView().onRequestFailure(throwable);
        }
    }

    @Override
    public void onRequestSuccess(int msgcount) {
        if (getView() != null) {
            getView().onRequestSuccess(msgcount);
        }
    }

    @Override
    public void requestMsgCount() {
        if (mModle != null) {
            mModle.requestMsgCount();
        }
    }
}
