package com.nciebt.zyj.activity;

import android.os.Bundle;

import com.nciebt.zyj.R;
import com.nciebt.zyj.activity.base.BaseActivity;
import com.nciebt.zyj.entity.FileDetails;
import com.nciebt.zyj.presenter.ProductDetailsPresenter;
import com.nciebt.zyj.presenter.contract.AppContract;
import com.nciebt.zyj.utils.DialogUtils;
import com.nciebt.zyj.utils.ToastUtils;

import java.io.File;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 类名称：VideoActivity
 * 类描述：视频播放
 * 创建人：ghl
 * 创建时间：2017/5/3 9:15
 * 修改人：ghl
 * 修改时间：2017/5/3 9:15
 *
 * @version v1.0
 */

public class VideoActivity extends BaseActivity implements AppContract.ProductView {

    @BindView(R.id.video_activity_content)
    JCVideoPlayerStandard mVideoPlayer;
    private AppContract.ProductPresenter mPresenter;
    FileDetails f;

    @Override
    public int getLayoutId() {
        return R.layout.ebt_zyj_video_activity_layout;
    }

    @Override
    public void initData() {
        Bundle key = getIntent().getExtras();
        if (key != null) {
            f = (FileDetails) key.getSerializable("key");
            new ProductDetailsPresenter(this, f);
            mPresenter.LoadProduct();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
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
        try {
            String[] split = s.split("/");
            mVideoPlayer.setUp(s, split[split.length - 1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void disMissLoading() {
        DialogUtils.dismissProgressDialog();
    }
}
