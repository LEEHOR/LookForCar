package com.cyht.wykc.mvp.modles.base;

import com.cyht.wykc.common.RxManager;
import com.cyht.wykc.mvp.contract.base.BaseContract;
import com.socks.library.KLog;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import rx.Subscriber;

/**
 * Author： hengzwd on 2017/5/31.
 * Email：hengzwdhengzwd@qq.com
 */

public abstract class BaseModel<P extends BaseContract.presenter> {

    protected P mPresenter;

    //每一套mvp应该拥有一个独立的RxManager
    public RxManager mRxManager = new RxManager();

    public BaseModel(P mpresenter) {
        mPresenter =  mpresenter;
    }

    public boolean isPresenterAttached() {
        return mPresenter != null ;
    }

    public P getPresenter() {
        return mPresenter;
    }

    public void detachPresenter() {
        if (mPresenter != null) {
            mPresenter = null;
        }
    }

    public abstract class RxSubscriber<T> extends Subscriber<T> {
        @Override
        public void onCompleted() {
            KLog.e("onCompleted");

        }

        @Override
        public void onStart() {
            super.onStart();
            KLog.e("onStart");

        }

        @Override
        public void onNext(T o) {
            KLog.e("onNext");
            _onNext(o);
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            //此处不考虑错误类型，笼统的以错误来介绍
            KLog.e("onError::" + e);
        }

        public abstract void _onNext(T t);
    }

}
