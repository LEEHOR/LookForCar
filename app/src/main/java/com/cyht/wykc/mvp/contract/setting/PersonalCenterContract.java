package com.cyht.wykc.mvp.contract.setting;

import com.cyht.wykc.mvp.contract.base.BaseContract;

/**
 * Author： hengzwd on 2017/6/1.
 * Email：hengzwdhengzwd@qq.com
 */

public interface PersonalCenterContract {

    interface View extends BaseContract.View {
        void onRequestFailure(Throwable throwable);
        void onRequestSuccess(int msgcount);

    }

    interface Presenter extends BaseContract.presenter {

        void onRequestFailure(Throwable throwable);
        void onRequestSuccess(int msgcount);
        void requestMsgCount();
    }

    interface Model extends BaseContract.Model {
        void requestMsgCount();
    }
}
