package com.cyht.wykc.mvp.contract.setting;

import com.cyht.wykc.mvp.contract.base.BaseContract;
import com.cyht.wykc.mvp.modles.bean.DeletedBean;
import com.cyht.wykc.mvp.modles.bean.HistoryBean;

import java.util.List;
import java.util.Map;

/**
 * Author： hengzwd on 2017/6/1.
 * Email：hengzwdhengzwd@qq.com
 */

public interface HistoryContract {

    interface View extends BaseContract.View {
        void onRequestSuccess(HistoryBean historyBean);
        void onrequestFailue(Throwable throwable);
        void ondeleteSuccess(List<HistoryBean.ListEntity> listEntities);
        void ondeleteFailue(Throwable throwable);
    }

    interface Presenter extends BaseContract.presenter {
        void onRequestSuccess(HistoryBean historyBean);
        void onrequestFailue(Throwable throwable);
        void requestMyHistory();
        void ondeleteSuccess(List<HistoryBean.ListEntity> listEntities);
        void ondeleteFailue(Throwable throwable);
        void deleleHistory(List<HistoryBean.ListEntity> listEntities);
    }

    interface Modle extends BaseContract.Model {
        void requestMyMyHistory();
        void deleleHistory(List<HistoryBean.ListEntity> listEntities);

    }
}
