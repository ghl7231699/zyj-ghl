package com.nciebt.zyj.common.config;

import com.bumptech.glide.request.animation.ViewPropertyAnimation;

/**
 * 类名称：ImageLoaderOptions
 * 类描述：glide配置
 * 创建人：ghl
 * 创建时间：2017/4/28 14:40
 * 修改人：ghl
 * 修改时间：2017/4/28 14:40
 *
 * @version v1.0
 */

public class ImageLoaderOptions {
    private int placeHolder = -1; // 当没有成功加载的时候显示的图片
    private ImageReSize size = null; // 重新设定容器宽高
    private int errorDrawable = -1; // 加载错误的时候显示的drawable
    private boolean isCrossFade = false; // 是否渐变平滑的显示图片
    private int diskCacheStrategy = -1; // 设置缓存策略
    private boolean isSkipMemoryCache = false; // 是否跳过内存缓存
    private ViewPropertyAnimation.Animator animator = null; // 图片加载动画

    public ImageLoaderOptions(int placeHolder, int errorDrawable,
                              boolean isCrossFade, boolean isSkipMemoryCache,
                              ViewPropertyAnimation.Animator animator, int diskCacheStrategy) {
        this.placeHolder = placeHolder;
        this.errorDrawable = errorDrawable;
        this.isCrossFade = isCrossFade;
        this.isSkipMemoryCache = isSkipMemoryCache;
        this.animator = animator;
        this.diskCacheStrategy = diskCacheStrategy;
    }


    public int getDiskCacheStrategy() {
        return diskCacheStrategy;
    }

    public void setDiskCacheStrategy(int diskCacheStrategy) {
        this.diskCacheStrategy = diskCacheStrategy;
    }

    public int getPlaceHolder() {
        return placeHolder;
    }

    public ImageReSize getSize() {
        return size;
    }

    public int getErrorDrawable() {
        return errorDrawable;
    }

    public boolean isCrossFade() {
        return isCrossFade;
    }

    public boolean isSkipMemoryCache() {
        return isSkipMemoryCache;
    }

    public ViewPropertyAnimation.Animator getAnimator() {
        return animator;
    }

    public class ImageReSize {
        public int reWidth = 0;
        public int reHeight = 0;

        public ImageReSize(int reWidth, int reHeight) {
            if (reHeight <= 0) {
                reHeight = 0;
            }
            if (reWidth <= 0) {
                reWidth = 0;
            }
            this.reHeight = reHeight;
            this.reWidth = reWidth;

        }

    }
}
