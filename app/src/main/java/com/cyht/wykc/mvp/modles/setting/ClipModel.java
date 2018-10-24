package com.cyht.wykc.mvp.modles.setting;

import com.cyht.wykc.common.Constants;
import com.cyht.wykc.mvp.contract.setting.ClipContract;
import com.cyht.wykc.mvp.modles.HttpHelper;
import com.cyht.wykc.mvp.modles.base.BaseModel;
import com.cyht.wykc.mvp.modles.bean.UploadPicBean;
import com.socks.library.KLog;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author： hengzwd on 2017/9/13.
 * Email：hengzwdhengzwd@qq.com
 */

public class ClipModel extends BaseModel<ClipContract.Presenter> implements ClipContract.Modle {
    public ClipModel(ClipContract.Presenter mpresenter) {
        super(mpresenter);
    }

    @Override
    public void start() {

    }


    @Override
    public void updateHeadPic(MultipartBody.Part file) {

        HttpHelper.getInstance().initService().uploadPic(Constants.sessionid,file).enqueue(new Callback<UploadPicBean>() {
            @Override
            public void onResponse(Call<UploadPicBean> call, Response<UploadPicBean> response) {
                if (response.isSuccessful()) {
                    UploadPicBean resultBean=response.body();
                    if (resultBean.isResult()) {
                        if (getPresenter() != null) {
                            getPresenter().onUpdateSuccess(resultBean.getHeadPic());
                        }
                    }else {
                        if (getPresenter() != null) {
                            getPresenter().onUpdateFailue(null);
                        }
                    }
                }else {
                    if (getPresenter() != null) {
                        getPresenter().onUpdateFailue(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<UploadPicBean> call, Throwable t) {
                KLog.e(t.getMessage());
                if (getPresenter() != null) {
                    getPresenter().onUpdateFailue(t);
                }
            }
        });
    }
}
