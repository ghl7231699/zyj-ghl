package com.nciebt.zyj.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;

import com.nciebt.zyj.common.config.RemindMessage;
import com.nciebt.zyj.common.httpurl.HttpUrls;
import com.nciebt.zyj.data.DataRepository;
import com.nciebt.zyj.entity.FileDetails;
import com.nciebt.zyj.presenter.contract.AppContract;

import java.io.File;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadStatus;

/**
 * 类名称：LoadDataUtils
 * 类描述：加载数据工具类
 * 创建人：ghl
 * 创建时间：2017/5/4 11:13
 * 修改人：ghl
 * 修改时间：2017/5/4 11:13
 *
 * @version v1.0
 */

public class LoadDataUtils {
    static Activity activity = null;

    /**
     * 加载本地数据
     *
     * @param s    加载文件路径
     * @param view view
     */
    public static void showDetailData(final String s, final AppContract.ProductView view) {
//        view.showLoading();
//        if (FileUtil.fileExits(s)) {
            view.loadDetailContent(new File(s), s);
            view.disMissLoading();
//        } else {
//            view.disMissLoading();
//            view.showError(RemindMessage.NO_CONTENT_MESSAGE);
//            if (view instanceof Activity) {
//                ((Activity) view).finish();
//            } else if (view instanceof Fragment) {
//                ((Fragment) view).getActivity().finish();
//            }
//        }
    }


    /**
     * 加载网络数据
     *
     * @param filePath 文件保存路径
     * @param fileName 文件保存名字
     * @param mView    view
     */
    public static void LoadRemoteData(final String url,
                                      final String filePath,
                                      final String fileName,
                                      final AppContract.ProductView mView) {
        if (mView instanceof Activity) {
            activity = (Activity) mView;
        } else if (mView instanceof Fragment) {
            activity = ((Fragment) mView).getActivity();
        }
        final boolean available = NetworkUtils.isNetworkAvailable(activity);
        DataRepository.readAllRecords(FileDetails.class, fileName)
                .subscribe(new Consumer<List<FileDetails>>() {
                    @Override
                    public void accept(@NonNull List<FileDetails> fileDetailses) throws Exception {
                        String id = null;
                        String directoriesId = null;
                        String suffix = null;
                        for (FileDetails f : fileDetailses
                                ) {
                            id = f.getContentId();
                            directoriesId = f.getDirectoriesId();
                            suffix = f.getContent();
                        }
                        if (available) {
                            downLoadDetail(url, filePath, mView, id, directoriesId, suffix);
                        } else
                            ToastUtils.toast("网络不可用");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mView.disMissLoading();
                        activity.finish();
                        ToastUtils.toast("未查询到数据");
                    }
                });

    }

    /**
     * 下载file内容
     *
     * @param url           下载url
     * @param filePath      保存路径
     * @param mView
     * @param directoriesId 归属id
     * @param df            文件后缀名称
     */
    private static void downLoadDetail(final String url, final String filePath,
                                       final AppContract.ProductView mView,
                                       final String id, final String directoriesId,
                                       final String df) {
        mView.showLoading();
        RxDownload.getInstance(activity)
                .defaultSavePath(filePath)
                .download(HttpUrls.urls)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DownloadStatus>() {
                    @Override
                    public void accept(@NonNull DownloadStatus downloadStatus) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        //download failed
                        mView.disMissLoading();
                        DialogUtils.showTwoBtnDialog("提示", "下载文件失败", "重试", "退出", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                downLoadDetail(url, filePath, mView, id, directoriesId, df);
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                activity.finish();
                            }
                        });
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        //downLoad success
                        mView.disMissLoading();
                        //保存路径到数据库
                        DataRepository.updateRecords(FileDetails.class, directoriesId, filePath + id + df);
                        //加载数据
                        loadLocalDetailData(filePath + id, mView);
                    }
                });
    }

    /**
     * 加载详情内容
     *
     * @param fileName 文件名称
     * @param mView    加载的view
     */
    private static void loadLocalDetailData(String fileName, AppContract.ProductView mView) {
        String s = fileName + ".mp4";
        if (FileUtil.fileExits(s)) {
            mView.loadDetailContent(new File(s), s);
            mView.disMissLoading();
        } else {
            mView.disMissLoading();
            mView.showError(RemindMessage.NO_CONTENT_MESSAGE);
            activity.finish();
        }
    }

}
