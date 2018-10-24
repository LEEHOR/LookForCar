package com.cyht.wykc.mvp.modles.videoplay;

import android.accounts.NetworkErrorException;

import com.cyht.wykc.mvp.contract.videoplay.TBSContract;
import com.cyht.wykc.mvp.modles.HttpHelper;
import com.cyht.wykc.mvp.modles.base.BaseModel;
import com.cyht.wykc.mvp.modles.bean.LoginBean;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author： hengzwd on 2017/9/4.
 * Email：hengzwdhengzwd@qq.com
 */

public class TBSModes extends BaseModel<TBSContract.Presenter> implements  TBSContract.Model {
    public TBSModes(TBSContract.Presenter mpresenter) {
        super(mpresenter);
    }

    @Override
    public void start() {
    }

    @Override
    public void otherLogin(Map map,String xingming) {
        HttpHelper.getInstance().initService().bindThreelogin(map, xingming).enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                if (response.isSuccessful()) {
                    LoginBean loginBean=response.body();
                    if (loginBean.getResult()==1) {
                        if (getPresenter() != null) {
                            getPresenter().onLoginSuccess(loginBean);
                        }
                    }else {
                        if (getPresenter() != null) {
                            getPresenter().onLoginFailue(null);
                        }
                    }
                }else {
                    if (getPresenter() != null) {
                        getPresenter().onLoginFailue(new NetworkErrorException());
                    }
                }
            }


            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                if (getPresenter() != null) {
                    getPresenter().onLoginFailue(t);
                }
            }
        });
    }
}
