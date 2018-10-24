package com.cyht.wykc.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.cyht.wykc.mvp.contract.Interface.OnPositionClickListener;

public class Tab1_2 extends LinearLayout {

	private Button btnLeft;
	private Button btnRight;
	private int mainColor = 0xFFFA94B4;
	private int textColor = 0xFFFFFFFF;
	protected int which = 0;
	private Context context;
	public Tab1_2(Context context) {
		this(context,null);
	}

	@SuppressWarnings("deprecation")
	public Tab1_2(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
//		this.setBackgroundColor(mainColor);
//		this.setPadding(dip2px(7), dip2px(7), dip2px(7), dip2px(7));
		
		LinearLayout container = new LinearLayout(context);
		LayoutParams lpContainer = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//		container.setBackgroundResource(getResources().getIdentifier("mtab1_2_table_bg", "drawable", context.getPackageName()));
		container.setOrientation(LinearLayout.HORIZONTAL);
		this.addView(container, lpContainer);
		
		btnLeft = new Button(context);
		LayoutParams lpLeft = new LayoutParams(0, LayoutParams.MATCH_PARENT,1.0f);
//		btnLeft.setBackgroundResource(getResources().getIdentifier("mtab1_2_table_selected", "drawable", context.getPackageName()));
		btnLeft.setPadding(0,0,0,0);
		btnLeft.setBackgroundResource(getResources().getIdentifier("tab_car_first_selector", "drawable", context.getPackageName()));
//		btnLeft.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//		btnLeft.setTextColor(mainColor);
		container.addView(btnLeft, lpLeft);
		
		btnRight = new Button(context);
		LayoutParams lpRight = new LayoutParams(0, LayoutParams.MATCH_PARENT,1.0f);
//		btnRight.setBackgroundDrawable(null);
		btnRight.setPadding(0,0,0,0);
		btnRight.setBackgroundResource(getResources().getIdentifier("tab_car_second_selector", "drawable", context.getPackageName()));
//		btnRight.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//		btnRight.setTextColor(textColor);
		container.addView(btnRight, lpRight);
		
		btnLeft.setOnClickListener(clickListener);
		btnRight.setOnClickListener(clickListener);
	}
	
	/**
	 * 设置内容
	 * @param leftContent 左边内容
	 * @param rightContent 右边内容
	 */
	public void setContent(String leftContent, String rightContent){
		btnLeft.setText(leftContent);
		btnRight.setText(rightContent);
	}
	
	/**
	 * 设置内容
	 * @param leftContent 左边内容
	 * @param rightContent 右边内容
	 * @param textSize 文字大小
	 */
	public void setContent(String leftContent, String rightContent, int textSize, int textColor, int mainColor){
		
		setContent(leftContent, rightContent);
		setTextSize(textSize);
		
		this.mainColor = mainColor;
		this.textColor = textColor;
		this.setBackgroundColor(mainColor);
		setSelection(which);
	}
	
	public void setTextSize(int textSize){
		btnLeft.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
		btnRight.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
	}
	
	/**
	 * 设置默认颜色
	 * @param color
	 */
	public void setTextColor(int color){
		textColor = color;
		setSelection(which);
	}
	
	/**
	 * 设置主调色
	 * @param mainColor
	 */
	public void setMainColor(int mainColor){
		this.mainColor = mainColor;
		this.setBackgroundColor(mainColor);
		setSelection(which);
	}
	
	/**
	 * 设置默认选中的条目 
	 * @param position
	 */
	public void setSelection(int position) {
		this.which = position % 2;
		setBtnBackgroup(which);
	}

	/**
	 * 设置即时选中条目
	 */
	public  void setSelected(int positon)
	{
		this.which = positon % 2;
		onPositionClickListener.setOnPositionClick(which);
		setBtnBackgroup(which);
	}

	OnClickListener clickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (v == btnLeft) {
				which = 0;
			}else if (v == btnRight) {
				which = 1;
			}
			if (onPositionClickListener != null) {
				onPositionClickListener.setOnPositionClick(which);
			}
			setBtnBackgroup(which);
		}
	};
	
	@SuppressWarnings("deprecation")
	protected void setBtnBackgroup(int position) {
		if (position == 0) {
//			btnLeft.setTextColor(mainColor);
//			btnLeft.setBackgroundResource(getResources().getIdentifier("mtab1_2_table_selected", "drawable", context.getPackageName()));
//			btnRight.setTextColor(textColor);
//			btnRight.setBackgroundDrawable(null);
			btnLeft.setSelected(true);
			btnRight.setSelected(false);
		}else if (position == 1) {
//			btnRight.setTextColor(mainColor);
//			btnRight.setBackgroundResource(getResources().getIdentifier("mtab1_2_table_selected", "drawable", context.getPackageName()));
//			btnLeft.setTextColor(textColor);
//			btnLeft.setBackgroundDrawable(null);
			btnLeft.setSelected(false);
			btnRight.setSelected(true);
		}
	}
	
	protected OnPositionClickListener onPositionClickListener;
	/**
	 * 根据坐标设置点击事件
	 * @param onPositionClickListener
	 */
	public void setOnPositionClickListener(OnPositionClickListener onPositionClickListener) {
		this.onPositionClickListener = onPositionClickListener;
	}

	
	private int dip2px(float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}
}
