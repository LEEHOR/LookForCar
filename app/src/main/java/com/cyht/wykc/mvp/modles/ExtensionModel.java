package com.cyht.wykc.mvp.modles;



import com.cyht.wykc.mvp.contract.ExtensionContract;
import com.cyht.wykc.mvp.modles.base.BaseModel;
import com.cyht.wykc.mvp.modles.bean.ExtensionBean;

import com.socks.library.KLog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author： hengzwd on 2017/11/10.
 * Email：hengzwdhengzwd@qq.com
 */

public class ExtensionModel extends BaseModel<ExtensionContract.Presenter> implements ExtensionContract.model {

    public ExtensionModel(ExtensionContract.Presenter mpresenter) {
        super(mpresenter);
    }

    @Override
    public void start() {
    }

    @Override
    public void requestExtension(String msgId) {
        HttpHelper.getInstance().initService().getExtension(msgId).enqueue(new Callback<ExtensionBean>() {
            @Override
            public void onResponse(Call<ExtensionBean> call, Response<ExtensionBean> response) {
                if (response.isSuccessful()) {
                    ExtensionBean extensionBean = response.body();
                    if (extensionBean.getResult() == 1) {
                        if (getPresenter() != null) {
                            getPresenter().onRequestSuccess(extensionBean.getData());
                        }
                    } else {
                        if (getPresenter() != null) {
                            getPresenter().onRequestFailure(null);

                        }
                    }
                } else {
                    if (getPresenter() != null) {
                        getPresenter().onRequestFailure(null);

                    }
                }
            }

            @Override
            public void onFailure(Call<ExtensionBean> call, Throwable t) {
                if (getPresenter() != null) {
                    getPresenter().onRequestFailure(t);
                    KLog.e("onhotfailue:"+t.getMessage());
                }
            }
        });
    }
}
