package com.cyht.wykc.mvp.view.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.cyht.wykc.utils.ActivityManagerUtils;
import com.umeng.message.PushAgent;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;


public abstract class BaseSwipeActivity extends SwipeBackActivity {

	/** 日志输出标志 **/
	protected final String TAG = this.getClass().getSimpleName();

	/** 当前Activity渲染的视图View **/
	private View mContextView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View mView = bindView();
		if (null == mView) {
			mContextView = LayoutInflater.from(this)
					.inflate(bindLayout(), null);
		} else
			mContextView = mView;
		setContentView(mContextView);
		if (null != getIntent() && getIntent().getExtras() != null){
			initParms(getIntent().getExtras());
		}
		initBroadCast();
		initPre();
		initView();
		initEvent();
		initData();
		ActivityManagerUtils.getInstance().addActivity(this);
		PushAgent.getInstance(BaseApplication.mContext).onAppStart();

	}

	/**
	 * [初始化参数]
	 *
	 * @param parms
	 */
	protected void initParms(Bundle parms){}

	/**
	 * [初始化数据]
	 */
	protected void initPre(){}

	/**
	 * 初始化广播
	 */
	protected void initBroadCast() {}

	/**
	 * [绑定视图]
	 *
	 * @return
	 */
	protected View bindView(){return null;}

	/**
	 * [绑定布局]
	 *
	 * @return
	 */
	protected abstract int bindLayout();

	/**
     * 初始化控件
     */
	protected abstract void initView();
    
    /**
     * 初始化控件
     */
    protected abstract void initEvent();
    
    /**
     * 初始化数据
     */
    protected void initData(){}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityManagerUtils.getInstance().removeActivity(this);
	}
}
