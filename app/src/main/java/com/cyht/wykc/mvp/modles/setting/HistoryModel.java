package com.cyht.wykc.mvp.modles.setting;

import com.cyht.wykc.common.Constants;
import com.cyht.wykc.mvp.contract.setting.HistoryContract;
import com.cyht.wykc.mvp.modles.HttpHelper;
import com.cyht.wykc.mvp.modles.base.BaseModel;
import com.cyht.wykc.mvp.modles.bean.DeletedBean;
import com.cyht.wykc.mvp.modles.bean.HistoryBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author： hengzwd on 2017/9/6.
 * Email：hengzwdhengzwd@qq.com
 */

public class HistoryModel extends BaseModel<HistoryContract.Presenter> implements HistoryContract.Modle {


    public HistoryModel(HistoryContract.Presenter mpresenter) {
        super(mpresenter);
    }

    @Override
    public void start() {

    }

    @Override
    public void requestMyMyHistory() {

        HttpHelper.getInstance().initService().getHistory(Constants.sessionid,"1").enqueue(new Callback<HistoryBean>() {
            @Override
            public void onResponse(Call<HistoryBean> call, Response<HistoryBean> response) {
                if (response.isSuccessful()) {
                    HistoryBean historyBean=response.body();
                    if (historyBean.getResult()==1) {
                        if (getPresenter() != null) {
                            getPresenter().onRequestSuccess(historyBean);
                        }
                    }else {
                        if (getPresenter() != null) {
                            getPresenter().onrequestFailue(null);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<HistoryBean> call, Throwable t) {
                if (getPresenter() != null) {
                    getPresenter().onrequestFailue(t);
                }
            }
        });
    }

    @Override
    public void deleleHistory(final List<HistoryBean.ListEntity> listEntities) {

        Map params=new HashMap();
        params.clear();
        String watcheds = "";
        for (HistoryBean.ListEntity c : listEntities){
            watcheds = watcheds + c.getId() + ",";
        }
        watcheds = watcheds.substring(0,watcheds.length()-1);
        params.put(Constants.WATCHED,watcheds);
        params.put(Constants.SESSION_ID,Constants.sessionid);
        HttpHelper.getInstance().initService().deleteHistory(params).enqueue(new Callback<DeletedBean>() {
            @Override
            public void onResponse(Call<DeletedBean> call, Response<DeletedBean> response) {
                if (response.isSuccessful()) {
                    DeletedBean deletedBean=response.body();
                    if (deletedBean.isResult()) {
                        if (getPresenter() != null) {
                            getPresenter().ondeleteSuccess(listEntities);
                        }
                    }else {
                        if (getPresenter() != null) {
                            getPresenter().ondeleteFailue(null);
                        }
                    }
                }else {
                    if (getPresenter() != null) {
                        getPresenter().ondeleteFailue(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<DeletedBean> call, Throwable t) {
                if (getPresenter() != null) {
                    getPresenter().ondeleteFailue(t);
                }
            }
        });
    }
}
