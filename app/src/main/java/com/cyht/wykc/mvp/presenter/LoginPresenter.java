package com.cyht.wykc.mvp.presenter;

import com.cyht.wykc.mvp.contract.LoginContract;
import com.cyht.wykc.mvp.modles.LoginModel;
import com.cyht.wykc.mvp.modles.bean.LoginBean;
import com.cyht.wykc.mvp.modles.bean.ResultBean;
import com.cyht.wykc.mvp.presenter.base.BasePresenter;
import com.cyht.wykc.mvp.contract.MainActivityContract;

import java.util.Map;

/**
 * Author： hengzwd on 2017/6/1.
 * Email：hengzwdhengzwd@qq.com
 */

public class LoginPresenter extends BasePresenter<LoginContract.view,LoginContract.model> implements LoginContract.Presenter {

    public LoginPresenter(LoginContract.view mview) {
        super(mview);
    }

    @Override
    public void start() {

    }


    @Override
    public LoginContract.model createModle() {
        return new LoginModel(this);
    }

    @Override
    public void onLoginFailue(Throwable throwable) {

        if (getView() != null) {
            getView().onLoginFailue(throwable);
        }
    }

    @Override
    public void onLoginSuccess(LoginBean loginBean) {

        if (getView() != null) {
            getView().onLoginSuccess(loginBean);
        }
    }

    @Override
    public void onPhoneLoginFailure(LoginBean resultBean) {
        if (getView() != null) {
            getView().onPhoneLoginFailure(resultBean);
        }
    }



    @Override
    public void onPhoneLoginSuccess(LoginBean loginBean) {
        if (getView() != null) {
            getView().onPhoneLoginSuccess(loginBean);
        }
    }

    @Override
    public void phoneLogin(Map map) {
        if (mModle != null) {
            mModle.phoneLogin(map);
        }
    }

    @Override
    public void otherLogin(Map map,String xingming) {
        if (mModle != null) {
            mModle.otherLogin(map,xingming);
        }
    }

    @Override
    public void getVerificationCode(String MobilePhone) {
        if (mModle != null) {
            mModle.getVerificationCode(MobilePhone);
        }
    }

    @Override
    public void onVerificationResult(String result) {
        if (getView() != null) {
            getView().onVerificationResult(result);
        }
    }
}
