package com.cyht.wykc.mvp.modles;

import android.accounts.NetworkErrorException;
import android.widget.Toast;

import com.cyht.wykc.mvp.contract.CarSearchContract;
import com.cyht.wykc.mvp.modles.base.BaseModel;
import com.cyht.wykc.mvp.modles.bean.CarBean;
import com.cyht.wykc.mvp.modles.bean.HotCarSearchBean;
import com.cyht.wykc.mvp.modles.bean.LoginBean;
import com.cyht.wykc.utils.ShareprefrenceStackUtils;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author： hengzwd on 2017/9/8.
 * Email：hengzwdhengzwd@qq.com
 * 网络连接层
 */

public class CarSearchModel extends BaseModel<CarSearchContract.Presenter> implements CarSearchContract.model {

    public CarSearchModel(CarSearchContract.Presenter mpresenter) {
        super(mpresenter);
    }

    @Override
    public void start() {

    }

    @Override
    public void requestHotSearch() {

        Map map = new HashMap();
        KLog.e("requestHotSearch;");
        HttpHelper.getInstance().initService().getHotSearch(map).enqueue(new Callback<HotCarSearchBean>() {
            @Override
            public void onResponse(Call<HotCarSearchBean> call, Response<HotCarSearchBean> response) {
                if (response.isSuccessful()) {
                    HotCarSearchBean hotCarSearchBean = response.body();
                    if (hotCarSearchBean.getResult() == 1) {
                            List<CarBean> carBeanList = new ArrayList<CarBean>();
                            for (HotCarSearchBean.DataEntity.CarListEntity carListEntity : hotCarSearchBean.getData().getCarList()) {
                                carBeanList.add(new CarBean(carListEntity.getId(), carListEntity.getName(), carListEntity.getBrand(), carListEntity.getType() + ""));
                            }
                            if (getPresenter() != null) {
                                getPresenter().onHotSuccess(carBeanList);
                            }
                    } else {
                        if (getPresenter() != null) {
                            getPresenter().onHotFailue(null);

                        }
                    }
                } else {
                    if (getPresenter() != null) {
                        getPresenter().onHotFailue(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<HotCarSearchBean> call, Throwable t) {
                if (getPresenter() != null) {
                    getPresenter().onHotFailue(t);
                    KLog.e("onhotfailue:"+t.getMessage());

                }
            }
        });
    }

    @Override
    public void getSearchHistory() {
        Observable.create(new Observable.OnSubscribe<List<CarBean>>() {
                              @Override
                              public void call(final Subscriber<? super List<CarBean>> subscriber) {
                                  final List<CarBean> list = ShareprefrenceStackUtils.getInstance().getCarModelList();
                                  if (list != null&&list.size()>0) {
                                      subscriber.onNext(list);
                                  }
                              }
                          }
        ).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<CarBean>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.getMessage());
//                        Toast.makeText(App.mcontext, e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(List<CarBean> carmodels) {
                        KLog.e("historycarmodels:" + carmodels.size());
                        if (getPresenter() != null) {
                            getPresenter().onHistorySuccess(carmodels);
                        }
                    }
                });
    }
}
