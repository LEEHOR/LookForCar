package com.cyht.wykc.mvp.presenter.setting;

import com.cyht.wykc.mvp.modles.bean.OpinionBean;
import com.cyht.wykc.mvp.modles.setting.OpinionModel;
import com.cyht.wykc.mvp.presenter.base.BasePresenter;
import com.cyht.wykc.mvp.contract.setting.OpinionContract;

import java.util.Map;

/**
 * Author： hengzwd on 2017/6/1.
 * Email：hengzwdhengzwd@qq.com
 */

public class OpinionPresenter extends BasePresenter<OpinionContract.View,OpinionContract.Model> implements OpinionContract.Presenter {


    public OpinionPresenter(OpinionContract.View mview) {
        super(mview);
    }


    @Override
    public void start() {

    }

    @Override
    public OpinionContract.Model createModle() {
        return new OpinionModel(this);
    }

    @Override
    public void onOpinionSuccess(OpinionBean opinionBean) {

        if (getView() != null) {
            getView().onOpinionSuccess(opinionBean);
        }
    }

    @Override
    public void onOpinionFailure(Throwable throwable) {
        if (getView() != null) {
            getView().onOpinionFailure(throwable);
        }
    }

    @Override
    public void requestOpinion(String opinion) {

        if (mModle != null) {
            mModle.requestOpinion(opinion);
        }
    }
}
