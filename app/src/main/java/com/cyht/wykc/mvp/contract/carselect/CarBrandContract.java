package com.cyht.wykc.mvp.contract.carselect;

import com.cyht.wykc.mvp.contract.base.BaseContract;

/**
 * Author： hengzwd on 2017/6/1.
 * Email：hengzwdhengzwd@qq.com
 */

public interface CarBrandContract {


    interface View extends BaseContract.View {
        void loadFragment();

        void toggleDrawer();
    }

    interface Presenter extends BaseContract.presenter {
        void onRequestSuccess();

        void onrequestFailue();
    }

    interface Model extends BaseContract.Model {
        void createRequestUrl();

        void requestBrandVideo();
    }
}
