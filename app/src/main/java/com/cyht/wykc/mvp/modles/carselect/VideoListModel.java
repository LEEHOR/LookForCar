package com.cyht.wykc.mvp.modles.carselect;

import android.accounts.NetworkErrorException;

import com.cyht.wykc.mvp.contract.carselect.VideoListContract;
import com.cyht.wykc.mvp.modles.HttpHelper;
import com.cyht.wykc.mvp.modles.base.BaseModel;
import com.cyht.wykc.mvp.modles.bean.CarMediaInfoBean;
import com.cyht.wykc.mvp.modles.bean.CarPriceBean;
import com.cyht.wykc.mvp.modles.bean.ChexingBean;
import com.cyht.wykc.mvp.view.base.BaseApplication;
import com.cyht.wykc.utils.NetworkUtils;
import com.cyht.wykc.widget.menu.MenuItem;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author： hengzwd on 2017/8/22.
 * Email：hengzwdhengzwd@qq.com
 */

public class VideoListModel extends BaseModel<VideoListContract.Presenter> implements  VideoListContract.Modle {

    public VideoListModel(VideoListContract.Presenter mpresenter) {
        super(mpresenter);
    }

    @Override
    public void start() {

    }

    @Override
    public void requestCarMedias(String carid) {
        final Map map= new HashMap();
        map.put("isapp", "1");
        map.put("car", carid);
//        map.put("car", "b6e0ea508e8248f9bc8bda2ac790b4f6");
        HttpHelper.getInstance().initService().getCarVideoList(map).enqueue(new Callback<CarMediaInfoBean>() {
            @Override
            public void onResponse(Call<CarMediaInfoBean> call, Response<CarMediaInfoBean> response) {
                KLog.e("response:"+response.message());
                if (response.isSuccessful()) {
                    CarMediaInfoBean carMediaInfoBean= response.body();
                    if (carMediaInfoBean.getResult()==1) {
                        if (getPresenter() != null)
                            getPresenter().onRequestMediasSuccess(carMediaInfoBean);
                    }else {
                        if (NetworkUtils.isAvailable(BaseApplication.mContext)) {
                            if (getPresenter() != null)
                                getPresenter().onRequestMediasFailure(new NetworkErrorException());
                        }else {
                            if (getPresenter() != null)
                                getPresenter().onRequestMediasFailure(null);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CarMediaInfoBean> call, Throwable t) {
                KLog.e("onfailure:"+t.getMessage());
                if (getPresenter() != null)
                    getPresenter().onRequestMediasFailure(t);
            }
        });

        requestCarPrice(carid);
        requestCheXing(carid);
    }

    @Override
    public void requestCarPrice(String carid) {
        final Map map= new HashMap();
//        map.put("car", "259b90c988294b2b8339f0c85b618626");
        map.put("car", carid);
        HttpHelper.getInstance().initService().getPrice(map).enqueue(new Callback<CarPriceBean>() {
            @Override
            public void onResponse(Call<CarPriceBean> call, Response<CarPriceBean> response) {
                KLog.e("response:"+response.message());
                if (response.isSuccessful()) {
                    CarPriceBean carPriceBean= response.body();
                    if (carPriceBean.getResult()==1) {
                        if (getPresenter() != null)
                        getPresenter().onRequestCarPriceSuccess(carPriceBean);
                    }else {
                        if (NetworkUtils.isAvailable(BaseApplication.mContext)) {
                            if (getPresenter() != null)
                            getPresenter().onRequestCarPriceFailure(new NetworkErrorException());
                        }else {
                            if (getPresenter() != null)
                                getPresenter().onRequestCarPriceFailure(null);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CarPriceBean> call, Throwable t) {
                if (getPresenter() != null)
                getPresenter().onRequestCarPriceFailure(t);
            }
        });
    }

    @Override
    public void requestCheXing(String carid) {
        final Map map= new HashMap();
        map.put("id" , carid);
        HttpHelper.getInstance().initService().getChexing(map).enqueue(new Callback<ChexingBean>() {
            @Override
            public void onResponse(Call<ChexingBean> call, Response<ChexingBean> response) {
                KLog.e("response:"+response.message());
                if (response.isSuccessful()) {
                    ChexingBean chexingBean= response.body();
                    if (chexingBean.getResult()==1) {
                        List<MenuItem> menuItemList= new ArrayList<MenuItem>();
                        for (ChexingBean.DataEntity.CarListEntity item:chexingBean.getData().getCarList())
                        {
                            menuItemList.add(new MenuItem(item.getId(),item.getName()));
                        }
                        if (getPresenter() != null)
                            getPresenter().onRequestChexingSuccess(menuItemList);
                    }else {
                        if (NetworkUtils.isAvailable(BaseApplication.mContext)) {
                            if (getPresenter() != null)
                            getPresenter().onRequestChexingFailue(new NetworkErrorException());
                        }else {
                            if (getPresenter() != null)
                                getPresenter().onRequestChexingFailue(null);
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<ChexingBean> call, Throwable t) {
                if (getPresenter() != null)
                getPresenter().onRequestChexingFailue(t);
            }
        });
    }
}
