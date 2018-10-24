package com.cyht.wykc.mvp.presenter.base;

import android.support.annotation.UiThread;

import com.cyht.wykc.mvp.contract.base.BaseContract;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Author： hengzwd on 2017/5/31.
 * Email：hengzwdhengzwd@qq.com
 */

public abstract class BasePresenter<V extends BaseContract.View, M extends BaseContract.Model> {

    public V mView;

    public M mModle;

    public BasePresenter(V mview) {
        attachView(mview);
        mModle = createModle();
    }

    public V getView() {
        return mView;
    }

    public abstract M createModle();

    public void attachView(V view) {
        mView = view;
    }

    @UiThread
    public boolean isViewAttached() {
        return mView != null;
    }

    @UiThread
    public void detachView() {
        if (mView != null) {
            mView = null;
        }
        if (mModle != null) {
            mModle = null;
        }
    }
}
