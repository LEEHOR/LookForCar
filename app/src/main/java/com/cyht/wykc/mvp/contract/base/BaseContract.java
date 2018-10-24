package com.cyht.wykc.mvp.contract.base;

import android.support.annotation.Nullable;

/**
 * Author： hengzwd on 2017/5/31.
 * Email：hengzwdhengzwd@qq.com
 */

public interface BaseContract {


    interface View {
        void showContent();

        void showError(@Nullable Throwable e);

        void showLoading();
//        void showEmpty();
    }

    interface Model {
        void start();

        void detachPresenter();
    }

    interface presenter {
        void start();

        void detachView();
    }

}
