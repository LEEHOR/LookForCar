package com.cyht.wykc.mvp.contract.carselect;

import android.support.annotation.Nullable;

import com.cyht.wykc.mvp.contract.base.BaseContract;
import com.cyht.wykc.mvp.modles.bean.BrandListBean;
import com.cyht.wykc.mvp.modles.bean.CarListBean;

import java.util.List;

/**
 * Author： hengzwd on 2017/6/1.
 * Email：hengzwdhengzwd@qq.com
 */

public interface CommercialCarContract {

    interface View extends BaseContract.View {
        void onRequestBrandSuccess(List<BrandListBean.DataEntity.BrandListEntity> list);
        void onRequestCarSuccess(List<CarListBean.DataEntity.CarListEntity> list);
        void onrequestBrandFailue(@Nullable Throwable e);
        void onrequestCarFailue(@Nullable Throwable e);
    }

    interface Presenter extends BaseContract.presenter {
        void onRequestBrandSuccess(List<BrandListBean.DataEntity.BrandListEntity> list);
        void onRequestCarSuccess(List<CarListBean.DataEntity.CarListEntity> list);
        void onrequestBrandFailue(@Nullable Throwable e);
        void onrequestCarFailue(@Nullable Throwable e);
        void requestCarForBrand(BrandListBean.DataEntity.BrandListEntity entity);
    }

    interface Modle extends BaseContract.Model {
        void  createRequestUrl();
        void  requestPassengerCarList(BrandListBean.DataEntity.BrandListEntity entity);
    }

}
