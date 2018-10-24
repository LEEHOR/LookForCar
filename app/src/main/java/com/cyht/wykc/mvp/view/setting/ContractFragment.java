package com.cyht.wykc.mvp.view.setting;


import android.view.View;
import android.widget.TextView;

import com.cyht.wykc.R;
import com.cyht.wykc.common.EventData;
import com.cyht.wykc.mvp.contract.base.BaseContract;
import com.cyht.wykc.mvp.view.base.BaseApplication;
import com.cyht.wykc.mvp.view.base.BaseFragment;
import com.cyht.wykc.utils.ScreenUtils;
import com.cyht.wykc.widget.MyTittleBar.NormalTittleBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;


/**
 * Author： hengzwd on 2017/6/1.
 * Email：hengzwdhengzwd@qq.com
 */

public class ContractFragment extends BaseFragment {


    @BindView(R.id.tb_tittle)
    NormalTittleBar tbTittle;
    @BindView(R.id.contact_tv_tel)
    TextView contactTvTel;
    @BindView(R.id.contact_tv_email)
    TextView contactTvEmail;

    public ContractFragment() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void recieveEventBus(EventData eventData) {

    }

    @Override
    public BaseContract.presenter createPresenter() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_contract;
    }

    @Override
    public void initView() {
        tbTittle.setPadding(tbTittle.getPaddingLeft(), ScreenUtils.getStatusBarHeight(BaseApplication.mContext), tbTittle.getPaddingRight(), tbTittle.getPaddingBottom());
        tbTittle.getTvTittle().setText("联系我们");
        tbTittle.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SettingActivity)_mActivity).onBackPressedSupport();
            }
        });
    }

    @Override
    public void initData() {

    }

}
