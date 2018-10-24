package com.cyht.wykc.mvp.modles.setting;

import com.cyht.wykc.common.Constants;
import com.cyht.wykc.mvp.modles.HttpHelper;
import com.cyht.wykc.mvp.modles.base.BaseModel;
import com.cyht.wykc.mvp.contract.setting.CollectionContract;
import com.cyht.wykc.mvp.modles.bean.CollectionBean;
import com.cyht.wykc.mvp.modles.bean.DeletedBean;
import com.cyht.wykc.mvp.modles.bean.HistoryBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author： hengzwd on 2017/6/1.
 * Email：hengzwdhengzwd@qq.com
 */

public class CollectionModel extends BaseModel<CollectionContract.Presenter> implements CollectionContract.Modle {
    public CollectionModel(CollectionContract.Presenter mpresenter) {
        super(mpresenter);
    }

    @Override
    public void start() {

    }

    @Override
    public void requestMyCollection() {
        Map map =  new HashMap();
        map.put("sessionid",Constants.sessionid);
        map.put("isapp","1") ;
        HttpHelper.getInstance().initService().getCollection(map).enqueue(new Callback<CollectionBean>() {
            @Override
            public void onResponse(Call<CollectionBean> call, Response<CollectionBean> response) {
                if (response.isSuccessful()) {
                    CollectionBean collectionBean=response.body();
                    if (collectionBean.getResult()==1) {
                        if (getPresenter() != null) {
                            getPresenter().onRequestSuccess(collectionBean);
                        }
                    }else {
                        if (getPresenter() != null) {
                            getPresenter().onrequestFailue(null);
                        }
                    }
                }else {
                    if (getPresenter() != null) {
                        getPresenter().onrequestFailue(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<CollectionBean> call, Throwable t) {
                if (getPresenter() != null) {
                    getPresenter().onrequestFailue(t);
                }
            }
        });
    }

    @Override
    public void deleleCollection(final List<CollectionBean.ListEntity> listEntities) {
        Map params=new HashMap();
        params.clear();
        String watcheds = "";
        for (CollectionBean.ListEntity c : listEntities){
            watcheds = watcheds + c.getId() + ",";
        }
        watcheds = watcheds.substring(0,watcheds.length()-1);
        params.put(Constants.FAVORITES,watcheds);
        params.put(Constants.SESSION_ID,Constants.sessionid);
        HttpHelper.getInstance().initService().deleteCollection(params).enqueue(new Callback<DeletedBean>() {
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
