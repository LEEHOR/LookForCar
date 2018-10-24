package com.cyht.wykc.mvp.contract.setting;

import com.cyht.wykc.mvp.contract.base.BaseContract;

/**
 * Author： hengzwd on 2017/6/1.
 * Email：hengzwdhengzwd@qq.com
 */

public interface TripartiteContract {

    interface View extends BaseContract.View {
        void bindSuccess();

        void bindFailure();

        void unBindSuccess();

        void unBindFailure();
    }

    interface Presenter extends BaseContract.presenter {
        void bindResponse();

        void unBindResponse();
    }

    interface Model extends BaseContract.Model {
        void bind();

        void unbind();
    }
}
