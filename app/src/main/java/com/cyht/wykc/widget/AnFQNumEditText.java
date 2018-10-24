package com.cyht.wykc.widget;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cyht.wykc.R;
import com.cyht.wykc.mvp.contract.Interface.OnTextInputListener;

/**
 * AnFQ
 *
 * @time 2016-10-09 14:50
 */

public class AnFQNumEditText extends RelativeLayout {
    //类型1(单数类型)：TextView显示总字数，然后根据输入递减.例：100，99，98
    //类型2(百分比类型)：TextView显示总字数和当前输入的字数，例：0/100，1/100，2/100
    public static final String SINGULAR = "Singular";//类型1(单数类型)
    public static final String PERCENTAGE = "Percentage";//类型2(百分比类型)
    private EditText etContent;//文本框
    private TextView tvNum;//字数显示TextView
    private View vLine;//底部横线
    private String TYPES = SINGULAR;//类型
    private int MaxNum = 100;//最大字符

    private OnTextInputListener onTextInputListener;

    public AnFQNumEditText(Context context) {
        this(context, null);
    }

    public AnFQNumEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.anfq_num_edittext, this, true);
        etContent = (EditText) findViewById(R.id.etContent);
        tvNum = (TextView) findViewById(R.id.tvNum);
        vLine = findViewById(R.id.vLine);
    }

    public String getText() {
        return etContent.getText().toString();
    }

    /**
     * 设置显示
     *
     * @return
     */
    public AnFQNumEditText show() {
        if (TYPES.equals(SINGULAR)) {//类型1
            tvNum.setText(String.valueOf(MaxNum));
        } else if (TYPES.equals(PERCENTAGE)) {//类型2
            tvNum.setText(0 + "/" + MaxNum);
        }
        //设置长度
        etContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MaxNum)});
        //监听输入
        etContent.addTextChangedListener(mTextWatcher);
        return this;
    }

    /**
     * 设置横线颜色
     *
     * @param color --颜色值
     * @return
     */
    public AnFQNumEditText setLineColor(int color) {
        vLine.setBackgroundColor(color);
        return this;
    }

    public AnFQNumEditText setCountColor(int color) {
        tvNum.setTextColor(color);
        return this;
    }

    public AnFQNumEditText setTextSize(int size) {
        etContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        return this;
    }

    public AnFQNumEditText setTextColor(int color) {
        etContent.setTextColor(color);
        return this;
    }

    /**
     * 设置类型
     *
     * @param type --类型
     * @return
     */
    public AnFQNumEditText setType(String type) {
        TYPES = type;
        return this;
    }

    /**
     * 设置最大字数
     *
     * @param num --字数
     * @return
     */
    public AnFQNumEditText setLength(int num) {
        this.MaxNum = num;
        return this;
    }

    /**
     * 设置文本框的Hint
     *
     * @param str --设置内容
     * @return
     */
    public AnFQNumEditText setEtHint(String str) {
        etContent.setHint(str);
        return this;
    }

    /**
     * 设置文本框的最小高度
     *
     * @param px --最小高度(单位px)
     * @return
     */
    public AnFQNumEditText setEtMinHeight(int px) {
        etContent.setMinHeight(px);
        return this;
    }


    private TextWatcher mTextWatcher = new TextWatcher() {
        private int editStart;
        private int editEnd;

        public void afterTextChanged(Editable s) {
            editStart = etContent.getSelectionStart();
            editEnd = etContent.getSelectionEnd();
            // 先去掉监听器，否则会出现栈溢出
            etContent.removeTextChangedListener(mTextWatcher);
            // 注意这里只能每次都对整个EditText的内容求长度，不能对删除的单个字符求长度
            // 因为是中英文混合，单个字符而言，calculateLength函数都会返回1
            while (calculateLength(s.toString()) > MaxNum) { // 当输入字符个数超过限制的大小时，进行截断操作
                s.delete(editStart - 1, editEnd);
                editStart--;
                editEnd--;
            }
            onTextInputListener.onInput(s.toString());
            // 恢复监听器
            etContent.addTextChangedListener(mTextWatcher);
            setLeftCount();
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    };


    public void setOnTextInputListener(OnTextInputListener onTextInputListener) {
        this.onTextInputListener = onTextInputListener;
    }

    /**
     * 刷新剩余输入字数
     */
    private void setLeftCount() {
        if (TYPES.equals(SINGULAR)) {//类型1
            tvNum.setText(String.valueOf((MaxNum - getInputCount())));
        } else if (TYPES.equals(PERCENTAGE)) {//类型2
            tvNum.setText(MaxNum - (MaxNum - getInputCount()) + "/" + MaxNum);
        }

    }

    /**
     * 获取用户输入内容字数
     */
    private long getInputCount() {
        return calculateLength(etContent.getText().toString());
    }

    /**
     * 计算分享内容的字数，一个汉字=两个英文字母，一个中文标点=两个英文标点
     * 注意：该函数的不适用于对单个字符进行计算，因为单个字符四舍五入后都是1
     *
     * @param cs
     * @return
     */
    public static long calculateLength(CharSequence cs) {
        double len = 0;
        for (int i = 0; i < cs.length(); i++) {
            int tmp = (int) cs.charAt(i);
            if (tmp > 0 && tmp < 127) {
                len += 1;
            } else {
                len++;
            }
        }
        return Math.round(len);
    }
}
