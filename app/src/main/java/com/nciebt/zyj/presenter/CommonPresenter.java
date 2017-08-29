package com.nciebt.zyj.presenter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.nciebt.zyj.common.config.RemindMessage;
import com.nciebt.zyj.data.module.CommonModule;
import com.nciebt.zyj.entity.FileDetails;
import com.nciebt.zyj.inter.PermissonListener;
import com.nciebt.zyj.presenter.contract.AppContract;
import com.nciebt.zyj.utils.PermissionUtil;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * 类名称：CommonPresenter
 * 类描述：常用界面presenter
 * 创建人：ghl
 * 创建时间：2017/4/24 15:16
 * 修改人：ghl
 * 修改时间：2017/4/24 15:16
 *
 * @version v1.0
 */

public class CommonPresenter implements AppContract.AddProductPresenter {
    private AppContract.AddProductView mView;
    private Activity mContext;

    public CommonPresenter(AppContract.AddProductView view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void LoadProduct() {
//        mView.showLoading();
        if (mView instanceof Fragment) {
            mContext = ((Fragment) mView).getActivity();
        } else if (mView instanceof Activity) {
            mContext = (Activity) mView;
        }
        PermissionUtil.requestPermission(mContext, new PermissonListener() {
            @Override
            public void onGranted() {
                getCommonData();
            }

            @Override
            public void onDenied() {
                mView.showError(RemindMessage.PERMISSION_DENIED_MESSAGE);
                mView.disMissLoading();
            }
        }, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

    }

    /**
     * 获取常用界面数据，当添加产品时通知数据刷新
     */
    private void getCommonData() {
        new CommonModule()
                .readRecords()
                .subscribe(new Consumer<List<FileDetails>>() {
                    @Override
                    public void accept(@NonNull List<FileDetails> fileDetailses) throws Exception {
                        FileDetails f = new FileDetails("", "ADD");
                        fileDetailses.add(f);
                        mView.loadAll(fileDetailses);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.e("CommonPresenter", "accept: " + throwable.getMessage());
                    }
                });
    }
}
