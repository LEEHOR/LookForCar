package com.cyht.wykc.mvp.presenter.setting;

import com.cyht.wykc.mvp.contract.setting.ClipContract;
import com.cyht.wykc.mvp.modles.setting.ClipModel;
import com.cyht.wykc.mvp.presenter.base.BasePresenter;

import okhttp3.MultipartBody;

/**
 * Author： hengzwd on 2017/9/13.
 * Email：hengzwdhengzwd@qq.com
 */

public class ClipPresenter extends BasePresenter<ClipContract.View,ClipContract.Modle> implements ClipContract.Presenter {
    public ClipPresenter(ClipContract.View mview) {
        super(mview);
    }

    @Override
    public void start() {

    }


    @Override
    public void onUpdateSuccess(String picUrl) {
        if (getView() != null) {
            getView().onUpdateSuccess(picUrl);
        }
    }

    @Override
    public void onUpdateFailue(Throwable throwable) {

        if (getView() != null) {
            getView().onUpdateFailue(throwable);
        }
    }

    @Override
    public void updateHeadPic(MultipartBody.Part part) {
        if (getView() != null&&mModle!=null) {
            mModle.updateHeadPic(part);
        }
    }

    @Override
    public ClipContract.Modle createModle() {
        return new ClipModel(this);
    }
}
