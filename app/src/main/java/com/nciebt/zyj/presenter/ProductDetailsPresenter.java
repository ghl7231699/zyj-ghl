package com.nciebt.zyj.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;

import com.nciebt.zyj.common.config.RemindMessage;
import com.nciebt.zyj.data.module.DetailModule;
import com.nciebt.zyj.entity.FileDetails;
import com.nciebt.zyj.inter.PermissonListener;
import com.nciebt.zyj.inter.UpdateProductListener;
import com.nciebt.zyj.presenter.contract.AppContract;
import com.nciebt.zyj.utils.DialogUtils;
import com.nciebt.zyj.utils.LoadDataUtils;
import com.nciebt.zyj.utils.NetworkUtils;
import com.nciebt.zyj.utils.PermissionUtil;
import com.nciebt.zyj.utils.StringUtils;

import org.litepal.crud.DataSupport;

import java.util.List;

import io.reactivex.Observable;

import static com.nciebt.zyj.inter.FinalInter.PRODUCT_FILE_NAME;

/**
 * 类名称：ProductDetailsPresenter
 * 类描述：产品详情presenter（包括Pdf、Ppt、Video、Flash）
 * 创建人：ghl
 * 创建时间：2017/5/24 13:51
 * 修改人：ghl
 * 修改时间：2017/5/24 13:51
 *
 * @version v1.0
 */

public class ProductDetailsPresenter implements AppContract.ProductPresenter {
    private AppContract.ProductView mView;
    private FileDetails mDetails;

    public ProductDetailsPresenter(AppContract.ProductView view, FileDetails details) {
        mView = view;
        mView.setPresenter(this);
        mDetails = details;
    }

    @Override
    public void LoadProduct() {
        final String title = StringUtils.splitFileName(mDetails.getTitle(), "/");
        final String id = mDetails.getDirectoriesId();
        String type = mDetails.getType();
        String content = mDetails.getContent();
        // 获取文件保存的后缀
        PermissionUtil.requestPermission((Activity) mView, new PermissonListener() {
            @Override
            public void onGranted() {
                DetailModule module = new DetailModule(id, new UpdateProductListener() {
                    @Override
                    public void noDownLoading(String path) {
                        LoadDataUtils.showDetailData(path, mView);
                    }

                    @Override
                    public void loadFailed() {
                        ((Activity) mView).finish();
                    }

                    @Override
                    public void obtainProductList(Observable<? extends List<? extends DataSupport>> o) {

                    }

                    @Override
                    public void DownLoading() {
                        boolean g = NetworkUtils.is3G((Activity) mView);
                        if (g) {
                            //下载
                            DialogUtils.showTwoBtnDialog("警告", "您正在使用非wifi网络，下载将产生流量费用", "流量够用", "取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    LoadDataUtils.LoadRemoteData(null, PRODUCT_FILE_NAME, id, mView);
                                }
                            }, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    ((Activity) mView).finish();
                                }
                            });
                        } else
                            LoadDataUtils.LoadRemoteData(null, PRODUCT_FILE_NAME, id, mView);
                    }
                });
                module.loadDetailData();
            }

            @Override
            public void onDenied() {
                mView.showError(RemindMessage.PERMISSION_DENIED_MESSAGE);
            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }
}
