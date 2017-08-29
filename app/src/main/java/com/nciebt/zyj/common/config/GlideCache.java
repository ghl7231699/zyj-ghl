package com.nciebt.zyj.common.config;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;
import com.nciebt.zyj.App;

/**
 * 类名称：GlideCache
 * 类描述：glide自定义缓存
 * 创建人：ghl
 * 创建时间：2017/4/6 14:35
 * 修改人：ghl
 * 修改时间：2017/4/6 14:35
 *
 * @version v1.0
 */

public class GlideCache implements GlideModule {
    private static final String FILENAME = "/GlideCache/Image";

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // TODO Auto-generated method stub
        // 设置图片的显示格式ARGB_8888(指图片大小为32bit)
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        // 设置磁盘缓存目录
        String file = App.getLocalDataPath() + FILENAME;
        // 设置缓存的大小为100M
        int cacheSize100MegaBytes = 104857600;
        builder.setDiskCache(new DiskLruCacheFactory(file,
                cacheSize100MegaBytes));
    }

    @Override
    public void registerComponents(Context context, Glide builder) {
        // TODO Auto-generated method stub

    }

}
