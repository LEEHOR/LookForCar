package com.cyht.wykc.mvp.presenter.setting;

import com.cyht.wykc.mvp.contract.setting.LetterDetailsContract;
import com.cyht.wykc.mvp.modles.setting.LettersDetailsModel;
import com.cyht.wykc.mvp.presenter.base.BasePresenter;
import com.socks.library.KLog;

import java.util.Map;

/**
 * Author： hengzwd on 2017/9/18.
 * Email：hengzwdhengzwd@qq.com
 */

public class LettersDetailsPresenter extends BasePresenter<LetterDetailsContract.View,LetterDetailsContract.Modle> implements LetterDetailsContract.Presenter {

    public LettersDetailsPresenter(LetterDetailsContract.View mview) {
        super(mview);
    }

    @Override
    public void start() {

    }

    @Override
    public void updateFailure(Throwable throwable) {

        if (getView() != null) {
            getView().updateFailure(throwable);
        }
    }

    @Override
    public void updateSuccess(String msgId) {

        if (getView() != null) {
            getView().updateSuccess(msgId);
        }
    }

    @Override
    public void updateMsg(Map map) {
        if (mModle != null&&getView()!=null) {
            KLog.e("updateMsg:");
            mModle.updateMsg(map);
        }
    }

    @Override
    public LetterDetailsContract.Modle createModle() {
        return new LettersDetailsModel(this);
    }
}
