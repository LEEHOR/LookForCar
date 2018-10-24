package com.cyht.wykc.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.cyht.wykc.R;


public class ShareDialog extends Dialog {

    public ShareDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static final int INDEX_WX = 0;
    public static final int INDEX_CYCLE = 1;
    public static final int INDEX_QQ = 2;
    public static final int INDEX_SPACE = 3;
    public static final int INDEX_Wb = 4;

    public static class Params {
        private View.OnClickListener cancelListener;
        private Context context;
        private OnShareIndexClickListener indexListener;
    }

    public static class Builder{
        private boolean canCancel = true;
        private boolean shadow = true;
        private final Params p;

        public Builder(Context context) {
            p = new Params();
            p.context = context;
        }

        public Builder setCanCancel(boolean canCancel) {
            this.canCancel = canCancel;
            return this;
        }

        public Builder setShadow(boolean shadow) {
            this.shadow = shadow;
            return this;
        }


        public Builder setCancelListener(View.OnClickListener cancelListener) {
            p.cancelListener = cancelListener;
            return this;
        }

        public Builder setShareIndexClickListener(OnShareIndexClickListener indexListener) {
            p.indexListener = indexListener;
            return this;
        }

        public ShareDialog create() {
            final ShareDialog dialog = new ShareDialog(p.context, shadow ? R.style.Theme_Light_NoTitle_Dialog : R.style.Theme_Light_NoTitle_NoShadow_Dialog);
            Window window = dialog.getWindow();
            window.setWindowAnimations(R.style.Animation_Bottom_Rising);

            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
            window.setGravity(Gravity.BOTTOM);

            View view = LayoutInflater.from(p.context).inflate(R.layout.share_dialog, null);
            ImageView btnWx = (ImageView) view.findViewById(R.id.share_btn_wx);
            ImageView btnCycle = (ImageView) view.findViewById(R.id.share_btn_cycle);
            ImageView btnQq = (ImageView) view.findViewById(R.id.share_btn_qq);
            ImageView btnSpace = (ImageView) view.findViewById(R.id.share_btn_space);
            ImageView btnWb = (ImageView) view.findViewById(R.id.share_btn_wb);
            btnWx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (p.indexListener != null){
                        p.indexListener.onClick(INDEX_WX);
                    }
                }
            });
            btnCycle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (p.indexListener != null){
                        p.indexListener.onClick(INDEX_CYCLE);
                    }
                }
            });
            btnQq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (p.indexListener != null){
                        p.indexListener.onClick(INDEX_QQ);
                    }
                }
            });
            btnSpace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (p.indexListener != null){
                        p.indexListener.onClick(INDEX_SPACE);
                    }
                }
            });
            btnWb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (p.indexListener != null){
                        p.indexListener.onClick(INDEX_Wb);
                    }
                }
            });

            Button btnCancel = (Button) view.findViewById(R.id.share_btn_cancel);
            if (p.cancelListener != null) {
                btnCancel.setOnClickListener(p.cancelListener);
            } else {
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }


            dialog.setContentView(view);
            dialog.setCanceledOnTouchOutside(canCancel);
            dialog.setCancelable(canCancel);
            dialog.show();
            return dialog;
        }
    }

    public interface OnShareIndexClickListener {
        void onClick(int index);
    }
}
