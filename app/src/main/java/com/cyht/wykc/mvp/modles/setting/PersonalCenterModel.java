package com.cyht.wykc.mvp.modles.setting;

import com.cyht.wykc.common.Constants;
import com.cyht.wykc.mvp.modles.HttpHelper;
import com.cyht.wykc.mvp.modles.base.BaseModel;
import com.cyht.wykc.mvp.contract.setting.PersonalCenterContract;
import com.cyht.wykc.mvp.modles.bean.UnReadBean;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author： hengzwd on 2017/6/1.
 * Email：hengzwdhengzwd@qq.com
 */

public class PersonalCenterModel extends BaseModel<PersonalCenterContract.Presenter> implements PersonalCenterContract.Model {
    public PersonalCenterModel(PersonalCenterContract.Presenter mpresenter) {
        super(mpresenter);
    }

    @Override
    public void start() {

    }

    @Override
    public void requestMsgCount() {
        final Map map= new HashMap();
        map.put("sessionid", Constants.sessionid);
        HttpHelper.getInstance().initService().unRead(map).enqueue(new Callback<UnReadBean>(){
            @Override
            public void onResponse(Call<UnReadBean> call, Response<UnReadBean> response) {
                if (response.isSuccessful()) {
                    UnReadBean unReadBean= response.body();
                    if (unReadBean.getResult()==1) {
                        if (getPresenter() != null) {
                            getPresenter().onRequestSuccess(unReadBean.getCount());
                        }
                    }else {
                        if (getPresenter() != null) {
                            getPresenter().onRequestFailure(null);
                        }
                    }
                }else {
                    if (getPresenter() != null) {
                        getPresenter().onRequestFailure(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<UnReadBean> call, Throwable t) {
                if (getPresenter() != null) {
                    getPresenter().onRequestFailure(t);
                }
            }
        });
    }
}
