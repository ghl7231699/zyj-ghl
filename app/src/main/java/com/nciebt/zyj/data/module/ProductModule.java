package com.nciebt.zyj.data.module;

import com.nciebt.zyj.data.DataRepository;
import com.nciebt.zyj.inter.UpdateProductListener;

/**
 * 类名称：
 * 类描述：产品界面Module
 * 创建人：ghl
 * 创建时间：2017/4/26 10:48
 * 修改人：ghl
 * 修改时间：2017/4/26 10:48
 *
 * @version v1.0
 */

public class ProductModule {

    private UpdateProductListener mListener;
    private String type;
    private String query;
    private Class mClass;

    public ProductModule(Class mClass, String query, String t, UpdateProductListener listener) {
        this.query = query;
        mListener = listener;
        this.type = t;
        this.mClass = mClass;
    }

    public void handleProductData() {
        //获取网络数据或本地数据
        DataRepository.LoadAllList(mClass, query, type, mListener);
    }
}
