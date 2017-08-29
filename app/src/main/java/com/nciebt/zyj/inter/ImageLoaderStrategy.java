package com.nciebt.zyj.inter;

import android.view.View;

import com.nciebt.zyj.common.config.ImageLoaderOptions;

/**
 * 类名称：ImageLoaderStrategy
 * 类描述：glide 显示接口
 * 创建人：ghl
 * 创建时间：2017/4/28 14:41
 * 修改人：ghl
 * 修改时间：2017/4/28 14:41
 *
 * @version v1.0
 */
public interface ImageLoaderStrategy {
    void showImage(View view, String url, ImageLoaderOptions options);// 显示网络图片

    void showImage(View view, int drawable, ImageLoaderOptions options);// 显示默认图片

}
