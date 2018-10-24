package com.cyht.wykc.widget.MyTittleBar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cyht.wykc.R;
import com.cyht.wykc.mvp.contract.main.MainContract;
import com.socks.library.KLog;

/**
 * Author： hengzwd on 2017/8/10.
 * Email：hengzwdhengzwd@qq.com
 */

public class SearchTittleBar extends FrameLayout {


    private int inflateView;
    private TextView tvContent;
    private EditText etSearch;
    private Context context;
    private LayoutParams layoutParams;
    private View view;

    public SearchTittleBar(Context context) {
        this(context, null);
    }

    public SearchTittleBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);

    }

    public SearchTittleBar(Context context,AttributeSet attributeSet, int defstyleAttr)
    {
        super(context,attributeSet,defstyleAttr);
        this.context=context;
        TypedArray a = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.SearchTittleBar, 0, 0);
        inflateView=a.getResourceId(R.styleable.SearchTittleBar_inflate,R.layout.search_main);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        view=  inflater.inflate(inflateView, this, true);
    }

    /**
     * 获取textview
     */

    public TextView getTvContent()
    {
        return (TextView) view.findViewById(R.id.tv_cancle);
    }
    public TextView getTvContent(int resId)
    {
        return (TextView) view.findViewById(resId);
    }

    /**
     * 获取editview
     */

    public TextView getSearch()
    {
        return (TextView) view.findViewById(R.id.tv_search);
    }

    public EditText getSearch(int resId)
    {
        return (EditText) view.findViewById(resId);
    }

}
