package com.cyht.wykc.mvp.contract.setting;

import com.cyht.wykc.mvp.contract.base.BaseContract;
import com.cyht.wykc.mvp.modles.bean.OpinionBean;

import java.util.Map;

/**
 * Author： hengzwd on 2017/6/1.
 * Email：hengzwdhengzwd@qq.com
 */

public interface OpinionContract {

    interface View extends BaseContract.View
    {
        void onOpinionSuccess(OpinionBean opinionBean);
        void onOpinionFailure(Throwable throwable);
    }
    interface  Presenter extends BaseContract.presenter
    {
        void onOpinionSuccess(OpinionBean opinionBean);
        void onOpinionFailure(Throwable throwable);
        void requestOpinion(String opinion);
    }
    interface  Model extends BaseContract.Model
    {
        void requestOpinion(String opinion);
    }
}
