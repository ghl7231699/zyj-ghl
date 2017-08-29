package com.nciebt.zyj.presenter.contract;

/**
 * 类名称：BaseView
 * 类描述：MVP  view层基础接口
 * 创建人：ghl
 * 创建时间：2017/4/6 14:46
 * 修改人：ghl
 * 修改时间：2017/4/6 14:46
 *
 * @version v1.0
 */

public interface BaseView<T> {
    void setPresenter(T presenter);

    void showLoading();//展示加载框

    void disMissLoading();//取消加载框展示

    void showError(String s);
}
