package com.nciebt.zyj.utils;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nciebt.zyj.common.config.ImageLoaderOptions;
import com.nciebt.zyj.inter.ImageLoaderStrategy;

/**
 * 类名称：GlideImageLoaderStrategy
 * 类描述：图片加载实现工具类
 * 创建人：ghl
 * 创建时间：2017/4/28 14:55
 * 修改人：ghl
 * 修改时间：2017/4/28 14:55
 *
 * @version v1.0
 */

public class GlideImageLoaderStrategy implements ImageLoaderStrategy {

    @Override
    public void showImage(View v, String url, ImageLoaderOptions options) {
        if (v instanceof ImageView) {
            // 将类型转换为ImageView
            ImageView imageView = (ImageView) v;
            // 配置基本的参数
            DrawableTypeRequest<String> dtr = Glide
                    .with(imageView.getContext()).load(url);
            // 配置附加参数
            loadOptions(dtr, options).diskCacheStrategy(DiskCacheStrategy.RESULT).fitCenter().into(imageView);
        }
    }

    @Override
    public void showImage(View v, int drawable, ImageLoaderOptions options) {
        if (v instanceof ImageView) {
            ImageView imageView = (ImageView) v;
            DrawableTypeRequest<Integer> dtr = Glide.with(
                    imageView.getContext()).load(drawable);
            loadOptions(dtr, options).diskCacheStrategy(DiskCacheStrategy.RESULT).fitCenter().into(imageView);
        }
    }

    // 这个方法用来装载由外部设置的参数
    private DrawableTypeRequest<?> loadOptions(DrawableTypeRequest<?> dtr,
                                               ImageLoaderOptions options) {
        if (options == null) {
            return dtr;
        }
        if (options.getPlaceHolder() != -1) {
            dtr.placeholder(options.getPlaceHolder());
        }
        if (options.getErrorDrawable() != -1) {
            dtr.error(options.getErrorDrawable());
        }
        if (options.isCrossFade()) {
            dtr.crossFade();
        }
        if (options.isSkipMemoryCache()) {
            dtr.skipMemoryCache(options.isSkipMemoryCache());
        }
        if (options.getAnimator() != null) {
            dtr.animate(options.getAnimator());
        }
        if (options.getSize() != null) {
            dtr.override(options.getSize().reWidth, options.getSize().reHeight);
        }
        if (options.getDiskCacheStrategy() != -1) {
            options.setDiskCacheStrategy(options.getDiskCacheStrategy());
        }
        return dtr;
    }
}
