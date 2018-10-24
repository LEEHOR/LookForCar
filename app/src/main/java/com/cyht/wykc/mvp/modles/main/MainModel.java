package com.cyht.wykc.mvp.modles.main;

import android.accounts.NetworkErrorException;

import com.cyht.wykc.common.Constants;
import com.cyht.wykc.mvp.contract.main.MainContract;
import com.cyht.wykc.mvp.modles.HttpHelper;
import com.cyht.wykc.mvp.modles.base.BaseModel;
import com.cyht.wykc.mvp.modles.bean.HotVideoBean;
import com.cyht.wykc.mvp.modles.bean.MainBean;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author： hengzwd on 2017/8/7.
 * Email：hengzwdhengzwd@qq.com
 */

public class MainModel extends BaseModel<MainContract.Presenter> implements  MainContract.Model {



    private  int currentPage = 0;
    public MainModel(MainContract.Presenter mpresenter) {
        super(mpresenter);
    }

    @Override
    public void start() {
            requestMain();
    }
    @Override
    public  void loadmore()
    {
        Map map=new HashMap();
        map.put("pageNo",currentPage+"");
        map.put("pageSize", Constants.PAGESIZE+"");
        HttpHelper.getInstance().initService().getHotVideo(map).enqueue(new Callback<MainBean>() {
            @Override
            public void onResponse(Call<MainBean> call, Response<MainBean> response) {
                if (response.isSuccessful()) {
                    MainBean mainBean=response.body();
                    if (mainBean.getResult()==1) {
                        if (getPresenter() != null)
                            getPresenter().onloadmoreSuccess(mainBean.getData().getVideoList());
                        currentPage++;
                    }else {
                        if (getPresenter() != null)
                            getPresenter().onloadmoreFailue(null);
                    }
                }else {
                    if (getPresenter() != null)
                        getPresenter().onloadmoreFailue(new NetworkErrorException());
                }
            }

            @Override
            public void onFailure(Call<MainBean> call, Throwable t) {
                KLog.e(t.getMessage());
                if (getPresenter() != null)
                    getPresenter().onloadmoreFailue(t);
            }
        });
    }

    @Override
    public void requestMain() {
        Map map=new HashMap();
        HttpHelper.getInstance().initService().getMain(map).enqueue(new Callback<MainBean>() {
            @Override
            public void onResponse(Call<MainBean> call, Response<MainBean> response) {
                if (response.isSuccessful()) {
                    MainBean mainBean=response.body();
                    if (mainBean.getResult()==1) {
                        if (getPresenter() != null)
                            getPresenter().onRequestMainSuccess(mainBean);
                        currentPage = 1;
                    }else {
                        if (getPresenter() != null)
                            getPresenter().onRequestMainFailue(null);
                    }
                }else {
                    if (getPresenter() != null)
                        getPresenter().onRequestMainFailue(new NetworkErrorException());

                }
            }

            @Override
            public void onFailure(Call<MainBean> call, Throwable t) {
                if (getPresenter() != null)
                    getPresenter().onRequestMainFailue(t);
            }
        });
    }
}
