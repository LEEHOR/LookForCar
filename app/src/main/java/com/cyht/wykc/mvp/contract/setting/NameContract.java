package com.cyht.wykc.mvp.contract.setting;

import com.cyht.wykc.mvp.contract.base.BaseContract;

import java.util.Map;

/**
 * Author： hengzwd on 2017/6/1.
 * Email：hengzwdhengzwd@qq.com
 */

public interface NameContract {

    interface View extends BaseContract.View {
        void onModifySuccess(String name);

        void onModifyFailue(Throwable throwable);
    }

    interface Presenter extends BaseContract.presenter {

        void onModifySuccess(String name);

        void onModifyFailue(Throwable throwable);
        void modifyName(String name);
    }

    interface Model extends BaseContract.Model {
        void modifyName(String name);
    }
}
