package com.cyht.wykc.mvp.contract.setting;

import com.cyht.wykc.mvp.contract.base.BaseContract;

/**
 * Author： hengzwd on 2017/9/5.
 * Email：hengzwdhengzwd@qq.com
 */

public interface SettingContract {

    interface View extends BaseContract.View {
        void unbindFailure(Throwable t);

        void unbindSuccess();
    }

    interface Presenter extends BaseContract.presenter {
          void unbind();
        void unbindFailure(Throwable t);

        void unbindSuccess();

    }

    interface Model extends BaseContract.Model {
        void unbind();
    }
}
