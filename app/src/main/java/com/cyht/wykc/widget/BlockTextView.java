package com.cyht.wykc.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.cyht.wykc.R;

/**
 * Author： hengzwd on 2017/12/12.
 * Email：hengzwdhengzwd@qq.com
 */

public class BlockTextView extends TextView{

    private boolean flag = false;  //是否读秒

    private MsgHandler msgHandler;
    private static final int WAIT_SECONDS = 60;
    private int reciprocal_time;
//    private ClickListener clickListener;
    /**
     * View的显示区域。
     */
    final Rect bounds = new Rect();
    /**
     * 外部轮廓的颜色。
     */
    private int outLineColor = Color.parseColor("#A7A7A7");
    /**
     * 外部轮廓的宽度。
     */
    private int outLineWidth = 2;
    /**
     * 画笔。
     */
    private Paint mPaint = new Paint();


    public BlockTextView(Context context) {
        super(context);
        msgHandler = new MsgHandler(this);
        this.setText(R.string.verification_input);
//        setClick();
    }

    public BlockTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        msgHandler = new MsgHandler(this);
        this.setText(R.string.verification_input);
//        setClick();
    }

    public BlockTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        msgHandler = new MsgHandler(this);
        this.setText(R.string.verification_input);
//        setClick();

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BlockTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        msgHandler = new MsgHandler(this);
        this.setText(R.string.verification_input);
//        setClick();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取view的边界
        getDrawingRect(bounds);
        //画边框
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(outLineWidth);
        mPaint.setColor(outLineColor);
        canvas.drawRect(bounds.left, bounds.top, bounds.right, bounds.bottom, mPaint);
    }


    public class MsgHandler extends Handler {

        private BlockTextView blockTextView;

        public MsgHandler(BlockTextView blockTextView) {
            super(Looper.getMainLooper());
            this.blockTextView = blockTextView;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (flag && msg.what >= 0) {
                reciprocal_time--;
                blockTextView.setText(msg.what + "s");
                Message message = new Message();
                message.what = reciprocal_time;
                msgHandler.sendMessageDelayed(message, 1000);
            } else {
                blockTextView.stopGetCount();
            }
        }
    }


    public void startGetCount() {
        flag = true;
        reciprocal_time = WAIT_SECONDS;
        this.setClickable(false);
        Message msg = new Message();
        msg.what = reciprocal_time;
        msgHandler.sendMessage(msg);
    }

    public void stopGetCount() {
        flag = false;
        this.setClickable(true);
        this.setText(R.string.verification_input);
    }


//    private void setClick() {
//        this.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startGetCount();
//            }
//        });
//    }
}
