package com.nciebt.zyj.presenter;

import android.Manifest;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.nciebt.zyj.common.config.RemindMessage;
import com.nciebt.zyj.data.module.ProductModule;
import com.nciebt.zyj.entity.FileDetails;
import com.nciebt.zyj.inter.PermissonListener;
import com.nciebt.zyj.inter.UpdateProductListener;
import com.nciebt.zyj.presenter.contract.AppContract;
import com.nciebt.zyj.utils.PermissionUtil;
import com.nciebt.zyj.utils.ToastUtils;

import org.litepal.crud.DataSupport;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;


/**
 * 类名称：ProductPresenter
 * 类描述：产品界面 presenter
 * 创建人：ghl
 * 创建时间：2017/4/11 14:50
 * 修改人：ghl
 * 修改时间：2017/4/11 14:50
 *
 * @version v1.0
 */

public class ProductPresenter implements AppContract.ProductInfoPresenter {
    private AppContract.ProductInfoView mView;
    private String mInt;

    public ProductPresenter(String t, AppContract.ProductInfoView view) {
        this.mInt = t;
        mView = view;
        view.setPresenter(this);
    }

    @Override
    public void loadProductLists() {
        mView.showLoading();
        final FragmentActivity activity = ((Fragment) mView).getActivity();
        PermissionUtil.requestPermission(activity, new PermissonListener() {
            @Override
            public void onGranted() {
                ProductModule module = new ProductModule(FileDetails.class, "DIRECTORIESID = ?", mInt, new UpdateProductListener() {

                    @Override
                    public void noDownLoading(String path) {

                    }

                    @Override
                    public void loadFailed() {
                        activity.finish();
                    }

                    @Override
                    public void obtainProductList(Observable<? extends List<? extends DataSupport>> o) {
                        Observable<List<FileDetails>> observable = (Observable<List<FileDetails>>) o;
                        LoadSqlData(observable);
                    }


                    @Override
                    public void DownLoading() {
                    }
                });
                module.handleProductData();
            }

            @Override
            public void onDenied() {
                mView.showError(RemindMessage.PERMISSION_DENIED_MESSAGE);
            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    private void LoadSqlData(Observable<List<FileDetails>> o) {
        mView.disMissLoading();
        o.subscribe(new Consumer<List<FileDetails>>() {
            @Override
            public void accept(@NonNull List<FileDetails> list) throws Exception {
                mView.showProductLists(list);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                ToastUtils.toast(throwable.getMessage());
            }
        });
    }
}
