package com.cyht.wykc.mvp.contract.setting;

import com.cyht.wykc.mvp.contract.base.BaseContract;
import com.cyht.wykc.mvp.modles.bean.MainBean;
import com.cyht.wykc.mvp.modles.bean.MsgBean;

import java.util.List;
import java.util.Map;

/**
 * Author： hengzwd on 2017/9/5.
 * Email：hengzwdhengzwd@qq.com
 */

public interface  LettersContract {

    interface View extends BaseContract.View {
        void onRequestSuccess(List<MsgBean.DataEntity.ListEntity> list);
        void onrequestFailue(Throwable throwable);
        void onloadmoreSuccess(List<MsgBean.DataEntity.ListEntity> list);
        void onloadmoreFailue(Throwable t);

        void updateFailure(Throwable throwable);
        void  updateSuccess(String msgId);
    }

    interface Presenter extends BaseContract.presenter {
        void onRequestSuccess(List<MsgBean.DataEntity.ListEntity> list);
        void onrequestFailue(Throwable throwable);
        void requestLetters();
        void loadmore();
        void onloadmoreSuccess(List<MsgBean.DataEntity.ListEntity> list);
        void onloadmoreFailue(Throwable t);

        void updateFailure(Throwable throwable);
        void  updateSuccess(String msgId);
        void  updateMsg(Map map);
    }

    interface Modle extends BaseContract.Model {
        void requestLetters();
        void loadmore();
        void  updateMsg(Map map);

    }
}
