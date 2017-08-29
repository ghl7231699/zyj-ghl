package com.nciebt.zyj.inter;

import org.litepal.crud.DataSupport;

import java.util.List;

import io.reactivex.Observable;

/**
 * 类名称：UpdateProductListener
 * 类描述：检测产品是否更新
 * 创建人：ghl
 * 创建时间：2017/4/27 14:04
 * 修改人：ghl
 * 修改时间：2017/4/27 14:04
 *
 * @version v1.0
 */

public interface UpdateProductListener {
    void noDownLoading(String path);//是否需要下载

    void loadFailed();//加载失败

    void obtainProductList(Observable<? extends List<? extends DataSupport>> o);//获取产品清单

    void DownLoading();//下载产品


}
