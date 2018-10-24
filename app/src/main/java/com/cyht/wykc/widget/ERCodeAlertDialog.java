package com.cyht.wykc.widget;


import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cyht.wykc.R;


public class ERCodeAlertDialog implements OnClickListener {
	private Context context;
	private Dialog dialog;
	private LinearLayout lLayout_bg;
	private ImageView iv_img;
	private ImageView btn_close;
	private Display display;

	public ERCodeAlertDialog(Context context) {
		this.context = context;
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		display = windowManager.getDefaultDisplay();
	}

	public ERCodeAlertDialog builder() {
		// 获取Dialog布局
		View view = LayoutInflater.from(context).inflate(
				R.layout.pop_code, null);

		// 获取自定义Dialog布局中的控件
		lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
		iv_img = (ImageView) view.findViewById(R.id.code_iv_img);
		btn_close = (ImageView) view.findViewById(R.id.code_btn_close);

		// 定义Dialog布局和参数
		dialog = new Dialog(context, R.style.AlertDialogStyle);
		dialog.setContentView(view);

		// 调整dialog背景大小
//		lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
//				.getWidth() * 0.85), LayoutParams.WRAP_CONTENT));
		lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
				.getWidth() * 0.65), (int) (display
				.getWidth() * 0.65 * 1.35)));

		btn_close.setOnClickListener(this);

		return this;
	}

	//按返回是否退出
	public ERCodeAlertDialog setCancelable(boolean cancel) {
		dialog.setCancelable(cancel);
		return this;
	}
	public ERCodeAlertDialog setCanceledOnTouchOutside(boolean cancel) {
		dialog.setCanceledOnTouchOutside(cancel);
		return this;
	}

	public ERCodeAlertDialog setImg(String url) {
		Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.RESULT).into(iv_img);
		return this;
	}



	public void show() {
		if (dialog != null && !dialog.isShowing()) {
			dialog.show();
		}
	}
	public void dismiss() {
		dialog.dismiss();
	}

	@Override
	public void onClick(View v) {
		dialog.dismiss();
	}
}
