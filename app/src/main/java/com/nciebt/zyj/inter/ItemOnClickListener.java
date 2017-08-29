package com.nciebt.zyj.inter;

import android.view.View;

/**
 * 类名称：
 * 类描述：点击事件监听（包括长按）
 * 创建人：ghl
 * 创建时间：2017/4/26 10:14
 * 修改人：ghl
 * 修改时间：2017/4/26 10:14
 *
 * @version v1.0
 */

public interface ItemOnClickListener {
    void onItemClick(View view, int position);//点击事件

    void onItemLongClick(View view, int position);//长按点击事件
}
