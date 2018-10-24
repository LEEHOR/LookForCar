package com.cyht.wykc.mvp.modles.setting;

import com.cyht.wykc.common.Constants;
import com.cyht.wykc.mvp.modles.HttpHelper;
import com.cyht.wykc.mvp.modles.base.BaseModel;
import com.cyht.wykc.mvp.contract.setting.OpinionContract;
import com.cyht.wykc.mvp.modles.bean.OpinionBean;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author： hengzwd on 2017/6/1.
 * Email：hengzwdhengzwd@qq.com
 */

public class OpinionModel extends BaseModel<OpinionContract.Presenter> implements OpinionContract.Model {
    public OpinionModel(OpinionContract.Presenter mpresenter) {
        super(mpresenter);
    }

    @Override
    public void start() {

    }


    @Override
    public void requestOpinion(String opinion) {
        HttpHelper.getInstance().initService().opinionFeed(Constants.sessionid,opinion).enqueue(new Callback<OpinionBean>() {

            @Override
            public void onResponse(Call<OpinionBean> call, Response<OpinionBean> response) {
                if (response.isSuccessful()) {
                    OpinionBean opinionBean=response.body();
                    if (opinionBean.isResult()) {
                        if (getPresenter() != null) {
                            getPresenter().onOpinionSuccess(opinionBean);
                        }
                    }else {
                        getPresenter().onOpinionFailure(null);
                    }
                }else {
                    if (getPresenter() != null) {
                        getPresenter().onOpinionFailure(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<OpinionBean> call, Throwable t) {
                if (getPresenter() != null) {
                    getPresenter().onOpinionFailure(t);
                }
            }
        });
    }
}
