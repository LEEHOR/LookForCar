package com.cyht.wykc.mvp.contract.setting;

import com.cyht.wykc.mvp.contract.base.BaseContract;
import com.cyht.wykc.mvp.modles.bean.CollectionBean;
import com.cyht.wykc.mvp.modles.bean.DeletedBean;
import com.cyht.wykc.mvp.modles.bean.HistoryBean;

import java.util.List;
import java.util.Map;

/**
 * Author： hengzwd on 2017/6/1.
 * Email：hengzwdhengzwd@qq.com
 */

public interface CollectionContract {

    interface View extends BaseContract.View {
        void onRequestSuccess(CollectionBean collectionBean);
        void onrequestFailue(Throwable throwable);
        void ondeleteSuccess(List<CollectionBean.ListEntity> listEntities);
        void ondeleteFailue(Throwable throwable);
    }

    interface Presenter extends BaseContract.presenter {
        void onRequestSuccess(CollectionBean collectionBean);
        void onrequestFailue(Throwable throwable);
        void ondeleteSuccess(List<CollectionBean.ListEntity> listEntities);
        void ondeleteFailue(Throwable throwable);
        void deleleCollection(List<CollectionBean.ListEntity> listEntities);
        void  requestMyCollection();
    }

    interface Modle extends BaseContract.Model {
        void  requestMyCollection();
        void deleleCollection(List<CollectionBean.ListEntity> listEntities);
    }
}
