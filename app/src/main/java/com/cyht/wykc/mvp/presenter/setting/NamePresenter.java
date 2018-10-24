package com.cyht.wykc.mvp.presenter.setting;

import com.cyht.wykc.mvp.modles.setting.NameModel;
import com.cyht.wykc.mvp.presenter.base.BasePresenter;
import com.cyht.wykc.mvp.contract.setting.NameContract;

import java.util.Map;

/**
 * Author： hengzwd on 2017/6/1.
 * Email：hengzwdhengzwd@qq.com
 */

public class NamePresenter extends BasePresenter<NameContract.View,NameContract.Model> implements NameContract.Presenter {

    public NamePresenter(NameContract.View mview) {
        super(mview);
    }



    @Override
    public void start() {

    }

    @Override
    public NameContract.Model createModle() {
        return new NameModel(this);
    }

    @Override
    public void onModifySuccess(String name) {
        if (getView() != null) {
            getView().onModifySuccess(name);
        }
    }

    @Override
    public void onModifyFailue(Throwable throwable) {
        if (getView() != null) {
            getView().onModifyFailue(throwable);
        }
    }

    @Override
    public void modifyName(String name) {

        if (mModle != null&&getView()!=null) {
            mModle.modifyName(name);
        }
    }
}
