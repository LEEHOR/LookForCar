package com.cyht.wykc.common;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;
import com.cyht.wykc.utils.StoreSpaceUtils;

import java.io.File;

/**
 * Author： hengzwd on 2017/10/12.
 * Email：hengzwdhengzwd@qq.com
 */

public class GlideCache implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //设置图片的显示格式ARGB_8888(指图片大小为32bit)
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        //设置磁盘缓存目录（和创建的缓存目录相同）
//        String storageDirectory = StoreSpaceUtils.getSDCardPath();
//        String downloadDirectoryPath = storageDirectory + "/GlideCache";
        //设置缓存的大小为100M
        int cacheSize = 100 * 1000 * 1000;
        builder.setDiskCache(new DiskLruCacheFactory(CYHTConstants.SAVE_DIR_GLIDE_CACHE, cacheSize));

    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
