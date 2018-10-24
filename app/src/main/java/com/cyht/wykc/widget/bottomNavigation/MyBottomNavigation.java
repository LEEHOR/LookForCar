package com.cyht.wykc.widget.bottomNavigation;

import android.content.Context;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cyht.wykc.R;


/**
 * Author： hengzwd on 2017/9/12.
 * Email：hengzwdhengzwd@qq.com
 */

public class MyBottomNavigation extends RelativeLayout {
    private int inflateView;
    private Context context;
    private View view;

    private OnTabPositionListener ontabpostionListener;

    public MyBottomNavigation(Context context) {
        this(context, null);
    }

    public MyBottomNavigation(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyBottomNavigation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyBottomNavigation, 0, 0);
        inflateView = a.getResourceId(R.styleable.MyBottomNavigation_layout_inflate0, R.layout.layout_mynavigation);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        view = inflater.inflate(R.layout.layout_mynavigation, this, true);
        initViewList();
    }

    private void initViewList() {

        getLeftLL().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ontabpostionListener.onPositionTab(0);
            }
        });
        getCenterLL().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ontabpostionListener.onPositionTab(1);
            }
        });
        getRightLL().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ontabpostionListener.onPositionTab(2);
            }
        });
    }

    public void setOnTabPositionListener(OnTabPositionListener ontablistener) {
        ontabpostionListener = ontablistener;
        ontabpostionListener.onPositionTab(0);
    }

    public LinearLayout getLeftLL() {
        return (LinearLayout) view.findViewById(R.id.ll_left);
    }

    public LinearLayout getCenterLL() {
        return (LinearLayout) view.findViewById(R.id.ll_center);
    }

    public LinearLayout getRightLL() {
        return (LinearLayout) view.findViewById(R.id.ll_right);
    }


    public ImageView getLeftIV() {
        return (ImageView) view.findViewById(R.id.iv_left);
    }

    public TextView getLeftTV() {
        return (TextView) view.findViewById(R.id.tv_left);
    }

    public ImageView getCenterIV() {
        return (ImageView) view.findViewById(R.id.iv_center);
    }

    public TextView getCenterTV() {
        return (TextView) view.findViewById(R.id.tv_center);
    }

    public ImageView getRightIV() {
        return (ImageView) view.findViewById(R.id.iv_right);
    }

    public TextView getRightTV() {
        return (TextView) view.findViewById(R.id.tv_right);
    }


    public void setSelect(int positon) {
        if (positon == 0) {
            getLeftIV().setImageResource(R.mipmap.main_pressed);
            getLeftTV().setTextColor(Color.parseColor("#45C6FD"));
            getCenterIV().setImageResource(R.mipmap.car_nopress);
            getCenterTV().setTextColor(getResources().getColor(R.color.gray));
            getRightIV().setImageResource(R.mipmap.person_nopress);
            getRightTV().setTextColor(getResources().getColor(R.color.gray));

        } else if (positon == 1) {
            getLeftIV().setImageResource(R.mipmap.main_nopress);
            getLeftTV().setTextColor(getResources().getColor(R.color.gray));
            getCenterIV().setImageResource(R.mipmap.car_pressed);
            getCenterTV().setTextColor(Color.parseColor("#45C6FD"));
            getRightIV().setImageResource(R.mipmap.person_nopress);
            getRightTV().setTextColor(getResources().getColor(R.color.gray));
        } else if (positon == 2) {
            getLeftIV().setImageResource(R.mipmap.main_nopress);
            getLeftTV().setTextColor(getResources().getColor(R.color.gray));
            getCenterIV().setImageResource(R.mipmap.car_nopress);
            getCenterTV().setTextColor(getResources().getColor(R.color.gray));
            getRightIV().setImageResource(R.mipmap.person_pressed);
            getRightTV().setTextColor(Color.parseColor("#45C6FD"));
        }
    }


    public interface OnTabPositionListener {
        void onPositionTab(int position);
    }
}
