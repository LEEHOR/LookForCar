package com.cyht.wykc.mvp.modles.setting;

import com.cyht.wykc.common.Constants;
import com.cyht.wykc.mvp.contract.setting.LettersContract;
import com.cyht.wykc.mvp.modles.HttpHelper;
import com.cyht.wykc.mvp.modles.base.BaseModel;
import com.cyht.wykc.mvp.modles.bean.CollectionBean;
import com.cyht.wykc.mvp.modles.bean.MsgBean;
import com.cyht.wykc.mvp.modles.bean.ResultBean;
import com.cyht.wykc.mvp.view.base.BaseApplication;
import com.cyht.wykc.utils.PreferenceUtils;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author： hengzwd on 2017/9/5.
 * Email：hengzwdhengzwd@qq.com
 */

public class LettersModel extends BaseModel<LettersContract.Presenter> implements LettersContract.Modle {

    private  int currentPage = 0;
    public LettersModel(LettersContract.Presenter mpresenter) {
        super(mpresenter);
    }

    @Override
    public void start() {

    }

    @Override
    public void requestLetters() {
        Map map =  new HashMap();
        KLog.e("requestLetters11111111111");
//        map.put("sessionid", "59b21a07d1e3408485bd1bce53daf012");
        map.put("sessionid", Constants.sessionid==null?PreferenceUtils.getPrefString(BaseApplication.mContext,Constants.SESSION_ID,null):Constants.sessionid);
        map.put("system",Constants.ANDROID) ;
//        map.put("pageNo",currentPage+"") ;
        map.put("pageNo","0") ;
        map.put("pageSize",Constants.PAGESIZE+"") ;
        HttpHelper.getInstance().initService().getMsgList(map).enqueue(new Callback<MsgBean>() {
            @Override
            public void onResponse(Call<MsgBean> call, Response<MsgBean> response) {
                if (response.isSuccessful()) {
                    MsgBean msgBean=response.body();
                    if (msgBean.getResult()==1) {
                        if (getPresenter() != null) {
                            getPresenter().onRequestSuccess(msgBean.getData().getMsgList());
                            currentPage=1;
                        }
                    }else {
                        if (getPresenter() != null) {
                            getPresenter().onrequestFailue(null);
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<MsgBean> call, Throwable t) {
                if (getPresenter() != null) {
                    getPresenter().onrequestFailue(t);
                }
            }
        });

    }

    @Override
    public void loadmore() {
        Map map =  new HashMap();
        map.put("sessionid", Constants.sessionid);
        map.put("system",Constants.ANDROID) ;
        map.put("pageNo",currentPage+"") ;
        map.put("pageSize",Constants.PAGESIZE+"") ;
        HttpHelper.getInstance().initService().getMsgList(map).enqueue(new Callback<MsgBean>() {
            @Override
            public void onResponse(Call<MsgBean> call, Response<MsgBean> response) {
                if (response.isSuccessful()) {
                    MsgBean msgBean=response.body();
                    if (msgBean.getResult()==1) {
                        if (getPresenter() != null) {
                            getPresenter().onloadmoreSuccess(msgBean.getData().getMsgList());
                            currentPage++;
                        }
                    }else {
                        if (getPresenter() != null) {
                            getPresenter().onloadmoreFailue(null);
                        }
                    }
                }else {
                    if (getPresenter() != null) {
                        getPresenter().onloadmoreFailue(null);
                    }
                }
            }
            @Override
            public void onFailure(Call<MsgBean> call, Throwable t) {
                if (getPresenter() != null) {
                    getPresenter().onloadmoreFailue(t);
                }
            }
        });
    }

    @Override
    public void updateMsg(final Map map) {

        HttpHelper.getInstance().initService().updateMsg(map).enqueue(new Callback<ResultBean>() {
            @Override
            public void onResponse(Call<ResultBean> call, Response<ResultBean> response) {
                if (response.isSuccessful()) {
                    ResultBean resultBean=response.body();
                    if (resultBean.getResult()==1) {
                        if (getPresenter() != null) {
                            getPresenter().updateSuccess((String) map.get("msgid"));
                        }
                    }else {
                        getPresenter().updateFailure(null);
                    }
                }else {
                    getPresenter().updateFailure(null);
                }
            }

            @Override
            public void onFailure(Call<ResultBean> call, Throwable t) {
                if (getPresenter() != null) {
                    getPresenter().updateFailure(t);
                    KLog.e("Throwable:"+t.getMessage());
                }
            }
        });
    }
}
