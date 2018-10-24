package com.cyht.wykc.mvp.view;


import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.cyht.wykc.R;

import com.cyht.wykc.common.Constants;
import com.cyht.wykc.mvp.contract.MainActivityContract;
import com.cyht.wykc.mvp.contract.base.BaseContract;
import com.cyht.wykc.mvp.modles.bean.LoginBean;
import com.cyht.wykc.mvp.presenter.LoginPresenter;
import com.cyht.wykc.mvp.view.base.BaseActivity;
import com.cyht.wykc.mvp.view.base.BaseApplication;
import com.cyht.wykc.mvp.view.carselect.CarBrandFragement;
import com.cyht.wykc.mvp.view.main.MainFragment;
import com.cyht.wykc.mvp.view.setting.PersonalCenterFragment;
import com.cyht.wykc.mvp.view.setting.SettingActivity;
import com.cyht.wykc.umeng.RecieveUmengPushService;
import com.cyht.wykc.utils.ActivityManagerUtils;
import com.cyht.wykc.utils.DensityUtils;

import com.cyht.wykc.utils.PreferenceUtils;
import com.cyht.wykc.utils.SharedPreferencesUtils;
import com.cyht.wykc.widget.LoginAlertDialog;
import com.cyht.wykc.widget.UpdateVersion.CheckVersion;
import com.cyht.wykc.widget.bottomNavigation.MyBottomNavigation;
import com.socks.library.KLog;
import com.umeng.message.PushAgent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;


/**
 * Author： hengzwd on 2017/6/1.
 * Email：hengzwdhengzwd@qq.com
 */

public class MainActivity extends BaseActivity{
    @BindView(R.id.fl_container_main_fragment)
    FrameLayout flContainer;
    @BindView(R.id.bottom_navigation_main_fragment)
    MyBottomNavigation bottomNavigation;

    private int action = 0;//1:QQ,2:微信,3:新浪
    private LoginAlertDialog mDialog;
    private long exitTime = 0;
    private static final long INTERVAL_TIME = 2000;
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    private long TOUCH_TIME = 0;
    private int bottomNavigationPreposition = 0;
    private CheckVersion checkVersion;
    private SupportFragment[] mFragments = new SupportFragment[3];

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mFragments[0] = findFragment(MainFragment.class);
        mFragments[1] = findFragment(CarBrandFragement.class);
        mFragments[2] = findFragment(PersonalCenterFragment.class);
//        getSupportFragmentManager().beginTransaction().remove(mFragments[0])
//                .remove(mFragments[1])
//                .remove(mFragments[2])
//                .commit();
//        initView();
        KLog.e("onRestoreInstanceState");
    }


    @Override
    public BaseContract.presenter createPresenter() {
        return null;
    }

    @Override
    public int binLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
//        Eyes.setStatusBarLightMode(this, Color.WHITE);
//        Eyes.translucentStatusBar(this);//设置半透明statusbar
//        Eyes.translucentStatusBar(this,true);
        KLog.e("external:"+ Environment.getExternalStorageDirectory().getPath());
        mFragments[0] = MainFragment.newInstance();
        mFragments[1] = CarBrandFragement.newInstance();
        mFragments[2] = PersonalCenterFragment.newInstance();
        loadMultipleRootFragment(R.id.fl_container_main_fragment, 0, mFragments[0], mFragments[1], mFragments[2]);
        flContainer.setPadding(0, 0, 0, DensityUtils.dp2px(this, 49));
    }


    @Override
    public void initData() {
        if (getIntent().getExtras() != null) {
            String pushType=getIntent().getExtras().getString("pushType");
            if (!("").equals(pushType)&&pushType!=null) {
                if (pushType.equals("0")) {
                    Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                    intent.putExtra("to_fragment", Constants.LETTERSFRAGMENT);
                    startActivity(intent);
                }else if (pushType.equals("1")){
                    Intent intent = new Intent(MainActivity.this, ExtensionActivity.class);
                    intent.putExtra("msgId",getIntent().getExtras().getString("msgId"));
                    startActivity(intent);
                }
            }
        }

        bottomNavigation.setOnTabPositionListener(new MyBottomNavigation.OnTabPositionListener() {
            @Override
            public void onPositionTab(int position) {
                KLog.e("onPositionTab",position+"");
                if (position == 2 && !PreferenceUtils.contains(BaseApplication.mContext, Constants.USERNAME)) {
//                    showLoginDialog();
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivityForResult(intent,0);
                } else {
                    showFragment(position);

                }
            }
        });
        checkVersion();
    }

    private void checkVersion() {
        checkVersion=new CheckVersion(this);
        checkVersion.check();
    }

    private String getAppInfo() {
        try {
            String pkName = getPackageName();
            return getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
        } catch (Exception e) {
        }
        return null;
    }
    public void showFragment(int positon) {
        showHideFragment(mFragments[positon], mFragments[bottomNavigationPreposition]);
        bottomNavigationPreposition = positon;
        bottomNavigation.setSelect(positon);
    }

    @Override
    public void onBackPressedSupport() {
        if ((System.currentTimeMillis() - exitTime) > INTERVAL_TIME) {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.cyht_exit), Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            ActivityManagerUtils.getInstance().appExit();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==1) {
            ((PersonalCenterFragment) mFragments[2]).setPersonInfo(Constants.username, Constants.touxiang);
            showFragment(2);
        }
    }

}
