package com.cyht.wykc.mvp.modles.setting;

import com.cyht.wykc.common.Constants;
import com.cyht.wykc.mvp.modles.HttpHelper;
import com.cyht.wykc.mvp.modles.base.BaseModel;
import com.cyht.wykc.mvp.contract.setting.NameContract;
import com.cyht.wykc.mvp.modles.bean.NameBean;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author： hengzwd on 2017/6/1.
 * Email：hengzwdhengzwd@qq.com
 */

public class NameModel extends BaseModel<NameContract.Presenter> implements NameContract.Model {

    public NameModel(NameContract.Presenter mpresenter) {
        super(mpresenter);
    }

    @Override
    public void start() {

    }


    @Override
    public void modifyName(final String name) {
        HttpHelper.getInstance().initService().reName(Constants.sessionid,name).enqueue(new Callback<NameBean>() {
            @Override
            public void onResponse(Call<NameBean> call, Response<NameBean> response) {
                if (response.isSuccessful()) {
                    NameBean bean=response.body();
                    if (bean.isResult()) {
                        if (getPresenter() != null) {
                            getPresenter().onModifySuccess(name);
                        }
                    }else {
                        if (getPresenter() != null) {
                            getPresenter().onModifyFailue(null);
                        }
                    }
                }else {
                    if (getPresenter() != null) {
                        getPresenter().onModifyFailue(null);
                    }
                }

            }

            @Override
            public void onFailure(Call<NameBean> call, Throwable t) {
                    if (getPresenter() != null) {
                        getPresenter().onModifyFailue(t);
                    }
            }
        });
    }
}
