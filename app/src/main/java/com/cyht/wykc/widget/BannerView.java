package com.cyht.wykc.widget;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.baidu.platform.comapi.map.K;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cyht.wykc.R;
import com.cyht.wykc.mvp.contract.Interface.ItemClickListener;
import com.cyht.wykc.mvp.view.base.BaseApplication;
import com.cyht.wykc.utils.Imageloader;
import com.cyht.wykc.utils.NetworkUtils;
import com.socks.library.KLog;

import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


public class BannerView extends FrameLayout {

    private static final int MSG_LOOP = 1000;
    private static long LOOP_INTERVAL = 5000;
    private LinearLayout mLinearPosition = null;
    private ViewPager mViewPager = null;
    private BannerHandler mBannerHandler = null;

    private List<String> imglist;
    private int viewSize;
    private ItemClickListener itemClickListener;

    private static class BannerHandler extends Handler {
        private WeakReference<BannerView> weakReference = null;

        public BannerHandler(BannerView bannerView) {
            super(Looper.getMainLooper());
            this.weakReference = new WeakReference<BannerView>(bannerView);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (this.weakReference == null) {
                return;
            }
            BannerView bannerView = this.weakReference.get();
            if (bannerView == null || bannerView.mViewPager == null || bannerView.mViewPager.getAdapter() == null || bannerView.mViewPager.getAdapter().getCount() <= 0) {
                sendEmptyMessageDelayed(MSG_LOOP, LOOP_INTERVAL);
                return;
            }
            int curPos = bannerView.mViewPager.getCurrentItem();
            curPos = (curPos + 1) % bannerView.mViewPager.getAdapter().getCount();
            bannerView.mViewPager.setCurrentItem(curPos);
            sendEmptyMessageDelayed(MSG_LOOP, LOOP_INTERVAL);
        }
    }

    public BannerView(Context context) {
        super(context);
        init();
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void startLoop(boolean flag) {
        if (flag) {
            if (mBannerHandler == null) {
                mBannerHandler = new BannerHandler(this);
            }
            if (mBannerHandler.hasMessages(MSG_LOOP)) {
                mBannerHandler.removeMessages(MSG_LOOP);
            }
            mBannerHandler.sendEmptyMessageDelayed(MSG_LOOP, LOOP_INTERVAL);
            KLog.e("sendEmptyMessageDelayed");
        } else {
            if (mBannerHandler != null) {
                if (mBannerHandler.hasMessages(MSG_LOOP)) {
                    mBannerHandler.removeMessages(MSG_LOOP);
                }
            }
        }
    }

    private void init() {
        initViewPager();
        initLinearPosition();
        this.addView(mViewPager);
        this.addView(mLinearPosition);
    }

    private void initViewPager() {
        mViewPager = new ViewPager(getContext());
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        mViewPager.setLayoutParams(layoutParams);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                updateLinearPosition();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        if (mBannerHandler != null) {
                            if (mBannerHandler.hasMessages(MSG_LOOP)) {
                                mBannerHandler.removeMessages(MSG_LOOP);
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mBannerHandler != null) {
                            mBannerHandler.sendEmptyMessageDelayed(MSG_LOOP, LOOP_INTERVAL);
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void initLinearPosition() {
        mLinearPosition = new LinearLayout(getContext());
        mLinearPosition.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        layoutParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.dimen_9dp);
        mLinearPosition.setPadding(getResources().getDimensionPixelSize(R.dimen.dimen_9dp), 0, 0, 0);
        mLinearPosition.setLayoutParams(layoutParams);
    }

    public void setAdapter(PagerAdapter adapter) {
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);
        adapter.registerDataSetObserver(mDataObserver);
        updateLinearPosition();
    }

    private DataSetObserver mDataObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            updateLinearPosition();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
        }
    };

