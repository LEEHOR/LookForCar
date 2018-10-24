package com.cyht.wykc.mvp.view.main;

import com.cyht.wykc.common.EventData;
import com.cyht.wykc.mvp.contract.base.BaseContract;
import com.cyht.wykc.mvp.view.base.BaseFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Author： hengzwd on 2017/6/1.
 * Email：hengzwdhengzwd@qq.com
 */

public class VideoExhibitionFragment extends BaseFragment {
    @Override
    public BaseContract.presenter createPresenter() {
        return null;
    }

    @Override
    public int bindLayout() {
        return 0;
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void recieveEventBus(EventData eventData)
    {

    }
    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}
