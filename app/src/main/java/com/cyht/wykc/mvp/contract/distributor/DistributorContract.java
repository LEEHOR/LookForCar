package com.cyht.wykc.mvp.contract.distributor;

import com.baidu.location.BDLocation;
import com.cyht.wykc.mvp.contract.base.BaseContract;
import com.cyht.wykc.mvp.modles.bean.DistributorInfoBean;
import com.cyht.wykc.mvp.modles.bean.MainBean;

import java.util.List;
import java.util.Map;


/**
 * Author： hengzwd on 2017/8/30.
 * Email：hengzwdhengzwd@qq.com
 */

public interface DistributorContract{


    interface View extends BaseContract.View {
        void onRequestDistributorFailure(Throwable t);
        void onRequestDistributorSuccess(DistributorInfoBean distributorBean);
        void locationSuccess(BDLocation bdLocation);
        void locationFailue(Throwable t);
        void onloadmoreSuccess(DistributorInfoBean distributorBean);
        void onloadmoreFailue(Throwable t);
    }

    interface Presenter extends BaseContract.presenter {
        void onRequestDistributorSuccess(DistributorInfoBean distributorBean);
        void onRequestDistributoreFailure(Throwable t);
        void requestDistributor(Map map);
        void locationSuccess(BDLocation bdLocation);
        void locationFailue(Throwable t);
        void startLocation();
        void loadMore(Map map);
        void onloadmoreSuccess(DistributorInfoBean distributorBean);
        void onloadmoreFailue(Throwable t);
    }

    interface Modle extends BaseContract.Model {

        void loadMore(Map map);
        void requestDistributor(Map map);
        void startLocation();
    }

}
