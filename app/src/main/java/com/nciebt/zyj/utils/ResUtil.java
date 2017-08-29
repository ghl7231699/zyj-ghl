package com.nciebt.zyj.utils;

import android.content.res.Resources;
import android.text.TextUtils;

import com.nciebt.zyj.App;

/**
 * 类名称：ResUtil
 * 类描述：(资源加载类)
 * 创建人：xs
 * 创建时间：2017/4/28 9:36
 * 修改人：xs
 * 修改时间：2017/4/28 9:36
 * @version v1.0
 */
public  class ResUtil {


    public static int stringToDrawable(String drawable){
        if(TextUtils.isEmpty(drawable))
            return  0;
        Resources res = App.getContext().getResources();
        int n = res.getIdentifier(drawable, "drawable", App.getContext().getPackageName());
        return n;
    }
}
