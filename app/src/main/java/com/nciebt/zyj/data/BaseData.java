package com.nciebt.zyj.data;

/**
 * 类名称：BaseData
 * 类描述：TODO (所有数据基类)
 * 创建人：xs
 * 创建时间：2017/4/14 16:04
 * 修改人：xs
 * 修改时间：2017/4/14 16:04
 * @version v1.0
 */

public abstract class BaseData {

    public BaseData()
    {
        init();
    }
    /**
     *初始化
     **/
    public abstract void init();
}
