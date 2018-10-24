package com.cyht.wykc.mvp.contract.setting;

import com.cyht.wykc.mvp.contract.base.BaseContract;
import com.cyht.wykc.mvp.modles.bean.MsgBean;

import java.util.List;
import java.util.Map;

/**
 * Author： hengzwd on 2017/9/18.
 * Email：hengzwdhengzwd@qq.com
 */

public interface LetterDetailsContract {


    interface View extends BaseContract.View {

        void updateFailure(Throwable throwable);
        void  updateSuccess(String msgId);

    }

    interface Presenter extends BaseContract.presenter {

        void updateFailure(Throwable throwable);
        void  updateSuccess(String msgId);
        void  updateMsg(Map map);

    }

    interface Modle extends BaseContract.Model {

        void  updateMsg(Map map);

    }
}
