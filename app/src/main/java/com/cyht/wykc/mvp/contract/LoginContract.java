package com.cyht.wykc.mvp.contract;

import com.cyht.wykc.mvp.contract.base.BaseContract;
import com.cyht.wykc.mvp.modles.bean.LoginBean;
import com.cyht.wykc.mvp.modles.bean.ResultBean;

import java.util.Map;

/**
 * Author： hengzwd on 2017/12/8.
 * Email：hengzwdhengzwd@qq.com
 */

public interface LoginContract extends BaseContract {

    interface view extends BaseContract.View {

        void onLoginFailue(Throwable throwable);

        void onLoginSuccess(LoginBean loginBean);

        void onPhoneLoginFailure(LoginBean loginBean);

        void onPhoneLoginSuccess(LoginBean loginBean);

        void onVerificationResult(String result);
    }

    interface Presenter extends BaseContract.presenter {
        void onLoginFailue(Throwable throwable);

        void onLoginSuccess(LoginBean loginBean);

        void onPhoneLoginFailure(LoginBean loginBean);

        void onPhoneLoginSuccess(LoginBean loginBean);

        void phoneLogin(Map map);

        void otherLogin(Map map, String xingming);

        void getVerificationCode(String MobilePhone);

        void onVerificationResult(String result);
    }

    interface model extends BaseContract.Model {
        void phoneLogin(Map map);

        void otherLogin(Map map, String xingming);

        void getVerificationCode(String MobilePhone);
    }
}
