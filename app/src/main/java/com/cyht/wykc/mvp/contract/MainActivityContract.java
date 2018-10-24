package com.cyht.wykc.mvp.contract;

import com.cyht.wykc.mvp.contract.base.BaseContract;
import com.cyht.wykc.mvp.modles.bean.LoginBean;

import java.util.Map;

/**
 * Author： hengzwd on 2017/6/1.
 * Email：hengzwdhengzwd@qq.com
 */

public interface MainActivityContract {

    interface view extends BaseContract.View {

        void onLoginFailue(Throwable throwable);
        void onLoginSuccess(LoginBean loginBean);

    }

    interface Presenter extends BaseContract.presenter {
        void onLoginFailue(Throwable throwable);
        void onLoginSuccess(LoginBean loginBean);
        void otherLogin(Map map,String xingming);

    }

    interface model extends BaseContract.Model {

        void otherLogin(Map map,String xingming);


    }
}
