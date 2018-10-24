package com.cyht.wykc.mvp.contract.videoplay;

import com.cyht.wykc.mvp.contract.base.BaseContract;
import com.cyht.wykc.mvp.modles.bean.LoginBean;

import java.util.Map;

/**
 * Author： hengzwd on 2017/9/4.
 * Email：hengzwdhengzwd@qq.com
 */

public interface TBSContract {

    interface View extends BaseContract.View {
        void onLoginFailue(Throwable throwable);
        void onLoginSuccess(LoginBean loginBean);
    }

    interface Presenter extends BaseContract.presenter {
        void onLoginFailue(Throwable throwable);
        void onLoginSuccess(LoginBean loginBean);
        void otherLogin(Map map,String xingming);
    }

    interface Model extends BaseContract.Model {
        void otherLogin(Map map,String xingming);
    }
}
