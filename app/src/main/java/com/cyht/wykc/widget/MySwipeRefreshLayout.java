package com.cyht.wykc.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.socks.library.KLog;

/**
 * Author： hengzwd on 2017/8/18.
 * Email：hengzwdhengzwd@qq.com
 */

public class MySwipeRefreshLayout extends SwipeRefreshLayout {

    public MySwipeRefreshLayout(Context context) {
        super(context,null);
    }


    public MySwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        return super.onTouchEvent(ev);
    }

    float  x=0.0f,y=0.0f;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x=ev.getX();
//                KLog.e("x:"+x);
                y=ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
//                KLog.e("x:"+x);
//                KLog.e("event.getRawX():"+ev.getRawX());

                if (Math.abs(ev.getRawX()-x)>Math.abs(ev.getRawY()-y)) {
                    return false;
                }

        }
        return super.onInterceptTouchEvent(ev);
    }



}
