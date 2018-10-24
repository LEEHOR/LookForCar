package com.cyht.wykc.mvp.contract.main;

import com.cyht.wykc.mvp.contract.base.BaseContract;
import com.cyht.wykc.mvp.modles.bean.HotVideoBean;
import com.cyht.wykc.mvp.modles.bean.MainBean;

import java.util.List;

/**
 * Author： hengzwd on 2017/8/7.
 * Email：hengzwdhengzwd@qq.com
 */

public interface MainContract {


    interface View extends BaseContract.View {

        void onRequestMainSuccess(MainBean mainBean);
        void onRequestMainFailue(Throwable t);
        void onloadmoreSuccess(List<MainBean.DataEntity.VideoListEntity> list);
        void onloadmoreFailue(Throwable t);
    }

    interface Presenter extends BaseContract.presenter {

        void loadMore();
        void onRequestMainSuccess(MainBean mainBean);
        void onRequestMainFailue(Throwable t);
        void onloadmoreSuccess(List<MainBean.DataEntity.VideoListEntity> list);
        void onloadmoreFailue(Throwable t);
    }

    interface Model extends BaseContract.Model {

        void loadmore();
        void requestMain();
    }
}
