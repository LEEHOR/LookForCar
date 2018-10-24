package com.cyht.wykc.widget;

import android.content.Context;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;


/**
 * Author： hengzwd on 2017/8/11.
 * Email：hengzwdhengzwd@qq.com
 */

public class MyDrawLayout extends DrawerLayout {

    public MyDrawLayout(Context context) {
        super(context,null);
    }

    public MyDrawLayout(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    public MyDrawLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch(ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                final float x = ev.getX();
                final float y = ev.getY();
                final View touchedView = findTopChildUnder((int) x, (int) y);
                if (touchedView != null && isContentView(touchedView)&& this.isDrawerOpen(GravityCompat.END)) {
                    return false;
                }
                break;
            default:
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    boolean isContentView(View child) {
        return ((LayoutParams) child.getLayoutParams()).gravity == Gravity.NO_GRAVITY;
    }
    /**
     * 判断点击位置是否位于相应的View内
     * @param x
     * @param y
     * @return
     */
    public View findTopChildUnder(int x, int y) {
        final int childCount = getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            final View child = getChildAt(i);
            if (x >= child.getLeft() && x < child.getRight() &&
                    y >= child.getTop() && y < child.getBottom()) {
                return child;
            }
        }
        return null;
    }

}
