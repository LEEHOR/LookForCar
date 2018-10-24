package com.cyht.wykc.mvp.contract;

import com.cyht.wykc.mvp.contract.base.BaseContract;
import com.cyht.wykc.mvp.modles.bean.CarBean;
import com.cyht.wykc.mvp.modles.bean.LoginBean;

import java.util.List;
import java.util.Map;

/**
 * Author： hengzwd on 2017/9/8.
 * Email：hengzwdhengzwd@qq.com
 */

public interface CarSearchContract {
    interface view extends BaseContract.View {
        void onHistorySuccess(List<CarBean> carBeanList);
        void onHotFailue(Throwable throwable);
        void onHotSuccess(List<CarBean> carBeanList);
    }

    interface Presenter extends BaseContract.presenter {
        void onHotFailue(Throwable throwable);
        void onHotSuccess(List<CarBean> carBeanList);
        void onHistorySuccess(List<CarBean> carBeanList);
        void requestHotSearch();
        void getSearchHistory();
    }

    interface model extends BaseContract.Model {

        void requestHotSearch();
        void getSearchHistory();
    }
}
