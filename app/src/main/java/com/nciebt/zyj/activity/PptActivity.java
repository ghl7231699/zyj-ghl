package com.nciebt.zyj.activity;

import android.os.Bundle;

import com.itsrts.pptviewer.PPTViewer;
import com.lzy.okhttputils.OkHttpUtils;
import com.nciebt.zyj.R;
import com.nciebt.zyj.activity.base.BaseActivity;
import com.nciebt.zyj.entity.FileDetails;
import com.nciebt.zyj.presenter.ProductDetailsPresenter;
import com.nciebt.zyj.presenter.contract.AppContract;
import com.nciebt.zyj.utils.DialogUtils;
import com.nciebt.zyj.utils.ToastUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类名称：PptActivity
 * 类描述：ppt详情
 * 创建人：ghl
 * 创建时间：2017/4/13 9:14
 * 修改人：ghl
 * 修改时间：2017/4/13 9:14
 *
 * @version v1.0
 */

public class PptActivity extends BaseActivity implements AppContract.ProductView {

    @BindView(R.id.ppt_activity_content)
    PPTViewer mPPTViewer;
    private AppContract.ProductPresenter mPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.ebt_zyj_ppt_activity_layout;
    }

    @Override
    public void initData() {
        Bundle key = getIntent().getExtras();
        if (key != null) {
            FileDetails f = (FileDetails) key.getSerializable("key");
            new ProductDetailsPresenter(this, f);
            mPresenter.LoadProduct();
        }
    }

    @OnClick(R.id.ppt_activity_content_fab)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void setPresenter(AppContract.ProductPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showError(String s) {
        ToastUtils.toast(s);
    }

    @Override
    public void showLoading() {
        DialogUtils.showPleaseWaitDialog();
    }

    @Override
    public void loadDetailContent(File file, String s) {
        mPPTViewer.setNext_img(R.drawable.next).setPrev_img(R.drawable.prev)
                .setSettings_img(R.drawable.settings)
                .setZoomin_img(R.drawable.zoomin)
                .setZoomout_img(R.drawable.zoomout);
        mPPTViewer.loadPPT(this, s);
    }

    @Override
    public void disMissLoading() {
        DialogUtils.dismissHProgressDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
