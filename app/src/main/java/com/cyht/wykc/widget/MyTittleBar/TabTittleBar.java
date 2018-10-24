package com.cyht.wykc.widget.MyTittleBar;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.cyht.wykc.widget.Tab1_2;

/**
 * Author： hengzwd on 2017/8/10.
 * Email：hengzwdhengzwd@qq.com
 */

public class TabTittleBar extends RelativeLayout {


    private Tab1_2 tab;
    private Button btnLeft;
    private Button btnRight;
    private Context context;
    private LayoutParams lpBtnRight;
    private LayoutParams lpBtnLeft;
    private LayoutParams lpTabcenter;

    public TabTittleBar(Context context) {
        this(context, null);
    }

    public TabTittleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
//		this.setMinimumHeight(dip2px(56));

        btnLeft = new Button(context);
//		lpBtnLeft = new LayoutParams(dip2px(50),dip2px(50));
        lpBtnLeft = new LayoutParams(dip2px(20),dip2px(20));
        lpBtnLeft.addRule(RelativeLayout.CENTER_VERTICAL);
        lpBtnLeft.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        lpBtnLeft.leftMargin=dip2px(15);
        btnLeft.setBackground(null);
        btnLeft.setPadding(0, 0, 0, 0);
        btnLeft.setVisibility(View.INVISIBLE);
        this.addView(btnLeft, lpBtnLeft);


        //tabview
        tab = new Tab1_2(context);
        lpTabcenter = new LayoutParams(dip2px(150),dip2px(25));
        lpTabcenter.addRule(RelativeLayout.CENTER_IN_PARENT);
        this.addView(tab, lpTabcenter);



        btnRight = new Button(context);
        lpBtnRight = new LayoutParams(dip2px(20),dip2px(20));
        lpBtnRight.addRule(RelativeLayout.CENTER_VERTICAL);
        lpBtnRight.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lpBtnRight.rightMargin=dip2px(15);
        btnRight.setBackground(null);
        btnRight.setPadding(0, 0, 0, 0);
        btnRight.setVisibility(View.INVISIBLE);
        this.addView(btnRight, lpBtnRight);

    }


    /**
     * 返回tabview
     * @return
     */
    public Tab1_2 getTabView() {
        if (tab != null) {
            return tab;
        }
        return null;
    }

    /**
     * 设置左边图标是否显示
     *
     * @param state true显示 false隐藏，默认隐藏
     */
    public void setLeftIconState(boolean state) {
        if (state) {
            btnLeft.setVisibility(View.VISIBLE);
        } else {
            btnLeft.setVisibility(View.GONE);
        }
    }

    /**
     * 设置左侧按钮
     *
     * @param resid         背景资源id
     * @param clickListener 点击事件
     */
    public void setLeftIcon(int resid, OnClickListener clickListener) {
        btnLeft.setVisibility(View.VISIBLE);
        btnLeft.setBackgroundResource(resid);
        if (clickListener != null) {
            btnLeft.setOnClickListener(clickListener);
        }
    }

    /**
     * 设置左侧按钮
     *
     * @param resid         背景资源id
     * @param width         宽度
     * @param height        高度
     * @param clickListener 点击事件
     */
    public void setLeftIcon(int resid, int width, int height, OnClickListener clickListener) {
        btnLeft.setVisibility(View.VISIBLE);
        btnLeft.setBackgroundResource(resid);
        lpBtnLeft.width = dip2px(width);
        lpBtnLeft.height = dip2px(height);
        if (clickListener != null) {
            btnLeft.setOnClickListener(clickListener);
        }
    }

    /**
     * 设置左侧按钮
     *
     * @param text          文本
     * @param clickListener 点击事件
     */
    public void setLeftIcon(String text, OnClickListener clickListener) {
        btnLeft.setVisibility(View.VISIBLE);
        btnLeft.setText(text);
        if (clickListener != null) {
            btnLeft.setOnClickListener(clickListener);
        }
    }

    /**
     * 设置左侧按钮
     *
     * @param text          文本
     * @param textSize      文本大小
     * @param textColor     文本颜色
     * @param clickListener 点击事件
     */
    public void setLeftIcon(String text, int textSize, int textColor, OnClickListener clickListener) {
        btnLeft.setVisibility(View.VISIBLE);
        btnLeft.setText(text);
        btnLeft.setTextColor(textColor);
        btnLeft.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        if (clickListener != null) {
            btnLeft.setOnClickListener(clickListener);
        }
    }

    /**
     * 设置右边图标是否显示
     *
     * @param state true显示 false隐藏，默认隐藏
     */
    public void setRightIconState(boolean state) {
        if (state) {
            btnRight.setVisibility(View.VISIBLE);
        } else {
            btnRight.setVisibility(View.GONE);
        }
    }

    /**
     * 设置右侧按钮
     *
     * @param resid         背景资源id
     * @param clickListener 点击事件
     */
    public void setRightIcon(int resid, OnClickListener clickListener) {
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setBackgroundResource(resid);
        if (clickListener != null) {
            btnRight.setOnClickListener(clickListener);
        }
    }

    /**
     * 设置右侧按钮
     *
     * @param resid         背景资源id
     * @param width         宽度
     * @param height        高度
     * @param clickListener 点击事件
     */
    public void setRightIcon(int resid, int width, int height, OnClickListener clickListener) {
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setBackgroundResource(resid);
        lpBtnRight.width = dip2px(width);
        lpBtnRight.height = dip2px(height);
        if (clickListener != null) {
            btnRight.setOnClickListener(clickListener);
        }
    }

    /**
     * 设置右侧按钮
     *
     * @param text          文本
     * @param clickListener 点击事件
     */
    public void setRightIcon(String text, OnClickListener clickListener) {
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setText(text);
        if (clickListener != null) {
            btnRight.setOnClickListener(clickListener);
        }
    }

    /**
     * 设置右侧按钮
     *
     * @param text          文本
     * @param textSize      文本大小
     * @param textColor     文本颜色
     * @param clickListener 点击事件
     */
    public void setRightIcon(String text, int textSize, int textColor, OnClickListener clickListener) {
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setText(text);
        btnRight.setTextColor(textColor);
        btnRight.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        if (clickListener != null) {
            btnRight.setOnClickListener(clickListener);
        }
    }


    private int dip2px(float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


}
