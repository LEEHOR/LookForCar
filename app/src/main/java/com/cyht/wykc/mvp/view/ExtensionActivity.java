package com.cyht.wykc.mvp.view;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.cyht.wykc.R;
import com.cyht.wykc.mvp.contract.ExtensionContract;
import com.cyht.wykc.mvp.contract.base.BaseContract;
import com.cyht.wykc.mvp.modles.bean.ExtensionBean;
import com.cyht.wykc.mvp.presenter.ExtensionPresenter;
import com.cyht.wykc.mvp.view.adapter.ExtensionAdapter;
import com.cyht.wykc.mvp.view.base.BaseActivity;
import com.cyht.wykc.mvp.view.base.BaseApplication;
import com.cyht.wykc.utils.NetUtil;
import com.cyht.wykc.utils.ScreenUtils;
import com.socks.library.KLog;


import butterknife.BindView;

/**
 * Author： hengzwd on 2017/11/8.
 * Email：hengzwdhengzwd@qq.com
 */

public class ExtensionActivity extends BaseActivity<ExtensionContract.Presenter> implements ExtensionContract.view {


    @BindView(R.id.rv_extension)
    RecyclerView rvExtension;
    private ExtensionAdapter extensionAdapter;
    private LinearLayoutManager mlinearlayoutmanager;
    private ExtensionBean.DataEntity dataEntity;

    @Override
    public ExtensionContract.Presenter createPresenter() {
        return new ExtensionPresenter(this);
    }

    @Override
    public int binLayout() {
        return R.layout.activity_extension;
    }

    @Override
    public void initData() {
        String msgId = getIntent().getExtras().getString("msgId");
        mlinearlayoutmanager = new LinearLayoutManager(BaseApplication.mContext);
        extensionAdapter = new ExtensionAdapter(BaseApplication.mContext);
        rvExtension.setLayoutManager(mlinearlayoutmanager);
        rvExtension.setAdapter(extensionAdapter);
        mPresenter.requestExtension(msgId);
    }

    @Override
    public void initView() {
        rvExtension.setPadding(rvExtension.getPaddingLeft(), ScreenUtils.getStatusBarHeight(BaseApplication.mContext), rvExtension.getPaddingRight(), rvExtension.getPaddingBottom());
    }

    @Override
    public void showLoading() {

    }


    @Override
    public void onRequestSuccess(ExtensionBean.DataEntity dataEntity) {
        extensionAdapter.setDataEntity(dataEntity);
        extensionAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestFailure(Throwable throwable) {
        KLog.e("onrequestFailue:");
        if (!NetUtil.checkNetWork(BaseApplication.mContext)) {
            Toast.makeText(BaseApplication.mContext,"网络连接错误",Toast.LENGTH_SHORT);
        }else {
            Toast.makeText(BaseApplication.mContext,"访问出错",Toast.LENGTH_SHORT);
        }
        if (throwable != null) {
            KLog.e("throwable:"+throwable.getMessage());
        }
    }
}
