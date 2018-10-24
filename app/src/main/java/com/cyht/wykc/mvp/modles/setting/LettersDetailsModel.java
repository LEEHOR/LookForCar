package com.cyht.wykc.mvp.modles.setting;

import com.cyht.wykc.mvp.contract.setting.LetterDetailsContract;
import com.cyht.wykc.mvp.modles.HttpHelper;
import com.cyht.wykc.mvp.modles.base.BaseModel;
import com.cyht.wykc.mvp.modles.bean.MsgBean;
import com.cyht.wykc.mvp.modles.bean.ResultBean;
import com.socks.library.KLog;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author： hengzwd on 2017/9/18.
 * Email：hengzwdhengzwd@qq.com
 */

public class LettersDetailsModel extends BaseModel<LetterDetailsContract.Presenter> implements LetterDetailsContract.Modle {
    public LettersDetailsModel(LetterDetailsContract.Presenter mpresenter) {
        super(mpresenter);
    }

    @Override
    public void start() {

    }

    @Override
    public void updateMsg(final Map map) {

    }
}
