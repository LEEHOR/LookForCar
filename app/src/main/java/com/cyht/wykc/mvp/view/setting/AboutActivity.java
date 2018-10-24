package com.cyht.wykc.mvp.view.setting;


import android.view.KeyEvent;
import android.view.View;

import com.cyht.wykc.R;
import com.cyht.wykc.common.EventData;
import com.cyht.wykc.mvp.contract.base.BaseContract;
import com.cyht.wykc.mvp.view.base.BaseActivity;
import com.cyht.wykc.mvp.view.base.BaseApplication;
import com.cyht.wykc.mvp.view.base.BaseFragment;
import com.cyht.wykc.utils.ScreenUtils;
import com.cyht.wykc.widget.MyTittleBar.NormalTittleBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;


/**
 * Author： hengzwd on 2017/9/5.
 * Email：hengzwdhengzwd@qq.com
 */

public class AboutActivity extends BaseActivity {
    @BindView(R.id.tb_tittle)
    NormalTittleBar tbTittle;


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void recieveEventBus(EventData eventData) {

    }
    @Override
    public BaseContract.presenter createPresenter() {
        return null;
    }

    @Override
    public int binLayout() {
        return R.layout.fragment_about;
    }


    @Override
    public void initView() {
        tbTittle.setPadding(tbTittle.getPaddingLeft(), ScreenUtils.getStatusBarHeight(BaseApplication.mContext), tbTittle.getPaddingRight(), tbTittle.getPaddingBottom());
        tbTittle.getTvTittle().setText("关于我们");
        tbTittle.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressedSupport();
            }
        });
    }



    @Override
    public void initData() {

    }


}
