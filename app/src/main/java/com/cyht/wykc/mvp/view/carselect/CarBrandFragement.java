package com.cyht.wykc.mvp.view.carselect;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.cyht.wykc.R;
import com.cyht.wykc.common.Constants;
import com.cyht.wykc.common.EventData;
import com.cyht.wykc.mvp.contract.Interface.OnPositionClickListener;
import com.cyht.wykc.mvp.contract.carselect.CarBrandContract;
import com.cyht.wykc.mvp.presenter.carselect.CarBrandPresenter;
import com.cyht.wykc.mvp.view.CarSearchActivity;
import com.cyht.wykc.mvp.view.base.BaseApplication;
import com.cyht.wykc.mvp.view.base.BaseFragment;
import com.cyht.wykc.utils.ScreenUtils;
import com.cyht.wykc.widget.MyTittleBar.TabTittleBar;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Author： hengzwd on 2017/6/1.
 * Email：hengzwdhengzwd@qq.com
 */

public class CarBrandFragement extends BaseFragment<CarBrandContract.Presenter> implements CarBrandContract.View {


    @BindView(R.id.cyht_biaotilan)
    TabTittleBar cyhtBiaotilan;
    @BindView(R.id.fl_container_brandfragment)
    FrameLayout flContainerBrandfragment;
    @BindView(R.id.content_frame)
    LinearLayout contentFrame;
    @BindView(R.id.status_bar_fill)
    View statusBarFill;
    private int NavigationPreposition;

    private SupportFragment[] mFragments = new SupportFragment[2];

    public static CarBrandFragement newInstance() {
        return new CarBrandFragement();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        KLog.e("onViewStateRestored");
        if (savedInstanceState != null) {
            mFragments[0] = findFragment(PassengerCarFragment.class);
            mFragments[1] = findFragment(CommercialCarFragment.class);
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void recieveEventBus(EventData eventData) {

        if (eventData.to== Constants.CARBRANDFRAGMENT&&eventData.from==Constants.MAINFRAGMENT) {
            int toF =eventData.brandListEntity.getType()==1?0:1;
            cyhtBiaotilan.getTabView().setSelected(toF);
            if (toF==0) {
                EventBus.getDefault().postSticky(new EventData(Constants.CARBRANDFRAGMENT,Constants.PASSENGERCARFRAGMENT,eventData.brandListEntity));
            }else {
                EventBus.getDefault().postSticky(new EventData(Constants.CARBRANDFRAGMENT,Constants.COMMERCIALCARFRAGMENT,eventData.brandListEntity));
            }

        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void loadFragment() {

    }

    @Override
    public void toggleDrawer() {
    }

    @Override
    public CarBrandContract.Presenter createPresenter() {
        return new CarBrandPresenter(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_carbrand;
    }


    @Override
    public void initView() {
        mFragments[0] = PassengerCarFragment.newInstance();
        mFragments[1] = CommercialCarFragment.newInstance();
        loadMultipleRootFragment(R.id.fl_container_brandfragment, 0, mFragments[0], mFragments[1]);
        cyhtBiaotilan.setRightIcon(R.drawable.cyht_search_selector, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_mActivity, CarSearchActivity.class);
                _mActivity.startActivity(intent);
            }
        });
        statusBarFill.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtils.getStatusBarHeight(BaseApplication.mContext)));
        cyhtBiaotilan.getTabView().setOnPositionClickListener(new OnPositionClickListener() {
            @Override
            public void setOnPositionClick(int position) {
                KLog.e("NavigationPreposition:" + NavigationPreposition);
                KLog.e("position:" + position);
                if (NavigationPreposition != position) {
                    showHideFragment(mFragments[position], mFragments[NavigationPreposition]);
                    NavigationPreposition = position;
                }
                switch (NavigationPreposition) {
                    case 0:
                        EventBus.getDefault().postSticky(new EventData(Constants.PASSENGERCARFRAGMENT));
                        break;
                    case 1:
                        EventBus.getDefault().postSticky(new EventData(Constants.COMMERCIALCARFRAGMENT));
                        break;
                }
            }
        });
        cyhtBiaotilan.getTabView().setSelected(0);


    }

    @Override
    public void initData() {
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}
