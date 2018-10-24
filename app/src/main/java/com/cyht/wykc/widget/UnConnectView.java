package com.cyht.wykc.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cyht.wykc.R;
import com.cyht.wykc.mvp.view.base.BaseApplication;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


public class UnConnectView extends RelativeLayout {

    private Context mContext;
//    private Button mBtnLoading;
    private LinearLayout mLayoutLoading;
    private TextView mTvLoading;
    public UnConnectView(Context context) {
        this(context, null);
    }

    public UnConnectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UnConnectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.unconnect,this,true);
//        mBtnLoading = (Button) view.findViewById(R.id.loadint_button);
        mLayoutLoading = (LinearLayout) view.findViewById(R.id.loading_layout);
        mTvLoading = (TextView) view.findViewById(R.id.loading_text);
        mLayoutLoading.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if(android.os.Build.VERSION.SDK_INT>10){
                    intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                }else{
                    intent = new Intent();
                    ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
                    intent.setComponent(component);
                    intent.setAction("android.intent.action.VIEW");
                }
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                BaseApplication.mContext.startActivity(intent);
                close();
            }
        });
    }


    public void close(){
        this.setVisibility(View.GONE);
    }

    public void show(OnClickListener clickListener){
        this.setVisibility(View.VISIBLE);
//        mBtnLoading.setVisibility(View.VISIBLE);
        if (clickListener != null)
            mLayoutLoading.setOnClickListener(clickListener);
    }

    public void show(String text){//OnClickListener clickListener
        this.setVisibility(View.VISIBLE);
        mTvLoading.setText(text);
//        if (clickListener != null)
        mLayoutLoading.setOnClickListener(null);
    }

    public void show(){
        this.setVisibility(View.VISIBLE);
    }
    public boolean isShow(){
        if (this.getVisibility() == View.VISIBLE)
            return true;
        else
            return false;
    }
}
