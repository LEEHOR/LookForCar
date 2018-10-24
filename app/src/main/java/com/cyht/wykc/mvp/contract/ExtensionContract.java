package com.cyht.wykc.mvp.contract;

import com.cyht.wykc.mvp.contract.base.BaseContract;
import com.cyht.wykc.mvp.modles.bean.ExtensionBean;
import com.cyht.wykc.mvp.modles.bean.LoginBean;

import java.util.Map;

/**
 * Author： hengzwd on 2017/11/10.
 * Email：hengzwdhengzwd@qq.com
 */

public interface ExtensionContract {

    interface view extends BaseContract.View {

        void onRequestSuccess(ExtensionBean.DataEntity dataEntity);
        void onRequestFailure(Throwable throwable);
    }

    interface Presenter extends BaseContract.presenter {
        void onRequestSuccess(ExtensionBean.DataEntity dataEntity);
        void onRequestFailure(Throwable throwable);
        void requestExtension(String msgId);
    }

    interface model extends BaseContract.Model {
        void requestExtension(String msgId);
    }
}
