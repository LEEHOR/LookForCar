package com.cyht.wykc.mvp.view.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.cyht.wykc.R;
import com.cyht.wykc.mvp.contract.base.BaseContract;
import com.cyht.wykc.mvp.contract.videoplay.TBSContract;
import com.cyht.wykc.mvp.view.LaunchActivity;
import com.cyht.wykc.mvp.view.LoginActivity;
import com.cyht.wykc.utils.ActivityManagerUtils;
import com.cyht.wykc.utils.KeyBoardUtils;
import com.cyht.wykc.utils.PermissionUtils;
import com.cyht.wykc.utils.flyn.Eyes;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.OnKeyboardListener;
import com.socks.library.KLog;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Author： hengzwd on 2017/6/1.
 * Email：hengzwdhengzwd@qq.com
 */

public abstract class BaseActivity<P extends BaseContract.presenter> extends SupportActivity {

    /**
     * 日志输出标志
     **/
    protected final String TAG = this.getClass().getSimpleName();

    public P mPresenter;
    private Unbinder unbinder;

    public abstract P createPresenter();

    public abstract int binLayout();

    public abstract void initView();

    public abstract void initData();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        setContentView(binLayout());
        KeyBoardUtils.UpdateUI(getWindow().getDecorView().getRootView(), BaseApplication.mContext);
//        Eyes.setStatusBarLightMode(this,Color.TRANSPARENT);
        ImmersionBar.with(this)
                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .init();
        getWindow().setBackgroundDrawableResource(R.drawable.bg_fff_background);
//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
        unbinder = ButterKnife.bind(this);
        ActivityManagerUtils.getInstance().addActivity(this);
        initView();
        initData();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (!(this instanceof LaunchActivity)) {
            if (!PermissionUtils.isPermissionsGranted(this, new int[]{
                    PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE,
                    PermissionUtils.CODE_READ_EXTERNAL_STORAGE})) {
                Intent intent = new Intent(this, LaunchActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(intent);
                ActivityManagerUtils.getInstance().appExit();
                KLog.e("onResume","00000000000");
            }
        }
    }

    public void showContent() {
    }

    public void showError(@Nullable Throwable e) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        ActivityManagerUtils.getInstance().removeActivity(this);
        ImmersionBar.with(this).destroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }
}
