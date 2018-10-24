package com.cyht.wykc.widget.MyTittleBar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cyht.wykc.R;
import com.cyht.wykc.widget.Tab1_2;

/**
 * Author： hengzwd on 2017/8/10.
 * Email：hengzwdhengzwd@qq.com
 */

public class NormalTittleBar extends RelativeLayout {


    private int inflateView;
    private TextView tvContent;
    private EditText etSearch;
    private Context context;
    private FrameLayout.LayoutParams layoutParams;
    private View view;

    public NormalTittleBar(Context context) {
        this(context, null);
    }

    public NormalTittleBar(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }


    public NormalTittleBar(Context context,AttributeSet attributeSet, int defstyleAttr)
    {
        super(context,attributeSet,defstyleAttr);
        this.context=context;
        TypedArray a = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.NormalTittleBar, 0, 0);
        inflateView=a.getResourceId(R.styleable.NormalTittleBar_layout_inflate,R.layout.nomal_tittle_bar);
        LayoutInflater inflater = LayoutInflater.from(getContext());

        view=  inflater.inflate(inflateView, this, true);
    }


    /**
     * 获取标题
     */

    public TextView getTvTittle()
    {
        return (TextView) view.findViewById(R.id.tv_center);
    }

    public TextView getTvTittle(int resId)
    {
        return (TextView) view.findViewById(resId);
    }




    public ImageView getLeftIcon()
    {
        return (ImageView) view.findViewById(R.id.iv_left);
    }

    public ImageView getLeftIcon(int resId)
    {
        return (ImageView) view.findViewById(resId);
    }






    public TextView getRightTV()
    {
        return (TextView) view.findViewById(R.id.tv_right);
    }

    public TextView getRightTV(int resId)
    {
        return (TextView) view.findViewById(resId);
    }


    public ImageView getRightIcon()
    {
        return (ImageView) view.findViewById(R.id.iv_right);
    }

    public ImageView getRightIcon(int resId)
    {
        return (ImageView) view.findViewById(resId);
    }

    private int dip2px(float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
