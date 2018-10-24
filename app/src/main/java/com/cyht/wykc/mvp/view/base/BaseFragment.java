package com.cyht.wykc.mvp.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.cyht.wykc.R;
import com.cyht.wykc.mvp.contract.base.BaseContract;
import com.cyht.wykc.utils.KeyBoardUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Author： hengzwd on 2017/6/1.
 * Email：hengzwdhengzwd@qq.com
 */

public abstract class BaseFragment<P extends BaseContract.presenter> extends SupportFragment{

    /** 日志输出标志 **/
    protected final String TAG = this.getClass().getSimpleName();

    protected  boolean hasStarted =false;
    public P mPresenter;

    private  Unbinder unbinder;
    public abstract P createPresenter();
    public abstract  int bindLayout();
    public abstract  void initView();
    public abstract  void initData();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter=createPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(bindLayout(),container,false);
        unbinder=ButterKnife.bind(this,view);
        UpdateUI(view.getRootView());
        initView();
        initData();
        EventBus.getDefault().register(this);
        return view;
    }


    public void showContent() {
    }

    public void showError(@Nullable Throwable e) {
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }


    public void UpdateUI(View view){//解决所有页面   touch所有edittext以外view，自动隐藏键盘  通过decorview可以获取所有子view，循环判断设置touch事件
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View arg0, MotionEvent arg1) {
                    // TODO Auto-generated method stub
                    KeyBoardUtils.hideKeybord(arg0,getActivity());
                    return false;
                }
            });
        }
        if (view instanceof ViewGroup) {
            for(int i = 0; i < ((ViewGroup) view).getChildCount(); i++){
                View innerView = ((ViewGroup) view).getChildAt(i);
                UpdateUI(innerView);
            }
        }
    }


}
