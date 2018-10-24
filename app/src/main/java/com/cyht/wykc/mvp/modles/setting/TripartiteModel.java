package com.cyht.wykc.mvp.modles.setting;

import com.cyht.wykc.mvp.modles.base.BaseModel;
import com.cyht.wykc.mvp.contract.setting.TripartiteContract;

/**
 * Author： hengzwd on 2017/6/1.
 * Email：hengzwdhengzwd@qq.com
 */

public class TripartiteModel extends BaseModel<TripartiteContract.Presenter> implements TripartiteContract.Model {
    public TripartiteModel(TripartiteContract.Presenter mpresenter) {
        super(mpresenter);
    }

    @Override
    public void start() {

    }

    @Override
    public void bind() {

    }

    @Override
    public void unbind() {

    }
}
