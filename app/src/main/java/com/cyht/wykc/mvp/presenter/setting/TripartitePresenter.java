package com.cyht.wykc.mvp.presenter.setting;

import com.cyht.wykc.mvp.modles.setting.TripartiteModel;
import com.cyht.wykc.mvp.presenter.base.BasePresenter;
import com.cyht.wykc.mvp.contract.setting.TripartiteContract;

/**
 * Author： hengzwd on 2017/6/1.
 * Email：hengzwdhengzwd@qq.com
 */

public class TripartitePresenter extends BasePresenter<TripartiteContract.View,TripartiteContract.Model> implements TripartiteContract.Presenter {
    public TripartitePresenter(TripartiteContract.View mview) {
        super(mview);
    }

    @Override
    public void start() {

    }

    @Override
    public void bindResponse() {

    }

    @Override
    public void unBindResponse() {

    }

    @Override
    public TripartiteContract.Model createModle() {
        return new TripartiteModel(this);
    }
}
