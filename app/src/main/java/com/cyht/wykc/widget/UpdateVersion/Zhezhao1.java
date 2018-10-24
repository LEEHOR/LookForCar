package com.cyht.wykc.widget.UpdateVersion;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.cyht.wykc.R;

public class Zhezhao1 {

    private Context mContext;
    private AlertDialog mDialog;
    private LinearLayout tipLayout;
    private Button ok;
    private Button cancel;
    private TextView content;
    private FrameLayout.LayoutParams lp;

    public Zhezhao1(Context context) {
        mContext = context;
        mDialog = new AlertDialog.Builder(mContext).create();
        Window window = mDialog.getWindow();
        window.setWindowAnimations(android.R.style.Animation_Dialog); // 添加动画

        tipLayout = new LinearLayout(context);
        lp = new FrameLayout.LayoutParams(dip2px(295), dip2px(197));
        lp.gravity = Gravity.CENTER_HORIZONTAL;
        tipLayout.setGravity(Gravity.CENTER_HORIZONTAL);
//		tipLayout.setBackgroundResource(context.getResources().getIdentifier("tishi_bg", "drawable", context.getPackageName()));
        tipLayout.setBackgroundResource(R.mipmap.tishi_bg);
        tipLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout layoutContainer = new LinearLayout(context);
        LayoutParams lpContainer = new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1.0f);
        layoutContainer.setPadding(dip2px(35), dip2px(15), dip2px(35), dip2px(7));
        layoutContainer.setGravity(Gravity.CENTER_HORIZONTAL);
        layoutContainer.setOrientation(LinearLayout.VERTICAL);
        tipLayout.addView(layoutContainer, lpContainer);

        ImageView icon = new ImageView(context);
        LayoutParams lpIcon = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lpIcon.bottomMargin = dip2px(7);
//		icon.setImageResource(context.getResources().getIdentifier("tishi_ico", "drawable", context.getPackageName()));
        icon.setImageResource(R.mipmap.tishi_ico);
        layoutContainer.addView(icon, lpIcon);

        content = new TextView(context);
        LayoutParams lpContent = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        content.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        content.setTextColor(Color.WHITE);
        layoutContainer.addView(content, lpContent);

        LinearLayout layoutBtnContainer = new LinearLayout(context);
        LayoutParams lpLayoutBtnContainer = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lpLayoutBtnContainer.bottomMargin = dip2px(15);
        layoutBtnContainer.setOrientation(LinearLayout.HORIZONTAL);
        tipLayout.addView(layoutBtnContainer, lpLayoutBtnContainer);

        cancel = new Button(context);
        LayoutParams lpCancel = new LayoutParams(dip2px(110), dip2px(35));
        cancel.setTextColor(Color.WHITE);
        cancel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
//		cancel.setBackgroundResource(context.getResources().getIdentifier("choice_left_button_selector", "drawable", context.getPackageName()));
        cancel.setBackgroundResource(R.drawable.choice_left_button_selector);
        layoutBtnContainer.addView(cancel, lpCancel);

        ok = new Button(context);
        LayoutParams lpOk = new LayoutParams(dip2px(110), dip2px(35));
        lpOk.leftMargin = dip2px(20);
        ok.setTextColor(Color.WHITE);
        ok.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
//		ok.setBackgroundResource(context.getResources().getIdentifier("choice_right_button_selector", "drawable", context.getPackageName()));
        ok.setBackgroundResource(R.drawable.choice_right_button_selector);
        layoutBtnContainer.addView(ok, lpOk);

        mDialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 设置消息
     *
     * @param message
     */
    public void setMessage(String message) {
        content.setText(message);
    }


    /**
     * 设置消息
     *
     * @param message
     * @param textSize
     * @param textColor
     */
    public void setMessage(String message, int textSize, int textColor) {
        content.setText(message);
        content.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        content.setTextColor(textColor);
    }

    /**
     * 设置消息
     *
     * @param message
     */
    public void setMessage(CharSequence message) {
        content.setText(message);
    }

    /**
     * 设置字体大小
     *
     * @param textSize
     */
    public void setTextSize(int textSize) {
        content.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
    }

    /**
     * 设置字体颜色
     *
     * @param textColor
     */
    public void setTextColor(int textColor) {
        content.setTextColor(textColor);
    }


    /**
     * 设置标题
     */

    public void setTittle(String tittle) {
        if (mDialog != null) {
            mDialog.setTitle(tittle);
        }
    }

    /**
     * 显示对话框
     */
    public void show() {
        if (mDialog != null) {
            mDialog.show();
            mDialog.setContentView(tipLayout, lp);
        }
    }

    /**
     * 关闭对话框
     */
    public void dismiss() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    /**
     * 设置右边按钮的点击事件
     *
     * @param text     右边按钮文本
     * @param listener 右边按钮的点击事件
     */
    public void setPositiveButton(String text, OnClickListener listener) {
        ok.setVisibility(View.VISIBLE);
        ok.setText(text);
        if (listener == null) {
            ok.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDialog != null) {
                        mDialog.dismiss();
                    }
                }
            });
        } else {
            ok.setOnClickListener(listener);
        }
    }

    /**
     * 设置左边按钮的点击事件
     *
     * @param text     左边按钮文本
     * @param listener 左边按钮点击事件
     */
    public void setNegativeButton(String text, OnClickListener listener) {
        cancel.setVisibility(View.VISIBLE);
        cancel.setText(text);
        if (listener == null) {
            cancel.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDialog != null) {
                        mDialog.dismiss();
                    }
                }
            });
        } else {
            cancel.setOnClickListener(listener);
        }
    }

    private int dip2px(float dipValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}