package com.cyht.wykc.mvp.contract.setting;

import com.cyht.wykc.mvp.contract.base.BaseContract;
import com.cyht.wykc.mvp.modles.bean.CollectionBean;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.Part;

/**
 * Author： hengzwd on 2017/9/13.
 * Email：hengzwdhengzwd@qq.com
 */

public interface ClipContract {

    interface View extends BaseContract.View {
        void onUpdateSuccess(String  picUrl);
        void onUpdateFailue(Throwable throwable);
    }

    interface Presenter extends BaseContract.presenter {
        void onUpdateSuccess(String  picUrl);
        void onUpdateFailue(Throwable throwable);
        void updateHeadPic(MultipartBody.Part  file);
    }

    interface Modle extends BaseContract.Model {

        void updateHeadPic(MultipartBody.Part  file);
    }
}
