package com.cyht.wykc.mvp.presenter.videoplay;


import com.cyht.wykc.mvp.contract.videoplay.TBSContract;
import com.cyht.wykc.mvp.modles.bean.LoginBean;
import com.cyht.wykc.mvp.modles.videoplay.TBSModes;
import com.cyht.wykc.mvp.presenter.base.BasePresenter;
import com.cyht.wykc.utils.NetworkUtils;

import java.util.Map;

/**
 * Author： hengzwd on 2017/9/4.
 * Email：hengzwdhengzwd@qq.com
 */

public class TBSPresenter extends BasePresenter<TBSContract.View,TBSContract.Model>  implements  TBSContract.Presenter{
    public TBSPresenter(TBSContract.View mview) {
        super(mview);
    }

    @Override
    public void onLoginFailue(Throwable throwable) {

        if (getView() != null)
        getView().onLoginFailue(throwable);
    }

    @Override
    public void onLoginSuccess(LoginBean loginBean) {
        if (getView() != null)

        getView().onLoginSuccess(loginBean);
    }

    @Override
    public void otherLogin(Map map,String xingming) {

        if (mModle != null)
        mModle.otherLogin(map, xingming);
    }

    @Override
    public void start() {

    }

    @Override
    public TBSContract.Model createModle() {
        return new TBSModes(this);
    }
}
