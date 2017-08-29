package com.nciebt.zyj.presenter;


import com.nciebt.zyj.presenter.contract.BaseView;

/**
 * 类名称：BasePresenter
 * 类描述：p层的父类
 * 创建人：ghl
 * 创建时间：2017/4/6 14:49
 * 修改人：ghl
 * 修改时间：2017/4/6 14:49
 *
 * @version v1.0
 */

public abstract class BasePresenter<M, V extends BaseView> {
    private M mM;
    private V mV;

    public BasePresenter(M m, V v) {
        mM = m;
        mV = v;
    }

    void onAttach(V view) {
        mV = view;
    }

    void onDetch(V view) {
        mV = null;
    }

}
