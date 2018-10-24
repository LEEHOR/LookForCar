package com.cyht.wykc.utils;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.cyht.wykc.R;
import com.cyht.wykc.mvp.view.base.BaseApplication;

/**
 * Author： hengzwd on 2017/10/11.
 * Email：hengzwdhengzwd@qq.com
 */

public class Imageloader {


    public static final int  NO_DEFAULT = 0;

    public static  void  loadImage(String path, final ImageView targetImage, final int errorResource)
    {
        Glide.with(BaseApplication.mContext).load(path).crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(new SimpleTarget<GlideDrawable>() {

            @Override
            public void onLoadStarted(Drawable placeholder) {
                super.onLoadStarted(placeholder);
            }

            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                targetImage.setImageDrawable(resource);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                if (errorResource!=NO_DEFAULT) {
                    targetImage.setImageDrawable(BaseApplication.mContext.getResources().getDrawable(errorResource));
                }
            }
        });
    }
}
