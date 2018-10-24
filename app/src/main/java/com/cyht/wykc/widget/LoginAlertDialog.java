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
import android.widget.LinearLayout.LayoutParams;

import com.cyht.wykc.R;


public class LoginAlertDialog implements OnClickListener {
	private Context context;
	private Dialog dialog;
	private LinearLayout lLayout_bg;
	private ImageView btn_wx;
	private ImageView btn_qq;
	private ImageView btn_wb;
	private ImageView btn_close;
	private Display display;

	public LoginAlertDialog(Context context) {
		this.context = context;
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		display = windowManager.getDefaultDisplay();
	}

	public LoginAlertDialog builder() {
		// 获取Dialog布局
		View view = LayoutInflater.from(context).inflate(
				R.layout.pop_login, null);

		// 获取自定义Dialog布局中的控件
		lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
		btn_wx = (ImageView) view.findViewById(R.id.login_btn_wx);
		btn_qq = (ImageView) view.findViewById(R.id.login_btn_qq);
		btn_wb = (ImageView) view.findViewById(R.id.login_btn_wb);
		btn_close = (ImageView) view.findViewById(R.id.login_btn_close);

		// 定义Dialog布局和参数
		dialog = new Dialog(context, R.style.AlertDialogStyle);
		dialog.setContentView(view);

		// 调整dialog背景大小
		lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
				.getWidth() * 0.85), LayoutParams.WRAP_CONTENT));


		btn_close.setOnClickListener(this);

		return this;
	}


	//按返回是否退出
	public LoginAlertDialog setCancelable(boolean cancel) {
		dialog.setCancelable(cancel);
		return this;
	}
	public LoginAlertDialog setCanceledOnTouchOutside(boolean cancel) {
		dialog.setCanceledOnTouchOutside(cancel);
		return this;
	}


	public LoginAlertDialog setWXLogin(final OnClickListener listener) {
		btn_wx.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onClick(v);
				dialog.dismiss();
			}
		});
		return this;
	}

	public LoginAlertDialog setQQLogin(final OnClickListener listener) {
		btn_qq.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onClick(v);
				dialog.dismiss();
			}
		});
		return this;
	}

	public LoginAlertDialog setWBLogin(final OnClickListener listener) {
		btn_wb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onClick(v);
				dialog.dismiss();
			}
		});
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
