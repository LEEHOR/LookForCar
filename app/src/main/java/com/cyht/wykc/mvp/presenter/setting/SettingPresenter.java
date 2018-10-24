package com.cyht.wykc.mvp.presenter.setting;

import com.cyht.wykc.mvp.contract.setting.SettingContract;
import com.cyht.wykc.mvp.presenter.base.BasePresenter;

/**
 * Author： hengzwd on 2017/9/5.
 * Email：hengzwdhengzwd@qq.com
 */

public class SettingPresenter extends BasePresenter<SettingContract.View,SettingContract.Model> implements SettingContract.Presenter {
    public SettingPresenter(SettingContract.View mview) {
        super(mview);
    }

    @Override
    public void unbind() {

    }

    @Override
    public void unbindFailure(Throwable t) {

    }

    @Override
    public void unbindSuccess() {

    }

    @Override
    public void start() {

    }

    @Override
    public SettingContract.Model createModle() {
        return null;
    }
}
