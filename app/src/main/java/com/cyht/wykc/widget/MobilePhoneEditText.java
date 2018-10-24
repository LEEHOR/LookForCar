package com.cyht.wykc.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;

/**
 * Author： hengzwd on 2017/12/14.
 * Email：hengzwdhengzwd@qq.com
 */

public class MobilePhoneEditText extends android.support.v7.widget.AppCompatEditText {

    private PhoneTextWathcer phoneTextWathcer;

    public MobilePhoneEditText(Context context) {
        super(context);
        init();
    }

    public MobilePhoneEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MobilePhoneEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        phoneTextWathcer = new PhoneTextWathcer(this);
        this.addTextChangedListener(phoneTextWathcer);
    }


    private class PhoneTextWathcer implements TextWatcher {

        MobilePhoneEditText editText;

        public PhoneTextWathcer(MobilePhoneEditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s == null || s.length() == 0) return;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < s.length(); i++) {
                if (i != 3 && i != 8 && s.charAt(i) == '-') {
                    continue;
                } else {
                    sb.append(s.charAt(i));
                    if ((sb.length() == 4 || sb.length() == 9) && sb.charAt(sb.length() - 1) != '-') {
                        sb.insert(sb.length() - 1, '-');
                    }
                }
            }
            if (!sb.toString().equals(s.toString())) {
                int index = start + 1;
                if (sb.charAt(start) == '-') {
                    if (before == 0) {
                        index++;
                    } else {
                        index--;
                    }
                } else {
                    if (before == 1) {
                        index--;
                    }
                }
                editText.setText(sb.toString());
                editText.setSelection(index);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    }




    public String getPhoneNumber() {
        String s = this.getText().toString().trim();
        StringBuffer sb= new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i)=='-') {
                continue;
            }else {
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }
}