    private void updateLinearPosition() {
        if (imglist != null && imglist.size() != 0) {
            if (mLinearPosition.getChildCount() != viewSize) {
                int diffCnt = mLinearPosition.getChildCount() - viewSize;
                boolean needAdd = diffCnt < 0;
                diffCnt = Math.abs(diffCnt);
                for (int i = 0; i < diffCnt; i++) {
                    if (needAdd) {
                        ImageView img = new ImageView(getContext());
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.rightMargin = getResources().getDimensionPixelOffset(R.dimen.dimen_9dp);
                        img.setLayoutParams(layoutParams);
                        img.setBackgroundResource(R.mipmap.banner_point);
                        mLinearPosition.addView(img);
                    } else {
                        mLinearPosition.removeViewAt(0);
                    }
                }
            }
            int curPos = mViewPager.getCurrentItem();
            for (int i = 0; i < mLinearPosition.getChildCount(); i++) {
                if (i == (curPos % viewSize)) {
                    mLinearPosition.getChildAt(i).setBackgroundResource(R.mipmap.banner_point_select);
                } else {
                    mLinearPosition.getChildAt(i).setBackgroundResource(R.mipmap.banner_point);
                }
            }
        }
    }


    public BannerView setImgList(List<String> imglist) {
        this.imglist = imglist;
        viewSize = this.imglist.size();
        KLog.e(viewSize);
        BannerAdapter bannerAdapter = new BannerAdapter(this.imglist);
        setAdapter(bannerAdapter);
        return this;
    }

    public void setTransformAnim(boolean flag) {
        if (flag) {
            mViewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
                private static final float MIN_SCALE = 0.75f;

                @Override
                public void transformPage(View view, float position) {
                    int pageWidth = view.getWidth();
                    if (position < -1) { // [-Infinity,-1)
                        // This page is way off-screen to the left.
                        view.setRotation(0);

                    } else if (position <= 1) { // [-1,1]
                        // Modify the default slide transition to shrink the page as well
                        if (position < 0) {

                            float mRot = (20f * position);
                            view.setPivotX(view.getMeasuredWidth() * 0.5f);
                            view.setPivotY(view.getMeasuredHeight());
                            view.setRotation(mRot);
                        } else {

                            float mRot = (20f * position);
                            view.setPivotX(view.getMeasuredWidth() * 0.5f);
                            view.setPivotY(view.getMeasuredHeight());
                            view.setRotation(mRot);
                        }

                        // Scale the page down (between MIN_SCALE and 1)

                        // Fade the page relative to its size.

                    } else { // (1,+Infinity]
                        // This page is way off-screen to the right.
                        view.setRotation(0);
                    }
                }
            });
        }
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public static void setLoopInterval(long loopInterval) {
        LOOP_INTERVAL = loopInterval;
    }


    public class BannerAdapter extends PagerAdapter {
        private List<String> imgList1;
        private int size;
        private final int cacheCount = 3;
        private List<ImageView> imageViews = new ArrayList<ImageView>();

        public BannerAdapter(List<String> viewList) {
            imgList1 = viewList;
            size = imgList1.size();
            for (int i = 0; i < size; i++) {
                ImageView image = new ImageView(BaseApplication.mContext);
                image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                //设置显示格式
                image.setScaleType(ImageView.ScaleType.FIT_XY);
                Imageloader.loadImage(imgList1.get(i), image, Imageloader.NO_DEFAULT);
                final int finalI = i;
                image.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemClickListener.onclick(finalI);
                    }
                });

                imageViews.add(image);
            }
        }

        // PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (size > cacheCount) {
                KLog.e("destroyItem", position + "");
                container.removeView(imageViews.get(position % size));
            }
        }

        // 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            KLog.e("instantiateItem", position + "");
            ViewGroup parent = (ViewGroup) imageViews.get(position % size).getParent();
            if (parent != null) {
                parent.removeView(imageViews.get(position % size));
            }
            container.addView(imageViews.get(position % size));
            return imageViews.get(position % size);

        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }


}
