package com.cyht.wykc.mvp.modles;

import android.accounts.NetworkErrorException;

import com.cyht.wykc.mvp.contract.LoginContract;
import com.cyht.wykc.mvp.contract.MainActivityContract;
import com.cyht.wykc.mvp.modles.base.BaseModel;
import com.cyht.wykc.mvp.modles.bean.LoginBean;
import com.cyht.wykc.mvp.modles.bean.ResultBean;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author： hengzwd on 2017/6/1.
 * Email：hengzwdhengzwd@qq.com
 */

public class LoginModel extends BaseModel<LoginContract.Presenter> implements LoginContract.model {
    public LoginModel(LoginContract.Presenter mpresenter) {
        super(mpresenter);
    }

    @Override
    public void start() {

    }


    @Override
    public void phoneLogin(Map map) {

        HttpHelper.getInstance().initService().phoneLogin(map).enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                if (response.isSuccessful()) {
                    LoginBean loginBean = response.body();
                    if (loginBean.getResult() == 1) {
                        if (getPresenter() != null) {
                            getPresenter().onPhoneLoginSuccess(loginBean);
                        }
                    } else {
                        if (getPresenter() != null) {
                            getPresenter().onPhoneLoginFailure(loginBean);
                        }
                    }
                } else {
                    if (getPresenter() != null) {
                        getPresenter().onLoginFailue(null);
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

    @Override
    public void otherLogin(Map map, String xingming) {

        HttpHelper.getInstance().initService().bindThreelogin(map, xingming).enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                if (response.isSuccessful()) {
                    LoginBean loginBean = response.body();
                    if (loginBean.getResult() == 1) {
                        if (getPresenter() != null) {
                            getPresenter().onLoginSuccess(loginBean);
                        }
                    } else {
                        if (getPresenter() != null) {
                            getPresenter().onLoginFailue(null);
                        }
                    }
                } else {
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

    @Override
    public void getVerificationCode(String MobilePhone) {


        HttpHelper.getInstance().initService().getVerificationCode(MobilePhone).enqueue(new Callback<ResultBean>() {
            @Override
            public void onResponse(Call<ResultBean> call, Response<ResultBean> response) {
                if (response.isSuccessful()) {
                    ResultBean resultBean = response.body();
                    if (getPresenter() != null) {
                        getPresenter().onVerificationResult(resultBean.getMsg());
                    }
                }else {
                    if (getPresenter() != null) {
                        getPresenter().onVerificationResult("请求失败");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultBean> call, Throwable t) {
                if (getPresenter() != null) {
                    getPresenter().onVerificationResult("请求失败");
                }
            }
        });
    }
}
