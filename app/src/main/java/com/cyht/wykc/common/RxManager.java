package com.cyht.wykc.common;


import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


/**
 * Author： hengzwd on 2017/5/31.
 * Email：hengzwdhengzwd@qq.com
 */

public class RxManager {
    /*管理Observables 和 Subscribers订阅*/
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    /**
     * 单纯的Observables 和 Subscribers管理
     *
     * @param m
     */
    public void add(Subscription m) {
        /*订阅管理*/
        mCompositeSubscription.add(m);
    }

    /**
     * 单个presenter生命周期结束，取消订阅和所有rxbus观察
     */
    public void clear() {
        mCompositeSubscription.unsubscribe();// 取消所有订阅
    }
}
