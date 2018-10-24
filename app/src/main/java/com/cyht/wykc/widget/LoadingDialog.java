package com.cyht.wykc.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cyht.wykc.R;

/**
 * Author： hengzwd on 2018/1/5.
 * Email：hengzwdhengzwd@qq.com
 */

public class LoadingDialog extends Dialog {

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.loadingDialogStyle);
    }
    private LoadingDialog(Context context, int theme) {
        super(context, theme);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
//        tv = (TextView)this.findViewById(R.id.tv);
//        tv.setText("正在上传...");
//        LinearLayout linearLayout = (LinearLayout)this.findViewById(R.id.LinearLayout);
//        linearLayout.getBackground().setAlpha(210);
//        ProgressBar progressBar= (ProgressBar) findViewById(R.id.progressBar1);
//        progressBar.getBackground().setAlpha(100);

    }
}
