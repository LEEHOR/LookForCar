package com.cyht.wykc.utils;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.cyht.wykc.mvp.view.base.BaseActivity;

//打开或关闭软键盘
public class KeyBoardUtils {

    private KeyBoardUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public static void showKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     * @param v 获取Token的View
     * @param mContext  上下文
     */
    public static void hideKeybord(View v, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }


    public static  void UpdateUI(View view, final Context mContext){//解决所有页面   touch所有edittext以外view，自动隐藏键盘  通过decorview可以获取所有子view，循环判断设置touch事件
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View arg0, MotionEvent arg1) {
                    // TODO Auto-generated method stub
                    KeyBoardUtils.hideKeybord(arg0,mContext);
                    return false;
                }
            });
        }
        if (view instanceof ViewGroup) {
            for(int i = 0; i < ((ViewGroup) view).getChildCount(); i++){
                View innerView = ((ViewGroup) view).getChildAt(i);
                UpdateUI(innerView,mContext);
            }
        }
    }
}
